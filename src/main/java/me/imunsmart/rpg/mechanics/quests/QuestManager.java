package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.questList.farmerbill.FarmerBillsPumpkinProblem;
import me.imunsmart.rpg.mechanics.quests.questList.kingduncan.AMineFullOfMonsters;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestManager {

    public static List<Quest> quests = new ArrayList<>();
    public static HashMap<String, QuestData> playerProgress = new HashMap<>();

    public QuestManager(Main pl) {
        loadQuests(pl);
    }

    public static Quest getQuest(String name) {
        for (Quest q : quests) {
            if (q.getName().equalsIgnoreCase(name)) {
                return q;
            }
        }
        return null;
    }

    public static QuestData getProgress(Player p) {
        return playerProgress.get(p.getName());
    }

    public static boolean doingQuest(Player p, Quest q) {
        if(!playerProgress.containsKey(p.getName())) return false;
        System.out.println(q);
        return playerProgress.get(p.getName()).getQuest().getName().equals(q.getName());
    }

    public static void loadProgress(Player p) {
        System.out.println(p.getName());
        Quest q = getQuest(Stats.getString(p, "current-quest.name"));
        int flag = Stats.getInt(p, "current-quest.progress");
        if(q != null) {
            playerProgress.put(p.getName(), new QuestData(q, flag));
        }
    }

    private void loadQuests(Main pl) {
        quests.add(new FarmerBillsPumpkinProblem(pl));
        quests.add(new AMineFullOfMonsters(pl));
    }

    public static void disable() {
        for (String s : playerProgress.keySet()) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(s);
            Stats.setStat(op, "current-quest.name", playerProgress.get(s).getQuest().getName());
            Stats.setStat(op, "current-quest.progress", playerProgress.get(s).getFlag());
        }
    }
}
