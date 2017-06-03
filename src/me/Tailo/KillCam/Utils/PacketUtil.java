package me.Tailo.KillCam.Utils;

import java.util.ArrayList;

public class PacketUtil {
	
	public static ArrayList<String> packets = new ArrayList<>();
	public static ArrayList<String> idpackets = new ArrayList<>();

	public static void loadPacket() {
		
		packets.add("PacketPlayOutEntityEffect");
		packets.add("PacketPlayOutEntityLook");
		packets.add("PacketPlayOutEntityMetadata");
		packets.add("PacketPlayOutEntityEquipment");
		packets.add("PacketPlayOutEntityTeleport");
		packets.add("PacketPlayOutEntityStatus");
		packets.add("PacketPlayOutEntityHeadRotation");
		packets.add("PacketPlayOutExplosion");
		packets.add("PacketPlayOutHeldItemSlot");
		packets.add("PacketPlayOutSetSlot");
		packets.add("PacketPlayOutRelEntityMove");
		packets.add("PacketPlayOutRelEntityMoveLook");
		packets.add("PacketPlayOutAnimation");
		packets.add("PacketPlayOutAttachEntity");
		packets.add("PacketPlayOutRemoveEntityEffect");
		packets.add("PacketPlayOutUpdateHealth");
		packets.add("PacketPlayOutNamedSoundEffect");
		packets.add("PacketPlayOutWorldParticles");
		packets.add("PacketPlayOutEntity");
		packets.add("PacketPlayOutEntityVelocity");
		packets.add("PacketPlayOutCollect");
		packets.add("PacketPlayOutEntityDestroy");
		packets.add("PacketPlayOutSpawnEntity");
		packets.add("PacketPlayOutBlockBreakAnimation");
		packets.add("PacketPlayOutBlockChange");
		packets.add("PacketPlayOutBlockAction");
		packets.add("PacketPlayOutMultiBlockChange");

		idpackets.add("PacketPlayOutEntityEffect");
		idpackets.add("PacketPlayOutEntityMetadata");
		idpackets.add("PacketPlayOutEntityEquipment");
		idpackets.add("PacketPlayOutEntityTeleport");
		idpackets.add("PacketPlayOutEntityStatus");
		idpackets.add("PacketPlayOutEntityHeadRotation");
		idpackets.add("PacketPlayOutAnimation");
		idpackets.add("PacketPlayOutAttachEntity");
		idpackets.add("PacketPlayOutRemoveEntityEffect");
		idpackets.add("PacketPlayOutEntityVelocity");
		idpackets.add("PacketPlayOutCollect");
		idpackets.add("PacketPlayOutEntityDestroy");
		idpackets.add("PacketPlayOutEntity");
		idpackets.add("PacketPlayOutSpawnEntity");
		idpackets.add("PacketPlayOutRelEntityMove");
		idpackets.add("PacketPlayOutRelEntityMoveLook");
		idpackets.add("PacketPlayOutEntityLook");
		idpackets.add("PacketPlayOutBlockBreakAnimation");
		
	}
	
}
