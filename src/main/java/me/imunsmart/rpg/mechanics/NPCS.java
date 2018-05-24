package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mechanics.gui.GlobalMarket;
import me.imunsmart.rpg.mechanics.gui.SellMenu;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.KingDuncan;
import me.imunsmart.rpg.util.Util;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class NPCS implements Listener {

    private static ArrayList<NPC> npcs = new ArrayList<>();
    private static ArrayList<Chunk> chunks = new ArrayList<>();

    public NPCS() {
        new Banker(new Location(Util.w, 21.5, 65, -4.5, 90, 0));
        new Marketer(new Location(Util.w, 21.5, 65, -2.5, 90, 0));
        new Merchant(new Location(Util.w, 21.5, 65, -0.5, 90, 0));
        //new Talker(new Location(Util.w, 19.5, 66, 0.5, 90, 0), Villager.Profession.PRIEST, "§bKing Duncan", "Have fun on your adventures!", "Don't die!");
        new KingDuncan(new Location(Util.w, 19.5, 66, 0.5, 90, 0));
    }

    public static void disable() {
        for (NPC npc : npcs) {
            npc.entity.remove();
        }
    }

    public abstract static class NPC {

        public LivingEntity entity;
        public String name;
        private Location location;

        private NPC(EntityType type, Location location, String name) {
            this.entity = (LivingEntity) Util.w.spawnEntity(location, type);
            this.location = location;
            npcs.add(this);
            this.name = name;
            set();
        }

        private NPC(Villager.Profession profession, Location location, String name) {
            this.entity = (LivingEntity) Util.w.spawnEntity(location, EntityType.VILLAGER);
            ((Villager) entity).setProfession(profession);
            this.location = location;
            npcs.add(this);
            this.name = name;
            set();
        }

        private void set() {
            entity.addScoreboardTag("npc");
            entity.addScoreboardTag(setOther());

            Chunk c = entity.getLocation().getChunk();
            if (!chunks.contains(c)) {
                chunks.add(c);
            }

            entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, true));
            entity.setInvulnerable(true);
            entity.setSilent(true);
            entity.setCollidable(false);
            if (name != null && !name.equals("")) {
                entity.setCustomName(name);
                entity.setCustomNameVisible(true);
            }
        }

        public void onClick(Player player) {

        }

        protected abstract String setOther();

        public Location getLocation() {
            return location;
        }
    }

    public static class Banker extends NPC {

        Banker(Location location) {
            super(Villager.Profession.LIBRARIAN, location, "§a§lBanker");
        }

        public void onClick(Player player) {
            Bank.open(player);
        }

        @Override
        protected String setOther() {
            return "banker";
        }
    }

    public static class Merchant extends NPC {

        Merchant(Location location) {
            super(Villager.Profession.LIBRARIAN, location, "§a§lMerchant");
        }

        public void onClick(Player player) {
            SellMenu.open(player);
        }

        @Override
        protected String setOther() {
            return "merchant";
        }
    }

    public static class Marketer extends NPC {
        Marketer(Location location) {
            super(EntityType.VILLAGER, location, "§2§lMarketer");
        }

        public void onClick(Player player) {
            GlobalMarket.open(player);
        }

        @Override
        protected String setOther() {
            return "marketer";
        }
    }

    public abstract static class QuestGiver extends Talker {


        public QuestGiver(Location location, EntityType type, String name, String... strings) {
            super(location, type, name, strings);
        }

        public QuestGiver(Location location, Villager.Profession profession, String name, String... strings) {
            super(location, profession, name, strings);
        }

    }

    public static class Talker extends NPC {

        private String[] strings;
        private int index = -1;

        public Talker(Location location, EntityType type, String name, String... strings) {
            super(type, location, name);
            this.strings = strings;
        }

        Talker(Location location, Villager.Profession profession, String name, String... strings) {
            super(profession, location, name);
            this.strings = strings;
        }

        @Override
        public void onClick(Player player) {
            index++;
            if (index >= strings.length)
                index = 0;
            player.sendMessage(name + "§f:§7 " + strings[index]);
        }

        @Override
        protected String setOther() {
            return "talker";
        }
    }

    @EventHandler
    public void onHit(EntityDamageEvent e) {
        if (e.getEntity().getScoreboardTags().contains("npc")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity.getScoreboardTags().contains("npc")) {
            e.setCancelled(true);
            for (NPC npc : npcs) {
                if (npc.entity == entity) {
                    npc.onClick(e.getPlayer());
                }
            }
        } else {
            System.out.println("unhandled npc");
        }
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent e) {
        if (chunks.contains(e.getChunk())) e.setCancelled(true);
    }
}

