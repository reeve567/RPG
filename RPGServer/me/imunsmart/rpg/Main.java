package me.imunsmart.rpg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.imunsmart.rpg.command.Admin;
import me.imunsmart.rpg.events.ChatEvents;
import me.imunsmart.rpg.events.DamageEvents;
import me.imunsmart.rpg.events.PlayerEvents;
import me.imunsmart.rpg.events.ServerEvents;
import me.imunsmart.rpg.events.SignEvents;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.events.WorldEvents;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.NPC;
import me.imunsmart.rpg.mechanics.RepairMenu;
import me.imunsmart.rpg.mechanics.Repairing;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.util.AutoBroadcaster;

public class Main extends JavaPlugin {

	private NPC npc;
	public LootChests lc;

	@Override
	public void onEnable() {
		super.onEnable();

		registerEvents();
		registerCommands();

		new AutoBroadcaster(this);

		new Stats(this);

		Health.task(this);
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

	private void registerEvents() {
		getLogger().log(Level.INFO, "Registered events.");
		Bukkit.getPluginManager().registerEvents(new WorldEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new DamageEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new ChatEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new ServerEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new SignEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new Repairing(this), this);

		new EntityManager(this);
		// npc = new NPC(this);
		new Bank(this);
		new RepairMenu(this);
		new Spawners(this);
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
			Glow glow = new Glow(70);
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException ignored) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
