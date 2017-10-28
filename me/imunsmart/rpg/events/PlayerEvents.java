package me.imunsmart.rpg.events;

import static me.imunsmart.rpg.mechanics.Health.calculateMaxHealth;
import static me.imunsmart.rpg.mechanics.Health.damage;
import static me.imunsmart.rpg.mechanics.Health.health;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;

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
				}
			}.runTaskLater(pl, 5);
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		e.setQuitMessage(ChatColor.AQUA + "-" + ChatColor.GRAY + " " + p.getName());
	}

	@EventHandler
	public void onSpawn(FoodLevelChangeEvent e) {
		e.setFoodLevel(20);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				double real = (e.getDamage() / 20.0d);
				damage(p, (int) Math.round(real * (calculateMaxHealth(p) / 4)));
			}
			e.setDamage(0);
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		new BukkitRunnable() {
			@Override
			public void run() {
				health.put(p.getName(), calculateMaxHealth(p));
				Health.resetPlayer(p);
			}
		}.runTaskLater(pl, 10);
	}

	@EventHandler
	public void onRegen(EntityRegainHealthEvent e) {
		e.setAmount(0);
		e.setCancelled(true);
	}

	HashMap<String, ItemStack> cancel = new HashMap<>();
	HashMap<String, Item> remove = new HashMap<>();
	HashMap<String, Block> anvil = new HashMap<>();

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getType() == Material.FURNACE) {
				e.setCancelled(true);
				if (e.getItem() != null) {
					if (e.getItem().getType().name().contains("HELMET") || e.getItem().getType().name().contains("CHESTPLATE") || e.getItem().getType().name().contains("LEGGINGS") || e.getItem().getType().name().contains("BOOTS") || e.getItem().getType().name().contains("SWORD") || e.getItem().getType().name().contains("AXE")) {
						if (!p.isSneaking()) {
							p.sendMessage(ChatColor.RED + "Shift-click a furnace with armor to smelt it into scraps (WARNING: CANNOT BE UNDONE).");
						} else {
							p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 0.67f);
							Items.convertToScraps(p);
						}
					}
				}
			} else if (e.getClickedBlock().getType() == Material.ANVIL) {
				e.setCancelled(true);
				if (e.getItem().getType().name().contains("HELMET") || e.getItem().getType().name().contains("CHESTPLATE") || e.getItem().getType().name().contains("LEGGINGS") || e.getItem().getType().name().contains("BOOTS") || e.getItem().getType().name().contains("SWORD") || e.getItem().getType().name().contains("AXE")) {
					ItemStack old = e.getItem();
					double perc = (double) old.getDurability() / old.getType().getMaxDurability();
					if (perc == 0) {
						p.sendMessage(ChatColor.RED + "No need to repair that...");
						p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
						return;
					}
					int cost = (int) (costs[Items.getTier(old) - 1] * perc);
					if (Bank.getGems(p) < cost) {
						p.sendMessage(ChatColor.RED + "Insufficient gems. That costs " + ChatColor.UNDERLINE + cost + ChatColor.RED + " gems.");
						p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
						return;
					}
					p.getInventory().remove(old);
					cancel.put(p.getName(), old);
					p.sendMessage(ChatColor.GRAY + "Do you wish to repair this item for " + ChatColor.AQUA + cost + " gems?" + ChatColor.GRAY + " (y/n)");
					Item i = e.getClickedBlock().getWorld().dropItem(e.getClickedBlock().getLocation().add(0.5, 1, 0.5), old);
					i.setVelocity(new Vector());
					i.setPickupDelay(Integer.MAX_VALUE);
					remove.put(p.getName(), i);
					anvil.put(p.getName(), e.getClickedBlock());
				}
			}
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (cancel.containsKey(p.getName())) {
			e.setCancelled(true);
			ItemStack i = cancel.remove(p.getName());
			double perc = (double) i.getDurability() / i.getType().getMaxDurability();
			int cost = (int) (costs[Items.getTier(i) - 1] * perc);
			remove.remove(p.getName()).remove();
			if (e.getMessage().equalsIgnoreCase("y")) {
				if (Bank.getGems(p) < cost) {
					p.sendMessage(ChatColor.RED + "Insufficient gems. That costs " + ChatColor.UNDERLINE + cost + ChatColor.RED + " gems.");
					p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
					return;
				} else {
					Bank.pay(p, cost);
					p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1f);
					i.setDurability((short) 0);
					p.getInventory().addItem(i);
					p.getWorld().playEffect(anvil.remove(p.getName()).getLocation(), Effect.STEP_SOUND, Material.ANVIL);
				}
			} else {
				p.sendMessage(ChatColor.RED + "Cancelled repair.");
				p.getInventory().addItem(i);
				p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
			}
		}
	}

	private int[] costs = { 64, 128, 256, 384, 512 };

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getSlotType() != SlotType.QUICKBAR)
			return;
		if (e.getClick() == ClickType.LEFT) {
			ItemStack clicked = e.getCurrentItem();
			if (clicked == null)
				return;
			new BukkitRunnable() {
				public void run() {
					ItemStack inHand = e.getClickedInventory().getItem(e.getSlot());
					if (inHand != null && clicked != null && inHand.getType() == Material.INK_SACK && inHand.hasItemMeta() && clicked.hasItemMeta()) {
						int max = clicked.getType().getMaxDurability();
						int amount = inHand.getAmount();
						short dur = clicked.getDurability();
						boolean playSound = false;
						for (int i = 0; i < amount; i++) {
							if (dur != 0) {
								int data = inHand.getDurability();
								double perc = 0.03;
								int tier = 1, itier = Items.getTier(clicked);
								if (data == 8)
									tier = 2;
								if (data == 7)
									tier = 3;
								if (data == 12)
									tier = 4;
								if (data == 11)
									tier = 5;
								if (tier != itier) {
									p.sendMessage(ChatColor.RED + "These scraps are not compatible.");
									return;
								}
								dur -= (perc * max);
								if (dur < 0)
									dur = 0;
								clicked.setDurability(dur);
								if (inHand.getAmount() == 1) {
									p.getInventory().remove(inHand);
								} else
									inHand.setAmount(inHand.getAmount() - 1);
								p.updateInventory();
								playSound = true;
							} else
								break;
						}
						if (playSound)
							p.playSound(p.getLocation(), Sound.BLOCK_STONE_BREAK, 1, 1);
					}
				}
			}.runTaskLater(pl, 2);
		}
	}

}
