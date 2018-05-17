package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerEvents implements Listener {
	private Main pl;
	
	public ServerEvents(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onPing(ServerListPingEvent e) {
		e.setMotd(Util.logo + ChatColor.GRAY + " > " + ChatColor.RED + "Version 0.01");
	}
	
}
