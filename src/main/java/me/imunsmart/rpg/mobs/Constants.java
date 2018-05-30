package me.imunsmart.rpg.mobs;

import java.lang.reflect.Field;

public class Constants {

    // GLOBAL
    public static final double SCALE_UNC = 1.8;
    public static final double SCALE_RARE = 2.3;

    // T1
    public static final int MIN_DAMAGE_1 = 1;
    public static final int MAX_DAMAGE_1 = 6;

    public static final double MAX_REGEN_1 = 0.19;

    public static final int MAX_HEALTH_1B = 12;
    public static final int MAX_HEALTH_1L = 25;
    public static final int MAX_HEALTH_1C = 30;
    public static final int MAX_HEALTH_1H = 15;
    public static final int MAX_HEALTH_T1 = MAX_HEALTH_1H + MAX_HEALTH_1C + MAX_HEALTH_1L + MAX_HEALTH_1B;

    public static final double DROP_RATE_1 = 0.1;

    public static final int MAX_T1_GEMS = 3;

    // T2
    public static final int MIN_DAMAGE_2 = 4 * MIN_DAMAGE_1;
    public static final int MAX_DAMAGE_2 = 4 * MAX_DAMAGE_1;

    public static final double MAX_REGEN_2 = 0.2;

    public static final int MAX_HEALTH_2B = 4 * MAX_HEALTH_1B;
    public static final int MAX_HEALTH_2L = 4 * MAX_HEALTH_1L;
    public static final int MAX_HEALTH_2C = 4 * MAX_HEALTH_1C;
    public static final int MAX_HEALTH_2H = 4 * MAX_HEALTH_1H;
    public static final int MAX_HEALTH_T2 = MAX_HEALTH_1H + MAX_HEALTH_1C + MAX_HEALTH_1L + MAX_HEALTH_1B;

    public static final double DROP_RATE_2 = 0.9;

    public static final int MAX_T2_GEMS = 14;

    // T3
    public static final int MIN_DAMAGE_3 = 4 * MIN_DAMAGE_2;
    public static final int MAX_DAMAGE_3 = 4 * MAX_DAMAGE_2;

    public static final double MAX_REGEN_3 = 0.21;

    public static final int MAX_HEALTH_3B = 4 * MAX_HEALTH_2B;
    public static final int MAX_HEALTH_3L = 4 * MAX_HEALTH_2L;
    public static final int MAX_HEALTH_3C = 4 * MAX_HEALTH_2C;
    public static final int MAX_HEALTH_3H = 4 * MAX_HEALTH_2H;
    public static final int MAX_HEALTH_T3 = MAX_HEALTH_1H + MAX_HEALTH_1C + MAX_HEALTH_1L + MAX_HEALTH_1B;

    public static final double DROP_RATE_3 = 0.07;

    public static final int MAX_T3_GEMS = 32;

    // T4
    public static final int MIN_DAMAGE_4 = 4 * MIN_DAMAGE_3;
    public static final int MAX_DAMAGE_4 = 4 * MAX_DAMAGE_3;

    public static final double MAX_REGEN_4 = 0.22;

    public static final int MAX_HEALTH_4B = 4 * MAX_HEALTH_3B;
    public static final int MAX_HEALTH_4L = 4 * MAX_HEALTH_3L;
    public static final int MAX_HEALTH_4C = 4 * MAX_HEALTH_3C;
    public static final int MAX_HEALTH_4H = 4 * MAX_HEALTH_3H;
    public static final int MAX_HEALTH_T4 = MAX_HEALTH_1H + MAX_HEALTH_1C + MAX_HEALTH_1L + MAX_HEALTH_1B;

    public static final double DROP_RATE_4 = 0.05;

    public static final int MAX_T4_GEMS = 58;

    // T5
    public static final int MIN_DAMAGE_5 = 4 * MIN_DAMAGE_4;
    public static final int MAX_DAMAGE_5 = 4 * MAX_DAMAGE_4;

    public static final double MAX_REGEN_5 = 0.23;

    public static final int MAX_HEALTH_5B = 4 * MAX_HEALTH_4B;
    public static final int MAX_HEALTH_5L = 4 * MAX_HEALTH_4L;
    public static final int MAX_HEALTH_5C = 4 * MAX_HEALTH_4C;
    public static final int MAX_HEALTH_5H = 4 * MAX_HEALTH_4H;
    public static final int MAX_HEALTH_T5 = MAX_HEALTH_1H + MAX_HEALTH_1C + MAX_HEALTH_1L + MAX_HEALTH_1B;

    public static final double DROP_RATE_5 = 0.02;

    public static final int MAX_T5_GEMS = 64;

    // Weapons
    public static final int[] USE_ITEM = {10, 8, 5, 0, 75};

    // Levels
    public static final int[] LEVEL_REQ = {0, 10, 20, 30, 50};
    public static float BASE_XP = 200;
    public static float MULT = 1.095f;

