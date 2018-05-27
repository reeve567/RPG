package me.imunsmart.rpg.command;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admincommands.playermoderation.BanManager;
import me.imunsmart.rpg.command.admincommands.playermoderation.InventoryC;
import me.imunsmart.rpg.command.admincommands.playermoderation.Kick;
import me.imunsmart.rpg.command.admincommands.playermoderation.Suicide;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Moderator implements CommandExecutor {
    private Main pl;

    public Moderator(Main pl) {
        this.pl = pl;
        pl.getCommand("kick").setExecutor(this);
        pl.getCommand("suicide").setExecutor(this);
        pl.getCommand("inventory").setExecutor(this);
        pl.getCommand("inv").setExecutor(this);
        pl.getCommand("ban").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("rpg.moderator")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do that.");
            return false;
        }
        if (label.equalsIgnoreCase("kick")) {
            Kick.run(sender, args);
            return false;
        } else if(label.equalsIgnoreCase("ban")) {
            BanManager.run(sender, args);
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to do that.");
            return true;
        }
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("inventory") || label.equalsIgnoreCase("inv")) {
            InventoryC.run(p, args);
        } else if(label.equalsIgnoreCase("suicide")) {
            Suicide.run(p, args);
        }
        return false;
    }
}