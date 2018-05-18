package me.imunsmart.rpg.mechanics.loot;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LootChests implements Listener {
	public static List<LootChest> chests = new ArrayList<>();
	private Main pl;
	
	public LootChests(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		File f = new File(pl.getDataFolder(), "lootchests.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for (String s : fc.getStringList("chests")) {
			String[] tokens = s.split(" ");
			int x = Integer.valueOf(tokens[0]);
			int y = Integer.valueOf(tokens[1]);
			int z = Integer.valueOf(tokens[2]);
			int tier = Integer.valueOf(tokens[3]);
			LootChest lc = new LootChest(tier, new Location(Util.w, x, y, z));
			chests.add(lc);
			lc.spawn();
		}
	}
	
	class LootChest {
		private int tier;
		private Location l;
		
		public LootChest(int tier, Location l) {
			this.tier = tier;
			this.l = l;
			spawn();
		}
		
		public void loot() {
			l.getWorld().playEffect(l, Effect.STEP_SOUND, Material.CHEST);
			l.getBlock().setType(Material.AIR);
			new BukkitRunnable() {
				@Override
				public void run() {
					spawn();
				}
			}.runTaskLater(pl, 3600);
		}
		
		public void spawn() {
			l.getBlock().setType(Material.CHEST);
			Chest c = (Chest) l.getBlock().getState();
			c.getBlockInventory().clear();
			int possible = 1 + tier;
			int added = 0;
			while (added < possible) {
				int i = (int) (Math.random() * c.getBlockInventory().getSize());
				ItemStack[] items = ChestItems.getTier(tier);
				if (Math.random() < 0.1) {
					c.getBlockInventory().setItem(i, items[(int) (Math.random() * items.length)]);
					added++;
				} else if (Math.random() >= 0.9) {
					int maxGems = (int) (Math.pow(2, tier) * 4) - 1;
					if (maxGems > 64) maxGems = 64;
					c.getBlockInventory().setItem(i, Items.createGems(1 + (int) (Math.random() * maxGems)));
					added++;
				} else if (Math.random() >= 0.8) {
					int maxGems = (int) (Math.pow(2, tier) * 4) - 1;
					c.getBlockInventory().setItem(i, Items.createGemNote(1 + (int) (Math.random() * maxGems)));
					added++;
				}
			}
		}
	}
	
	public void addChest(Location l, int tier) {
		LootChest lc = new LootChest(tier, l);
		chests.add(lc);
	}
	
	public void disable() {
		File f = new File(pl.getDataFolder(), "lootchests.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		List<String> c = new ArrayList<>();
		for (LootChest lc : chests) {
			c.add(lc.l.getBlockX() + " " + lc.l.getBlockY() + " " + lc.l.getBlockZ() + " " + lc.tier + " ");
		}
		fc.set("chests", c);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (e.getInventory().getType() == InventoryType.CHEST) {
			System.out.println(e.getInventory().getTitle());
			if(!e.getInventory().getTitle().equals("container.chest"))
				return;
			boolean empty = true;
			for (ItemStack i : e.getInventory().getContents()) {
				if (i != null)
					empty = false;
			}
			if (empty) {
				for (LootChest lc : chests) {
					if (e.getInventory().getLocation().getBlockX() == lc.l.getBlockX() && e.getInventory().getLocation().getBlockY() == lc.l.getBlockY() && e.getInventory().getLocation().getBlockZ() == lc.l.getBlockZ()) {
						lc.loot();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (me.imunsmart.rpg.command.admincommands.rpg.give.LootChest.lc.containsKey(p.getName())) {
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				p.sendMessage(ChatColor.RED + "Cancelled placement of loot chest.");
				me.imunsmart.rpg.command.admincommands.rpg.give.LootChest.lc.remove(p.getName());
				return;
			}
			int tier = me.imunsmart.rpg.command.admincommands.rpg.give.LootChest.lc.get(p.getName());
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.CHEST) {
					if (tier == 0) {
						me.imunsmart.rpg.command.admincommands.rpg.give.LootChest.lc.remove(p.getName());
						Iterator<LootChest> it = chests.iterator();
						while (it.hasNext()) {
							LootChest lc = it.next();
							if (lc.l == e.getClickedBlock().getLocation()) {
								it.remove();
								e.getClickedBlock().setType(Material.AIR);
								return;
							}
						}
					} else if (tier > 0) {
						me.imunsmart.rpg.command.admincommands.rpg.give.LootChest.lc.remove(p.getName());
						LootChest lc = new LootChest(tier, e.getClickedBlock().getLocation());
						chests.add(lc);
						lc.spawn();
						p.sendMessage(ChatColor.GRAY + "Successfully created a tier " + ChatColor.AQUA + tier + ChatColor.GRAY + " loot chest.");
					}
				}
			}
		}
	}
}