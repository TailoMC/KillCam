package me.Tailo.KillCam.Listeners;

import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.PacketUtils.PacketReader;
import me.Tailo.KillCam.System.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvent_Listener implements Listener {
	
	private main plugin;

	public PlayerQuitEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		
		KillCamRecorder.getKillCamRecorder(p).remove();
		PacketReader.getPacketReader(p).uninject();
		
	}
	
}
