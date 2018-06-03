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
        if(m.getHealthPercentage() <= 0.5 && flag == 0) {
            for(int i = 0; i < 2; i++) {
                Mob mob = EntityManager.spawn(m.getMob().getLocation(), "zombie", 1);
                mob.getMob().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
                ((Zombie) mob.getMob()).setBaby(true);
                mob.setName(ChatColor.GOLD + "Pumpking's Minion");
                mob.getMob().setRemoveWhenFarAway(true);
            }
            Util.broadcastAround(m.getName() + ": " + ChatColor.WHITE + "Don't touch me!", m.getMob().getLocation(), 20);
            flag++;
        }
    }
}
