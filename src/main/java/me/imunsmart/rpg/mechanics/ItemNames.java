package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import java.util.Iterator;

public class ItemNames implements Listener {
	
	//ItemMergeEvent -- ItemSpawnEvent
	
	
	@EventHandler
	public void onMerge(ItemMergeEvent e) {
		Item item = e.getTarget();
		Item i = e.getEntity();
		int a = i.getItemStack().getAmount() + item.getItemStack().getAmount();
		String count = Main.main.getConfig().getString("stack-count").replaceAll("%amount%", "" + a);
		item.setCustomName(pickName(item) + ChatColor.translateAlternateColorCodes('&', count));
	}
	
	public String pickName(Item item) {
		if (item.getItemStack().hasItemMeta() && item.getItemStack().getItemMeta().hasDisplayName()) {
			item.setCustomName(item.getItemStack().getItemMeta().getDisplayName());
			return ChatColor.translateAlternateColorCodes('&', item.getCustomName());
		} else {
			Iterator var3 = Main.main.getConfig().getConfigurationSection("item-names").getKeys(false).iterator();
			
			String keys;
			do {
				if (!var3.hasNext()) {
					return ChatColor.translateAlternateColorCodes('&', item.getCustomName());
				}
				
				keys = (String) var3.next();
			} while (item.getItemStack().getType() != Material.valueOf(keys));
			
			for (String ik : Main.main.getConfig().getConfigurationSection("item-names." + keys).getKeys(false)) {
				if (item.getItemStack().getDurability() == Short.valueOf(ik)) {
					return ChatColor.translateAlternateColorCodes('&', Main.main.getConfig().getString("item-names." + keys + "." + ik));
				}
			}
			
			return ChatColor.translateAlternateColorCodes('&', Main.main.getConfig().getString("item-names." + keys + ".0"));
		}
	}
	
	@EventHandler
	public void onSpawn(ItemSpawnEvent e) {
		Item item = e.getEntity();
		item.setCustomNameVisible(true);
		item.setCustomName(pickName(item));
		if (item.getItemStack().getAmount() > 1) {
			int a = item.getItemStack().getAmount();
			String count = Main.main.getConfig().getString("stack-count").replaceAll("%amount%", "" + a);
			item.setCustomName(item.getCustomName() + ChatColor.translateAlternateColorCodes('&', count));
		}
	}
	
}
