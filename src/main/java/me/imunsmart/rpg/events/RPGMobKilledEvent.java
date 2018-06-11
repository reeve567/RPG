package me.imunsmart.rpg.events;

import me.imunsmart.rpg.mobs.Mob;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class RPGMobKilledEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private UUID player;
	private Mob mob;
	private boolean cancelled = false;
	
	public RPGMobKilledEvent(UUID player, Mob mob) {
		this.player = player;
		this.mob = mob;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public UUID getPlayer() {
		return player;
	}
	
	public Mob getMob() {
		return mob;
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
