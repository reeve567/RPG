package me.imunsmart.rpg.command.admins;

import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.entity.Player;

public class Fly {

	public static void run(Player player) {
		player.sendMessage(MessagesUtil.flyToggle);
		player.setAllowFlight(!player.getAllowFlight());
	}

}
