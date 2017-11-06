package me.imunsmart.rpg.command.AdminCommands.PlayerModeration;

import me.imunsmart.rpg.mechanics.Health;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Suicide {
	
	public static void run(Player p, String[] args) {
		if (args.length == 0) {
			Health.health.put(p.getName(), 0);
			p.setHealth(0);
		} else if (args.length == 1) {
			Player tp = Bukkit.getPlayer(args[0]);
			if (tp == null) {
				p.sendMessage(ChatColor.RED + "Invalid player.");
				return;
			}
			Health.health.put(tp.getName(), 0);
			tp.setHealth(0);
		} else {
			p.sendMessage(ChatColor.RED + "/suicide [player]");
		}
	}
	
}
