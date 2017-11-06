package me.imunsmart.rpg;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.imunsmart.rpg.command.AdminCommands;
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
import me.imunsmart.rpg.mechanics.Stats;
import me.imunsmart.rpg.mobs.MobManager;
import me.imunsmart.rpg.util.AutoBroadcaster;

public class Main extends JavaPlugin {

	private NPC npc;

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
		MobManager.disable();
		MobManager.pl = null;
		Spawners.disable();
		
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

		new MobManager(this);
//		npc = new NPC(this);
		new Bank(this);
		new RepairMenu(this);
		new Spawners(this);
	}

	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new AdminCommands(this);
	}
}
