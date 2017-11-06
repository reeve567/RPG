package me.imunsmart.rpg.mobs;

import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import net.md_5.bungee.api.ChatColor;

public class Mob {
	public LivingEntity mob;
	private double health;
	private ItemStack drop;
	private Main pl;
	private int tier;
	private Location loc;

	public Mob(LivingEntity mob, String name, int tier) {
		this.mob = mob;
		this.tier = tier;
		loc = mob.getLocation();
		mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
		pl = MobManager.pl;
		int mh = DropManager.getMaxHealth(tier, "H") / 2 + (int) (Math.random() * (DropManager.getMaxHealth(tier, "H") / 2));
		int mc = DropManager.getMaxHealth(tier, "C") / 2 + (int) (Math.random() * (DropManager.getMaxHealth(tier, "C") / 2));
		int ml = DropManager.getMaxHealth(tier, "L") / 2 + (int) (Math.random() * (DropManager.getMaxHealth(tier, "L") / 2));
		int mb = DropManager.getMaxHealth(tier, "B") / 2 + (int) (Math.random() * (DropManager.getMaxHealth(tier, "B") / 2));
		String flag = randomArmorFlag(mh);
		if (flag.contains("Uncommon")) {
			mh *= DropManager.SCALE_UNC_1;
		}
		if (flag.contains("Rare")) {
			mh *= DropManager.SCALE_RARE_1;
		}
		ItemStack h = Items.randomDurability(Items.createArmor("helmet", tier, mh, flag));
		if (Math.random() < 0.1) {
			drop = h;
		}
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
		mob.getEquipment().setHelmet(skull);
		flag = randomArmorFlag(mc);
		if (flag.contains("Uncommon")) {
			mc *= DropManager.SCALE_UNC_1;
		}
		if (flag.contains("Rare")) {
			mc *= DropManager.SCALE_RARE_1;
		}
		ItemStack c = Items.randomDurability(Items.createArmor("chestplate", tier, mc, flag));
		if (Math.random() < 0.1) {
			drop = c;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setChestplate(c);
		flag = randomArmorFlag(ml);
		if (flag.contains("Uncommon")) {
			ml *= DropManager.SCALE_UNC_1;
		}
		if (flag.contains("Rare")) {
			ml *= DropManager.SCALE_RARE_1;
		}
		ItemStack l = Items.randomDurability(Items.createArmor("leggings", tier, ml, flag));
		if (Math.random() < 0.1) {
			drop = l;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setLeggings(l);
		flag = randomArmorFlag(mb);
		if (flag.contains("Uncommon")) {
			mb *= DropManager.SCALE_UNC_1;
		}
		if (flag.contains("Rare")) {
			mb *= DropManager.SCALE_RARE_1;
		}
		ItemStack b = Items.randomDurability(Items.createArmor("boots", tier, mb, flag));
		if (Math.random() < 0.1) {
			drop = b;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setBoots(b);
		flag = randomWeaponFlag();
		String type = Math.random() < 0.5 ? "sword" : "axe";
		int max = (int) (Math.random() * DropManager.getMaxDamage(tier));
		int min = DropManager.getMinDamage(tier) + (int) (Math.random() * ((max - DropManager.getMinDamage(tier))));
		if (max < min)
			max = min;
		if (flag.contains("Uncommon")) {
			min *= DropManager.SCALE_UNC_1;
			max *= DropManager.SCALE_UNC_1;
		}
		if (flag.contains("Rare")) {
			min *= DropManager.SCALE_RARE_1;
			max *= DropManager.SCALE_RARE_1;
		}
		ItemStack w = Items.randomDurability(Items.createWeapon(type, tier, min, max, flag));
		if (Math.random() < 0.1) {
			drop = w;
		}
		mob.getEquipment().setItemInMainHand(w);
		mob.getEquipment().setBootsDropChance(0);
		mob.getEquipment().setHelmetDropChance(0);
		mob.getEquipment().setChestplateDropChance(0);
		mob.getEquipment().setLeggingsDropChance(0);
		mob.getEquipment().setItemInMainHandDropChance(0);
		health = Health.calculateMaxHealth(mob);
		mob.setCustomName(name);
	}

	public Mob(LivingEntity mob, String name, String type, int min, int max, String flag, int tier, int mh, int mc, int ml, int mb, String hf, String cf, String lf, String bf) {
		this.mob = mob;
		this.tier = tier;
		loc = mob.getLocation();
		mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
		pl = MobManager.pl;
		ItemStack h = Items.randomDurability(Items.createArmor("helmet", tier, mh, hf));
		if (Math.random() < 0.1) {
			drop = h;
		}
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		mob.getEquipment().setHelmet(skull);
		ItemStack c = Items.randomDurability(Items.createArmor("chestplate", tier, mc, cf));
		if (Math.random() < 0.1) {
			drop = c;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setChestplate(c);
		ItemStack l = Items.randomDurability(Items.createArmor("leggings", tier, ml, lf));
		if (Math.random() < 0.1) {
			drop = l;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setLeggings(l);
		ItemStack b = Items.randomDurability(Items.createArmor("boots", tier, mb, bf));
		if (Math.random() < 0.1) {
			drop = b;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setBoots(b);
		ItemStack w = Items.randomDurability(Items.createWeapon(type, tier, min, max, flag));
		if (Math.random() < 0.1) {
			drop = w;
		}
		mob.getEquipment().setItemInMainHand(w);
		mob.getEquipment().setBootsDropChance(0);
		mob.getEquipment().setHelmetDropChance(0);
		mob.getEquipment().setChestplateDropChance(0);
		mob.getEquipment().setLeggingsDropChance(0);
		mob.getEquipment().setItemInMainHandDropChance(0);
		health = Health.calculateMaxHealth(mob);
		mob.setCustomName(name);
	}

	public void tick() {
		if (health < 1) {
			mob.playEffect(EntityEffect.DEATH);
			new BukkitRunnable() {
				@Override
				public void run() {
					mob.remove();
				}
			}.runTaskLater(pl, 10);
			if (drop != null) {
				mob.getWorld().dropItemNaturally(mob.getLocation(), drop);
			}
			if (Math.random() < 0.75) {
				int gd = (int) (Math.random() * DropManager.getMaxDrops(tier));
				mob.getWorld().dropItem(mob.getLocation(), Items.createGems(gd));
			}
		}
		if (loc.distanceSquared(mob.getLocation()) >= 625) {
			mob.teleport(loc);
		}
		Spawners.die(this);
	}

	public double getHealth() {
		return health;
	}

	public void damage(double i) {
		health -= i;
		if (health < 0)
			health = 0;
		double perc = (health / (double) Health.calculateMaxHealth(mob));
		String bar = ChatColor.GREEN.toString();
		int x = 10;
		for (int h = 0; h < (perc * 10); h++) {
			bar += "•";
			x--;
		}
		bar += ChatColor.WHITE.toString();
		for (int h = 0; h < x; h++) {
			bar += "•";
		}
		mob.setCustomName(ChatColor.GRAY + "[" + bar + ChatColor.GRAY + "]");
	}

	public static String randomArmorFlag(int max) {
		String flag = "";
		if (Math.random() < 0.6) {
			flag += "Regen:" + (int) (max * DropManager.MAX_REGEN_1);
		}
		double perc = Math.random();
		if (perc >= 0.1 && perc < 0.2)
			flag += ",§eUncommon";
		else if (perc < 0.1)
			flag += ",§aRare";
		return flag;
	}

	public static String randomWeaponFlag() {
		String flag = "";
		double perc = Math.random();
		if (perc >= 0.1 && perc < 0.2)
			flag += ",§eUncommon";
		else if (perc < 0.1)
			flag += ",§aRare";
		return flag;
	}
}
