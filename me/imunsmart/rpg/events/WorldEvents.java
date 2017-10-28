package me.imunsmart.rpg.events;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.imunsmart.rpg.Main;

public class WorldEvents implements Listener {
	private Main pl;

	public WorldEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason() != SpawnReason.CUSTOM)
			e.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}

	@EventHandler
	public void onExplode(BlockExplodeEvent e) {
		e.blockList().clear();
	}
}
