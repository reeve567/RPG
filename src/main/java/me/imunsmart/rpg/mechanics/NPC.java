package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NPC implements Listener {
	public List<NPCEntity> npc = new ArrayList<>();
	
	private World w = Bukkit.getWorld("world");
	
	private Main pl;
	private String[] banker = {"Click the enderchest to access your storage module.", "Can I help you with anything?", "Try clicking the enderchest for bank access."};
	private String[] market = {"This is the global exchange where you can buy and sell goods."};
	private String[] guard = {"Be safe out there friend.", "Beyond these walls is a world of peril."};
	private String[] smither = {"Feel free to use any of me stuff!", "Ya know ya c'n repa'r your things using me anvil? And conv'rt your junk to scraps with me furnaces?"};
	private String[] kingDuncan = {"Be safe in your travels."};
	
	public NPC(Main pl) {
		this.pl = pl;
		init();
	}
	
	private void init() {
		//createNPC(w.spawn(new Location(w, 6, 63, -11), Villager.class), ChatColor.GREEN + "Merchant", market, Profession.LIBRARIAN).addTag("market");
		createNPC(w.spawn(new Location(w, 18.5, 66, 0.5), Villager.class), "§aKing Duncan", kingDuncan, Profession.NITWIT).addTag("king");
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
			for (NPCEntity npe : npc) {
				LivingEntity le = npe.getNpc();
				le.removePotionEffect(PotionEffectType.SLOW);
				le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255,true));
				if (le.getLocation().getX() != npe.getData().l.getX() || le.getLocation().getY() != npe.getData().l.getY() || le.getLocation().getZ() != npe.getData().l.getZ())
					le.teleport(npe.getData().l);
			}
		}, 0, 5);
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
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getEntity();
			if (le.getScoreboardTags().contains("npc")) {
				e.setCancelled(true);
				e.setDamage(0);
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getRightClicked();
			if (le.getScoreboardTags().contains("npc")) {
				NPCEntity npe = null;
				for (NPCEntity n : npc) {
					if (n.getNpc() == le) {
						npe = n;
						break;
					}
				}
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
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) e.getEntity();
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
