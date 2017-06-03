package me.Tailo.KillCam.Utils;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

public class NPCUtils {

	public static void addPlayer(Player p, Player target, int id) {
		
		Location loc = target.getLocation();

		PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();

		Reflections.setValue(packet, "a", id);
		Reflections.setValue(packet, "b", target.getUniqueId());
		Reflections.setValue(packet, "c", toLocation(loc.getX()));
		Reflections.setValue(packet, "d", toLocation(loc.getY()));
		Reflections.setValue(packet, "e", toLocation(loc.getZ()));
		Reflections.setValue(packet, "f", toRotation(loc.getYaw()));
		Reflections.setValue(packet, "g", toRotation(loc.getPitch()));
		Reflections.setValue(packet, "h", 0);
		DataWatcher w = new DataWatcher(null);
		w.a(10, (byte) Byte.MAX_VALUE);
		Reflections.setValue(packet, "i", w);

		PacketPlayOutEntityEquipment hand = new PacketPlayOutEntityEquipment(id, 0, CraftItemStack.asNMSCopy(p.getItemInHand()));
		PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(id, 1, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
		PacketPlayOutEntityEquipment leggings = new PacketPlayOutEntityEquipment(id, 2, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
		PacketPlayOutEntityEquipment chestplate = new PacketPlayOutEntityEquipment(id, 3, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
		PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(id, 4, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));

		sendPacket(p, packet);
		sendPacket(p, hand);
		sendPacket(p, boots);
		sendPacket(p, leggings);
		sendPacket(p, chestplate);
		sendPacket(p, helmet);
		
	}

	public static void removePlayer(Player p, int id) {
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { id });
		sendPacket(p, packet);
	}

	public static void setLocation(Player p, int id, Location loc) {
		PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(id, toLocation(loc.getX()), toLocation(loc.getY()), toLocation(loc.getZ()), toRotation(loc.getYaw()), toRotation(loc.getPitch()), true);
		sendPacket(p, packet);
	}

	public static void setCamera(Player p, int id) {
		PacketPlayOutCamera packet = new PacketPlayOutCamera();
		Reflections.setValue(packet, "a", id);
		sendPacket(p, packet);
	}

	public static int toLocation(double d) {
		return MathHelper.floor(d * 32.0D);
	}

	public static byte toRotation(float f) {
		return (byte) (int) (f * 256.0F / 360.0F);
	}

	private static void sendPacket(Player p, Packet<?> packet) {
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
