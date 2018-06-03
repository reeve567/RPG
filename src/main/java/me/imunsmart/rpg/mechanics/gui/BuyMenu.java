package me.imunsmart.rpg.mechanics.gui;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.util.MessagesUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class BuyMenu implements Listener {

    private Main pl;
    private static Inventory buy, menu;

    public BuyMenu(Main pl) {
        this.pl = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);

        init();
    }

    public static void showMenu(Player p) {
        p.openInventory(menu);
    }

    public static void open(Player p) {
        p.openInventory(buy);
    }

    private void init() {
        buy = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Buy");
        for (int i = 0; i < 9; i++) {
            buy.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.GRAY + " "));
        }
        buy.setItem(4, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gem Shop", ChatColor.GRAY + "Buy items for gems.", ChatColor.GRAY + "Left click to purchase."));
        for (int i = 45; i < 54; i++) {
            buy.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, ChatColor.GRAY + " "));
        }
        ItemStack pick1 = Items.createPickaxe(1, 0, "");
        addItem(pick1, 100);
        ItemStack pick2 = Items.createPickaxe(10, 0, "");
        addItem(pick2, 1100);
        ItemStack pick3 = Items.createPickaxe(20, 0, "");
        addItem(pick3, 2400);
        addItem(Items.createPotion(1), 15);
        addItem(Items.createPotion(2), 50);
        addItem(Items.createPotion(3), 160);
        addItem(Items.createPotion(4), 250);
        addItem(Items.createPotion(5), 500);
        addItem(Items.createWeapon("axe", 2, 10, 20, "name:&bWar Axe,Critical:20%,exclusive"), 200);

        menu = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Merchant");
        for (int i = 0; i < menu.getSize(); i++) {
            menu.setItem(i, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, " "));
        }
        menu.setItem(12, Items.createItem(Material.EMERALD_BLOCK, 1, 0, ChatColor.GREEN + "Buy Items", "Click to purchase items."));
        menu.setItem(14, Items.createItem(Material.DIAMOND_BLOCK, 1, 0, ChatColor.AQUA + "Sell Items", "Click to sell items."));
    }

    private void addItem(ItemStack i, int cost) {
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Cost: " + ChatColor.AQUA + cost);
        im.setLore(lore);
        i.setItemMeta(im);
        buy.addItem(i);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE)
            return;
        Player p = (Player) e.getWhoClicked();
        Inventory top = p.getOpenInventory().getTopInventory();
        if (top.getName().equals(ChatColor.DARK_AQUA + "Buy")) {
            e.setCancelled(true);
            if (e.getCurrentItem().hasItemMeta()) {
                if (!e.getCurrentItem().getItemMeta().hasLore() || e.getCurrentItem().getItemMeta().getDisplayName().contains("Shop")) return;
                ItemStack i = e.getCurrentItem();
                int cost = Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(i.getItemMeta().getLore().size() - 1)).split(" ")[1]);
                if (Bank.pay(p, cost)) {
                    p.closeInventory();
                    Sounds.play(p, Sound.ENTITY_PLAYER_LEVELUP, 2);
                    ItemStack x = i.clone();
                    ItemMeta im = x.getItemMeta();
                    List<String> lore = im.getLore();
                    lore.remove(lore.size() - 2);
                    lore.remove(lore.size() - 1);
                    im.setLore(lore);
                    x.setItemMeta(im);
                    p.getInventory().addItem(x);
                    p.sendMessage(ChatColor.GREEN + "Purchase successful.");
                    return;
                } else {
                    p.sendMessage(MessagesUtil.notEnoughGems(cost));
                    Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
                    return;
                }
            }
        } else if (top.getName().equals(ChatColor.DARK_GREEN + "Merchant")) {
            e.setCancelled(true);
            if (!e.getCurrentItem().hasItemMeta())
                return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Items")) {
                p.closeInventory();
                open(p);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Items")) {
                p.closeInventory();
                SellMenu.open(p);
            }
        }
    }
}
