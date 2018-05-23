package me.imunsmart.rpg.mobs;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class Mob {
    public LivingEntity mob;
    private double health, maxHP;
    private ItemStack drop;
    private Main pl;
    private int tier;
    private Location loc;
    private HashMap<String, Integer> hits = new HashMap<>();
    private int hitsTaken = 0;

    public Mob(LivingEntity mob, String name, int tier) {
        this.mob = mob;
        this.tier = tier;
        loc = mob.getLocation();
        mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
        pl = EntityManager.pl;
        // Create Items
        String type = Math.random() < 0.5 ? "sword" : "axe";
        ItemStack h = Items.getRandomArmorPiece(tier, "helmet");
        ItemStack c = Items.getRandomArmorPiece(tier, "chestplate");
        ItemStack l = Items.getRandomArmorPiece(tier, "leggings");
        ItemStack b = Items.getRandomArmorPiece(tier, "boots");
        ItemStack w = Items.getRandomWeapon(tier, type);
        // Drops
        if (Math.random() < 0.1) {
            drop = h;
        } else if (Math.random() < 0.1) {
            drop = c;
        } else if (Math.random() < 0.1) {
            drop = l;
        } else if (Math.random() < 0.1) {
            drop = b;
        } else if (Math.random() < 0.1) {
            drop = w;
        }
        // Wear
        if (Math.random() > 0.25)
            mob.getEquipment().setChestplate(c);
        if (Math.random() > 0.25)
            mob.getEquipment().setLeggings(l);
        if (Math.random() > 0.25)
            mob.getEquipment().setBoots(b);
        mob.getEquipment().setItemInMainHand(w);
        // Drop Chances
        mob.getEquipment().setBootsDropChance(0);
        mob.getEquipment().setHelmetDropChance(0);
        mob.getEquipment().setChestplateDropChance(0);
        mob.getEquipment().setLeggingsDropChance(0);
        mob.getEquipment().setItemInMainHandDropChance(0);
        // Wear Skull
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        sm.setOwner(Util.names[(int) (Math.random() * Util.names.length)]);
        skull.setItemMeta(sm);
        mob.getEquipment().setHelmet(skull);

        maxHP = health = Health.calculateMaxHealth(mob);
        mob.setCustomName(name);
        mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * (4.0 / 3.0));
    }

    public Mob(LivingEntity mob, String name, String type, int min, int max, String flag, int tier, int maxHelmet, int maxChestplate, int maxLeggings, int maxBoots,
               String helmetFlag, String chestplateFlag, String leggingsFlag, String bootsFlag, String skullName) {
        this.mob = mob;
        this.tier = tier;
        loc = mob.getLocation();
        mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(8.0);
        pl = EntityManager.pl;
        // Create Items
        ItemStack h = Items.createArmor("helmet", tier, maxHelmet, helmetFlag);
        ItemStack c = Items.createArmor("chestplate", tier, maxChestplate, chestplateFlag);
        ItemStack l = Items.createArmor("leggings", tier, maxLeggings, leggingsFlag);
        ItemStack b = Items.createArmor("boots", tier, maxBoots, bootsFlag);
        ItemStack w = Items.getRandomWeapon(tier, type);
        // Drops
        if (Math.random() < 0.1) {
            drop = h;
        } else if (Math.random() < 0.1) {
            drop = c;
        } else if (Math.random() < 0.1) {
            drop = l;
        } else if (Math.random() < 0.1) {
            drop = b;
        } else if (Math.random() < 0.1) {
            drop = w;
        }
        // Wear
        if (mob.getType() == EntityType.ZOMBIE || mob.getType() == EntityType.SKELETON) {
            if (Math.random() > 0.25)
                mob.getEquipment().setChestplate(c);
            if (Math.random() > 0.25)
                mob.getEquipment().setLeggings(l);
            if (Math.random() > 0.25)
                mob.getEquipment().setBoots(b);
            mob.getEquipment().setItemInMainHand(w);
            // Drop Chances
            mob.getEquipment().setBootsDropChance(0);
            mob.getEquipment().setHelmetDropChance(0);
            mob.getEquipment().setChestplateDropChance(0);
            mob.getEquipment().setLeggingsDropChance(0);
            mob.getEquipment().setItemInMainHandDropChance(0);
            // Wear Skull
            ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta sm = (SkullMeta) skull.getItemMeta();
            sm.setOwner(skullName);
            skull.setItemMeta(sm);
            mob.getEquipment().setHelmet(skull);
        }
        maxHP = health = Health.calculateMaxHealth(mob);
        mob.setCustomName(name);
        mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(mob.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * (4.0 / 3.0));
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
                double perc = (double) hits.get(s) / hitsTaken;
                double xp = Math.pow(1.75, tier) * 6;
                double bonus = 1 + ((double) Constants.LEVEL_REQ[tier - 1] / Stats.getLevel(p)) / 100.0;
                double totalXP = perc * Math.random() * (xp - 1) * bonus;
                Stats.addXP(p, (int) totalXP + 1);
            }
        }
        hits.clear();
    }

    public void damage(double i, Player p) {
        hitsTaken++;
        if (p != null)
            hits.put(p.getName(), !hits.containsKey(p.getName()) ? 1 : hits.get(p.getName()) + 1);
        health -= i;
        if (health < 0)
            health = 0;
        double perc = (health / (double) maxHP);
        String bar = ChatColor.GREEN.toString();
        // int x = 10;
        // for (int h = 0; h < (perc * 10); h++) {
        // bar += "♥";
        // x--;
        // }
        // bar += ChatColor.WHITE.toString();
        // for (int h = 0; h < x; h++) {
        // bar += "♥";
        // }
        mob.setCustomName(ChatColor.WHITE.toString() + (int) health + " " + ChatColor.RED.toString() + ChatColor.BOLD + "HP");
    }

    public double getHealth() {
        return health;
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
}
