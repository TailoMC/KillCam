package me.Tailo.KillCam.Listeners;

import me.Tailo.KillCam.Methods.BlockRecorder;
import me.Tailo.KillCam.Methods.BlockRecorder.BlockEvent;
import me.Tailo.KillCam.System.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakEvent_Listener implements Listener {
	
	private main plugin;

	public BlockBreakEvent_Listener(main main) {
		this.plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, main);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		BlockRecorder.addBlockChange(e.getBlock(), BlockEvent.BREAK);
		
	}
	
}
