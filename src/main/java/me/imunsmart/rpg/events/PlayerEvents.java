package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerEvents implements Listener {
	private Main pl;

	public PlayerEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() != SlotType.OUTSIDE) {
			if (e.getSlot() == 40) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		e.setDeathMessage("");
		e.setDroppedExp(0);
		PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
		new BukkitRunnable() {
			@Override
			public void run() {
				((CraftPlayer) p).getHandle().playerConnection.a(packet);
			}
		}.runTaskLater(pl, 5);
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(ChatColor.AQUA + "+" + ChatColor.GRAY + " " + p.getName());
		Stats.setStat(p, "name", p.getName());
		Nametags.init(p);
		p.setCollidable(false);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!p.hasPlayedBefore()) {
					Health.resetPlayer(p);
					p.teleport(Util.spawn);
				}
			}
		}.runTaskLater(pl, 8);
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.AQUA + "-" + ChatColor.GRAY + " " + p.getName());
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().hasItemMeta()) {
			if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("Gem")) {
				new ActionBar(ChatColor.AQUA + "+" + e.getItem().getItemStack().getAmount() + " Gems").sendToPlayer(e.getPlayer());
			}
		}
	}

	@EventHandler
	public void onRegen(EntityRegainHealthEvent e) {
		e.setAmount(0);
		e.setCancelled(true);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		e.setRespawnLocation(Util.spawn);
		new BukkitRunnable() {
			@Override
			public void run() {
				Health.resetPlayer(p);
			}
		}.runTaskLater(pl, 10);
	}

	@EventHandler
	public void onSwap(PlayerSwapHandItemsEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onCraft(CraftItemEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == p.getInventory())
			e.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem() != null) {
				if (e.getItem().getType() == Material.EMPTY_MAP) {
					if (e.getItem().hasItemMeta())
						e.setCancelled(true);
					return;
				}
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
				e.setCancelled(true);
				Bank.open(p);
			}
		}
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.CHEST) {
				if (e.getClickedBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK) {
					e.setCancelled(true);
					GlobalMarket.open(p);
				}
			}
		}
	}

}
