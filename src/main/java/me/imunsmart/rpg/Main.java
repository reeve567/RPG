package me.imunsmart.rpg;

import me.imunsmart.rpg.command.Admin;
import me.imunsmart.rpg.events.*;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.mechanics.classes.ClassManager;
import me.imunsmart.rpg.mechanics.classes.ClassSelector;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.util.AutoBroadcaster;
import me.imunsmart.rpg.util.Glow;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Main extends JavaPlugin {
	
	private static NPC npc;
	public LootChests lc;
	
	public static NPC getNpc() {
		return npc;
	}
	
	@Override
	public void onDisable() {
		Health.disable();
		EntityManager.disable();
		GemSpawners.disable();
		EntityManager.pl = null;
		Spawners.disable();
		lc.disable();
		
	}
	
	@Override
	public void onEnable() {
		registerEvents();
		registerCommands();
		registerGlow();
		Nametags.setupDevTeam();
		Nametags.setupTesterTeam();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			Nametags.init(p);
		}
		
		new AutoBroadcaster(this);
		
		new Stats(this);
		
		Health.task(this);
	}
	
	private void registerEvents() {
		getLogger().log(Level.INFO, "Registered events.");
		register(new WorldEvents(this),
				new PlayerEvents(this),
				new DamageEvents(this),
				new ChatEvents(this),
				new ServerEvents(this),
				new SignEvents(this),
				new Repairing(this),
				new Potions(this),
				npc = new NPC(this),
				new AdminTools()
		);
		new GemSpawners(this);
		new EntityManager(this);
		new Bank(this);
		new RepairMenu(this);
		new Spawners(this);
		new GlobalMarket(this);
		lc = new LootChests(this);
	}
	
	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new Admin(this);
	}
	
	private void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow(999);
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException ignored) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void register(Listener... listeners) {
		for (Listener l : listeners) {
			Bukkit.getPluginManager().registerEvents(l, this);
		}
	}
	
}
