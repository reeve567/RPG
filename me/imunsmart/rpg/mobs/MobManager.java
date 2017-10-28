package me.imunsmart.rpg.mobs;

import java.util.HashMap;
import java.util.Iterator;

import me.imunsmart.rpg.mechanics.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Health;
import net.md_5.bungee.api.ChatColor;

public class MobManager implements Listener {
	public static HashMap<LivingEntity, Mob> mobs = new HashMap<LivingEntity, Mob>();
	public static Main pl;

	public MobManager(Main m) {
		pl = m;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(m, new Runnable() {
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
		Bukkit.getPluginManager().registerEvents(this, m);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (mobs.containsKey(e.getEntity()))
			e.setDamage(0);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (mobs.containsKey(e.getEntity()) && e.getDamager() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getDamager();
			LivingEntity hit = (LivingEntity) e.getEntity();
			double damage = 1;
			if (le.getEquipment().getItemInMainHand() != null)
				damage = Health.getAttributeI(le.getEquipment().getItemInMainHand(), "Damage");
			String name = le.getEquipment().getItemInMainHand().getType().name();
			if (name.contains("SWORD") || name.contains("AXE")) {
				incDur(le.getEquipment().getItemInMainHand());
			}
			for(ItemStack i : hit.getEquipment().getArmorContents()) {
				incDur(i);
			}
			if (damage == 0)
				damage += 1;
			if (le instanceof Player) {
				ActionBar actionBar = new ActionBar(ChatColor.GREEN + "Damage -> " + ChatColor.RED + mobs.get(e.getEntity()).getHealth() + " - " + ChatColor.BOLD + damage);
				actionBar.sendToPlayer((Player) le);
				le.sendMessage(ChatColor.GREEN + "Damage -> " + ChatColor.RED + mobs.get(e.getEntity()).getHealth() + " - " + ChatColor.BOLD + damage);
			}
			mobs.get(e.getEntity()).damage(damage);
		}
	}

	private void incDur(ItemStack i) {
		int v = i.getType().name().contains("AXE") ? 1 : 0;
		short dur = (short) (i.getDurability() - v);
		if (dur < 0)
			dur = 0;
		if (i.getType().name().contains("AXE")) {
			i.setDurability(dur);
		}
		dur = (short) (i.getDurability() - 1);
		if (dur < 0)
			dur = 0;
		if (i.getType().name().contains("GOLD")) {
			if (Math.random() > 0.05) {
				i.setDurability(dur);
			}
		}
		if (i.getType().name().contains("STONE")) {
			if (Math.random() > 0.15) {
				i.setDurability(dur);
			}
		}
		if (i.getType().name().contains("WOOD")) {
			if (Math.random() > 0.3) {
				i.setDurability(dur);
			}
		}
	}

	public static Mob spawn(Location l, String type, int tier) {
		if (type.equalsIgnoreCase("zombie")) {
			Zombie z = l.getWorld().spawn(l, Zombie.class);
			Mob m = new Mob(z, ChatColor.GREEN + DropManager.getRandomZombieName(tier), tier);
			z.setCustomNameVisible(true);
			return mobs.put(z, m);
		}
		return null;
	}
}
