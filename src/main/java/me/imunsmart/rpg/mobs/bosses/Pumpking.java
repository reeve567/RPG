package me.imunsmart.rpg.mobs.bosses;

import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.mobs.Mob;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class Pumpking {
    public static int flag = 0;

    public static void handleDamage(Mob m) {
        if (m.getHealthPercentage() <= 0.5 && flag == 0) {
            spawnMinions(m);
            flag++;
        } else if (m.getHealthPercentage() <= 0.25 && flag == 1) {
            spawnMinions(m);
            flag++;
        }
    }

    private static void spawnMinions(Mob m) {
        for (int i = 0; i < 2; i++) {
            Mob mob = EntityManager.customMob(Util.w.spawn(m.getMob().getLocation(), Zombie.class), ChatColor.GOLD + "Pumpking's Minion", 1, "axe", 3, 5,
                    "Critical:5%,uncommon", 4, 7, 5, 4, "Regen:1", "Regen:1", "Regen:1",
                    "Regen:1", "ImUnsmart");
            mob.getMob().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
            ((Zombie) mob.getMob()).setBaby(true);
            mob.getMob().setRemoveWhenFarAway(true);
        }
        Util.broadcastAround(m.getName() + ": " + ChatColor.WHITE + "Don't touch me!", m.getMob().getLocation(), 20);
    }
}
