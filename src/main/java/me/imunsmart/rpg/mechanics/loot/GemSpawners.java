package me.imunsmart.rpg.mechanics.loot;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admincommands.rpg.give.GemSpawnerC;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.LocationUtility;
import me.imunsmart.rpg.util.MessagesUtil;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GemSpawners implements Listener {
	public static List<GemSpawner> spawners = new ArrayList<>();
	private static Main pl;
	
	public GemSpawners(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		File f = new File(pl.getDataFolder(), "gemspawners.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for (String s : fc.getStringList("spawners")) {
			String[] tokens = s.split(" ");
			int x = Integer.valueOf(tokens[0]);
			int y = Integer.valueOf(tokens[1]);
			int z = Integer.valueOf(tokens[2]);
			int tier = Integer.valueOf(tokens[3]);
			GemSpawner spawner = new GemSpawner(tier, new Location(Util.w, x, y, z));
			spawners.add(spawner);
			spawner.spawn();
		}
		
	}
	
	public static void addSpawner(int tier, Location location) {
		GemSpawner spawner = new GemSpawner(tier, location);
		spawners.add(spawner);
		spawner.spawn();
	}
	
	public static void disable() {
		File f = new File(pl.getDataFolder(), "gemspawners.yml");
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		List<String> c = new ArrayList<>();
		for (GemSpawners.GemSpawner lc : spawners) {
			c.add(lc.location.getBlockX() + " " + lc.location.getBlockY() + " " + lc.location.getBlockZ() + " " + lc.tier + " ");
		}
		fc.set("spawners", c);
		try {
			fc.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean removeSpawner(Location l) {
		GemSpawner spawner = null;
		for (GemSpawner sp : spawners) {
			if (sp.location.equals(l)) {
				spawner = sp;
				sp.location.getBlock().setType(Material.AIR);
			}
		}
		if (spawner != null) return spawners.remove(spawner);
		return false;
	}
	
	public static class GemSpawner {
		private int tier;
		private Location location;
		
		GemSpawner(int tier, Location location) {
			this.tier = tier;
			this.location = location;
		}
		
		public Location getLocation() {
			return location;
		}
		
		public int getTier() {
			return tier;
		}
		
		public void loot() {
			//location.getWorld().playEffect(LocationUtility.centerBlock(location), Effect.CLICK1, 1);
			location.getWorld().playSound(LocationUtility.centerBlock(location), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
			location.getWorld().dropItem(LocationUtility.centerBlock(location), new CustomItem(Items.gem).setCustomAmount((int) (Math.random() * tier * 1.5) + 1));
			location.getBlock().setType(Material.AIR);
			new BukkitRunnable() {
				@Override
				public void run() {
					spawn();
				}
			}.runTaskLater(pl, 600);
		}
		
		public void spawn() {
			location.getBlock().setType(Material.DIAMOND_ORE);
		}
		
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			if (e.getBlock().getType() == Material.DIAMOND_ORE)
				for (GemSpawner sp : GemSpawners.spawners)
					if (sp.getLocation().equals(e.getBlock().getLocation()))
						sp.loot();
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (GemSpawnerC.gsp.containsKey(p.getName())) {
			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Cancelled placement of gem spawner.");
				GemSpawnerC.gsp.remove(p.getName());
				return;
			}
			int tier = GemSpawnerC.gsp.get(p.getName());
			Location l = e.getClickedBlock().getLocation();
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				if (e.getClickedBlock().getType() == Material.DIAMOND_ORE) {
					GemSpawnerC.gsp.remove(p.getName());
					if (tier == 0) {
						Iterator<GemSpawner> it = spawners.iterator();
						while (it.hasNext()) {
							GemSpawner lc = it.next();
							if (l.getBlockX() == lc.location.getBlockX() && l.getBlockY() == lc.location.getBlockY() && l.getBlockZ() == lc.location.getBlockZ()) {
								it.remove();
								e.getClickedBlock().setType(Material.AIR);
								p.sendMessage(net.md_5.bungee.api.ChatColor.RED + "Removed loot chest.");
								return;
							}
						}
					} else if (tier > 0) {
						GemSpawner lc = new GemSpawner(tier, e.getClickedBlock().getLocation());
						spawners.add(lc);
						lc.spawn();
						p.sendMessage(MessagesUtil.gemSpawnerCreated(tier));
					}
				}
			}
		}
	}
	
}
