package me.imunsmart.rpg.mechanics.quests.questList.kingduncan;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AMineFullOfMonsters extends Quest {

    public AMineFullOfMonsters(Main pl) {
        super(pl, "A Mine Full of Monsters",
                new String[] { "The royal mine has an infestation", "of creepy monsters. Help the King", "solve the situation." },
                new String[] { "Hello there! You look like a fine adventurer.", "In that case, perhaps you can be of some assistance.", "See our mine seems to be infected with a plague of monsters.",
                "Normally I would send someone more qualified but I can't afford to lose any more men.", "Anyway good luck and don't die!", "You actually did it!? I mean... Please take this as thanks." }, Quest.KILL);
        setFlags(10);
    }

    @Override
    public void rewardPlayer(Player p) {
        super.rewardPlayer(p);
        Stats.addXP(p, 300);
        p.getInventory().addItem(Items.createWeapon("axe", 1, 5, 9, "Critical:2%,uncommon"));
        p.sendMessage(ChatColor.GRAY + "Rewards:\n-" + ChatColor.YELLOW + " 300 Experience" + ChatColor.GRAY + "\n-" + ChatColor.GOLD + " Tier 1 Axe");
    }
}
