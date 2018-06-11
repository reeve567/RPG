package me.imunsmart.rpg.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class RPGRepairSuccessEvent extends RPGRepairEvent {
	private static final HandlerList handlers = new HandlerList();
	
	public RPGRepairSuccessEvent(Player player, boolean scrap, Location repairLocation) {
		super(player, scrap, repairLocation);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
