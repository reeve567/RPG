package me.imunsmart.rpg.util;

import org.bukkit.Location;

public class MathUtils {

    public static boolean inCircle(Location l, Location c, int r) {
        int x = l.getBlockX();
        int z = l.getBlockZ();
        int cx = c.getBlockX();
        int cz = c.getBlockZ();
        double d = ((x - cx) * (x - cx))  + ((z - cz) * (z - cz));
        return d <= r;
    }
}
