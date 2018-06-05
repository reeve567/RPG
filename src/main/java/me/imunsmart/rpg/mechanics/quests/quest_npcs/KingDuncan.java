package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestData;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mechanics.quests.questList.farmerbill.FarmerBillsPumpkinProblem;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class KingDuncan extends NPCS.QuestGiver {

    public static String name = ChatColor.GREEN.toString() + ChatColor.BOLD + "King Duncan";

    private static final String[] strings = {
            "Be wary of thieves.",
            "Have fun on your adventures.",
            "Watch out for powerful monsters."
    };
    private static ArrayList<String> quests = new ArrayList<>();
    private static int index = -1;

    public KingDuncan(Location location) {
        super(location, Villager.Profession.PRIEST, "§bKing Duncan", strings);
        quests.add("A Mine Full of Monsters");
    }

    @Override
    protected String setOther() {
        return "king_duncan";
    }

    public void onClick(Player player) {
        if (QuestManager.playerProgress.containsKey(player.getName()) && QuestManager.getProgress(player).getQuest().getName().equalsIgnoreCase(quests.get(0))) {
            Quest q = QuestManager.getProgress(player).getQuest();
            if (QuestManager.getProgress(player).getFlag() >= q.getFlags()) {
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 1]);
                q.rewardPlayer(player);
                Util.launchFirework(player.getLocation(), FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.OLIVE).withFade(Color.GREEN).flicker(true).trail(true).build());
                return;
            } else {
                player.sendMessage(name + ChatColor.WHITE + ": You haven't quite killed enough monsters yet.");
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
        if(index >= strings.length)
            index = 0;
        player.sendMessage(name + ChatColor.WHITE + ": " + strings[index]);
    }
}
