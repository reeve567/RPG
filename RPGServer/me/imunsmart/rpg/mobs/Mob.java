package me.imunsmart.rpg.mobs;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import net.md_5.bungee.api.ChatColor;

public class Mob {
	public LivingEntity mob;
	private double health, maxHP;
	private ItemStack drop;
	private Main pl;
	private int tier;
	private Location loc;

	public Mob(LivingEntity mob, String name, int tier) {
		this.mob = mob;
		this.tier = tier;
		loc = mob.getLocation();
		mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
		pl = EntityManager.pl;
		int mh = Constants.getMaxHealth(tier, "H") / 2 + (int) (Math.random() * (Constants.getMaxHealth(tier, "H") / 2));
		int mc = Constants.getMaxHealth(tier, "C") / 2 + (int) (Math.random() * (Constants.getMaxHealth(tier, "C") / 2));
		int ml = Constants.getMaxHealth(tier, "L") / 2 + (int) (Math.random() * (Constants.getMaxHealth(tier, "L") / 2));
		int mb = Constants.getMaxHealth(tier, "B") / 2 + (int) (Math.random() * (Constants.getMaxHealth(tier, "B") / 2));
		String flag = Constants.randomArmorFlag(mh, tier);
		if (flag.contains("Uncommon")) {
			mh *= Constants.SCALE_UNC;
		}
		if (flag.contains("Rare")) {
			mh *= Constants.SCALE_RARE;
		}
		ItemStack h = Items.randomDurability(Items.createArmor("helmet", tier, mh, flag));
		if (Math.random() < 0.1) {
			drop = h;
		}
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		mob.getEquipment().setHelmet(skull);
		flag = Constants.randomArmorFlag(mc, tier);
		if (flag.contains("Uncommon")) {
			mc *= Constants.SCALE_UNC;
		}
		if (flag.contains("Rare")) {
			mc *= Constants.SCALE_RARE;
		}
		ItemStack c = Items.randomDurability(Items.createArmor("chestplate", tier, mc, flag));
		if (Math.random() < 0.1) {
			drop = c;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setChestplate(c);
		flag = Constants.randomArmorFlag(ml, tier);
		if (flag.contains("Uncommon")) {
			ml *= Constants.SCALE_UNC;
		}
		if (flag.contains("Rare")) {
			ml *= Constants.SCALE_RARE;
		}
		ItemStack l = Items.randomDurability(Items.createArmor("leggings", tier, ml, flag));
		if (Math.random() < 0.1) {
			drop = l;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setLeggings(l);
		flag = Constants.randomArmorFlag(mb, tier);
		if (flag.contains("Uncommon")) {
			mb *= Constants.SCALE_UNC;
		}
		if (flag.contains("Rare")) {
			mb *= Constants.SCALE_RARE;
		}
		ItemStack b = Items.randomDurability(Items.createArmor("boots", tier, mb, flag));
		if (Math.random() < 0.1) {
			drop = b;
		}
		if (Math.random() > 0.25)
			mob.getEquipment().setBoots(b);
		flag = Constants.randomWeaponFlag(tier);
		String type = Math.random() < 0.5 ? "sword" : "axe";
		int max = (int) (Math.random() * Constants.getMaxDamage(tier));
		int min = Constants.getMinDamage(tier) + (int) (Math.random() * ((max - Constants.getMinDamage(tier))));
		if (max < min)
			max = min;
		if (flag.contains("Uncommon")) {
			max += Constants.getMaxDamage(tier) / 4;
			min *= Constants.SCALE_UNC;
			max *= Constants.SCALE_UNC;
		}
		if (flag.contains("Rare")) {
			max += Constants.getMaxDamage(tier) / 4;
			min += Constants.getMaxDamage(tier) / 4;
			min *= Constants.SCALE_RARE;
			max *= Constants.SCALE_RARE;
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
		maxHP = health = Health.calculateMaxHealth(mob);
		mob.setCustomName(name);
		mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * (4.0 / 3.0));
	}

	public Mob(LivingEntity mob, String name, String type, int min, int max, String flag, int tier, int mh, int mc, int ml, int mb, String hf, String cf, String lf, String bf) {
		this.mob = mob;
		this.tier = tier;
		loc = mob.getLocation();
		mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
		pl = EntityManager.pl;
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
			die();
		}
		if (loc.distanceSquared(mob.getLocation()) >= 625) {
			mob.teleport(loc);
		}
		Spawners.die(this);
	}
	
	private void die() {
		mob.setHealth(0);
		if (drop != null) {
			mob.getWorld().dropItemNaturally(mob.getLocation(), drop);
		}
		if (Math.random() < 0.75) {
			int gd = (int) (Math.random() * Constants.getMaxDrops(tier));
			mob.getWorld().dropItemNaturally(mob.getLocation(), Items.createGems(gd));
		}
		for (String s : hits.keySet()) {
			Player p = Bukkit.getPlayer(s);
			if (p != null) {
				Stats.addXP(p, 1 + (int) (((double) hits.get(s) / hitsTaken) * (Math.random() * (24 * tier))));
			}
		}
		hits.clear();
	}

	public double getHealth() {
		return health;
	}

	private HashMap<String, Integer> hits = new HashMap<>();
	private int hitsTaken = 0;

	public void damage(double i, Player p) {
		hitsTaken++;
		if (p != null)
			hits.put(p.getName(), !hits.containsKey(p.getName()) ? 1 : hits.get(p.getName()) + 1);
		health -= i;
		if (health < 0)
			health = 0;
		double perc = (health / (double) maxHP);
		String bar = ChatColor.GREEN.toString();
//		int x = 10;
//		for (int h = 0; h < (perc * 10); h++) {
//			bar += "♥";
//			x--;
//		}
//		bar += ChatColor.WHITE.toString();
//		for (int h = 0; h < x; h++) {
//			bar += "♥";
//		}
		mob.setCustomName(ChatColor.WHITE.toString() + ChatColor.BOLD + (int) health + ChatColor.RED.toString() + ChatColor.BOLD + "HP");
	}
}
