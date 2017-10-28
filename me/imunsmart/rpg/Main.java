package me.imunsmart.rpg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.imunsmart.rpg.command.AdminCommands;
import me.imunsmart.rpg.events.DamageEvents;
import me.imunsmart.rpg.events.PlayerEvents;
import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.events.WorldEvents;
import me.imunsmart.rpg.mechanics.Bank;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.mechanics.NPC;
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mobs.MobManager;

public class Main extends JavaPlugin {

	private NPC npc;

	@Override
	public void onEnable() {
		super.onEnable();

		registerEvents();
		registerCommands();
		
		new Stats(this);

		Health.task(this);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		Health.disable();
		MobManager.pl = null;
		npc.disable();
	}

	private void registerEvents() {
		getLogger().log(Level.INFO, "Registered events.");
		Bukkit.getPluginManager().registerEvents(new WorldEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new DamageEvents(this), this);

		new MobManager(this);
		npc = new NPC(this);
		new Bank(this);
		new Spawners(this);
	}

	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new AdminCommands(this);
	}
}
