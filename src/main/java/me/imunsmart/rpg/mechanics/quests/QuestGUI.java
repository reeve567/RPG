package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.mechanics.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class QuestGUI implements Listener {
    private Main pl;

    public QuestGUI(Main pl) {
        this.pl = pl;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public static HashMap<String, Integer> page = new HashMap<>();

    public static void show(Player p, Inventory inv) {
        int i = 0;
        if(page.containsKey(p.getName()))
            i = page.get(p.getName());
        if(inv == null)
            inv = Bukkit.createInventory(null, 54, ChatColor.DARK_AQUA + "Quests");
        else
            inv.clear();
        for(int j = 0; j < 9; j++)
            inv.setItem(j, Items.createItem(Material.STAINED_GLASS_PANE, 1, 15, " "));
        inv.setItem(0, Items.createItem(Material.CARPET, 1, 14, ChatColor.RED + "<- Prev"));
        inv.setItem(8, Items.createItem(Material.CARPET, 1, 13, ChatColor.DARK_GREEN + "Next ->"));
        String curQuest = QuestManager.playerProgress.containsKey(p.getName()) ? QuestManager.getProgress(p).getQuest().getName() : "none";
        inv.setItem(4, Items.createItem(Material.BOOK, 1, 0, ChatColor.GRAY + "Current Quest: " + ChatColor.AQUA + curQuest, "All quests in order of increasing", "difficulty. Glowing quests have", "already been completed."));
        for(int j = (i * 45); j < (i * 45) + 45; j++) {
            if(j >= QuestManager.quests.size())
                break;
            Quest q = QuestManager.quests.get(j);
            ChatColor c = ChatColor.RED;
            if(Stats.getCompletedQuests(p).contains(q.getName()))
                c = ChatColor.GREEN;
            if(curQuest.equalsIgnoreCase(q.getName()))
                c = ChatColor.YELLOW;
            List<String> d = new ArrayList<>();
            for(String s : q.getDescription())
                d.add(s);
            d.add(" ");
            for(String s : q.getRewards().split(","))
                d.add(s);
            String[] desc = new String[d.size()];
            for(int k = 0; k < desc.length; k++) {
                desc[k] = d.get(k);
            }
            ItemStack x = Items.createItem(Material.BOOK, 1, 0, c + q.getName(), desc);
            if(Stats.getCompletedQuests(p).contains(q.getName()))
                x.addUnsafeEnchantment(Enchantment.getByName("glow"), 1);
            inv.setItem(9 + j, x);
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        Player p = (Player) e.getWhoClicked();
        if (e.getCurrentItem() == null) return;
        if (!e.getCurrentItem().hasItemMeta()) return;
        if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;

        if(!e.getInventory().getTitle().contains("Quests"))
            return;
        e.setCancelled(true);
        int pg = page.containsKey(p.getName()) ? page.get(p.getName()) : 0;
        if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName()) {
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("<- Prev")) {
                Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
                pg--;
                if (pg <= 0)
                    pg = 0;
                page.put(p.getName(), pg);
                show(p, e.getInventory());
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Page: " + (pg + 1))) {
                Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
                page.put(p.getName(), 0);
                show(p, e.getInventory());
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Next ->")) {
                Sounds.play(p, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1);
                pg++;
                if (pg * 45 >= QuestManager.quests.size())
                    pg--;
                page.put(p.getName(), pg);
                show(p, e.getInventory());
                return;
            }
        }
    }
}
