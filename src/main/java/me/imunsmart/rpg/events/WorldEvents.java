package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;

public class WorldEvents implements Listener {

	public WorldEvents(Main pl) {
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			BlockState bs = e.getBlock().getState();
			bs.update(true);
		}
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.blockList().clear();
	}
	
	@EventHandler
	public void onExplode(BlockExplodeEvent e) {
		e.blockList().clear();
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason() != SpawnReason.CUSTOM)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityInteract(EntityInteractEvent event) {
		if (event.getBlock().getType() == Material.LEGACY_SOIL)
			event.setCancelled(true);
	}
}
