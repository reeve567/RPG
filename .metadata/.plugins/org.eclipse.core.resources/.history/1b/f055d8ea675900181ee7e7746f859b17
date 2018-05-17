package me.imunsmart.rpg.mobs;

import java.lang.reflect.Field;

public class Constants {

	// GLOBAL
	public static final double SCALE_UNC = 1.8;
	public static final double SCALE_RARE = 2.3;

	// T1
	public static final int MIN_DAMAGE_1 = 1;
	public static final int MAX_DAMAGE_1 = 8;
	public static final double MAX_REGEN_1 = 0.1;

	public static final int MAX_HEALTH_1B = 12;
	public static final int MAX_HEALTH_1L = 25;
	public static final int MAX_HEALTH_1C = 30;
	public static final int MAX_HEALTH_1H = 15;

	public static final int MAX_T1_GEMS = 3;

	// T2
	public static final int MIN_DAMAGE_2 = 4 * MIN_DAMAGE_1;
	public static final int MAX_DAMAGE_2 = 4 * MAX_DAMAGE_1;
	public static final double MAX_REGEN_2 = 0.11;

	public static final int MAX_HEALTH_2B = 4 * MAX_HEALTH_1B;
	public static final int MAX_HEALTH_2L = 4 * MAX_HEALTH_1L;
	public static final int MAX_HEALTH_2C = 4 * MAX_HEALTH_1C;
	public static final int MAX_HEALTH_2H = 4 * MAX_HEALTH_1H;

	public static final int MAX_T2_GEMS = 14;

	// T3
	public static final int MIN_DAMAGE_3 = 4 * MIN_DAMAGE_2;
	public static final int MAX_DAMAGE_3 = 4 * MAX_DAMAGE_2;
	public static final double MAX_REGEN_3 = 0.12;

	public static final int MAX_HEALTH_3B = 4 * MAX_HEALTH_2B;
	public static final int MAX_HEALTH_3L = 4 * MAX_HEALTH_2L;
	public static final int MAX_HEALTH_3C = 4 * MAX_HEALTH_2C;
	public static final int MAX_HEALTH_3H = 4 * MAX_HEALTH_2H;

	public static final int MAX_T3_GEMS = 32;

	// T4
	public static final int MIN_DAMAGE_4 = 4 * MIN_DAMAGE_3;
	public static final int MAX_DAMAGE_4 = 4 * MAX_DAMAGE_3;
	public static final double MAX_REGEN_4 = 0.14;

	public static final int MAX_HEALTH_4B = 4 * MAX_HEALTH_3B;
	public static final int MAX_HEALTH_4L = 4 * MAX_HEALTH_3L;
	public static final int MAX_HEALTH_4C = 4 * MAX_HEALTH_3C;
	public static final int MAX_HEALTH_4H = 4 * MAX_HEALTH_3H;

	public static final int MAX_T4_GEMS = 58;

	// T5
	public static final int MIN_DAMAGE_5 = 4 * MIN_DAMAGE_4;
	public static final int MAX_DAMAGE_5 = 4 * MAX_DAMAGE_4;
	public static final double MAX_REGEN_5 = 0.16;

	public static final int MAX_HEALTH_5B = 4 * MAX_HEALTH_4B;
	public static final int MAX_HEALTH_5L = 4 * MAX_HEALTH_4L;
	public static final int MAX_HEALTH_5C = 4 * MAX_HEALTH_4C;
	public static final int MAX_HEALTH_5H = 4 * MAX_HEALTH_4H;

	public static final int MAX_T5_GEMS = 64;

	public static int getMinDamage(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("MIN_DAMAGE_" + tier);
			return f.getInt(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static int getMaxDamage(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("MAX_DAMAGE_" + tier);
			return f.getInt(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static int getMaxHealth(int tier, String i) {
		try {
			Field f = Constants.class.getDeclaredField("MAX_HEALTH_" + tier + i);
			return f.getInt(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static double getMaxRegen(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("MAX_REGEN_" + tier);
			return f.getDouble(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	public static double getMaxDrops(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("MAX_T" + tier + "_GEMS");
			return f.getDouble(null);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return 1;
	}

	private static final String[] zt1 = { "Rotting Zombie", "Dying Zombie", "Scattered Zombie", "Weak Zombie" };
	private static final String[] zt2 = { "Creepy Zombie", "Smart Zombie", "Cool Zombie", "Undead Zombie" };
	private static final String[] zt3 = { "Strong Zombie", "Intelligent Zombie", "Buff Zombie", "Gross Zombie" };
	private static final String[] zt4 = { "Horrible Zombie", "Crazy Zombie", "Killer Zombie", "Destructive Zombie" };
	private static final String[] zt5 = { "Untouchable Zombie", "Godly Zombie", "Legendary Zombie", "Inhuman Zombie" };

	public static String getRandomZombieName(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("zt" + tier);
			String[] name = (String[]) f.get(null);
			return name[(int) (Math.random() * name.length)];
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return "null";
	}

	private static final String[] st1 = { "Crackling Skeleton", "Broken Skeleton", "Lost Skeleton", "Weak Skeleton" };
	private static final String[] st2 = { "Creepy Skeleton", "Happy Skeleton", "Rough Skeleton", "Undead Skeleton" };
	private static final String[] st3 = { "Strong Skeleton", "Intelligent Skeleton", "Unbroken Skeleton", "Decaying Skeleton" };
	private static final String[] st4 = { "Horrible Skeleton", "Insane Skeleton", "Killer Skeleton", "Destructive Skeleton" };
	private static final String[] st5 = { "Untouchable Skeleton", "Godly Skeleton", "Legendary Skeleton", "Inhuman Skeleton" };

	public static String getRandomSkeletonName(int tier) {
		try {
			Field f = Constants.class.getDeclaredField("st" + tier);
			String[] name = (String[]) f.get(null);
			return name[(int) (Math.random() * name.length)];
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return "null";
	}

	public static String randomArmorFlag(int max, int tier) {
		String flag = "";
		if (Math.random() < 0.6) {
			flag += "Regen:" + (int) (max * Constants.getMaxRegen(tier)) + ",";
		}
		double perc = Math.random();
		if (perc >= 0.1 && perc < 0.2)
			flag += "uncommon";
		else if (perc < 0.1)
			flag += "rare";
		return flag;
	}

	public static String randomWeaponFlag(int tier) {
		String flag = "";
		double perc = Math.random();
		if (Math.random() < 0.1) {
			flag += "Critical: " + (int) (Math.random() * 50 + 1) + "%";
		}
		if (perc >= 0.1 && perc < 0.2)
			flag += "uncommon";
		else if (perc < 0.1)
			flag += "rare";
		return flag;
	}
}
