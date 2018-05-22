package me.imunsmart.rpg.util;

import me.imunsmart.rpg.mechanics.Stats;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Util {
    public static final String logo = ChatColor.AQUA.toString() + ChatColor.BOLD + "Placeholder";

    public static final World w = Bukkit.getWorld("world");
    public static final Location spawn = new Location(w, 0.5, 65.5, 0.5);

    public static final int[] s_radi = {25,};
    public static final Location[] safeZones = {spawn};

    public static final String[] warpNames = {"Spawn", "Credits"};
    public static final Location[] warps = {spawn, new Location(Util.w, 10.5, 12, 182.5)};

    public static final int[] p_radi = {};
    public static final Location[] pvpZones = {};

    public static float baseXP = 200;
    public static float mult = 1.095f;

    public static String[] names = {"ImUnsmart", "Xwy", "maxrocks0406"};

    public static boolean inPvPZone(Player p) {
        return inZone(p, pvpZones, p_radi);
    }

    private static boolean inZone(Player p, Location[] zones, int[] radi) {
        for (int i = 0; i < zones.length; i++) {
            Location l = zones[i];
            int r = radi[i] * radi[i];
            if (p.getLocation().distanceSquared(l) <= r)
                return true;
        }
        return false;
    }

    public static boolean inSafeZone(Player p) {
        return inZone(p, safeZones, s_radi);
    }

    public static float neededXP(OfflinePlayer p) {
        return (float) Math.pow(mult, Stats.getLevel(p)) * baseXP;
    }

    public static boolean validClick(PlayerInteractEvent e) {
        return e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK);
    }

    public static Location getWarp(String name) {
        for (int i = 0; i < warpNames.length; i++) {
            String s = warpNames[i];
            if (name.equalsIgnoreCase(s)) return warps[i];
        }
        return null;
    }

    public static boolean moved(Location prev, Location next) {
        return prev.distanceSquared(next) >= 0.01;
    }

}
