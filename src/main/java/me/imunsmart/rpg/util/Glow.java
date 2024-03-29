package me.imunsmart.rpg.util;

import me.imunsmart.rpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class Glow extends Enchantment {
	
	/**
	insert this in main class and run in onEnable
	
	private void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow(70);
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException ignored) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	**/
	public Glow(NamespacedKey k) {
		
		super(k);
	}
	
	@Override
	public boolean canEnchantItem(ItemStack itemStack) {
		
		return true;
	}
	
	@Override
	public boolean conflictsWith(Enchantment enchantment) {
		
		return false;
	}
	
	@Override
	public EnchantmentTarget getItemTarget() {
		
		return null;
	}
	
	@Override
	public int getMaxLevel() {
		
		return 0;
	}
	
	@Override
	public String getName() {
		return "glow";
	}
	
	@Override
	public int getStartLevel() {
		
		return 0;
	}
	
	@Override
	public boolean isCursed() {
		return false;
	}
	
	@Override
	public boolean isTreasure() {
		return false;
	}

	public static ItemStack add(ItemStack i) {
		if(i == null)
			return null;
		i.addUnsafeEnchantment(Enchantment.getByName("glow"), 1);
		return i;
	}
}
