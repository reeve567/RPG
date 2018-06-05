package me.imunsmart.rpg.mechanics.quests.questList.kingduncan;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.quests.Quest;
import org.bukkit.entity.Player;

public class AMineFullOfMonsters extends Quest {

    public AMineFullOfMonsters(Main pl) {
        super(pl, "A Mine Full of Monsters",
                new String[] { "The royal mine has an infestation", "of creepy monsters. Help the King", "solve the situation." },
                new String[] { "Hello there! You look like a fine adventurer", "In that case, perhaps you can be of some assistance" });
    }

    @Override
    public void rewardPlayer(Player p) {

    }
}
