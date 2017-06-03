package me.Tailo.KillCam.Utils;

import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder.EnumWorldBorderAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldBorder;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BloodEffectUtil {

	public static void addBloodEffect(Player p) {
		
		WorldBorder border = new WorldBorder();
		border.setCenter(10000, 10000);
		border.setSize(1);
		border.setWarningDistance(1);

		PacketPlayOutWorldBorder packet1 = new PacketPlayOutWorldBorder(border, EnumWorldBorderAction.INITIALIZE);
		PacketPlayOutWorldBorder packet2 = new PacketPlayOutWorldBorder(border, EnumWorldBorderAction.SET_WARNING_BLOCKS);

		PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;

		con.sendPacket(packet1);
		con.sendPacket(packet2);
		
	}

	public static void removeBloodEffect(Player p) {
		
		WorldBorder border = ((CraftPlayer)p).getHandle().getWorld().getWorldBorder();

		PacketPlayOutWorldBorder packet1 = new PacketPlayOutWorldBorder(border, EnumWorldBorderAction.INITIALIZE);
		PacketPlayOutWorldBorder packet2 = new PacketPlayOutWorldBorder(border, EnumWorldBorderAction.SET_WARNING_BLOCKS);

		PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;

		con.sendPacket(packet1);
		con.sendPacket(packet2);
		
	}
	
}
