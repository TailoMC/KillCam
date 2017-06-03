package me.Tailo.KillCam.Listeners;

import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.PacketUtils.PacketReader;
import me.Tailo.KillCam.System.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEvent_Listener implements Listener {
	
	private main plugin;

	public PlayerJoinEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
		new PacketReader(p);
		new KillCamRecorder(p);
		
	}
	
}
