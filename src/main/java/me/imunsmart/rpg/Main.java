package me.imunsmart.rpg;

import me.imunsmart.rpg.command.Admin;
import me.imunsmart.rpg.events.*;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.util.AutoBroadcaster;
import me.imunsmart.rpg.util.Glow;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Main extends JavaPlugin {
	
	public LootChests lc;
	private NPC npc;
	
	private void register(Listener... listeners) {
		for (Listener l : listeners) {
			Bukkit.getPluginManager().registerEvents(l, this);
		}
	}
	
	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new Admin(this);
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
				new Potions(this));
		
		new EntityManager(this);
		// npc = new NPC(this);
		new Bank(this);
		new RepairMenu(this);
		new Spawners(this);
		lc = new LootChests(this);
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
			Glow glow = new Glow(70);
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException ignored) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onDisable() {
		Health.disable();
		EntityManager.disable();
		EntityManager.pl = null;
		Spawners.disable();
		
		lc.disable();
		
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		registerEvents();
		registerCommands();
		registerGlow();
		
		new AutoBroadcaster(this);
		
		new Stats(this);
		
		Health.task(this);
	}
	
}
