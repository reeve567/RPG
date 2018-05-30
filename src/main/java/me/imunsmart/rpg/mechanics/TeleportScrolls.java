package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportScrolls implements Listener {
    private Main pl;

    public TeleportScrolls(Main pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Util.validClick(e)) {
            if (e.getItem().getType() == Material.BOOK && e.getItem().hasItemMeta()) {
                Items.useItem(p);
                String name = ChatColor.stripColor(e.getItem().getItemMeta().getDisplayName()).split(" ")[1];
                int delay = Integer.parseInt(ChatColor.stripColor(e.getItem().getItemMeta().getLore().get(0)).split(" ")[2]);
                Location original = p.getLocation();
                p.sendMessage(ChatColor.GRAY + "Teleporting in...");

                Sounds.play(p, Sound.AMBIENT_CAVE, 1);
                p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 8*20, 0));

                new BukkitRunnable() {
                    int x = delay;

                    @Override
                    public void run() {
                        System.out.println(getTaskId());
                        if (Util.moved(original, p.getLocation())) {
                            p.sendMessage(ChatColor.RED + "You moved, cancelling teleport.");
                            cancel();
                            return;
                        } else if(x != 0)
                            p.sendMessage(ChatColor.AQUA.toString() + x + "s...");
                        if (x == 0) {
                            p.sendMessage(ChatColor.GRAY + "You successfully teleported to " + ChatColor.AQUA + name + ChatColor.GRAY + ".");
                            p.teleport(Util.getTP(name));

                            p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                            Sounds.play(p, Sound.ENTITY_ENDERMEN_TELEPORT, 0.67f);
                            cancel();
                            return;
                        }
                        x--;
                    }
                }.runTaskTimer(pl, 0, 20);
            }
        }
    }
}
