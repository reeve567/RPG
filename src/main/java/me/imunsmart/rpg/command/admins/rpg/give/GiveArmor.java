package me.imunsmart.rpg.command.admins.rpg.give;

import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GiveArmor {

    public static void run(Player p, String[] args) {
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("random")) {
                String type = args[1];
                int tier = Integer.parseInt(args[2]);
                p.getInventory().addItem(Items.getRandomArmorPiece(tier, type));
                p.updateInventory();
                p.sendMessage(ChatColor.GREEN + "Item created.");
            } else {
                String type = args[0].toLowerCase();
                int tier = Integer.parseInt(args[1]);
                int maxhp = Integer.valueOf(args[2]);
                p.getInventory().addItem(Items.createArmor(type, tier, maxhp, ""));
                p.updateInventory();
                p.sendMessage(ChatColor.GREEN + "Item created.");
            }
        } else if (args.length == 4) {
            Player tp = Bukkit.getPlayer(args[0]);
            String type = args[1].toLowerCase();
            int tier = Integer.parseInt(args[2]);
            int maxhp = Integer.valueOf(args[3]);
            tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, ""));
            tp.updateInventory();
            p.sendMessage(ChatColor.GREEN + "Item created.");
            tp.sendMessage(ChatColor.GREEN + "Item created.");
        } else if (args.length == 5) {
            Player tp = Bukkit.getPlayer(args[0]);
            String type = args[1].toLowerCase();
            int tier = Integer.parseInt(args[2]);
            int maxhp = Integer.valueOf(args[3]);
            tp.getInventory().addItem(Items.createArmor(type, tier, maxhp, args[4]));
            tp.updateInventory();
            p.sendMessage(ChatColor.GREEN + "Item created.");
            tp.sendMessage(ChatColor.GREEN + "Item created.");
        } else {
            p.sendMessage(ChatColor.RED + "Usage: /givearmor [name] <type> <tier> <maxhp> [flags]");
        }
    }
}
