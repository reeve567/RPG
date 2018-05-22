package me.imunsmart.rpg.command.admincommands.rpg.give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class GiveItem {

    public static void run(Player p, String[] args) {
        Player tp = p;
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("potion")) {
                int tier = Integer.parseInt(args[1]);
                tp.getInventory().addItem(Items.createPotion(tier));
                p.sendMessage(ChatColor.GREEN + "Item created successfully.");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("scroll")) {
                tp.getInventory().addItem(Items.createTeleportScroll(Integer.parseInt(args[2]), args[1], 5));
                p.sendMessage(ChatColor.GREEN + "Item created successfully.");
            }
        } else {
            p.sendMessage(ChatColor.RED + "Usage: /giveitem <type> <data>");
        }
    }

}
