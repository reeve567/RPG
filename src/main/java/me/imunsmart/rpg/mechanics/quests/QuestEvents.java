package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QuestEvents {
    public static void handleKill(Player p) {
        if(QuestManager.playerProgress.containsKey(p.getName())) {
            QuestData qd = QuestManager.getProgress(p);
            if(qd.getQuest().getType()== Quest.KILL && qd.getFlag() < qd.getQuest().getFlags()) {
                qd.incrementFlag();
                p.sendMessage(ChatColor.AQUA + qd.getQuest().getName() + ChatColor.GRAY + " progress: " + ChatColor.AQUA + qd.getFlag() + "/" + qd.getQuest().getFlags());
            }
        }
    }
}
