package me.Tailo.KillCam.PacketUtils;

import io.netty.channel.Channel;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketReader {
	
	private static HashMap<Player, PacketReader> getreader = new HashMap<>();
	private Player p;
	private Channel channel;

	public PacketReader(Player p) {
		this.p = p;
		inject();
		getreader.put(p, this);
	}

	private void inject() {
		channel = ((CraftPlayer)p).getHandle().playerConnection.networkManager.channel;
		PacketHandler ph = new PacketHandler(p);
		channel.pipeline().addBefore("packet_handler", "KillCamInjector", ph);
	}

	public void uninject() {
		if(channel.pipeline().get("KillCamInjector") != null) {
			channel.eventLoop().execute(new Runnable() {				
				@Override
				public void run() {
					channel.pipeline().remove("KillCamInjector");
				}
			});
		}
		getreader.remove(p);
	}

	public static PacketReader getPacketReader(Player p) {
		return getreader.get(p);
	}

	public static void injectAll() {
		for(Player players : Bukkit.getOnlinePlayers()) {
			new PacketReader(players);
		}
	}

	public static void uninjectAll() {
		for(Player players : Bukkit.getOnlinePlayers()) {
			PacketReader pr = getPacketReader(players);
			pr.uninject();
		}
	}
	
}
