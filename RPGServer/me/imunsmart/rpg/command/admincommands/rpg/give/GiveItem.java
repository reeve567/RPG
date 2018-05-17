package me.imunsmart.rpg.command.admincommands.rpg.give;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Potions;
import net.md_5.bungee.api.ChatColor;

public class GiveItem {

	public static void run(Player p, String[] args) {
		Player tp = p;
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("potion")) {
				int tier = Integer.parseInt(args[1]);
				ItemStack i = Items.createItem(Material.POTION, 1, 0, Items.nameColor[tier - 1] + Potions.names[tier - 1] + " Potion of Healing", Arrays.asList("Restores: " + Potions.amounts[tier - 1]));
				PotionMeta pm = (PotionMeta) i.getItemMeta();
				pm.setColor(Potions.colors[tier - 1]);
				i.setItemMeta(pm);
				tp.getInventory().addItem(i);
				p.sendMessage(ChatColor.GREEN + "Item created successfully.");
			}
		} else {
			p.sendMessage(ChatColor.RED + "Usage: /giveitem <type> <data>");
		}
	}

}
