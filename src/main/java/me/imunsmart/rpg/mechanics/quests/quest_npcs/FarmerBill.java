package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestData;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mechanics.quests.questList.farmerbill.FarmerBillsPumpkinProblem;
import me.imunsmart.rpg.mechanics.quests.questList.farmerbill.MelonTending;
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

public class FarmerBill extends NPCS.QuestGiver {

    private static String name = ChatColor.GOLD.toString() + ChatColor.BOLD + "Farmer Bill";

    private static final String[] strings = {
            "I've got plenty of fruits and veggies!",
            "I'd love to go on your adventures.",
            "What all is out there?"
    };

    private static ArrayList<String> quests = new ArrayList<>();
    private static int index = -1;

    public FarmerBill(Location location) {
        super(location, Villager.Profession.FARMER, "§aFarmer Bill", strings);
        quests.add("Melon Tending");
        quests.add("Farmer Bill's Pumpkin Problem");
    }

    @Override
    protected String setOther() {
        return "farmer_bill";
    }


    @Override
    public void onClick(Player player) {
        if (QuestManager.playerProgress.containsKey(player.getName()) && QuestManager.getProgress(player).getQuest().getName().equalsIgnoreCase(quests.get(0))) {
            Quest q = QuestManager.getProgress(player).getQuest();
            if (player.getInventory().containsAtLeast(MelonTending.melon, 10)) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).hasItemMeta())
                        if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equals(MelonTending.melon.getItemMeta().getDisplayName()))
                            player.getInventory().setItem(i, null);
                }
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 1]);
                q.rewardPlayer(player);
                Util.launchFirework(player.getLocation(), FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.OLIVE).withFade(Color.GREEN).flicker(true).trail(true).build());
                return;
            } else {
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 2]);
                return;
            }
        } else if (QuestManager.playerProgress.containsKey(player.getName()) && QuestManager.getProgress(player).getQuest().getName().equalsIgnoreCase(quests.get(1))) {
            Quest q = QuestManager.getProgress(player).getQuest();
            if (player.getInventory().containsAtLeast(FarmerBillsPumpkinProblem.pumpkin, 1)) {
                for (int i = 0; i < player.getInventory().getSize(); i++) {
                    if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).hasItemMeta())
                        if (player.getInventory().getItem(i).getItemMeta().getDisplayName().equals(FarmerBillsPumpkinProblem.pumpkin.getItemMeta().getDisplayName()))
                            player.getInventory().setItem(i, null);
                }
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 1]);
                q.rewardPlayer(player);
                Util.launchFirework(player.getLocation(), FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.OLIVE).withFade(Color.GREEN).flicker(true).trail(true).build());
                return;
            } else {
                player.sendMessage(name + ChatColor.WHITE + ": " + q.getDialogs()[q.getDialogs().length - 2]);
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
