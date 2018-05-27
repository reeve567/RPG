package me.imunsmart.rpg;

import me.imunsmart.rpg.command.Admin;
import me.imunsmart.rpg.command.Default;
import me.imunsmart.rpg.command.Moderator;
import me.imunsmart.rpg.command.admincommands.playermoderation.BanManager;
import me.imunsmart.rpg.events.*;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.mechanics.gui.BuyMenu;
import me.imunsmart.rpg.mechanics.gui.GlobalMarket;
import me.imunsmart.rpg.mechanics.gui.RepairMenu;
import me.imunsmart.rpg.mechanics.gui.SellMenu;
import me.imunsmart.rpg.mechanics.loot.GemSpawners;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
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
	
	public LootChests lc;
	
	private void register(Listener... listeners) {
		for (Listener l : listeners) {
			Bukkit.getPluginManager().registerEvents(l, this);
		}
	}
	
	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new Admin(this);
		new Default(this);
		new Moderator(this);
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
				new AdminTools(),
				new TeleportScrolls(this),
				new NPCS()
		);
		new QuestManager(this);
		new GemSpawners(this);
		new EntityManager(this);
		new Bank(this);
		new RepairMenu(this);
		new SellMenu(this);
		new BuyMenu(this);
		new Spawners(this);
		new GlobalMarket(this);
		new BanManager(this);
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
			Glow glow = new Glow(999);
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
		GemSpawners.disable();
		EntityManager.pl = null;
		Spawners.disable();
		lc.disable();
		Holograms.disable();
		NPCS.disable();
		QuestManager.disable();
		SellMenu.disable();
	}
	
	@Override
	public void onEnable() {
		registerEvents();
		registerCommands();
		registerGlow();
		Nametags.setupDevTeam();
		Nametags.setupTesterTeam();
		new Holograms(this);
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			Nametags.init(p);
			QuestManager.init(p);
			p.setCollidable(false);
		}
		
		new AutoBroadcaster(this);
		
		new Stats(this);
		
		Health.task(this);
	}
	
}