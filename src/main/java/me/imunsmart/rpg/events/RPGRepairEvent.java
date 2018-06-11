package me.imunsmart.rpg.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RPGRepairEvent extends Event implements Cancellable {
	public static final HandlerList handler = new HandlerList();
	private boolean cancelled = false;
	private Player player;
	private boolean scrap;
	private Location repairLocation;
	
	public RPGRepairEvent(Player player, boolean scrap, Location repairLocation) {
		this.player = player;
		this.scrap = scrap;
		this.repairLocation = repairLocation;
	}
	
	public static HandlerList getHandlerList() {
		return handler;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handler;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean isScrap() {
		return scrap;
	}
	
	public Location getRepairLocation() {
		return repairLocation;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
}
