package me.imunsmart.rpg.util;

import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class DiscordBroadcaster extends BukkitRunnable {
	private static final String prefix = "[INFO] " + " [RPG1] ";
	private static final String icon = "https://camo.githubusercontent.com/a4e336d9971686ae5698d2a39011e8e499a82475/68747470733a2f2f6a6172692e6c6f6c2f5a6e4e305451654d6d692e706e67";
	private static final String title = "System";
	private static final String webhook = "https://discordapp.com/api/webhooks/451152302401650718/pj5Twv8JUIAjgLuyIFHfp7luNeQwWY3a9Q8k0rTpiJ3H-SJF2ocvvBZ2ZSlclYovFnP5";
	public static ArrayList<String> messages = new ArrayList<>();
	private static TemmieWebhook temmie = new TemmieWebhook(webhook);
	
	public DiscordBroadcaster() {
		messages.add("Started discordBroadcaster");
	}
	
	public static void instantBroadcast(String message) {
		DiscordMessage message1 = new DiscordMessage(title, prefix + message, icon);
		temmie.sendMessage(message1);
		message1 = null;
	}
	
	@Override
	public void run() {
		if (messages.size() > 0)
			instantBroadcast(messages.remove(0));
	}
}
