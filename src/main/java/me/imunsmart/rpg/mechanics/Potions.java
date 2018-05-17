package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Potions implements Listener {
	public static String[] names = {"Lesser", "Standard", "Greater", "Mystic", "Godly"};
	public static int[] amounts = {50, 100, 250, 500, 1000};
	public static Color[] colors = {Color.ORANGE, Color.GRAY, Color.WHITE, Color.AQUA, Color.YELLOW};
	private Main pl;
	
	public Potions(Main pl) {
		this.pl = pl;
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (Util.validClick(e)) {
			if (e.getItem().getType() == Material.POTION) {
				if (e.getItem().hasItemMeta()) {
					if (e.getItem().getItemMeta().getDisplayName().contains("Potion of Healing")) {
						if (Health.hasAttribute(e.getItem(), "restores")) {
							e.setCancelled(true);
							if (Health.atMaxHealth(p)) {
								p.sendMessage(ChatColor.RED + "You are already healthy.");
								return;
							}
							int restore = Health.getAttributeI(e.getItem(), "restores");
							Health.heal(p, restore);
							Sounds.play(p, Sound.ENTITY_GENERIC_DRINK, 1);
							new ActionBar(ChatColor.GREEN + "+" + restore + "HP").sendToPlayer(p);
							p.getInventory().setItemInMainHand(null);
						}
					}
				}
			}
		}
	}
}
