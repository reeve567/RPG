package me.imunsmart.rpg.mobs;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class Boss {

    private Location l;
    private String name;
    private int tier;
    private int maxHelmet, maxChestplate, maxLeggings, maxBoots, varh, varc, varl, varb;
    private int minDMG, maxDMG;
    private Class<? extends LivingEntity> clazz;

    public Boss(Location l, Class<? extends LivingEntity> clazz, String name, int tier, int maxHelmet, int maxChestplate, int maxLeggings, int maxBoots,
                int varh, int varc, int varl, int varb, int minDMG, int maxDMG) {
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
    }

    private LivingEntity spawn() {
        LivingEntity le = Util.w.spawn(l, clazz);
        if (le instanceof Zombie)
            ((Zombie) le).setBaby(false);
        String type = Math.random() < 0.5 ? "Sword" : "Axe";
        int mh = maxHelmet + (int) (Math.random() * varh);
        int mc = maxHelmet + (int) (Math.random() * varc);
        int ml = maxHelmet + (int) (Math.random() * varl);
        int mb = maxHelmet + (int) (Math.random() * varb);
        String t = Constants.randomWeaponFlag(tier, 1.3);
        Mob m = new Mob(le.getUniqueId(), name, tier, type, minDMG, maxDMG, t, mh, mc, ml, mb, Constants.randomArmorFlag(mh, tier, 1.3),
                Constants.randomArmorFlag(mc, tier, 1.3), Constants.randomArmorFlag(ml, tier, 1.3), Constants.randomArmorFlag(mb, tier, 1.3), "ImUnsmart");
        EntityManager.mobs.put(le.getUniqueId(), m);
        Bukkit.broadcastMessage(name + ChatColor.RED.toString() + ChatColor.BOLD + " has spawned.");
        return le;
    }

    public void init(Main pl) {
        final LivingEntity le = spawn();
        new BukkitRunnable() {
            int time = 60 * tier;

            public void run() {
                if (le == null || le.isDead()) {
                    time--;
                    if (time < 1) {
                        cancel();
                        init(pl);
                    }
                }
            }
        }.runTaskTimer(pl, 0, 20);
    }
}
