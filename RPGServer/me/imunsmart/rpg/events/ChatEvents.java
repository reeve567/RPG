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
		String name = p.getName();
		if (e.getPlayer().isOp()) {
			name = ChatColor.RED + name;
		}
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

}