package me.Tailo.KillCam.Utils;

import java.util.HashMap;
import me.Tailo.KillCam.System.main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TickUtil {
	
	private static long currenttick = 0;

	private BukkitTask run;
	private Player p;
	private static HashMap<Player, TickUtil> getutil = new HashMap<>();

	public TickUtil(Player p) {
		this.p = p;
		getutil.put(p, this);
		startTicks();
	}

	public static TickUtil getTickUtil(Player p) {
		return getutil.get(p);
	}

	private void startTicks() {
		
		run = new BukkitRunnable() {
			
			@Override
			public void run() {
				TickUtil.currenttick += 1;
			}
			
		}.runTaskTimer(main.instance, 0, 1);
		
	}

	public void stopTicks() {
		run.cancel();
		currenttick = 0;
		getutil.remove(p);
	}

	public long getTick() {
		return currenttick;
	}
	
}
