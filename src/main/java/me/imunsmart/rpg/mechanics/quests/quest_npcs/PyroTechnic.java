package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestData;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mechanics.quests.questList.farmerbill.MelonTending;
import me.imunsmart.rpg.mechanics.quests.questList.pyrotechnic.ContestingTheFlameOfTruth;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.mobs.Mob;
import me.imunsmart.rpg.util.Util;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PyroTechnic extends NPCS.QuestGiver {

    private static String name = ChatColor.RED.toString() + ChatColor.BOLD + "PyroTechnic";

    private static final String[] strings = {
            "Hey kid... you wanna buy some fireworks?",
            "People say I'm a hothead but I just like fire.",
            "How long you think the forest would take to finally burn down?"
    };

    private static ArrayList<String> quests = new ArrayList<>();
    private static int index = -1;

    public PyroTechnic(Location location) {
        super(location, Villager.Profession.FARMER, name, strings);
        quests.add("Contesting the Flame of Truth");
    }

    @Override
    protected String setOther() {
        return "pyro_technic";
    }


    @Override
    public void onClick(Player player) {
        if (QuestManager.playerProgress.containsKey(player.getName()) && QuestManager.getProgress(player).getQuest().getName().equalsIgnoreCase(quests.get(0))) {
            Quest q = QuestManager.getProgress(player).getQuest();
            if (player.getInventory().containsAtLeast(ContestingTheFlameOfTruth.torch, 1)) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).hasItemMeta())
                        if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equals(ContestingTheFlameOfTruth.torch.getItemMeta().getDisplayName()))
                            player.getInventory().setItem(i, null);
                }
                player.sendMessage(name + ChatColor.WHITE + ": Please stand back, I'm going to try an ancient ritual! Let's hope this works!");
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        getLocation().getWorld().playEffect(getLocation(), Effect.SMOKE, 0);
                        Sounds.play(player, Sound.ENTITY_GENERIC_EXPLODE, 0.67f);
                        Skeleton s = getLocation().getWorld().spawn(getLocation(), Skeleton.class);
                        Mob mob = EntityManager.customMob(s, ChatColor.RED + "PyroManiac", 3, "axe", 30, 90, "Fire Damage:+10,Rare",
                                240, 480, 400, 192, "Regen:10", "Regen:20", "Regen:15", "Regen:10", "Prestonplayz");
                        mob.addDrop(ContestingTheFlameOfTruth.firespirit, 1);
                        QuestManager.getProgress(player).incrementFlag();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!EntityManager.mobs.containsKey(mob.getMob().getUniqueId())) {
                                    cancel();
                                    return;
                                }
                                if (!player.isOnline() || getLocation().distanceSquared(player.getLocation()) >= (40 * 40)) {
                                    mob.getMob().remove();
                                    EntityManager.mobs.remove(mob.getMob().getUniqueId());
                                    cancel();
                                    return;
                                }
                            }
                        }.runTaskTimer(EntityManager.pl, 0, 20);
                    }
                }.runTaskLater(EntityManager.pl, 40);
                return;
            } else if (player.getInventory().containsAtLeast(ContestingTheFlameOfTruth.firespirit, 1)) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).hasItemMeta())
                        if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equals(ContestingTheFlameOfTruth.firespirit.getItemMeta().getDisplayName()))
                            player.getInventory().setItem(i, null);
                }
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 1]);
                q.rewardPlayer(player);
                Util.launchFirework(player.getLocation(), FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.OLIVE).withFade(Color.GREEN).flicker(true).trail(true).build());
                return;
            } else {
                player.sendMessage(name + ChatColor.WHITE + ": Any information yet?");
                return;
            }
        } else {
            for (String s : quests) {
                if (!Stats.getCompletedQuests(player).contains(s)) {
                    if (QuestManager.playerProgress.containsKey(player.getName())) {
                        player.sendMessage(name + ChatColor.WHITE + ": Sorry! You seem too busy to help me!");
                        return;
                    } else {
                        Quest q = QuestManager.getQuest(s);
                        QuestManager.playerProgress.put(player.getName(), new QuestData(q, 0));
                        new BukkitRunnable() {
                            int i = 0;

                            @Override
                            public void run() {
                                if (i < q.getDialogs().length - 1) {
                                    player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[i]);
                                    i++;
                                } else
                                    cancel();
                            }
                        }.runTaskTimer(EntityManager.pl, 0, 40);
                        return;
                    }
                }
            }
        }
        speak(player);
    }

    public void speak(Player player) {
        index++;
        if (index >= strings.length)
            index = 0;
        player.sendMessage(name + ChatColor.WHITE + ": " + strings[index]);
    }
}
