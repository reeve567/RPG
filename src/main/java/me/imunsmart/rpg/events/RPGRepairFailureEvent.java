package me.imunsmart.rpg.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class RPGRepairFailureEvent extends RPGRepairEvent {
	private static final HandlerList handlers = new HandlerList();
	private FailureReason failureReason;
	
	public RPGRepairFailureEvent(Player player, boolean scrap, Location repairLocation, FailureReason failureReason) {
		super(player, scrap, repairLocation);
		this.failureReason = failureReason;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public FailureReason getFailureReason() {
		return failureReason;
	}
	
	public enum FailureReason {
		FULL_DURABILITY, INSUFFICIENT_GEMS, CANCELLED, NONE
	}
}
