package me.imunsmart.rpg.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;

public class ChatEvents implements Listener {
	private Main pl;

	public ChatEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String name = getPrefix(p) + p.getName();
		if (e.getPlayer().hasPermission("rpg.moderator"))
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
		e.setFormat(name + ChatColor.WHITE + ": " + e.getMessage());

		if (ChatColor.stripColor(e.getMessage()).equalsIgnoreCase("..si")) {
			e.setCancelled(true);
			p.sendMessage(Items.serialize(p.getInventory().getItemInMainHand()));
		}
		if (ChatColor.stripColor(e.getMessage()).equalsIgnoreCase("..dsi")) {
			e.setCancelled(true);
			p.getInventory().addItem(Items.deserialize(Items.serialize(p.getInventory().getItemInMainHand())));
		}
	}
	
	public static String getPrefix(Player p) {
		if(p.getName().equals("Xwy") || p.getName().equals("ImUnsmart"))
			return ChatColor.AQUA.toString() + ChatColor.BOLD + "DEV " + ChatColor.WHITE;
		if(p.isOp())
			return ChatColor.RED.toString() + ChatColor.BOLD + "ADMIN " + ChatColor.WHITE;
		if(p.hasPermission("rpg.moderator"))
			return ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "MOD " + ChatColor.WHITE;
		return ChatColor.GRAY.toString();
	}

}