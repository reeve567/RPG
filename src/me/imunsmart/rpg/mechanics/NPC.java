package me.imunsmart.rpg.mechanics;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;

public class NPC implements Listener {
	public HashMap<LivingEntity, Data> npc = new HashMap<LivingEntity, Data>();

	private World w = Bukkit.getWorld("world");

	private Main pl;

	public NPC(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		init();
	}

	public void init() {
		createNPC(w.spawn(new Location(w, 2.5, 4, 8.5), IronGolem.class), ChatColor.WHITE.toString() + ChatColor.BOLD + "Bank Guard", guard);
		createNPC(w.spawn(new Location(w, -1.5, 4, 8.5), IronGolem.class), ChatColor.WHITE.toString() + ChatColor.BOLD + "Bank Guard", guard);
		
		createNPC(w.spawn(new Location(w, -7.5, 4, -0.5), Villager.class), ChatColor.GREEN.toString() + ChatColor.BOLD + "Banker", banker, Profession.LIBRARIAN);
		createNPC(w.spawn(new Location(w, 8.5, 4, -0.5), Villager.class), ChatColor.GREEN.toString() + ChatColor.BOLD + "Banker", banker, Profession.LIBRARIAN);
		createNPC(w.spawn(new Location(w, 0.5, 4, -8.5), Villager.class), ChatColor.GREEN.toString() + ChatColor.BOLD + "Banker", banker, Profession.LIBRARIAN);

		createNPC(w.spawn(new Location(w, -10.5, 4, 19.5), Villager.class), ChatColor.GOLD.toString() + ChatColor.BOLD + "Blacksmith", smither, Profession.BLACKSMITH);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
			@Override
			public void run() {
				Iterator<LivingEntity> it = npc.keySet().iterator();
				while (it.hasNext()) {
					LivingEntity le = it.next();
					le.removePotionEffect(PotionEffectType.SLOW);
					le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255));
					if (le.getLocation().getX() != npc.get(le).l.getX() || le.getLocation().getY() != npc.get(le).l.getY() || le.getLocation().getZ() != npc.get(le).l.getZ())
						le.teleport(npc.get(le).l);
				}
			}
		}, 0, 5);
	}

	public void disable() {
		Iterator<LivingEntity> it = npc.keySet().iterator();
		while (it.hasNext()) {
			LivingEntity le = it.next();
			le.remove();
			it.remove();
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (npc.containsKey(e.getEntity())) {
			e.setCancelled(true);
			e.setDamage(0);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if (npc.containsKey(e.getRightClicked())) {
			e.setCancelled(true);
			int r = (int) (Math.random() * npc.get(e.getRightClicked()).texts.length);
			e.getPlayer().sendMessage(e.getRightClicked().getCustomName() + ChatColor.WHITE + ": " + npc.get(e.getRightClicked()).texts[r]);
		}
	}

	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (npc.containsKey(e.getEntity())) {
			e.setCancelled(true);
			e.setTarget(null);
		}
	}

	private void createNPC(LivingEntity le, String name, String[] texts, Object... data) {
		le.setCollidable(false);
		le.setCustomNameVisible(true);
		le.setCustomName(name);
		if (le instanceof Villager)
			((Villager) le).setProfession((Profession) data[0]);
		npc.put(le, new Data(le.getLocation(), texts));
	}

	private String[] banker = { "Click the enderchest to access your storage module.", "Can I help you with anything?", "Try clicking the enderchest for bank access." };
	private String[] guard = { "Be safe out there friend.", "Beyond these walls is a world of peril." };
	private String[] smither = { "Feel free to use any of me stuff!", "Ya know ya c'n repa'r your things using me anvil? And conv'rt your junk to scraps with me furnaces?" };
}

class Data {
	Location l;
	String[] texts;

	public Data(Location l, String[] texts) {
		this.l = l;
		this.texts = texts;
	}
}
