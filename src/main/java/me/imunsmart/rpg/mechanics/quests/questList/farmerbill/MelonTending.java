package me.imunsmart.rpg.mechanics.quests.questList.farmerbill;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.mobs.Mob;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
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
        if(e.getBlock().getType() == Material.MELON) {
            if (QuestManager.doingQuest(p, this)) {
                QuestManager.update.add(e.getBlock().getState());
                e.getBlock().setType(Material.AIR);
                p.getInventory().addItem(melon);
                if(Math.random() < 0.2) {
                    Location l = e.getBlock().getLocation();
                    Mob m = EntityManager.customMob(p.getWorld().spawn(e.getBlock().getLocation(), Zombie.class), ChatColor.AQUA + "Melon Felon", 1, "axe", 1, 3, "Critical:10%",
                            0, 0, 0, 0, "", "", "", "", "ImUnsmart");
                    m.getMob().getEquipment().setHelmet(new ItemStack(Material.LEGACY_MELON_BLOCK));
                    ((Zombie) m.getMob()).setBaby(true);
                    m.invalidateDrop(true, true, true, true, false);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(!EntityManager.mobs.containsKey(m.getMob().getUniqueId())) {
                                cancel();
                                return;
                            }
                            if(!p.isOnline() || l.distanceSquared(p.getLocation()) >= (40 * 40)) {
                                m.getMob().remove();
                                EntityManager.mobs.remove(m.getMob().getUniqueId());
                                cancel();
                                return;
                            }
                        }
                    }.runTaskTimer(EntityManager.pl, 0, 20);
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        e.getBlock().setType(Material.LEGACY_MELON_BLOCK);
                        QuestManager.update.remove(e.getBlock().getState());
                    }
                }.runTaskLater(pl, 200);
            }
        }
    }
}
