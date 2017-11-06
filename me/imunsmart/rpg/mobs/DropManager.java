package me.imunsmart.rpg.mobs;

import java.lang.reflect.Field;

public class DropManager {

	//GLOBAL
	public static final double SCALE_UNC_1 = 2;
	public static final double SCALE_RARE_1 = 2.5;

	//T1
	public static final int MIN_DAMAGE_1 = 1;
	public static final int MAX_DAMAGE_1 = 8;
	public static final double MAX_REGEN_1 = 0.2;

	public static final int MAX_HEALTH_1B = 12;
	public static final int MAX_HEALTH_1L = 25;
	public static final int MAX_HEALTH_1C = 30;
	public static final int MAX_HEALTH_1H = 15;

	public static final int MAX_T1_GEMS = 3;

	//T2
	public static final int MIN_DAMAGE_2 = 9;
	public static final int MAX_DAMAGE_2 = 21;
	public static final double MAX_REGEN_2 = 0.25;

	public static final int MAX_HEALTH_2B = 52;
	public static final int MAX_HEALTH_2L = 90;
	public static final int MAX_HEALTH_2C = 100;
	public static final int MAX_HEALTH_2H = 60;

	public static final int MAX_T2_GEMS = 12;

	//T3
	public static final int MIN_DAMAGE_3 = 56;
	public static final int MAX_DAMAGE_3 = 112;
	public static final double MAX_REGEN_3 = 0.3;

	public static final int MAX_HEALTH_3B = 200;
	public static final int MAX_HEALTH_3L = 270;
	public static final int MAX_HEALTH_3C = 315;
	public static final int MAX_HEALTH_3H = 230;

	public static final int MAX_T3_GEMS = 32;

	//T4
	public static final int MIN_DAMAGE_4 = 82;
	public static final int MAX_DAMAGE_4 = 164;
	public static final double MAX_REGEN_4 = 0.35;

	public static final int MAX_HEALTH_4B = 500;
	public static final int MAX_HEALTH_4L = 650;
	public static final int MAX_HEALTH_4C = 700;
	public static final int MAX_HEALTH_4H = 550;

	public static final int MAX_T4_GEMS = 58;

	//T5
	public static final int MIN_DAMAGE_5 = 160;
	public static final int MAX_DAMAGE_5 = 256;
	public static final double MAX_REGEN_5 = 0.4;

	public static final int MAX_HEALTH_5B = 800;
	public static final int MAX_HEALTH_5L = 950;
	public static final int MAX_HEALTH_5C = 1075;
	public static final int MAX_HEALTH_5H = 860;

	public static final int MAX_T5_GEMS = 64;

	public static int getMinDamage(int tier) {
		try {
			Field f = DropManager.class.getDeclaredField("MIN_DAMAGE_" + tier);
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
			Field f = DropManager.class.getDeclaredField("MAX_DAMAGE_" + tier);
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
			Field f = DropManager.class.getDeclaredField("MAX_HEALTH_" + tier + i);
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
			Field f = DropManager.class.getDeclaredField("MAX_REGEN_" + tier);
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
			Field f = DropManager.class.getDeclaredField("MAX_T" + tier + "_GEMS");
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
			Field f = DropManager.class.getDeclaredField("zt" + tier);
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
}
