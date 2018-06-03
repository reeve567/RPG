package me.imunsmart.rpg.mobs;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.util.LocationUtility;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class EntityManager implements Listener {
    public static HashMap<UUID, Mob> mobs = new HashMap<>();
    public static Main pl;
    private HashMap<String, Integer> weapon = new HashMap<>();
    private HashMap<String, int[]> armor = new HashMap<>();
    private Boss pumpking;

    public EntityManager(Main m) {
        pl = m;
        Bukkit.getPluginManager().registerEvents(this, m);
        task();
        new BukkitRunnable() {
            public void run() {
                init();
            }
        }.runTaskLater(pl, 60);
    }

    private void init() {
        pumpking = new Boss(new Location(Util.w, 11.5, 64.5, -55.5), Zombie.class, ChatColor.GOLD.toString() + ChatColor.BOLD + "Pumpking", 1, 30, 45,
                20, 15, 8, 10, 5, 4, 5, 15, "The oppressive", new Runnable() {
            @Override
            public void run() {
                pumpking.getMob().getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
            }
        });
        pumpking.init(pl);
    }

    private void task() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
            Iterator<UUID> it = mobs.keySet().iterator();
            while (it.hasNext()) {
                UUID le = it.next();
                Mob m = mobs.get(le);
                m.tick();
                if (m.getHealth() < 1) {
                    System.out.println(m.getMob().getCustomName());
                    it.remove();
                }
            }
        }, 0, 2);
    }

    public static Mob spawn(Location l, String type, int tier) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("zombie")) {
            Zombie z = l.getWorld().spawn(l, Zombie.class);
            z.setBaby(false);
            Mob m = new Mob(z.getUniqueId(), ChatColor.GREEN + Constants.getRandomZombieName(tier), tier);
            mobs.put(z.getUniqueId(), m);
            return m;
        }
        if (type.equalsIgnoreCase("skeleton")) {
            Skeleton s = l.getWorld().spawn(l, Skeleton.class);
            Mob m = new Mob(s.getUniqueId(), ChatColor.GREEN + Constants.getRandomSkeletonName(tier), tier);
            mobs.put(s.getUniqueId(), m);
            return m;
        }
        if (type.equalsIgnoreCase("spider")) {
            Spider s = l.getWorld().spawn(l, Spider.class);
            Mob m = new Mob(s.getUniqueId(), ChatColor.GREEN + Constants.getRandomSpiderName(tier), tier);
            mobs.put(s.getUniqueId(), m);
            return m;
        }
        return null;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (mobs.containsKey(e.getEntity().getUniqueId()))
            e.setDamage(0);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) e.getDamager();
            LivingEntity hit = (LivingEntity) e.getEntity();
            if (le instanceof Player && hit instanceof Player) {
                Player p = (Player) le;
                Player pp = (Player) hit;
                if (!Util.inPvPZone(p) || !Util.inPvPZone(pp)) {
                    e.setCancelled(true);
                    e.setDamage(0);
                    return;
                }
            }
            if(e.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                e.setCancelled(true);
                e.setDamage(0);
                return;
            }
            double damage = 1;
            ItemStack i = le.getEquipment().getItemInMainHand();
            if (i != null)
                damage = Health.getAttributeI(le.getEquipment().getItemInMainHand(), "Damage");
            String name = i.getType().name();
            if (name.contains("SWORD") || name.contains("AXE")) {
                if (le instanceof Player) {
                    Player p = (Player) le;
                    int tier = Items.getTier(i);
                    if (!Stats.canWield(p, tier)) {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "You must be level " + ChatColor.UNDERLINE + Constants.LEVEL_REQ[tier - 1] + ChatColor.RED + " to wield Tier " + tier + " weapons.");
                        Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 0.67f);
                        e.setDamage(0);
                        return;
                    }
                    incWep((Player) le, i);
                } else
                    incDur(i);
            }
            if (hit instanceof Player)
                incArmor((Player) hit, hit.getEquipment().getArmorContents());
            else {
                for (ItemStack it : hit.getEquipment().getArmorContents()) {
                    incDur(it);
                }
                if(Boss.isBoss(hit)) {
                    Mob mob = mobs.get(hit.getUniqueId());
                    String n = ChatColor.stripColor(mob.getName()).replaceAll(" ", "").trim();
                    try {
                        Class c = Class.forName("me.imunsmart.rpg.mobs.bosses." + n);
                        Method m = c.getDeclaredMethod("handleDamage", Mob.class);
                        m.setAccessible(true);
                        m.invoke(null, mob);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if (damage == 0)
                damage += 1;
            double cc = Health.getAttributeP(i, "Critical");
            boolean crit = false;
            if (Math.random() < cc) {
                damage *= 2;
                crit = true;
            }
            if (le instanceof Player) {
                Player p = (Player) le;
                double hp = 0;
                if (mobs.containsKey(hit.getUniqueId())) {
                    hp = mobs.get(hit.getUniqueId()).getHealth();
                } else if (hit instanceof Player && Health.health.containsKey(e.getEntity().getName()))
                    hp = Health.health.get(e.getEntity().getName());
                String mess = "§c-" + (int) damage + " ♥";
                if (crit) {
                    mess = ChatColor.YELLOW.toString() + ChatColor.BOLD + "!CRIT! " + mess;
                    Sounds.play(p, Sound.BLOCK_ANVIL_PLACE, 1);
                }
                Holograms.TextHologram hologram = Holograms.addTextHologram(LocationUtility.moveUp(hit.getLocation(), 1), mess);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        hologram.disable();
                    }
                }.runTaskLaterAsynchronously(pl, 30);
                Health.combat.put(p.getName(), 16);
            }
            if (hit instanceof Player) {
                Player p = (Player) hit;
                Health.combat.put(p.getName(), 16);
                Health.damage(p, (int) damage);
            } else if (mobs.containsKey(e.getEntity().getUniqueId())) {
                mobs.get(e.getEntity().getUniqueId()).damage(damage, le instanceof Player ? (Player) le : null);
            }
            e.setDamage(0);
        }
    }

    private void incWep(Player p, ItemStack i) {
        if (i == null)
            return;
        int w = weapon.containsKey(p.getName()) ? weapon.get(p.getName()) + 1 : 1;
        weapon.put(p.getName(), w);
        if (i.getType().name().contains("AXE"))
            i.setDurability((short) (i.getDurability() - 2));
        else if (i.getType().name().contains("SWORD"))
            i.setDurability((short) (i.getDurability() - 1));
        int tier = Items.getTier(i);
        if (weapon.get(p.getName()) >= Constants.USE_ITEM[tier - 1]) {
            i.setDurability((short) (i.getDurability() + 1));
            weapon.put(p.getName(), 0);
        }
        if (i.getDurability() > i.getType().getMaxDurability()) {
            p.getInventory().setItemInMainHand(null);
            Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 1);
        }
    }

    private void incDur(ItemStack i) {
        if (i == null)
            return;
        int v = i.getType().name().contains("AXE") ? 2 : 1;
        i.setDurability((short) (i.getDurability() - v));
    }

    private void incArmor(Player p, ItemStack[] a) {
        for (int x = 0; x < a.length; x++) {
            ItemStack i = a[x];
            if (i == null)
                continue;
            int tier = Items.getTier(i);
            int[] temp = {0, 0, 0, 0};
            if (armor.containsKey(p.getName()))
                temp = armor.get(p.getName());
            temp[x]++;
            i.setDurability((short) (i.getDurability() - 1));
            if (temp[x] >= Constants.USE_ITEM[tier - 1]) {
                i.setDurability((short) (i.getDurability() + 1));
                temp[x] = 0;
            }
            if (i.getDurability() > i.getType().getMaxDurability()) {
                a[x] = null;
                p.getInventory().setArmorContents(a);
                Sounds.play(p, Sound.ENTITY_ITEM_BREAK, 1);
            }
            armor.put(p.getName(), temp);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (mobs.containsKey(e.getEntity().getUniqueId())) {
            e.setDroppedExp(0);
            e.getDrops().clear();
        }
    }

    public static Mob customMob(LivingEntity le, String name, int tier, String type, int minDMG, int maxDMG, String weaponFlag, int maxHelmet, int maxChestplate, int maxLeggings,
                                int maxBoots, String helmetFlag, String chestplateFlag, String leggingsFlag, String bootsFlag, String skullName) {
        Mob m = new Mob(le.getUniqueId(), name, tier, type, minDMG, maxDMG, weaponFlag, maxHelmet, maxChestplate, maxLeggings, maxBoots, helmetFlag, chestplateFlag, leggingsFlag, bootsFlag, skullName);
        mobs.put(le.getUniqueId(), m);
        return m;
    }
}
