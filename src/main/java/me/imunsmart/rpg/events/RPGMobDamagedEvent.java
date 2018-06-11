package me.imunsmart.rpg.events;

import me.imunsmart.rpg.mobs.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RPGMobDamagedEvent extends Event implements Cancellable {
	public static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private Player player;
	private Mob mob;
	
	public RPGMobDamagedEvent(Player player, Mob mob) {
		this.player = player;
		this.mob = mob;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Mob getMob() {
		return mob;
	}
}
