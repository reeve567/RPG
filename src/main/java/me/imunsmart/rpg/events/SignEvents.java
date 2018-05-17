package me.imunsmart.rpg.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.imunsmart.rpg.Main;

public class SignEvents implements Listener {
	private Main pl;
	
	public SignEvents(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onChange(SignChangeEvent e) {
		for(int i = 0; i < e.getLines().length; i++) {
			e.setLine(i, ChatColor.translateAlternateColorCodes('&', e.getLine(i)));
		}
		e.getBlock().getState().update();
	}
}
