package me.imunsmart.rpg.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem extends ItemStack {
	
	public CustomItem(Material material) {
		super(material);
	}
	
	public CustomItem(ItemStack stack) {
		super(stack);
	}
	
	private ItemMeta meta() {
		return getItemMeta();
	}
	
	private void setMeta(ItemMeta meta) {
		setItemMeta(meta);
	}
	
	public CustomItem addGlow() {
		ItemMeta meta = meta();
		meta.addEnchant(new Glow(999), 1, true);
		setMeta(meta);
		return this;
	}
	
	public CustomItem addLore(String s) {
		ItemMeta meta = meta();
		List<String> lores = new ArrayList<>();
		if (meta.hasLore()) {
			lores = meta.getLore();
		}
		if (s.split("\n").length > 1) {
			for (String st : s.split("\n")) {
				lores.add(ChatColor.translateAlternateColorCodes('&', st));
			}
		} else {
			lores.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		meta.setLore(lores);
		setMeta(meta);
		return this;
	}
	
	public CustomItem removeGlow() {
		ItemMeta meta = meta();
		meta.removeEnchant(new Glow(999));
		setMeta(meta);
		return this;
	}
	
	public CustomItem setDurability(int data) {
		setDurability((short) data);
		return this;
	}
	
	public CustomItem setLore(List<String> lore) {
		ItemMeta meta = meta();
		meta.setLore(lore);
		setMeta(meta);
		return this;
	}
	
	public CustomItem setLore(String s) {
		ItemMeta meta = meta();
		List<String> lores = new ArrayList<>();
		lores.add(ChatColor.translateAlternateColorCodes('&', s));
		meta.setLore(lores);
		setMeta(meta);
		return this;
	}
	
	public CustomItem setName(String s) {
		ItemMeta meta = meta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
		setMeta(meta);
		return this;
	}
}



