package me.imunsmart.rpg.events;

import static me.imunsmart.rpg.mechanics.Health.calculateMaxHealth;
import static me.imunsmart.rpg.mechanics.Health.damage;
import static me.imunsmart.rpg.mechanics.Health.health;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admincommands.rpg.give.LootChest;
import me.imunsmart.rpg.mechanics.ActionBar;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.RepairMenu;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.PacketPlayInEntityAction.EnumPlayerAction;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.ChatClickable.EnumClickAction;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class PlayerEvents implements Listener {
	private Main pl;

	public PlayerEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		e.setJoinMessage(ChatColor.AQUA + "+" + ChatColor.GRAY + " " + p.getName());
		if (!p.hasPlayedBefore()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Health.resetPlayer(p);
					p.teleport(Util.spawn);
				}
			}.runTaskLater(pl, 8);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.AQUA + "-" + ChatColor.GRAY + " " + p.getName());
	}

	@EventHandler
	public void onHunger(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		new BukkitRunnable() {
			@Override
			public void run() {
				Health.resetPlayer(p);
			}
		}.runTaskLater(pl, 10);
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
	public void onRegen(EntityRegainHealthEvent e) {
		e.setAmount(0);
		e.setCancelled(true);
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e) {
		if (e.getItem().getItemStack().hasItemMeta()) {
			if (e.getItem().getItemStack().getItemMeta().getDisplayName().contains("Gem")) {
				new ActionBar(ChatColor.AQUA + "+" + e.getItem().getItemStack().getAmount() + " Gems").sendToPlayer(e.getPlayer());
			}
		}
	}

}
