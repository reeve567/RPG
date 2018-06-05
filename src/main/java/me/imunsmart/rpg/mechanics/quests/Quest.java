package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Quest implements Listener {
    protected Main pl;

    private String name ;
    private String[] description, dialogs;

    public Quest(Main pl, String name, String[] description, String[] dialogs) {
        this.pl = pl;
        this.name = name;
        this.description = description;
        this.dialogs = dialogs;
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


    public abstract void rewardPlayer(Player p);
}
