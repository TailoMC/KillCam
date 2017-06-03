package me.Tailo.KillCam.Utils;

import org.bukkit.Bukkit;

import me.Tailo.KillCam.System.main;

public class VersionUtils {

	public static void setupVersion() {
		
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

		if (!version.equals("v1_8_R3")) {
			
			System.out.println("[KillCam] Version " + version + " is not supported! Disabling the plugin...");
			Bukkit.getServer().getPluginManager().disablePlugin(main.instance);
			
		}
		
	}
}
