package me.Tailo.KillCam.Listeners;

import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.System.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEvent_Listener implements Listener {
	
	private main plugin;

	public PlayerMoveEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		KillCamRecorder r = KillCamRecorder.getKillCamRecorder(p);
		if(r != null) {
			r.addMove(p.getLocation());
		}
		
	}
	
}
