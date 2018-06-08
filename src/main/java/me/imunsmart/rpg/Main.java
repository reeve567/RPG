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
import me.imunsmart.rpg.util.AutoBroadcaster;
import me.imunsmart.rpg.util.DiscordBroadcaster;
import me.imunsmart.rpg.util.Glow;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    public LootChests lc;
    public static Main main;

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

    @Override
    public void onDisable() {
        QuestManager.disable();
        DiscordBroadcaster.messages.add("Disabled QuestManager");
        Health.disable();
        DiscordBroadcaster.messages.add("Ended Health task");
        GemSpawners.disable();
        DiscordBroadcaster.messages.add("Disabled GemSpawners");
        EntityManager.pl = null;
        Spawners.disable();
        DiscordBroadcaster.messages.add("Disabled MobSpawners");
        LootChests.disable();
        DiscordBroadcaster.messages.add("Disabled LootChests");
        Holograms.disable();
        DiscordBroadcaster.messages.add("Disabled Holograms");
        NPCS.disable();
        DiscordBroadcaster.messages.add("Disabled NPCS");
        SellMenu.disable();
        DiscordBroadcaster.messages.add("Disabled SellMenu");
        for (LivingEntity le : Util.w.getLivingEntities()) {
            if (!(le instanceof Player))
                le.remove();
        }
        DiscordBroadcaster.messages.add("Removed Entities");
        DiscordBroadcaster.messages.add("Closing RPG1...");
    }

    @Override
    public void onEnable() {
        main = this;
        new DiscordBroadcaster().runTaskTimer(this, 1, 5);
        this.saveDefaultConfig();
        this.reloadConfig();
        DiscordBroadcaster.messages.add("Loaded config.yml");
        registerGlow();
        DiscordBroadcaster.messages.add("Registered Glow");
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
            Glow glow = new Glow(999);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                new NPCS(),
                new ItemNames()
        );
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
}