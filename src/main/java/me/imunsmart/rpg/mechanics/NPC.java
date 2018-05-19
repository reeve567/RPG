package me.imunsmart.rpg.mechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
	public List<NPCEntity> npc = new ArrayList<NPCEntity>();

	private World w = Bukkit.getWorld("world");

	private Main pl;
	private String[] banker = { "Click the enderchest to access your storage module.", "Can I help you with anything?", "Try clicking the enderchest for bank access." };
	private String[] market = { "This is the global exchange where you can buy and sell goods." };
	private String[] guard = { "Be safe out there friend.", "Beyond these walls is a world of peril." };
	private String[] smither = { "Feel free to use any of me stuff!", "Ya know ya c'n repa'r your things using me anvil? And conv'rt your junk to scraps with me furnaces?" };

	public NPC(Main pl) {
		this.pl = pl;
		Bukkit.getPluginManager().registerEvents(this, pl);
		init();
	}

	public NPCEntity createNPC(LivingEntity le, String name, String[] texts, Object... data) {
		le.setCollidable(false);
		le.setCustomNameVisible(true);
		le.setCustomName(name);
		le.addScoreboardTag("npc");
		if (le instanceof Villager)
			((Villager) le).setProfession((Profession) data[0]);
		NPCEntity npe = new NPCEntity(le, new Data(le.getLocation(), texts));
		npc.add(npe);
		return npe;
	}

	private void init() {
		createNPC(w.spawn(new Location(w, 6, 63, -11), Villager.class), ChatColor.GREEN + "Merchant", market, Profession.LIBRARIAN).addTag("market");

		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
			for (NPCEntity npe : npc) {
				LivingEntity le = npe.getNpc();
				le.removePotionEffect(PotionEffectType.SLOW);
				le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255));
				if (le.getLocation().getX() != npe.getData().l.getX() || le.getLocation().getY() != npe.getData().l.getY() || le.getLocation().getZ() != npe.getData().l.getZ())
					le.teleport(npe.getData().l);
			}
		}, 0, 5);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e;
			if (le.getScoreboardTags().contains("npc")) {
				e.setCancelled(true);
				e.setDamage(0);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if (e instanceof LivingEntity) {
			System.out.println(1);
			LivingEntity le = (LivingEntity) e;
			if (le.getScoreboardTags().contains("npc")) {
				System.out.println(2);
				NPCEntity npe = null;
				for (NPCEntity n : npc) {
					if (n.getNpc() == le) {
						npe = n;
						break;
					}
				}
				System.out.println(npe);
				if (npe == null)
					return;
				e.setCancelled(true);
				int r = (int) (Math.random() * npe.getData().texts.length);
				e.getPlayer().sendMessage(e.getRightClicked().getCustomName() + ChatColor.WHITE + ": " + npe.getData().texts[r]);
				if (e.getRightClicked().getScoreboardTags().contains("market")) {
					GlobalMarket.open(e.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if (e instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e;
			if (le.getScoreboardTags().contains("npc")) {
				e.setCancelled(true);
				e.setTarget(null);
			}
		}
	}
}

class NPCEntity {
	private LivingEntity npc;
	private Data data;

	public NPCEntity(LivingEntity npc, Data data) {
		this.npc = npc;
		this.data = data;
	}

	public LivingEntity getNpc() {
		return npc;
	}

	public Data getData() {
		return data;
	}

	public NPCEntity addTag(String tag) {
		npc.addScoreboardTag(tag);
		return this;
	}
}

class Data {
	Location l;
	String[] texts;

	public Data(Location l, String[] texts) {
		this.l = l;
		this.texts = texts;
	}
}
