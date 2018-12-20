package me.imunsmart.rpg.mechanics.gui;

import me.imunsmart.rpg.Main;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ItemMenu implements Listener {
    private Main pl;

    private static final int TO_REPAIR_SLOT = 10;
	private static final int SCRAP_SLOT = 12;
	private static final int MODIFY_SLOT = 16;

    public ItemMenu(Main pl) {
        this.pl = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_AQUA + "Item Modification");
        for (int i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, Items.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, 0, ChatColor.GRAY + " "));
        }
        inv.setItem(TO_REPAIR_SLOT, null);
        inv.setItem(SCRAP_SLOT, null);
        inv.setItem(MODIFY_SLOT, null);
        inv.setItem(19, Items.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, ChatColor.GRAY + "Item to Repair"));
        inv.setItem(21, Items.createItem(Material.RED_STAINED_GLASS_PANE, 1, 0, ChatColor.GRAY + "Scraps"));
        inv.setItem(25, Items.createItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1, 0, ChatColor.GRAY + "Modify"));
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getSlotType() == SlotType.OUTSIDE)
            return;
        Player p = (Player) e.getWhoClicked();
        Inventory top = p.getOpenInventory().getTopInventory();
        if (top.getName().equals(ChatColor.DARK_AQUA + "Item Modification")) {
            if (e.getCurrentItem().getType().toString().contains("STAINED_GLASS_PANE"))
                e.setCancelled(true);
            if (e.getCurrentItem().hasItemMeta()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "Modify")) {
                    e.setCancelled(true);
                    ItemStack i = top.getItem(TO_REPAIR_SLOT);
                    if (i == null || top.getItem(SCRAP_SLOT) == null)
                        return;


                    //some scrap repair thing
                    if (top.getItem(SCRAP_SLOT).getItemMeta().getDisplayName().contains("scrap")) {


                    	int dur = i.getDurability();
                        ItemStack scraps = top.getItem(SCRAP_SLOT);
                        double rp = 0.03;

                        short data = scraps.getDurability();

                        if (data == 8)
                            rp = 0.04;
                        if (data == 7)
                            rp = 0.07;
                        if (data == 12)
                            rp = 0.11;
                        if (data == 11)
                            rp = 0.15;
                        rp *= scraps.getAmount();


                        if (dur == 0) {
                            p.sendMessage(ChatColor.RED + "That item cannot be repaired.");
                            Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
                            return;
                        }
                        p.sendMessage(ChatColor.GREEN + "Repaired successfully.");
                        Sounds.play(p, Sound.BLOCK_ANVIL_USE, 0.67f);
                        i.setDurability((short) (dur - (int) (rp * i.getType().getMaxDurability())));
                        p.getInventory().addItem(i);
                        e.getInventory().clear();
                        p.closeInventory();
                    } else if (top.getItem(SCRAP_SLOT).getType() == Material.PAPER && top.getItem(SCRAP_SLOT).hasItemMeta()) {
                        if (top.getItem(SCRAP_SLOT).getItemMeta().getDisplayName().contains("Tier " + Items.getTier(i) + " Enchant")) {
                            ItemMeta im = i.getItemMeta();
                            String name = im.getDisplayName();
                            int tier = 0;
                            if (name.contains("[")) {
                                tier = Integer.valueOf(ChatColor.stripColor(name).substring(1, name.indexOf("]") - 2));
                            }
                            if (tier >= 6) {
                                if (Math.random() >= 0.85 - (tier / 100.0)) {
                                    p.sendMessage(MessagesUtil.failedEnchant);
                                    Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
                                    Sounds.play(p, Sound.AMBIENT_CAVE, 0.67f);
                                    e.getInventory().clear();
                                    p.closeInventory();
                                    return;
                                }
                            }
                            im.setDisplayName(ChatColor.AQUA + "[" + (tier + 1) + "] " + name.substring(name.contains("[") ? tier > 9 ? 7 : 6 : 0));
                            double perc = 1.05 + (Math.random() / 8.0);
                            if(Items.getTier(i) == 1)
                                perc = 1.2 + (Math.random() / 5.0);
                            if(Items.getTier(i) == 5)
                                perc = 1.01 + (Math.random() / 10.0);
                            List<String> l = im.getLore();
                            for (int j = 0; j < im.getLore().size(); j++) {
                                String s = l.get(j);
                                if (s.contains(":")) {
                                    String n = "";
                                    int z = 0;
                                    String ss = s.split(" ")[1];
                                    if (ss.contains("+")) {
                                        ss = ss.substring(1);
                                        n = "+";
                                    } else if (ss.contains("%")) {
                                        ss = ss.substring(0, ss.length() - 1);
                                        z = 1;
                                        perc /= 1.1;
                                        perc = Math.max(1, perc);
                                    }
                                    if (ss.contains("-"))
                                        n += (int) Math.round(Integer.valueOf(ss.split("-")[0]) * perc) + "-" + (int) Math.round(Integer.valueOf(ss.split("-")[1]) * perc);
                                    else {
                                        int x = (int) Math.round(Integer.valueOf(ss) * perc);
                                        if(z == 1 && x > 100)
                                            x = 100;
                                        n += x;
                                    }
                                    if (z == 1) n += "%";
                                    l.set(j, ChatColor.RED + s.split(":")[0] + ": " + n);
                                }
                            }
                            im.setLore(l);
                            i.setItemMeta(im);
                            p.sendMessage(ChatColor.GREEN + "Enchantment successful.");
                            Sounds.play(p, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 0.67f);
                            p.getInventory().addItem(i);
                            e.getInventory().clear();
                            p.closeInventory();
                        }
                    }
                }
            }
            new BukkitRunnable() {
                public void run() {
                    ItemStack i = top.getItem(TO_REPAIR_SLOT);
                    if (i != null && top.getItem(SCRAP_SLOT) != null) {
                        if (top.getItem(SCRAP_SLOT).getItemMeta().getDisplayName().contains("Scrap")) {
                            int dur = i.getDurability();
                            ItemStack scraps = top.getItem(SCRAP_SLOT);
                            double rp = 0.03;
                            short data = scraps.getDurability();
                            if (data == 8)
                                rp = 0.04;
                            if (data == 7)
                                rp = 0.07;
                            if (data == 12)
                                rp = 0.11;
                            if (data == 11)
                                rp = 0.15;
                            rp *= scraps.getAmount();
                            top.setItem(16, Items.createItem(i.getType(), 1, (short) (dur - (int) (rp * i.getType().getMaxDurability())), ChatColor.GREEN + "Modify: " + i.getItemMeta().getDisplayName(), i.getItemMeta().getLore()));
                        } else if (top.getItem(SCRAP_SLOT).getType() == Material.PAPER && top.getItem(SCRAP_SLOT).hasItemMeta()) {
                            if (top.getItem(SCRAP_SLOT).getItemMeta().getDisplayName().contains("Tier " + Items.getTier(i) + " Enchant")) {
                                top.setItem(16, Items.createItem(i.getType(), 1, i.getDurability(), ChatColor.GREEN + "Modify: " + i.getItemMeta().getDisplayName(), i.getItemMeta().getLore()));
                                if (top.getItem(SCRAP_SLOT).getAmount() > 1) {
                                    int amount = top.getItem(SCRAP_SLOT).getAmount() - 1;
                                    top.getItem(SCRAP_SLOT).setAmount(1);
                                    ItemStack n = top.getItem(SCRAP_SLOT).clone();
                                    n.setAmount(amount);
                                    p.getInventory().addItem(n);
                                }
                            }
                        }
                    } else {
                        top.setItem(16, null);
                    }
                    p.updateInventory();
                }
            }.runTaskLater(pl, 2);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        Inventory top = p.getOpenInventory().getTopInventory();
        if (top.getName().equals(ChatColor.DARK_AQUA + "Item Modification")) {
            if (top.getItem(TO_REPAIR_SLOT) != null) {
                p.getInventory().addItem(top.getItem(TO_REPAIR_SLOT));
            }
            if (top.getItem(SCRAP_SLOT) != null) {
                p.getInventory().addItem(top.getItem(SCRAP_SLOT));
            }
        }
    }
}
