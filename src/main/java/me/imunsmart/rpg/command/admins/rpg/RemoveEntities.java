package me.imunsmart.rpg.command.admins.rpg;

import me.imunsmart.rpg.util.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RemoveEntities {

    private static final String[] tags = {"hologram-text", "npc", "monster"};

    public static void run(Player p) {
        for (Entity e : Util.w.getEntities()) {
            if (!(e instanceof Player)) {
                boolean found = false;
                for (String s : tags) {
                    if (found) continue;
                    if (e.getScoreboardTags().contains(s)) found = true;
                }
                if (!found) {
                    p.sendMessage("failure");
                    e.remove();
                } else {
                    p.sendMessage("success");
                }
            }
        }
    }
}
