package me.Tailo.KillCam.Methods;

import java.util.HashMap;

import me.Tailo.KillCam.System.main;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public class BlockRecorder {
	
	public static HashMap<Block, BlockEvent> preblocks = new HashMap<>();

	public static void addBlockChange(Block b, BlockEvent e) {		
		
		preblocks.put(b, e);

		Bukkit.getScheduler().scheduleSyncDelayedTask(main.instance, new Runnable() {
			
			@Override
			public void run() {
				
				BlockRecorder.preblocks.remove(this);
				
			}
		}, 180L);
		
	}

	public static enum BlockEvent {
		PLACE, BREAK;
	}
	
}
