package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks.KDFT;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import me.imunsmart.rpg.util.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.HashMap;
import java.util.UUID;

public class QuestManager implements Listener {
	
	public static HashMap<UUID, QuestPlayerData> playerData = new HashMap<>();
	
	public QuestManager(Main main) {
		Bukkit.getPluginManager().registerEvents(new KillTracker(), main);
		Bukkit.getPluginManager().registerEvents(this, main);
	}
	
	public static void init(Player player) {
		if (!playerData.containsKey(player.getUniqueId())) {
			playerData.put(player.getUniqueId(), new QuestPlayerData(player.getUniqueId()));
		}
	}
	
	public static void disable() {
		playerData = null;
	}
	
	public static Quest getQuest(Player player, String s) {
		
		if (s.endsWith("FT")) {
			return new FirstTaskFinder(player, s).getTask();
		}
		
		return null;
	}
	
	public static Quest getQuest(Player player, String s, String progress) {
		
		if (s.endsWith("FT")) {
			return new FirstTaskFinder(player, s, progress).getTask();
		}
		
		return null;
	}
	
	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		CustomItem book = Items.createQuestInfo();
		Player p = (Player) e.getPlayer();
		QuestPlayerData data = QuestManager.playerData.get(p.getUniqueId());
		if (data.isInQuest()) {
			book.setLore("§a" + data.getActiveQuest().getName());
			book.setLore("§aProgress: " + data.getActiveQuest().readableProgress());
			book.setLore("§aReturn to " + data.getActiveQuest().getNpc());
		} else {
			book.setLore("§cNo quest active!");
		}
		p.getInventory().setItem(17, book);
	}
	
	@EventHandler
	public void onAnything(InventoryClickEvent e) {
		if (e.getSlot() == 17) e.setCancelled(true);
	}
	
	private static class FirstTaskFinder {
		
		Player player;
		String s;
		String progress = null;
		
		public FirstTaskFinder(Player player, String s, String progress) {
			this(player, s);
			this.progress = progress;
		}
		
		public FirstTaskFinder(Player player, String s) {
			this.s = s;
			this.player = player;
		}
		
		private Quest getTask() {
			
			boolean useProgress = progress != null;
			switch (s) {
				
				case "KDFT":
					if (!useProgress)
						return new KDFT(player);
					else return new KDFT(player, progress);
				case "":
					break;
			}
			
			return null;
		}
		
	}
	
}
