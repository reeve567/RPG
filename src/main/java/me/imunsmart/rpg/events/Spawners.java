package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.mobs.Mob;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Spawners {
    public static List<Spawner> spawns = new ArrayList<Spawner>();
    private static File spawn;
    private static Spawners spawners;
    private static int total = 0;
    Main pl;

    public Spawners(Main pl) {
        this.pl = pl;
        spawn = new File(pl.getDataFolder(), "spawners.yml");
        spawners = this;
        reloadSpawners();
    }

    public static void disable() {
        for (LivingEntity le : Util.w.getLivingEntities()) {
            if (le.getCustomName() != null && !le.getScoreboardTags().contains("npc"))
                le.remove();
        }
    }

    public static void reloadSpawners() {
        disable();
        spawns.clear();
        FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
        total = fc.getInt("total", 0);
        for (String name : fc.getKeys(false)) {
            if (name.equalsIgnoreCase("total")) continue;
            spawns.add(new Spawner(spawners, name, fc.getString(name + ".type"), Bukkit.getWorld(fc.getString(name + ".world")), fc.getInt(name + ".x"), fc.getInt(name + ".y"), fc.getInt(name + ".z"), fc.getInt(name + ".tier"), fc.getInt(name + ".max")).spawn());
        }
    }

    public static boolean remove(Location l) {
        boolean b = false;
        Iterator<Spawner> it = spawns.iterator();
        while (it.hasNext()) {
            Spawner ss = it.next();
            if (ss.getLocation().getBlockX() == l.getBlockX() && ss.getLocation().getBlockY() == l.getBlockY() && ss.getLocation().getBlockZ() == l.getBlockZ()) {
                it.remove();
                FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
                fc.set(ss.getName(), null);
                try {
                    fc.save(spawn);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b = true;
            }
        }
        return b;
    }

    public static void setSpawn(Location l, int tier, int max, String type) {
        String w = l.getWorld().getName();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        String name = String.valueOf(total++);
        FileConfiguration fc = YamlConfiguration.loadConfiguration(spawn);
        fc.set("total", total);
        fc.set(name + ".world", w);
        fc.set(name + ".x", x);
        fc.set(name + ".y", y);
        fc.set(name + ".z", z);
        fc.set(name + ".tier", tier);
        fc.set(name + ".max", max);
        fc.set(name + ".type", type);
        try {
            fc.save(spawn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadSpawners();
    }

    public static void removeEntity(Mob m) {
        for (Spawner s : spawns) {
            if (s.contains(m)) {
                s.getSpawned().remove(m);
                s.dec();
            }
        }
    }
}

class Spawner {
    private List<Mob> spawned = new ArrayList<>();
    private String name, type;
    private int tier, max, amount;
    private World w;
    private Spawners s;
    private Location l;

    public Spawner(Spawners s, String name, String type, World w, int x, int y, int z, int tier, int max) {
        this.s = s;
        this.name = name;
        this.type = type;
        this.l = new Location(w, x, y, z);
        this.tier = tier;
        this.max = max;
    }

    public Spawner spawn() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(s.pl, () -> {
            if (amount < max) {
                spawned.add(EntityManager.spawn(l.clone().add(0.5, 0.5, 0.5), type, tier));
                amount++;
            }
        }, 0, (900 * tier));
        return this;
    }

    public Location getLocation() {
        return l;
    }

    public String getName() {
        return name;
    }

    public int getTier() {
        return tier;
    }

    public int getMax() {
        return max;
    }

    public List<Mob> getSpawned() {
        return spawned;
    }

    public boolean contains(Mob m) {
        for (Mob s : spawned) {
            if (m.getMob() == s.getMob())
                return true;
        }
        return false;
    }

    public void dec() {
        amount--;
    }
}