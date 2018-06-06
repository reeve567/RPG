package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Stats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Quest implements Listener {
    public static final int MISC = 0, KILL = 1;

    protected Main pl;

    private String name ;
    private String[] description, dialogs;
    private String rewards;
    private int type;
    private int flags;

    public Quest(Main pl, String name, String[] description, String[] dialogs, String rewards, int type) {
        this.pl = pl;
        this.name = name;
        this.description = description;
        this.rewards = rewards;
        this.dialogs = dialogs;
        this.type = type;
        flags = 0;
        Bukkit.getPluginManager().registerEvents(this, pl);
    }

    public String getName() {
        return name;
    }

    public String[] getDescription() {
        return description;
    }

    public String[] getDialogs() {
        return dialogs;
    }

    public int getType() {
        return type;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getFlags() {
        return flags;
    }

    public void rewardPlayer(Player p) {
        Stats.completeQuest(p, getName());
        QuestManager.playerProgress.remove(p.getName());
        p.sendMessage(rewards.replaceAll(",","\n"));
    }

    public String getRewards() {
        return rewards;
    }
}
