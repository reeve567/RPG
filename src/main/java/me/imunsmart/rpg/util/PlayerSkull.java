package me.imunsmart.rpg.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSkull extends CustomItem {
	
	public PlayerSkull(Player p) {
		this(p.getName());
	}
	
	public PlayerSkull(String s) {
		super(Material.SKULL_ITEM);
		setDurability(3);
		SkullMeta meta = (SkullMeta) getItemMeta();
		meta.setOwner(s);
		setItemMeta(meta);
	}
	
}