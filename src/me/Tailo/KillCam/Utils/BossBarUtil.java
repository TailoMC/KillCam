package me.Tailo.KillCam.Utils;

import java.util.HashMap;

import me.Tailo.KillCam.System.main;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class BossBarUtil {
	
	private static HashMap<Player, Integer> getid = new HashMap<>();

	@SuppressWarnings("deprecation")
	public static void sendBossBar(Player p) {
		
		int id = (int) Math.ceil(Math.random() * 1000) + 2000;

		getid.put(p, id);

		Location loc = p.getLocation().clone().subtract(0, 4, 0);

		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
		Reflections.setValue(packet, "a", id);
		Reflections.setValue(packet, "b", EntityType.WITHER.getTypeId());
		Reflections.setValue(packet, "c", NPCUtils.toLocation(loc.getX()));
		Reflections.setValue(packet, "d", NPCUtils.toLocation(loc.getY()));
		Reflections.setValue(packet, "e", NPCUtils.toLocation(loc.getZ()));
		Reflections.setValue(packet, "f", (byte) 0);
		Reflections.setValue(packet, "g", (byte) 0);
		Reflections.setValue(packet, "h", (byte) 0);
		Reflections.setValue(packet, "i", (byte) 0);
		Reflections.setValue(packet, "j", (byte) 0);
		Reflections.setValue(packet, "k", (byte) 0);

		DataWatcher w = new DataWatcher(null);
		w.a(0, (byte) 32);
		w.a(6, 300F);
		w.a(2, main.text);
		w.a(3, (byte) 1);
		w.a(16, 300);

		Reflections.setValue(packet, "l", w);

		sendPacket(p, packet);
		
	}

	public static void removeBossBar(Player p) {
		
		if(getid.containsKey(p)) {
			
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { getid.get(p) });

			sendPacket(p, packet);
			
		}
		
	}

	public static void setHealth(Player p, int health) {
		
		if(getid.containsKey(p)) {
			
			DataWatcher w = new DataWatcher(null);
			w.a(0, (byte) 32);
			w.a(6, (float) health);
			w.a(2, main.text);
			w.a(3, (byte) 1);
			w.a(16, health);

			PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(getid.get(p), w, true);

			sendPacket(p, packet);
			
		}
		
	}

	private static void sendPacket(Player p, Packet<?> packet) {
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