    public static float PICK_BASE_XP = 200;
    public static float PICK_MULT = 1.05f;

    // Zombies
    private static final String[] zt1 = {"Rotting Zombie", "Dying Zombie", "Scattered Zombie", "Weak Zombie"};
    private static final String[] zt2 = {"Creepy Zombie", "Smart Zombie", "Cool Zombie", "Undead Zombie"};
    private static final String[] zt3 = {"Strong Zombie", "Intelligent Zombie", "Buff Zombie", "Gross Zombie"};
    private static final String[] zt4 = {"Horrible Zombie", "Crazy Zombie", "Killer Zombie", "Destructive Zombie"};
    private static final String[] zt5 = {"Untouchable Zombie", "Godly Zombie", "Legendary Zombie", "Inhuman Zombie"};

    // Skeletons
    private static final String[] st1 = {"Crackling Skeleton", "Broken Skeleton", "Lost Skeleton", "Weak Skeleton"};
    private static final String[] st2 = {"Creepy Skeleton", "Happy Skeleton", "Rough Skeleton", "Undead Skeleton"};
    private static final String[] st3 = {"Strong Skeleton", "Intelligent Skeleton", "Unbroken Skeleton", "Decaying Skeleton"};
    private static final String[] st4 = {"Horrible Skeleton", "Insane Skeleton", "Killer Skeleton", "Destructive Skeleton"};
    private static final String[] st5 = {"Untouchable Skeleton", "Godly Skeleton", "Legendary Skeleton", "Inhuman Skeleton"};

    // Spiders
    private static final String[] spt1 = {"Harmless Spiderling", "Small Spiderling", "Weak Spiderling", "Young Spiderling"};
    private static final String[] spt2 = {"Angry Spider", "Defensive Spider", "Territorial Spider", "Smart Spider"};
    private static final String[] spt3 = {"Fearless Arachnid", "Intelligent Arachnid", "Powerful Arachnid", "Creepy Arachnid"};
    private static final String[] spt4 = {"Horrible Arachnid", "Insane Arachnid", "Killer Arachnid", "Destructive Arachnid"};
    private static final String[] spt5 = {"Arachne"};

    public static int getMaxDamage(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("MAX_DAMAGE_" + tier);
            return f.getInt(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static double getMaxDrops(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("MAX_T" + tier + "_GEMS");
            return f.getDouble(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getMaxHealth(int tier, String i) {
        try {
            Field f = Constants.class.getDeclaredField("MAX_HEALTH_" + tier + i);
            return f.getInt(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getMaxHealth(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("MAX_HEALTH_T" + tier);
            return f.getInt(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static double getMaxRegen(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("MAX_REGEN_" + tier);
            return f.getDouble(null);
        } catch (IllegalArgumentException | SecurityException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static int getMinDamage(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("MIN_DAMAGE_" + tier);
            return f.getInt(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static double getDropRate(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("DROP_RATE_" + tier);
            return f.getDouble(null);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return 0.1;
    }

    public static String randomArmorFlag(int max, int tier) {
        String flag = "";
        if (Math.random() < 0.6) {
            flag += "Regen:" + (1 + (int) (Math.random() * (max * Constants.getMaxRegen(tier)))) + ",";
        }
        double perc = Math.random();
        if (perc >= 0.1 && perc < 0.2)
            flag += "uncommon";
        else if (perc < 0.1)
            flag += "rare";
        return flag;
    }

    public static String randomArmorFlag(int max, int tier, double mult) {
        String flag = "";
        if (Math.random() < 0.6 * mult) {
            flag += "Regen:" + (1 + (int) (Math.random() * (max * Constants.getMaxRegen(tier)))) + ",";
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
            flag += "Critical:" + (int) (Math.random() * 25 + 1) + "%,";
        }
        if (perc >= 0.1 && perc < 0.2)
            flag += "uncommon";
        else if (perc < 0.1)
            flag += "rare";
        return flag;
    }

    public static String randomWeaponFlag(int tier, double mult) {
        String flag = "";
        double perc = Math.random();
        if (Math.random() < 0.1 * mult) {
            flag += "Critical:" + (int) (Math.random() * 25 + 1) + "%,";
        }
        if (perc >= 0.1 && perc < 0.2)
            flag += "uncommon";
        else if (perc < 0.1)
            flag += "rare";
        return flag;
    }

    public static String getRandomSkeletonName(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("st" + tier);
            String[] name = (String[]) f.get(null);
            return name[(int) (Math.random() * name.length)];
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String getRandomZombieName(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("zt" + tier);
            String[] name = (String[]) f.get(null);
            return name[(int) (Math.random() * name.length)];
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "null";
    }

    public static String getRandomSpiderName(int tier) {
        try {
            Field f = Constants.class.getDeclaredField("spt" + tier);
            String[] name = (String[]) f.get(null);
            return name[(int) (Math.random() * name.length)];
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
