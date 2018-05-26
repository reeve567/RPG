package me.imunsmart.rpg.util;

import me.imunsmart.rpg.mechanics.ActionBar;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mobs.Constants;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import sun.misc.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Util {
    public static final String logo = ChatColor.AQUA.toString() + ChatColor.BOLD + "FallenRealms";
    public static final String motd = ChatColor.GRAY.toString() + ChatColor.BOLD + "Closed beta coming soon.";

    public static final World w = Bukkit.getWorld("world");
    public static final Location spawn = new Location(w, 0.5, 65.5, 0.5);

    public static final int[] s_radi = {25,};
    public static final Location[] safeZones = {spawn};

    public static final String[] warpNames = {"Spawn", "Credits"};
    public static final Location[] warps = {spawn, new Location(Util.w, 10.5, 12, 182.5)};

    public static final String[] tpNames = {"Spawn"};
    public static final Location[] tp = {spawn};

    public static final int[] p_radi = {};
    public static final Location[] pvpZones = {};

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

    public static int neededXP(OfflinePlayer p) {
        return (int) (Math.pow(Constants.MULT, Stats.getLevel(p)) * Constants.BASE_XP);
    }

    public static int pickXP(int level) {
        return (int) (Math.pow(Constants.PICK_MULT, level) * Constants.PICK_BASE_XP);
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

    public static Location getTP(String name) {
        for (int i = 0; i < tpNames.length; i++) {
            String s = tpNames[i];
            if (name.equalsIgnoreCase(s)) return tp[i];
        }
        return null;
    }

    public static boolean moved(Location prev, Location next) {
        return prev.distanceSquared(next) >= 0.01;
    }

    private static HashMap<String, Integer> uses = new HashMap<String, Integer>();

    public static void usePick(Player p, int t) {
        ItemStack i = p.getInventory().getItemInMainHand();
        int tier = Items.getTier(i);
        if(!uses.containsKey(p.getName()))
            uses.put(p.getName(), 0);
        uses.put(p.getName(), uses.get(p.getName()) + 1);
        if(uses.get(p.getName()) >= Constants.USE_ITEM[tier - 1]) {
            i.setDurability((short) (i.getDurability() + 1));
            if (i.getDurability() > i.getType().getMaxDurability()) {
                p.getInventory().setItemInMainHand(null);
                Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 1);
            }
            uses.put(p.getName(), 0);
        }
        int level = Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(2)).split(" ")[1]);
        int xp = Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(3)).split(" ")[1]);
        int x = 50 + (int) (Math.random() * (75 * t));
        xp += x;
        ItemMeta im = i.getItemMeta();
        if(xp >= pickXP(level)) {
            xp -= pickXP(level);
            level++;
            if(level % 20 == 0 && tier != 5) {
                float perc = (float) i.getDurability() / (float) i.getType().getMaxDurability();
                i.setType(Material.valueOf(Items.tools[tier] + "_PICKAXE"));
                i.setDurability((short) (i.getType().getMaxDurability() * perc));
                im.setDisplayName(Items.nameColor[tier] + Items.picks[tier] + " Pickaxe");
                Firework fw = p.getWorld().spawn(p.getLocation(), Firework.class);
                FireworkMeta fm = fw.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder().with(FireworkEffect.Type.BALL_LARGE).withColor(Color.BLUE).withFade(Color.AQUA).flicker(true).trail(true).build());
                fm.setPower(1);
                fw.setFireworkMeta(fm);
                p.sendMessage(MessagesUtil.pickaxeTier(tier + 1));
                Sounds.play(p, Sound.ENTITY_PLAYER_LEVELUP, 1);
            }
            new ActionBar(ChatColor.AQUA + "Level Up! [" + (level - 1) + "->" + level + "]").sendToPlayer(p);
        } else
            new ActionBar(ChatColor.YELLOW + "+" + x + "XP").sendToPlayer(p);
        List<String> lore = im.getLore();
        lore.set(2, ChatColor.GRAY + "Level: " + ChatColor.AQUA + level);
        lore.set(3, ChatColor.GRAY + "XP: " + ChatColor.AQUA + xp + " / " + Util.pickXP(level));
        im.setLore(lore);
        i.setItemMeta(im);
    }

}
