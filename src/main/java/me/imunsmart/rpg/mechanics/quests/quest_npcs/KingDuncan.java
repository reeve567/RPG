package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

public class KingDuncan extends NPCS.QuestGiver {

    private static final String[] strings = {
            "Don't die!",
            "Have fun on your adventures.",
            "Watch out for powerful monsters."
    };
    private static ArrayList<String> quests = new ArrayList<>();
    private static int index = -1;

    public KingDuncan(Location location) {
        super(location, Villager.Profession.PRIEST, "§bKing Duncan",
                strings
        );
        setQuests();
    }

    private static void setQuests() {
        quests.add("KingDuncanFirstTask");
    }

    @Override
    protected String setOther() {
        return "king_duncan";
    }

    public void onClick(Player player) {

    }
}
