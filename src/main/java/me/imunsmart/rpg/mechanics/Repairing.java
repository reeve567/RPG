package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.gui.ItemMenu;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class Repairing implements Listener {
    private HashMap<String, ItemStack> cancel = new HashMap<>();
    private HashMap<String, Item> remove = new HashMap<>();
    private HashMap<String, Block> anvil = new HashMap<>();
    private Main pl;
    private int[] costs = {64, 128, 256, 384, 512};

    public Repairing(Main pl) {
        this.pl = pl;
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

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Util.validClick(e)) {
            if (e.getItem().hasItemMeta()) {
                if (e.getItem().getItemMeta().getDisplayName().contains("Scrap") || e.getItem().getItemMeta().getDisplayName().contains("Enchant")) {
                    ItemMenu.open(p);
                    return;
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.FURNACE) {
                e.setCancelled(true);
                if (e.getItem() != null) {
                    if (e.getItem().getType().name().contains("HELMET") || e.getItem().getType().name().contains("CHESTPLATE") || e.getItem().getType().name().contains("LEGGINGS") || e.getItem().getType().name().contains("BOOTS") || e.getItem().getType().name().contains("SWORD") || e.getItem().getType().name().contains("AXE")) {
                        if (!p.isSneaking()) {
                            p.sendMessage(ChatColor.RED + "Shift-click a furnace with armor or weapons to smelt it into scraps (WARNING: CANNOT BE UNDONE).");
                        } else {
                            if (e.getItem().containsEnchantment(Enchantment.VANISHING_CURSE)) {
                                p.sendMessage(ChatColor.RED + "You cannot smelt cursed gear!");
                                p.playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 0.67f);
                                return;
                            }
                            p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 1, 0.67f);
                            Items.convertToScraps(p);
                        }
                    }
                }
            } else if (e.getClickedBlock().getType() == Material.ANVIL) {
                e.setCancelled(true);
                if (e.getItem() != null) {
                    if (e.getItem().getType().name().contains("HELMET") || e.getItem().getType().name().contains("CHESTPLATE") || e.getItem().getType().name().contains("LEGGINGS") || e.getItem().getType().name().contains("BOOTS") || e.getItem().getType().name().contains("SWORD") || e.getItem().getType().name().contains("AXE")) {
                        ItemStack old = e.getItem();
                        double perc = (double) old.getDurability() / old.getType().getMaxDurability();
                        if (old.getDurability() == 0) {
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
                        return;
                    }
                }
                p.sendMessage(ChatColor.GRAY + "Right click the anvil with a tool or scraps to repair.");
            }
        }
    }
}
