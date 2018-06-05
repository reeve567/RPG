package me.imunsmart.rpg.command.defaults;

import me.imunsmart.rpg.mechanics.quests.QuestGUI;
import org.bukkit.entity.Player;

public class QuestLog {

    public static void run(Player p) {
        QuestGUI.show(p, null);
    }

}
