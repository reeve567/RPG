package me.imunsmart.rpg.mechanics.loot;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admins.rpg.give.LootChestC;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.util.MessagesUtil;
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
    private static Main pl;

    public LootChests(Main pl) {
        LootChests.pl = pl;
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

    public static void addChest(Location l, int tier) {
        LootChest lc = new LootChest(tier, l);
        chests.add(lc);
    }

    public static boolean removeChest(Location l) {
        LootChest chest = null;
        for (LootChest c : chests) {
            if (c.l.equals(l)) {
                chest = c;
                c.l.getBlock().setType(Material.AIR);
            }
        }
        if (chest != null) return chests.remove(chest);
        return false;
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
            if (!e.getInventory().getTitle().equals("container.chest"))
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
        if (LootChestC.lc.containsKey(p.getName())) {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                p.sendMessage(ChatColor.RED + "Cancelled placement of loot chest.");
                LootChestC.lc.remove(p.getName());
                return;
            }
            int tier = LootChestC.lc.get(p.getName());
            Location l = e.getClickedBlock().getLocation();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (e.getClickedBlock().getType() == Material.CHEST) {
                    LootChestC.lc.remove(p.getName());
                    if (tier == 0) {
                        Iterator<LootChest> it = chests.iterator();
                        while (it.hasNext()) {
                            LootChest lc = it.next();
                            if (l.getBlockX() == lc.l.getBlockX() && l.getBlockY() == lc.l.getBlockY() && l.getBlockZ() == lc.l.getBlockZ()) {
                                it.remove();
                                e.getClickedBlock().setType(Material.AIR);
                                p.sendMessage(MessagesUtil.lootChestRemoved);
                                return;
                            }
                        }
                    } else if (tier > 0) {
                        LootChest lc = new LootChest(tier, e.getClickedBlock().getLocation());
                        chests.add(lc);
                        lc.spawn();
                        p.sendMessage(MessagesUtil.lootChestCreated(tier));
                    }
                }
            }
        }
    }

    static class LootChest {
        private int tier;
        private Location l;

        public LootChest(int tier, Location l) {
            this.tier = tier;
            this.l = l;
            spawn();
        }

        public void spawn() {
            l.getBlock().setType(Material.CHEST);
            Chest c = (Chest) l.getBlock().getState();
            c.getBlockInventory().clear();
            int possible = 2 + (tier / 2);
            int added = 0;
            while (added < possible) {
                int i = (int) (Math.random() * c.getBlockInventory().getSize());
                ItemStack[] items = {
                        Items.createPotion(tier),
                        Items.createTeleportScroll(1, Util.tpNames[(int) (Math.random() * Util.tpNames.length)], 5),
                };
                if(Math.random() < 0.5) {
                    double perc = Math.random();
                    if (perc >= 0.9) {
                        int maxGems = (int) (Math.pow(2, tier - 1) * 4) - 1;
                        if (maxGems > 64) maxGems = 64;
                        c.getBlockInventory().setItem(i, Items.createGems(1 + (int) (Math.random() * maxGems)));
                        added++;
                    }else if (perc >= 0.89) {
                        c.getBlockInventory().setItem(i, Items.getRandomItem(tier));
                        added++;
                    } else if (perc >= 0.85) {
                        c.getBlockInventory().setItem(i, items[(int) (Math.random() * items.length)]);
                    }
                }
            }
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
    }
}