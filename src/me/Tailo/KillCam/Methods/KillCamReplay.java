package me.Tailo.KillCam.Methods;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;

import me.Tailo.KillCam.System.main;
import me.Tailo.KillCam.Utils.BloodEffectUtil;
import me.Tailo.KillCam.Utils.BossBarUtil;
import me.Tailo.KillCam.Utils.EntityIdUtil;
import me.Tailo.KillCam.Utils.NPCUtils;
import me.Tailo.KillCam.Utils.PacketUtil;
import me.Tailo.KillCam.Utils.Reflections;
import me.Tailo.KillCam.Utils.TickUtil;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class KillCamReplay {

	public static void playKillCam(Player p, Player killer) {

		BloodEffectUtil.addBloodEffect(p);

		ItemStack kill = killer.getItemInHand();
		
		ArrayList<Integer> npcs = new ArrayList<>();
		Set<Player> hidden = p.spigot().getHiddenPlayers();
		ArrayList<Player> cansee = new ArrayList<>();

		for(Player players : Bukkit.getOnlinePlayers()) {
			
			EntityIdUtil idutil = EntityIdUtil.getEntityIdUtil(players.getEntityId());
			NPCUtils.addPlayer(p, players, idutil.fakeid);
			npcs.add(idutil.fakeid);
			
			if(players.canSee(p)) cansee.add(players);
			p.hidePlayer(players);
			players.hidePlayer(p);
			
		}
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 3, true, false));

		Bukkit.getScheduler().scheduleSyncDelayedTask(main.instance, new Runnable() {
			
			@Override
			public void run() {
				
				Executors.newCachedThreadPool().execute(new Runnable() {
					
					@Override
					public void run() {
						
						KillCamRecorder r = KillCamRecorder.getKillCamRecorder(killer);

						HashMap<Long, ArrayList<Packet<?>>> packets = KillCamReplay.setupPackets(r);

						ItemStack inhand = p.getInventory().getItem(0);
						p.getInventory().setItem(0, kill);

						EntityIdUtil idutil = EntityIdUtil.getEntityIdUtil(killer.getEntityId());
						NPCUtils.setCamera(p, idutil.fakeid);

						HashMap<Long, Location> moves = new HashMap<>();
						moves.putAll(r.moves);

						KillCamReplay.sendPreBlocks(p);

						BossBarUtil.sendBossBar(p);
						
						if(r != null) {
							
							new BukkitRunnable() {
								
								long max = TickUtil.getTickUtil(p).getTick();
								long tick = TickUtil.getTickUtil(p).getTick() - 200;
								
								@Override
								public void run() {

									KillCamReplay.setBossBar(p, tick, max);

									p.getInventory().setHeldItemSlot(0);

									if(packets.containsKey(tick)) {
										for (Packet<?> packet : packets.get(tick)) {
											sendPacket(p, packet);
										}
									}

									if(r.moves.containsKey(tick)) {
										NPCUtils.setLocation(p, idutil.fakeid, r.moves.get(tick));
									}

									if(r.itemchanges.containsKey(tick)) {
										p.getInventory().setItem(0,	r.itemchanges.get(tick));
									}

									if(tick >= max) {
										
										BossBarUtil.removeBossBar(p);

										p.getInventory().setItem(0, inhand);

										BloodEffectUtil.removeBloodEffect(p);
										
										for(int id : npcs) {
											NPCUtils.removePlayer(p, id);
										}
										
										NPCUtils.setCamera(p, p.getEntityId());
									
										for(Player players : Bukkit.getOnlinePlayers()) {
											if(!hidden.contains(players)) {
												p.showPlayer(players);
											}
										}
										
										for(Player players : cansee) {
											players.showPlayer(p);
										}

										cancel();

										Bukkit.getScheduler().scheduleSyncDelayedTask(main.instance, new Runnable() {
											
											@Override
											public void run() {
												
												p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 3, true, false));
												
											}
										}, 20L);
										
									}

									tick += 1;
									
								}
							}.runTaskTimer(main.instance, 0, 1);
							
						}
						
					}
				});
				
			}
		}, 40L);
		
	}

	private static HashMap<Long, ArrayList<Packet<?>>> setupPackets(KillCamRecorder r) {

		HashMap<Long, ArrayList<Packet<?>>> packets = new HashMap<>();
		packets.putAll(r.packets);
		
		HashMap<Long, ArrayList<Packet<?>>> newpackets = new HashMap<>();

		try {
			for(long tick : packets.keySet()) {

				ArrayList<Packet<?>> packetlist = new ArrayList<>();

				for(Packet<?> packet : packets.get(tick)) {

					if(PacketUtil.idpackets.contains(packet.getClass().getSimpleName())) {

						packetlist.add(modifyEnityId(packet));

					} else {

						packetlist.add(packet);

					}

				}

				newpackets.put(tick, packetlist);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return newpackets;

	}

	@SuppressWarnings("deprecation")
	private static void sendPreBlocks(Player p) {

		for(Block b : BlockRecorder.preblocks.keySet()) {

			if(BlockRecorder.preblocks.get(b) == BlockRecorder.BlockEvent.PLACE) {
				p.sendBlockChange(b.getLocation(), Material.AIR, (byte) 0);
			} else {
				p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
			}

		}

	}

	private static Packet<?> modifyEnityId(Packet<?> packet) {
		try {
			if(packet.getClass().getSimpleName().equals("PacketPlayOutEntityDestroy")) {

				int[] realid = (int[]) Reflections.getValue(packet, "a");
				EntityIdUtil idutil = EntityIdUtil.getEntityIdUtil(realid[0]);
				realid[0] = idutil.fakeid;
				Reflections.setValue(packet, "a", realid);

			} else if(packet.getClass().getSimpleName().equals("PacketPlayOutRelEntityMove") || packet.getClass().getSimpleName().equals("PacketPlayOutRelEntityMoveLook") || packet.getClass().getSimpleName().equals("PacketPlayOutEntityLook")) {

				int realid = (int) getSuperClassValue(packet, "a");
				EntityIdUtil idutil = EntityIdUtil.getEntityIdUtil(realid);
				setSuperClassValue(packet, "a", idutil.fakeid);

			} else {

				int realid = (int) Reflections.getValue(packet, "a");
				EntityIdUtil idutil = EntityIdUtil.getEntityIdUtil(realid);
				Reflections.setValue(packet, "a", idutil.fakeid);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return packet;
	}

	private static void setSuperClassValue(Object clazz, String field, Object value) {
		try {
			Field f = clazz.getClass().getSuperclass().getDeclaredField(field);
			f.setAccessible(true);
			f.set(clazz, value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static Object getSuperClassValue(Object clazz, String field) {
		try {
			Field f = clazz.getClass().getSuperclass().getDeclaredField(field);
			f.setAccessible(true);
			return f.get(clazz);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setBossBar(Player p, long tick, long max) {
		int percentage = (int) ((max - tick) / 2 * 3);
		BossBarUtil.setHealth(p, percentage);
	}

	private static void sendPacket(Player p, Packet<?> packet) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}

}
