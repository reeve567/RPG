package me.imunsmart.rpg.events;

import me.imunsmart.rpg.mechanics.NPCS;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RPGNPCClickEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private NPCS.NPC npc;
	
	public RPGNPCClickEvent(Player player, NPCS.NPC npc) {
		this.player = player;
		this.npc = npc;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public NPCS.NPC getNPC() {
		return npc;
	}
}
