package me.imunsmart.rpg.mechanics.quests.questList.farmerbill;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class MelonTending extends Quest {

    public static ItemStack melon;

    public MelonTending(Main pl) {
        super(pl,"Melon Tending", new String[] { "With so much to harvest this year", "Farmer Bill could really use some help!" },
                new String[] { "Hello there. If you're an adventurer looking for some work, I might have something.", "I have so many crops for this years harvest and so little time!",
                "If you could go out and bring me 10 melons, that would be wonderful!", "Of course I will compensate you somehow...", "Thanks a ton! Here's the reward I promised!" },
                ChatColor.GRAY + "Rewards:,-" + ChatColor.YELLOW + " 100 Experience" + ChatColor.GRAY + ",-" + ChatColor.GOLD + " 2 Lesser Potions of Healing", Quest.MISC);
        melon = Items.createItem(Material.MELON, 1, 0, ChatColor.RED + "Bill's Melon", "I think Farmer Bill might", "be interested in this item.", "", ChatColor.YELLOW + getName() + " Quest Item");
        melon.addUnsafeEnchantment(Enchantment.getByName("glow"), 1);
    }

    @Override
    public void rewardPlayer(Player p) {
        super.rewardPlayer(p);
        Stats.addXP(p, 100);
        p.getInventory().addItem(Items.createPotion(1));
        p.getInventory().addItem(Items.createPotion(1));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        System.out.println(1);
        if(e.getBlock().getType() == Material.MELON_BLOCK) {
            if (QuestManager.doingQuest(p, this)) {
                QuestManager.update.add(e.getBlock().getState());
                e.getBlock().setType(Material.AIR);
                p.getInventory().addItem(melon);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.getBlock().setType(Material.MELON_BLOCK);
                        QuestManager.update.remove(e.getBlock().getState());
                    }
                }.runTaskLater(pl, 200);
            }
        }
    }
}
