package me.imunsmart.rpg.command.admins.rpg.give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveTool {

    public static void run(Player p, String[] args) {
        if (args.length == 1) {
            int tier = Integer.parseInt(args[0]);
            p.getInventory().addItem(Items.createPickaxe(tier, 0, ""));
            p.updateInventory();
            p.sendMessage(ChatColor.GREEN + "Item created.");
        } else if (args.length == 2) {
            Player tp = Bukkit.getPlayer(args[0]);
            int tier = Integer.parseInt(args[1]);
            tp.getInventory().addItem(Items.createPickaxe(tier, 0, ""));
            tp.updateInventory();
            p.sendMessage(ChatColor.GREEN + "Item created.");
            tp.sendMessage(ChatColor.GREEN + "Item created.");
        } else if (args.length == 3) {
            Player tp = Bukkit.getPlayer(args[0]);
            int tier = Integer.parseInt(args[1]);
            tp.getInventory().addItem(Items.createPickaxe(tier, 0, args[2]));
            tp.updateInventory();
            p.sendMessage(ChatColor.GREEN + "Item created.");
            tp.sendMessage(ChatColor.GREEN + "Item created.");
        } else {
            p.sendMessage(ChatColor.RED + "Usage: /givetool [name] <type> <tier> [flags]");
        }
    }

}
