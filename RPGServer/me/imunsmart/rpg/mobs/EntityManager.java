package me.imunsmart.rpg.mobs;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.ActionBar;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.Sounds;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.util.Util;
import net.md_5.bungee.api.ChatColor;

public class EntityManager implements Listener {
	public static HashMap<LivingEntity, Mob> mobs = new HashMap<LivingEntity, Mob>();
	public static Main pl;

	public EntityManager(Main m) {
		pl = m;
		task();
		Bukkit.getPluginManager().registerEvents(this, m);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (mobs.containsKey(e.getEntity()))
			e.setDamage(0);
	}

	public HashMap<String, Integer> weapon = new HashMap<>();
	public HashMap<String, int[]> armor = new HashMap<>();

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
						p.sendMessage(ChatColor.RED + "You cannot use that item yet.");
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
				double hp = mobs.containsKey(e.getEntity()) ? mobs.get(e.getEntity()).getHealth() : Health.health.containsKey(e.getEntity().getName()) ? Health.health.get(e.getEntity().getName()) : 0;
				String message = ChatColor.GRAY + "Damage: [" + ChatColor.GREEN + (int) hp + " - " + (int) damage + ChatColor.GRAY + "] => " + ChatColor.RED + (int) (hp - damage);
				if (crit) {
					message = ChatColor.YELLOW.toString() + ChatColor.BOLD + "!CRIT! " + message;
					Sounds.play(p, Sound.BLOCK_ANVIL_PLACE, 1);
				}
				ActionBar ab = new ActionBar(message);
				ab.sendToPlayer(p);
				Health.combat.put(p.getName(), 16);
			}
			if (hit instanceof Player) {
				Player p = (Player) hit;
				Health.combat.put(p.getName(), 16);
				Health.damage(p, (int) damage);
			} else if (mobs.containsKey(e.getEntity())) {
				mobs.get(e.getEntity()).damage(damage, le instanceof Player ? (Player) le : null);
			}
			e.setDamage(0);
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if (mobs.containsKey(e.getEntity())) {
			e.setDroppedExp(0);
			e.getDrops().clear();
		}
	}

	private void incWep(Player p, ItemStack i) {
		if (i == null)
			return;
		weapon.put(p.getName(), weapon.containsKey(p.getName()) ? weapon.get(p.getName()) + 1 : 1);
		int v = i.getType().name().contains("AXE") ? 2 : 1;
		i.setDurability((short) (i.getDurability() - v));
		if (i.getType().name().contains("GOLD") && weapon.get(p.getName()) >= 100) {
			i.setDurability((short) (i.getDurability() + 1));
			weapon.remove(p.getName());
		} else if (i.getType().name().contains("STONE") && weapon.get(p.getName()) >= 50) {
			i.setDurability((short) (i.getDurability() + 1));
			weapon.remove(p.getName());
		} else if (i.getType().name().contains("WOOD") && weapon.get(p.getName()) >= 30) {
			i.setDurability((short) (i.getDurability() + 1));
			weapon.remove(p.getName());
		}
	}

	private void incArmor(Player p, ItemStack[] a) {
		for (int x = 0; x < a.length; x++) {
			ItemStack i = a[x];
			if (i == null)
				continue;
			int[] temp = { 0, 0, 0, 0 };
			if (armor.containsKey(p.getName()))
				temp = armor.get(p.getName());
			temp[x]++;
			i.setDurability((short) (i.getDurability() - 1));
			if (i.getType().name().contains("GOLD") && temp[x] >= 100) {
				i.setDurability((short) (i.getDurability() + 1));
				temp[x] = 0;
			} else if (i.getType().name().contains("CHAINMAIL") && temp[x] >= 50) {
				i.setDurability((short) (i.getDurability() + 1));
				temp[x] = 0;
			} else if (i.getType().name().contains("LEATHER") && temp[x] >= 30) {
				i.setDurability((short) (i.getDurability() + 1));
				temp[x] = 0;
			}
			armor.put(p.getName(), temp);
		}
	}

	private void incDur(ItemStack i) {
		if (i == null)
			return;
		int v = i.getType().name().contains("AXE") ? 2 : 1;
		i.setDurability((short) (i.getDurability() - v));
	}

	public static Mob spawn(Location l, String type, int tier) {
		if (type.equalsIgnoreCase("zombie")) {
			Zombie z = l.getWorld().spawn(l, Zombie.class);
			z.setBaby(false);
			Mob m = new Mob(z, ChatColor.GREEN + Constants.getRandomZombieName(tier), tier);
			z.setCustomNameVisible(true);
			return mobs.put(z, m);
		}
		if (type.equalsIgnoreCase("skeleton")) {
			Skeleton s = l.getWorld().spawn(l, Skeleton.class);
			Mob m = new Mob(s, ChatColor.GREEN + Constants.getRandomSkeletonName(tier), tier);
			s.setCustomNameVisible(true);
			return mobs.put(s, m);
		}
		return null;
	}

	public static void disable() {
		for (LivingEntity le : mobs.keySet()) {
			le.remove();
		}
	}

	private void task() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
			@Override
			public void run() {
				Iterator<LivingEntity> it = mobs.keySet().iterator();
				while (it.hasNext()) {
					LivingEntity le = it.next();
					Mob m = mobs.get(le);
					m.tick();
					if (m.getHealth() < 1) {
						it.remove();
					}
				}
			}
		}, 0, 2);
	}
}
