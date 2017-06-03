package me.Tailo.KillCam.PacketUtils;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.Utils.PacketUtil;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.entity.Player;

public class PacketHandler extends ChannelDuplexHandler {
	
	private Player p;

	public PacketHandler(Player p) {
		this.p = p;
	}

	public void write(ChannelHandlerContext ctx, Object packet,	ChannelPromise promise) throws Exception {
		
		if(PacketUtil.packets.contains(packet.getClass().getSimpleName())) {
			KillCamRecorder r = KillCamRecorder.getKillCamRecorder(p);
			if(r != null) {
				r.addPacket((Packet<?>) packet);
			}
		}

		super.write(ctx, packet, promise);
		
	}
	
}
