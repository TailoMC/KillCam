package me.Tailo.KillCam.Listeners;

import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.Methods.KillCamReplay;
import me.Tailo.KillCam.System.main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEvent_Listener implements Listener {
	
	private main plugin;

	public PlayerDeathEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
			
		Player p = e.getEntity();
		Player killer = e.getEntity().getKiller();
		
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(10);

		if(killer != null) {
			
			if(KillCamRecorder.getKillCamRecorder(killer) != null) {
				KillCamReplay.playKillCam(p, killer);
			}
			
		}
		
	}
	
}
