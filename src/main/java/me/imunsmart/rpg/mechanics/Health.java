package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.command.admins.playermoderation.BanManager;
import me.imunsmart.rpg.mobs.Constants;
import me.imunsmart.rpg.util.MessagesUtil;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Health {

    public static HashMap<String, Integer> health = new HashMap<>();
    public static HashMap<String, BossBar> bar = new HashMap<>();
    public static HashMap<String, Integer> combat = new HashMap<>();

    public static boolean atMaxHealth(Player p) {
        if (!health.containsKey(p.getName()))
            return true;
        return health.get(p.getName()) == calculateMaxHealth(p);
    }

    public static int calculateHealthRegen(Player p) {
        PlayerInventory pi = p.getInventory();
        int regen = 2;
        for (int i = 0; i < pi.getArmorContents().length; i++) {
            ItemStack it = pi.getArmorContents()[i];
            if (it != null) {
                if (it.hasItemMeta()) {
                    regen += getAttribute(it, "Regen");
                }
            }
        }
        return regen;
    }

    public static int calculateMaxHealth(LivingEntity e) {
        int maxhp = e instanceof Player ? 50 : 20;
        for (int i = 0; i < e.getEquipment().getArmorContents().length; i++) {
            ItemStack it = e.getEquipment().getArmorContents()[i];
            if (it != null) {
                if (!it.hasItemMeta()) {
                    e.getEquipment().getArmorContents()[i].setType(Material.AIR);
                } else {
                    if (it.getType() == Material.LEGACY_SKULL_ITEM)
                        continue;
                    maxhp += getAttribute(it, "Health");
                }
            }
        }
        return maxhp;
    }

    public static void damage(Player p, int i) {
        if (Util.inSafeZone(p))
            return;
        int hp = health.get(p.getName());
        hp -= i;
        if (hp < 0)
            hp = 0;
        if (hp == 0) {
            p.setHealth(0);
        }
        health.put(p.getName(), hp);
        if ((double) hp / calculateMaxHealth(p) < 0.2) {
            Sounds.play(p, Sound.ENTITY_PLAYER_BIG_FALL, 1);
        }
        new ActionBar(MessagesUtil.damage(hp, i)).sendToPlayer(p);
    }

    public static void disable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (bar.containsKey(p.getName())) {
                BossBar b = bar.remove(p.getName());
                b.removeAll();
            }
        }
    }

    public static int getAttribute(ItemStack i, String name) {
        if (!hasAttribute(i, name)) {
            return 0;
        } else {
            for (String s : i.getItemMeta().getLore()) {
                String line = ChatColor.stripColor(s);
                if (line.split(":")[0].equalsIgnoreCase(name)) {
                    String val = line.split(" ")[line.split(" ").length - 1];
                    if (val.contains("+"))
                        val = val.substring(1);
                    if(val.contains("%"))
                        val = val.substring(0, val.length() - 1);
                    if (val.contains("-")) {
                        int min = Integer.parseInt(val.split("-")[0]);
                        return (min + (int) Math.round(Math.random() * (Integer.parseInt(val.split("-")[1]) - min)));
                    }
                    return Integer.parseInt(val);
                }
            }
        }
        return 0;
    }

    public static ItemStack setAttribute(ItemStack item, String name, String value) {
        int pos = getAttributePos(item, name);
        if(pos == -1)
            return item;
        ItemMeta im = item.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(pos, ChatColor.RED + name + ": " + value);
        im.setLore(lore);
        return item;
    }

    public static List<String> getAttributes(ItemStack item) {
        List<String> att = new ArrayList<>();
        if(!item.hasItemMeta() && item.getItemMeta().hasLore())
            return att;
        for(String s : item.getItemMeta().getLore()) {
            if(s.contains(":")) {
                att.add(ChatColor.stripColor(s.split(":")[0]));
            }
        }
        return att;
    }

    public static boolean hasAttribute(ItemStack i, String name) {
        if (i == null)
            return false;
        if (!i.hasItemMeta() || !i.getItemMeta().hasLore()) {
            return false;
        }
        for (String s : i.getItemMeta().getLore()) {
            String l = ChatColor.stripColor(s);
            if (l.split(":")[0].equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    public static int getAttributePos(ItemStack i, String name) {
        if (i == null)
            return -1;
        if (!i.hasItemMeta() || !i.getItemMeta().hasLore()) {
            return -1;
        }
        for (int j = 0; j < i.getItemMeta().getLore().size(); j++) {
            String l = ChatColor.stripColor(i.getItemMeta().getLore().get(j));
            if (l.split(":")[0].equalsIgnoreCase(name))
                return j;
        }
        return -1;
    }

    public static void heal(Player p, int i) {
        if (!health.containsKey(p.getName()))
            return;
        int hp = health.get(p.getName());
        int max = calculateMaxHealth(p);
        hp += i;
        if (hp > max)
            hp = max;
        health.put(p.getName(), hp);
        Nametags.setScore(p, health.get(p.getName()));
    }

    public static void resetPlayer(Player p) {
        p.teleport(Util.spawn);
        ItemStack i = Items.createWeapon("sword", 1, 4, 8, "");
        i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        p.getInventory().addItem(i);
        i = Items.createArmor("boots", 1, Constants.MAX_HEALTH_1B / 2, "Regen:2");
        i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        p.getInventory().addItem(i);
        i = Items.createArmor("leggings", 1, Constants.MAX_HEALTH_1L / 2, "Regen:4");
        i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        p.getInventory().addItem(i);
        i = Items.createArmor("chestplate", 1, Constants.MAX_HEALTH_1C / 2, "Regen:5");
        i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        p.getInventory().addItem(i);
        i = Items.createArmor("helmet", 1, Constants.MAX_HEALTH_1H / 2, "Regen:3");
        i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        p.getInventory().addItem(i);
        combat.remove(p.getName());
        heal(p, 50);
    }

    public static void task(Main pl) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
            BanManager.security();
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Double.MAX_VALUE);
                p.setLevel(Stats.getInt(p, "level", 1));
                float xp = Math.min((float) Stats.getInt(p, "xp", 0) / (Util.neededXP(p)), 1);
                p.setExp(xp);
                if (!health.containsKey(p.getName())) {
                    health.put(p.getName(), calculateMaxHealth(p));
                }
                int max = calculateMaxHealth(p);
                int hp = health.get(p.getName());

                if (hp > max) {
                    hp = max;
                    health.put(p.getName(), hp);
                }

                if (!p.isDead()) {
                    int regen = calculateHealthRegen(p);
                    if (Util.inSafeZone(p))
                        regen *= 25;
                    if (combat.containsKey(p.getName())) {
                        if (combat.get(p.getName()) == 0) {
                            combat.remove(p.getName());
                            p.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Exiting combat.");
                        } else {
                            combat.put(p.getName(), combat.get(p.getName()) - 1);
                        }
                    } else {
                        if (hp != max)
                            heal(p, regen);
                    }
                }

                if (!p.isDead()) {
                    double per = ((double) hp / (double) max);
                    if (per > 1)
                        per = 1;
                    p.setHealth(1 + (19 * per));
                    BossBar b;
                    if (!bar.containsKey(p.getName()))
                        b = Bukkit.createBossBar("health", BarColor.GREEN, BarStyle.SOLID);
                    else
                        b = bar.get(p.getName());
                    b.setTitle(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Health: " + hp + " / " + max);
                    b.setProgress(per);
                    b.setVisible(true);
                    Nametags.setScore(p, hp);

                    if (!bar.containsKey(p.getName())) {
                        b.addPlayer(p);
                        bar.put(p.getName(), b);
                    } else {
                        b.removeAll();
                        b.addPlayer(p);
                    }

                    for (int x = 0; x < p.getInventory().getArmorContents().length; x++) {
                        ItemStack i = p.getInventory().getArmorContents()[x];
                        if (i == null)
                            continue;
                        int tier = Items.getTier(i);
                        if (!Stats.canWield(p, tier)) {
                            ItemStack[] armor = new ItemStack[4];
                            for (int y = 0; y < p.getInventory().getArmorContents().length; y++) {
                                if (y != x)
                                    armor[y] = p.getInventory().getArmorContents()[y];
                            }
                            p.getInventory().setArmorContents(armor);
                            if (p.getInventory().firstEmpty() != -1)
                                p.getInventory().addItem(i);
                            else
                                p.getWorld().dropItemNaturally(p.getEyeLocation(), i);
                            p.sendMessage(ChatColor.RED + "You must be level " + ChatColor.UNDERLINE + Constants.LEVEL_REQ[tier - 1] + ChatColor.RED + " to wield Tier " + tier + " armor.");
                            Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
                            Nametags.setScore(p, hp);
                        }
                    }
                }
            }
        }, 0, 10);
    }

    public static boolean inCombat(Player p) {
        return combat.containsKey(p.getName());
    }
}