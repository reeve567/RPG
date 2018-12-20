package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.MessagesUtil;
import me.imunsmart.rpg.util.Sounds;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Bank implements Listener {
    private static int[] upgradeCosts = {200, 500, 1000, 2500, 5000};
    private HashMap<String, Boolean> withdraw = new HashMap<>();
    private List<String> upgrade = new ArrayList<>();
    private Main pl;

    public Bank(Main pl) {
        this.pl = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public static boolean hasSpace(Player p, int slots) {
        int free = 0; // account for 5 slots always empty
        for (int i = 0; i < 36; i++) {
            if (p.getInventory().getItem(i) == null)
                free++;
        }
        return free >= slots;
    }

    public static boolean pay(Player p, int gems) {
        if (getGems(p) < gems)
            return false;
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (gems == 0)
                break;
            if (p.getInventory().getItem(i) != null) {
                ItemStack it = p.getInventory().getItem(i);
                if (it.getType() == Material.DIAMOND) {
                    if (gems > it.getAmount()) {
                        gems -= it.getAmount();
                        p.getInventory().setItem(i, null);
                    } else {
                        it.setAmount(it.getAmount() - gems);
                        gems = 0;
                        break;
                    }
                } else if (it.getType() == Material.LEGACY_EMPTY_MAP) {
                    if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
                        int value = Integer.valueOf(ChatColor.stripColor(it.getItemMeta().getLore().get(0).split(" ")[1].trim()));
                        if (value > gems) {
                            ItemMeta im = it.getItemMeta();
                            im.setLore(Arrays.asList(ChatColor.GRAY + "Value: " + (value - gems)));
                            it.setItemMeta(im);
                            p.updateInventory();
                            gems = 0;
                            break;
                        } else {
                            gems -= value;
                            if (it.getAmount() > 1)
                                it.setAmount(it.getAmount() - 1);
                            else
                                p.getInventory().setItem(i, null);
                        }
                    }
                }
            }
        }
        p.updateInventory();
        return true;
    }

    public static int getGems(Player p) {
        int gems = 0;
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (p.getInventory().getItem(i) != null) {
                ItemStack it = p.getInventory().getItem(i);
                if (it.getType() == Material.DIAMOND) {
                    gems += p.getInventory().getItem(i).getAmount();
                } else if (it.getType() == Material.LEGACY_EMPTY_MAP) {
                    if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
                        gems += Integer.valueOf(ChatColor.stripColor(it.getItemMeta().getLore().get(0).split(" ")[1].trim())) * it.getAmount();
                    }
                }
            }
        }
        return gems;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (withdraw.containsKey(p.getName())) {
            e.setCancelled(true);
            boolean wd = true;
            int gems = 0;
            try {
                gems = Integer.valueOf(e.getMessage());
            } catch (Exception e2) {
                wd = false;
            }
            int pg = Stats.getInt(p, "gems");
            if (gems > pg) {
                wd = false;
            }
            if (wd) {
                if (withdraw.get(p.getName())) {
                    ItemStack i = Items.createGemNote(gems);
                    p.getInventory().addItem(i);
                    p.sendMessage(MessagesUtil.bankWithdrawSuccess(gems));
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
                    Stats.setStat(p, "gems", pg - gems);
                } else {
                    int total = gems;
                    int stacks = gems / 64;
                    gems -= stacks * 64;
                    int space = stacks;
                    if (gems > 0)
                        space++;
                    if (!hasSpaceGems(p, total)) {
                        withdraw.remove(p.getName());
                        p.sendMessage(MessagesUtil.notEnoughSpace);
                        return;
                    }
                    for (int i = 0; i < stacks; i++) {
                        p.getInventory().addItem(Items.createGems(64));
                    }
                    p.getInventory().addItem(Items.createGems(gems));
                    p.sendMessage(ChatColor.GRAY + "Withdrew a total of " + ChatColor.AQUA + (stacks * 64 + gems) + " gems.");
                    Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
                    Stats.setStat(p, "gems", pg - total);
                }
                withdraw.remove(p.getName());
            } else {
                p.sendMessage(MessagesUtil.bankEnterAmountFailure);
                withdraw.remove(p.getName());
                Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
            }
        }
        if (upgrade.contains(p.getName())) {
            e.setCancelled(true);
            upgrade.remove(p.getName());
            if (e.getMessage().contains("confirm")) {
                int gems = Stats.getInt(p, "gems");
                int size = Stats.getInt(p, "bank.size", 1);
                int cost = upgradeCosts[size - 1];
                Stats.setStat(p, "gems", gems - cost);
                Stats.setStat(p, "bank.size", size + 1);
                p.sendMessage(MessagesUtil.bankUpgradeSuccess);
                Sounds.play(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2);
            } else {
                p.sendMessage(MessagesUtil.bankUpgradeFailure);
                Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
            }
        }
    }

    public static boolean hasSpaceGems(Player p, int gems) {
        int free = 0;
        for (int i = 0; i < 36; i++) {
            if (p.getInventory().getItem(i) == null)
                free += 64;
            else if (p.getInventory().getItem(i).getType() == Material.DIAMOND) {
                if (p.getInventory().getItem(i).hasItemMeta() && p.getInventory().getItem(i).getItemMeta().getDisplayName().contains("Gem"))
                    free += 64 - p.getInventory().getItem(i).getAmount();
            }
        }
        return free >= gems;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getSlotType() == SlotType.OUTSIDE) return;
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

        if (e.getInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank")) {
            e.setCancelled(true);
            if (!e.getCurrentItem().getItemMeta().hasDisplayName())
                return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Gems: ")) {
                p.sendMessage(MessagesUtil.bankEnterAmount);
                withdraw.put(p.getName(), e.getClick() == ClickType.SHIFT_LEFT);
                p.closeInventory();
                return;
            }
            if (e.getClick() == ClickType.SHIFT_LEFT) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gem") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Bank Note")) {
                    depositGems(p);
                    p.closeInventory();
                    open(p);
                    return;
                }
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Storage")) {
                if (e.getClick() == ClickType.LEFT) {
                    openBank(p);
                }
            }
        }
        if (e.getInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank Storage")) {
            if (!e.getCurrentItem().getItemMeta().hasDisplayName())
                return;
            if (e.getClick() == ClickType.SHIFT_LEFT) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Gem") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Bank Note")) {
                    e.setCancelled(true);
                    depositGems(p);
                    p.closeInventory();
                    open(p);
                    return;
                }
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Upgrade")) {
                e.setCancelled(true);
                p.closeInventory();
                int gems = Stats.getInt(p, "gems");
                int size = Stats.getInt(p, "bank.size", 1);
                int cost = upgradeCosts[size - 1];
                if (gems >= cost) {
                    upgrade.add(p.getName());
                    p.sendMessage(MessagesUtil.bankUpgradeConfirm(cost));
                } else {
                    p.sendMessage(MessagesUtil.bankUpgradeNotEnoughGems(cost));
                }
            }
        }
    }

    public static void depositGems(Player p) {
        int gems = getGems(p);
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            if (p.getInventory().getItem(i) != null) {
                ItemStack it = p.getInventory().getItem(i);
                if (it.getType() == Material.DIAMOND) {
                    p.getInventory().remove(it);
                } else if (it.getType() == Material.LEGACY_EMPTY_MAP) {
                    if (it.hasItemMeta() && it.getItemMeta().getDisplayName().contains("Bank Note")) {
                        p.getInventory().remove(it);
                    }
                }
            }
        }
        p.sendMessage(ChatColor.GRAY + "Deposited " + ChatColor.AQUA + gems + ChatColor.GRAY + " gems.");
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
        p.updateInventory();
        Stats.addStat(p, "gems", gems);
    }

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GREEN + p.getName() + "'s Bank");
        int gems = Stats.getInt(p, "gems");
        inv.setItem(4, Items.createItem(Material.DIAMOND, 1, 0, ChatColor.AQUA + "Gems: " + gems, "Left click to withdraw.", "Shift-left to withdraw a note.", "Shift-click gems in your inventory", "to deposit them."));
        inv.setItem(8, Items.createItem(Material.CHEST, 1, 0, ChatColor.GOLD + "Storage", "Left click to open."));
        p.openInventory(inv);
    }

    public static void openBank(Player p) {
        int size = Stats.getInt(p, "bank.size", 1);
        int op = size * 9 + 9;
        if (size == 6)
            op = 54;
        Inventory inv = Bukkit.createInventory(null, op, ChatColor.GREEN + p.getName() + "'s Bank Storage");
        for (String s : Stats.getKey(p, "bank.storage").getKeys(false)) {
            inv.setItem(Integer.valueOf(s), Stats.getItem(p, "bank.storage." + s));
        }
        if (size < 6) {
            for (int i = 9; i > 0; i--) {
                //TODO: CHECK COLOR
                inv.setItem(inv.getSize() - i, Items.createItem(Material.GREEN_STAINED_GLASS_PANE, 1, 0, ChatColor.GREEN + "Upgrade (Gems: " + upgradeCosts[size - 1] + ")", "Click to upgrade your bank."));
            }
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getTitle().equals(ChatColor.GREEN + p.getName() + "'s Bank Storage")) {
            for(int i = 0; i < e.getInventory().getSize() - 9; i++) {
                Stats.setStat(p, "bank.storage." + i, e.getInventory().getItem(i));
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Util.moved(e.getFrom(), e.getTo())) {
            if (withdraw.containsKey(p.getName())) {
                p.sendMessage(MessagesUtil.moveCancel("withdraw"));
                withdraw.remove(p.getName());
            } else if (upgrade.contains(p.getName())) {
                p.sendMessage(MessagesUtil.moveCancel("upgrade"));
                upgrade.remove(p.getName());
            }
        }
    }
}
