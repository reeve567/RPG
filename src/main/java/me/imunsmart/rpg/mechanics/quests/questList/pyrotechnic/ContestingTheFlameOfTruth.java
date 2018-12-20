package me.imunsmart.rpg.mechanics.quests.questList.pyrotechnic;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ContestingTheFlameOfTruth extends Quest {

    public static ItemStack torch, firespirit;

    public ContestingTheFlameOfTruth(Main pl) {
        super(pl, "Contesting the Flame of Truth", new String[]{"The pyrotechnic is a real hothead, but", "could he have caused so much trouble?"},
                new String[]{"Please don't throw rocks at me!", "What? Oh so you don't know...", "Maybe you could help me then?", "See, recently I've been accused of a terrible thing.",
                        "I swear it wasn't me though!", "If you could just prove who it was I'd be willing to give you something in return.", "Thank you, thank you, thank you!"},
                ChatColor.GRAY + "Rewards:,-" + ChatColor.YELLOW + " 1000 Experience" + ChatColor.GRAY + ",-" + ChatColor.GRAY + " 4 Standard Potions of Healing," + ChatColor.GRAY +
                        "-" + ChatColor.AQUA + "250 gems (noted)", Quest.MISC);
        torch = Items.createItem(Material.LEGACY_REDSTONE_TORCH_ON, 1, 0, ChatColor.RED + "Red Hot Stick", "A mysterious fiery stick...", "The Pyrotechnic might be interested in this item.", "", ChatColor.YELLOW + getName() + " Quest Item");
        torch.addUnsafeEnchantment(Enchantment.getByName("glow"), 1);
        firespirit = Items.createItem(Material.LEGACY_TOTEM, 1, 0, ChatColor.RED + "Spirit of Fire", "A poor, cursed flame soul.", "The Pyrotechnic would be interested in this item.", "", ChatColor.YELLOW + getName() + " Quest Item");
        firespirit.addUnsafeEnchantment(Enchantment.getByName("glow"), 1);
    }

    @Override
    public void rewardPlayer(Player p) {
        super.rewardPlayer(p);
        Stats.addXP(p, 1000);
        for (int i = 0; i < 4; i++)
            p.getInventory().addItem(Items.createPotion(2));
        p.getInventory().addItem(Items.createGemNote(250));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (e.getBlock().getType() == Material.LEGACY_REDSTONE_TORCH_ON) {
            if (QuestManager.doingQuest(p, this)) {
                if(!p.getInventory().contains(torch)) {
                    p.getInventory().addItem(torch);
                    p.sendMessage(ChatColor.GRAY + "You pluck one of the torches off the ground, an ominous feeling sets in your stomach.");
                }
            }
        }
    }
}
