package me.imunsmart.rpg.mechanics.gui;

import io.netty.util.internal.MathUtil;
import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SellMenu implements Listener {
    private static Main pl;
    private static List<String> open = new ArrayList<>();

    public SellMenu(Main pl) {
        this.pl = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public static void disable() {
        pl = null;
        open.clear();
        open = null;
    }

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Sell");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.GRAY + " "));
        }
        for (int i = 45; i < 54; i++) {
            inv.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.GRAY + " "));
        }
        inv.setItem(49, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Click to Sell", ChatColor.GRAY + "Gem Value: " + ChatColor.AQUA + "0"));
        p.openInventory(inv);
        open.add(p.getName());
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!open.contains(p.getName())) {
                    cancel();
                    return;
                }
                int gems = 0;
                for (int x = 9; x < 45; x++) {
                    if (inv.getItem(x) == null) continue;
                    ItemStack i = inv.getItem(x);
                    if (i.getType() == Material.COAL) gems += i.getAmount();
                    else if (i.getType() == Material.IRON_INGOT) gems += i.getAmount() * 3;
                    else if (i.getType() == Material.EMERALD) gems += i.getAmount() * 5;
                    else if (i.getType() == Material.REDSTONE) gems += i.getAmount() * 8;
                    else if (i.getType() == Material.GOLD_INGOT) gems += i.getAmount() * 10;
                }
                Material m = gems > 64 ? Material.EMPTY_MAP : Material.DIAMOND;
                inv.setItem(49, Items.createItem(m, 1, 0, ChatColor.AQUA + "Click to Sell", ChatColor.GRAY + "Gem Value: " + ChatColor.AQUA + gems));
            }
        }.runTaskTimer(pl, 0, 1);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getSlotType() == SlotType.OUTSIDE)
            return;
        Player p = (Player) e.getWhoClicked();
        Inventory top = p.getOpenInventory().getTopInventory();
        if (top.getName().equals(ChatColor.DARK_AQUA + "Sell")) {
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
                e.setCancelled(true);
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getType() == Material.DIAMOND || e.getCurrentItem().getType() == Material.EMPTY_MAP) {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Click to Sell")) {
                        e.setCancelled(true);
                        int gems = Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[2]));
                        if (gems > 64) {
                            p.getInventory().addItem(Items.createGemNote(gems));
                        } else {
                            p.getInventory().addItem(Items.createGems(gems));
                        }
                        p.sendMessage(ChatColor.GREEN + "Successfully sold items!");
                        open.remove(p.getName());
                        p.closeInventory();
                        Sounds.play(p, Sound.ENTITY_PLAYER_LEVELUP, 1);
                        return;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory top = p.getOpenInventory().getTopInventory();
        if (top.getName().equals(ChatColor.DARK_AQUA + "Sell") && open.contains(p.getName())) {
            for (int i = 9; i < 45; i++) {
                if (top.getItem(i) != null) {
                    p.getInventory().addItem(top.getItem(i));
                }
            }
            p.sendMessage(ChatColor.RED + "Cancelling sell...");
            open.remove(p.getName());
        }
    }
}
