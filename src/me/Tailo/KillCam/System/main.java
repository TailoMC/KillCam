package me.Tailo.KillCam.System;

import me.Tailo.KillCam.Listeners.BlockBreakEvent_Listener;
import me.Tailo.KillCam.Listeners.BlockPlaceEvent_Listener;
import me.Tailo.KillCam.Listeners.PlayerDeathEvent_Listener;
import me.Tailo.KillCam.Listeners.PlayerItemHeldEvent_Listener;
import me.Tailo.KillCam.Listeners.PlayerJoinEvent_Listener;
import me.Tailo.KillCam.Listeners.PlayerMoveEvent_Listener;
import me.Tailo.KillCam.Listeners.PlayerQuitEvent_Listener;
import me.Tailo.KillCam.Methods.KillCamRecorder;
import me.Tailo.KillCam.PacketUtils.PacketReader;
import me.Tailo.KillCam.Utils.PacketUtil;
import me.Tailo.KillCam.Utils.VersionUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
	
	public static String text = "§4§lKillCam";
	public static main instance;

	public void onEnable() {
		
		instance = this;

		PacketReader.injectAll();

		new PlayerJoinEvent_Listener(this);
		new PlayerQuitEvent_Listener(this);
		new PlayerDeathEvent_Listener(this);
		new PlayerMoveEvent_Listener(this);
		new BlockBreakEvent_Listener(this);
		new BlockPlaceEvent_Listener(this);
		new PlayerItemHeldEvent_Listener(this);

		VersionUtils.setupVersion();

		PacketUtil.loadPacket();

		KillCamRecorder.loadKillCamRecorder();
		
	}

	public void onDisable() {
		PacketReader.uninjectAll();		
	}
}
