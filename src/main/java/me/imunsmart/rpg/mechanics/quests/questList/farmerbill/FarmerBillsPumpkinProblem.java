package me.imunsmart.rpg.mechanics.quests.questList.farmerbill;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.ActionBar;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FarmerBillsPumpkinProblem extends Quest {

    public static ItemStack pumpkin;

    public FarmerBillsPumpkinProblem(Main pl) {
        super(pl,"Farmer Bill's Pumpkin Problem", new String[] {"A strange monster has stolen", "Farmer Bill's prized pumpkin!", "Help him get it back before", "the big competition!" },
                new String[] { "Please help me. I've got a terrible issue.", "There's a crazed beast who stole my largest pumpkin!", "I need that pumpkin for the competition tomorrow!",
                        "Please help me get my pumpkin back!", "Thank you so much! Please take this as thanks." },
                ChatColor.GRAY + "Rewards:,-" + ChatColor.YELLOW + " 225 Experience" + ChatColor.GRAY + ",-" + ChatColor.AQUA + " 50 Gems (noted)", Quest.MISC);
        pumpkin = Items.createItem(Material.PUMPKIN, 1, 0, ChatColor.GOLD + "Enormous Pumpkin", "I think Farmer Bill might", "be interested in this item.", "", ChatColor.YELLOW + getName() + " Quest Item");
    }

    @Override
    public void rewardPlayer(Player p) {
        super.rewardPlayer(p);
        p.getInventory().addItem(Items.createGemNote(50));
        Stats.addXP(p, 225);
    }
}
