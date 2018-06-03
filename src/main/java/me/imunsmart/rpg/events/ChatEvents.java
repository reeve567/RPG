package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvents implements Listener {
    private Main pl;

    public ChatEvents(Main pl) {
        this.pl = pl;
    }

    private String[] badWords = { "fuck", "shit", "damn", "dam", "nigger", "nigga", "bitch", "dick", "penis", "vagina", "cunt", "ass" };

    public static String getPrefix(Player p) {
        if (p.getName().equals("Xwy") || p.getName().equals("ImUnsmart"))
            return ChatColor.AQUA.toString() + ChatColor.BOLD + "DEV " + ChatColor.WHITE;
        if (p.getName().equals("TeddyBe"))
            return ChatColor.RED.toString() + ChatColor.BOLD + "TESTER " + ChatColor.WHITE;
        if (p.isOp())
            return ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN " + ChatColor.WHITE;
        if (p.hasPermission("rpg.moderator"))
            return ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "MOD " + ChatColor.WHITE;
        return ChatColor.GRAY.toString();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String name = getPrefix(p) + p.getName();
        if (e.getPlayer().hasPermission("rpg.moderator"))
            e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        e.setFormat(name + ChatColor.WHITE + ": " + e.getMessage());

        if (p.hasPermission("rpg.admin")) {
            String msg = ChatColor.stripColor(e.getMessage());
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("..si")) {
                e.setCancelled(true);
                p.sendMessage(Items.serialize(p.getInventory().getItemInMainHand()));
            } else if (args[0].equalsIgnoreCase("..dsi")) {
                e.setCancelled(true);
                p.getInventory().addItem(Items.deserialize(Items.serialize(p.getInventory().getItemInMainHand())));
            } else if (args[0].equalsIgnoreCase("..durability")) {
                e.setCancelled(true);
                p.getInventory().getItemInMainHand().setDurability(Short.parseShort(args[1]));
            }
        } else {
            for(String s : badWords) {
                if (e.getMessage().contains(s)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "No swearsies, the puppers don't like.");
                }
            }
        }
    }

}
