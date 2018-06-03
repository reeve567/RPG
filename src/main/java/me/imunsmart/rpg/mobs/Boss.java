package me.imunsmart.rpg.mobs;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.MessagesUtil;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Boss {

    private static List<UUID> bosses = new ArrayList<>();

    private Location l;
    private String name, deathMessage;
    private int tier;
    private int maxHelmet, maxChestplate, maxLeggings, maxBoots, varh, varc, varl, varb;
    private int minDMG, maxDMG;
    private Class<? extends LivingEntity> clazz;
    private UUID le;
    private Runnable postSpawn;

    public Boss(Location l, Class<? extends LivingEntity> clazz, String name, int tier, int maxHelmet, int maxChestplate, int maxLeggings, int maxBoots,
                int varh, int varc, int varl, int varb, int minDMG, int maxDMG, String deathMessage) {
        this.l = l;
        this.clazz = clazz;
        this.name = name;
        this.tier = tier;
        this.maxHelmet = maxHelmet;
        this.maxChestplate = maxChestplate;
        this.maxLeggings = maxLeggings;
        this.maxBoots = maxBoots;
        this.varh = varh;
        this.varc = varc;
        this.varl = varl;
        this.varb = varb;
        this.minDMG = minDMG;
        this.maxDMG = maxDMG;
        this.deathMessage = deathMessage;
    }

    public Boss(Location l, Class<? extends LivingEntity> clazz, String name, int tier, int maxHelmet, int maxChestplate, int maxLeggings, int maxBoots,
                int varh, int varc, int varl, int varb, int minDMG, int maxDMG, String deathMessage, Runnable postSpawn) {
        this(l, clazz, name, tier, maxHelmet, maxChestplate, maxLeggings, maxBoots, varh, varc, varl, varb, minDMG, maxDMG, deathMessage);
        this.postSpawn = postSpawn;
    }

    public LivingEntity spawn() {
        if (le != null)
            bosses.remove(le);
        le = Util.w.spawn(l, clazz).getUniqueId();
        reset();
        bosses.add(le);
        LivingEntity l = getMob();
        if (l instanceof Zombie)
            ((Zombie) l).setBaby(false);
        String type = Math.random() < 0.5 ? "Sword" : "Axe";
        int mh = maxHelmet + (int) (Math.random() * varh);
        int mc = maxHelmet + (int) (Math.random() * varc);
        int ml = maxHelmet + (int) (Math.random() * varl);
        int mb = maxHelmet + (int) (Math.random() * varb);
        String t = Constants.randomWeaponFlag(tier, 1.3);
        Mob m = EntityManager.customMob(getMob(), name, tier, type, minDMG, maxDMG, t, mh, mc, ml, mb, Constants.randomArmorFlag(mh, tier, 1.3),
                Constants.randomArmorFlag(mc, tier, 1.3), Constants.randomArmorFlag(ml, tier, 1.3), Constants.randomArmorFlag(mb, tier, 1.3), "ImUnsmart");
        EntityManager.mobs.put(le, m);
        if(postSpawn != null) {
            postSpawn.run();
        }
        return l;
    }

    public void init(Main pl) {
        spawn();
        new BukkitRunnable() {
            int time = 120 * tier;

            LivingEntity l = getMob();
            boolean b = true;

            public void run() {
                if (l == null || l.isDead()) {
                    if (b && l != null) {
                        Util.broadcastAround(MessagesUtil.mobDefeated(name, deathMessage), l.getLocation(), 100);
                        b = false;
                    }
                    time--;
                    if (time < 1) {
                        cancel();
                        init(pl);
                    }
                }
            }
        }.runTaskTimer(pl, 0, 20);
    }

    public LivingEntity getMob() {
        return (LivingEntity) Bukkit.getEntity(le);
    }

    private void reset() {
        String n = ChatColor.stripColor(name).replaceAll(" ", "").trim();
        try {
            Class c = Class.forName("me.imunsmart.rpg.mobs.bosses." + n);
            Field f = c.getDeclaredField("flag");
            f.setAccessible(true);
            f.setInt(null, 0);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBoss(LivingEntity le) {
        return bosses.contains(le.getUniqueId());
    }

}
