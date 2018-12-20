package me.imunsmart.rpg;

import me.imunsmart.rpg.command.Admin;
import me.imunsmart.rpg.command.Default;
import me.imunsmart.rpg.command.Moderator;
import me.imunsmart.rpg.command.admins.playermoderation.BanManager;
import me.imunsmart.rpg.events.*;
import me.imunsmart.rpg.mechanics.*;
import me.imunsmart.rpg.mechanics.gui.BuyMenu;
import me.imunsmart.rpg.mechanics.gui.GlobalMarket;
import me.imunsmart.rpg.mechanics.gui.ItemMenu;
import me.imunsmart.rpg.mechanics.gui.SellMenu;
import me.imunsmart.rpg.mechanics.loot.GemSpawners;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.mechanics.quests.QuestGUI;
import me.imunsmart.rpg.mechanics.quests.QuestManager;
import me.imunsmart.rpg.mobs.EntityManager;
import me.imunsmart.rpg.util.*;
import me.imunsmart.rpg.util.json.ResourceLoader;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Main extends JavaPlugin {

	public static Main main;
//	public MarkerAPI markerAPI;
	public static NamespacedKey key;

	@Override
	public void onDisable() {
		QuestManager.disable();
		DiscordBroadcaster.instantBroadcast("Disabled QuestManager");
		Health.disable();
		DiscordBroadcaster.instantBroadcast("Ended Health task");
		GemSpawners.disable();
		DiscordBroadcaster.instantBroadcast("Disabled GemSpawners");
		EntityManager.pl = null;
		Spawners.disable();
		DiscordBroadcaster.instantBroadcast("Disabled MobSpawners");
		LootChests.disable();
		DiscordBroadcaster.instantBroadcast("Disabled LootChests");
		Holograms.disable();
		DiscordBroadcaster.instantBroadcast("Disabled Holograms");
		NPCS.disable();
		DiscordBroadcaster.instantBroadcast("Disabled NPCS");
		SellMenu.disable();
		DiscordBroadcaster.instantBroadcast("Disabled SellMenu");
		for (LivingEntity le : Util.w.getLivingEntities()) {
			if (!(le instanceof Player))
				le.remove();
		}
		DiscordBroadcaster.instantBroadcast("Removed Entities");
		DiscordBroadcaster.instantBroadcast("Closing RPG1...");
	}

	@Override
	public void onEnable() {
		main = this;
		new DiscordBroadcaster().runTaskTimer(this, 1, 5);
		this.saveDefaultConfig();
		this.reloadConfig();

		new ResourceLoader();

		DiscordBroadcaster.messages.add("Loaded config.yml");
		registerGlow();
		DiscordBroadcaster.messages.add("Registered Glow");
//		setDynmapAPI();
		registerEvents();
		DiscordBroadcaster.messages.add("Registered listeners");
		registerCommands();
		DiscordBroadcaster.messages.add("Registered Commands");
		Nametags.init();
		DiscordBroadcaster.messages.add("Initiated Nametags");
		new Holograms(this);
		DiscordBroadcaster.messages.add("Initiated Holograms");
		DiscordBroadcaster.messages.add("Setup online players for Quests and Nametags");
		new AutoBroadcaster(this);
		DiscordBroadcaster.messages.add("Started AutoBroadcaster");
		new Stats(this);
		DiscordBroadcaster.messages.add("Initiated Stats");
		Health.task(this);
		DiscordBroadcaster.messages.add("Started Health task");
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
			key = new NamespacedKey(this,"rpg_Glow");
			Glow glow = new Glow(key);
			Enchantment.registerEnchantment(glow);
		} catch (IllegalArgumentException ignored) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void setDynmapAPI() {
//		try {
//			DynmapPlugin core = (DynmapPlugin) getServer().getPluginManager().getPlugin("dynmap");
//			markerAPI = core.getMarkerAPI();
//			DiscordBroadcaster.messages.add("Setup DynMap Hook");
//		} catch (Exception e) {
//			e.printStackTrace();
//			DiscordBroadcaster.messages.add("Could not get dynmapAPI");
//		}
//
//	}

	private void registerEvents() {
		getLogger().log(Level.INFO, "Registered events.");
		register(new WorldEvents(this), new PlayerEvents(this), new DamageEvents(this), new ChatEvents(this),
				new ServerEvents(this), new SignEvents(this), new Repairing(this), new Potions(this), new AdminTools(),
				new TeleportScrolls(this), new NPCS());
		new QuestManager(this);
		new GemSpawners(this);
		new EntityManager(this);
		new Bank(this);
		new ItemMenu(this);
		new SellMenu(this);
		new BuyMenu(this);
		new QuestGUI(this);
		new Spawners(this);
		new GlobalMarket(this);
		new BanManager(this);
		new LootChests(this);

		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					Nametags.init(p);
					p.setCollidable(false);
					QuestManager.loadProgress(p);
				}
			}
		}.runTaskLater(this, 20);
	}

	private void registerCommands() {
		getLogger().log(Level.INFO, "Registered commands.");
		new Admin(this);
		new Default(this);
		new Moderator(this);
	}

	private void register(Listener... listeners) {
		for (Listener l : listeners) {
			Bukkit.getPluginManager().registerEvents(l, this);
		}
	}
}