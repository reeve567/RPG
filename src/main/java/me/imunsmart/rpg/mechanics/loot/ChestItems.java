package me.imunsmart.rpg.mechanics.loot;

import me.imunsmart.rpg.mechanics.Items;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class ChestItems {
	public static final ItemStack[] tier1 = new ItemStack[]{
			Items.createPotion(1),
	};
	
	public static final ItemStack[] tier2 = new ItemStack[]{
			Items.createPotion(2),
	};
	
	public static final ItemStack[] tier3 = new ItemStack[]{
			Items.createPotion(3),
	};
	
	public static ItemStack[] getTier(int tier) {
		try {
			Field f = ChestItems.class.getField("tier" + tier);
			return (ItemStack[]) f.get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return tier1;
	}
}
