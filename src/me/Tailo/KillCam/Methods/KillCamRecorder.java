package me.Tailo.KillCam.Methods;

import java.util.ArrayList;
import java.util.HashMap;

import me.Tailo.KillCam.System.main;
import me.Tailo.KillCam.Utils.TickUtil;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KillCamRecorder {
	
	private static HashMap<Player, KillCamRecorder> getrecorder = new HashMap<Player, KillCamRecorder>();

	HashMap<Long, ArrayList<Packet<?>>> packets = new HashMap<>();
	HashMap<Long, Location> moves = new HashMap<>();
	HashMap<Long, ItemStack> itemchanges = new HashMap<>();

	private TickUtil tickutil;
	private Player p;

	public KillCamRecorder(Player p) {
		this.p = p;
		tickutil = new TickUtil(p);
		getrecorder.put(p, this);
	}

	public static KillCamRecorder getKillCamRecorder(Player p) {
		return getrecorder.get(p);
	}

	@SuppressWarnings("deprecation")
	public void addPacket(Packet<?> packet) {
		
		long tick = tickutil.getTick();

		ArrayList<Packet<?>> packetlist = packets.get(tick);
		
		if(packetlist == null) {
			
			packetlist = new ArrayList<Packet<?>>();

			Bukkit.getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
				
				@Override
				public void run() {
					
					packets.remove(tick);
					
				}
			}, 200L);
			
		}

		if(!packetlist.contains(packet)) {
			packetlist.add(packet);
		}

		packets.put(tick, packetlist);
		
	}

	@SuppressWarnings("deprecation")
	public void addMove(Location loc) {
		
		long tick = tickutil.getTick();

		moves.put(tick, loc);

		Bukkit.getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
			
			@Override
			public void run() {				

				moves.remove(tick);
				
			}
		}, 200L);
		
	}

	@SuppressWarnings("deprecation")
	public void addItemChange(ItemStack item) {
		
		long tick = tickutil.getTick();

		itemchanges.put(tick, item);

		Bukkit.getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
			
			@Override
			public void run() {				

				itemchanges.remove(tick);
				
			}
		}, 200L);
		
	}

	public void remove() {
		
		tickutil.stopTicks();
		getrecorder.remove(p);
		
	}

	public static void loadKillCamRecorder() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			new KillCamRecorder(players);
		}
	}
	
}
