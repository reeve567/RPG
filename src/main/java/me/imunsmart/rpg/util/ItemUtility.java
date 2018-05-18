package me.imunsmart.rpg.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.imunsmart.rpg.mechanics.classes.Class;

import java.util.ArrayList;
import java.util.List;

public class ItemUtility {
	
	public static ItemStack addLore(ItemStack it, String s, boolean trans) {
		ItemMeta im = it.getItemMeta();
		List<String> lores = im.getLore();
		if (lores == null) {
			lores = new ArrayList<>();
		}
		if (trans) lores.add(StringUtility.colorConv(s));
		else lores.add(s);
		im.setLore(lores);
		it.setItemMeta(im);
		return it;
	}
	
	public static int getLevel(ItemStack it) {
		if (getType(it) != null) {
			Class type = getType(it);
			assert type != null;
			ItemMeta itemMeta = it.getItemMeta();
			List<String> lores = itemMeta.getLore();
			if (type.equals(Class.WARRIOR)) {
				for (String s: lores) {
					if (s.startsWith(StringUtility.colorConv("&4Warrior"))) {
						return RomanNumber.fromRoman(s.substring(s.indexOf(' ') + 1));
					}
				}
			}
			else if (type.equals(Class.WIZARD)) {
				for (String s: lores) {
					if (s.startsWith(StringUtility.colorConv("&1Wizard"))) {
						return RomanNumber.fromRoman(s.substring(s.indexOf(' ') + 1));
					}
				}
			}
			else if (type.equals(Class.ARCHER)) {
				for (String s: lores) {
					if (s.startsWith(StringUtility.colorConv("&fArcher"))) {
						return RomanNumber.fromRoman(s.substring(s.indexOf(' ') + 1));
					}
				}
			}
			
			
		}
		return 0;
	}
	
	public static int getLevelCandy(ItemStack it) {
		return RomanNumber.fromRoman(it.getItemMeta().getLore().get(0).substring(it.getItemMeta().getLore().get(0).indexOf(' ') + 1));
	}
	
	public static void removeOne(ItemStack it) {
		if (it.getAmount() > 1) {
			it.setAmount(it.getAmount()-1);
		}
		else if (it.getAmount() == 1) {
			it.setType(Material.AIR);
		}
	}
	
	
	public static Class getType(ItemStack it) {
		if (it.hasItemMeta() && it.getItemMeta().hasLore()) {
			for (String s:it.getItemMeta().getLore()) {
				if (s.startsWith(StringUtility.colorConv("&1Wizard"))) return Class.WIZARD;
				else if (s.startsWith(StringUtility.colorConv("&4Warrior"))) return Class.WARRIOR;
				else if (s.startsWith(StringUtility.colorConv("&fArcher"))) return Class.ARCHER;
			}
		}
		return null;
	}
	
	public static ItemStack addGlow(ItemStack it) {
		Glow glow = new Glow(999);
		addEnchant(it, glow, 1);
		return it;
	}
	
	public static void addProt(ItemStack it, int level) {
		it.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, level);
	}
	
	public static ItemStack setName(ItemStack it, String s, boolean trans) {
		ItemMeta im = it.getItemMeta();
		if (trans) im.setDisplayName(ChatColor.translateAlternateColorCodes('&', s));
		else im.setDisplayName(s);
		it.setItemMeta(im);
		return it;
	}
	
	public static ItemStack addEnchant(ItemStack it, Enchantment e, int level) {
		it.addUnsafeEnchantment(e, level);
		return it;
	}
	
}
