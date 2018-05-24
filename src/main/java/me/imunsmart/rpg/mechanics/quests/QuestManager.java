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
import org.bukkit.event.inventory.InventoryMoveItemEvent;
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
	public void onOpen(InventoryMoveItemEvent e) {
	
	}
	
	public static void updateBook(Player player) {
		System.out.println("test4");
		CustomItem book = Items.createQuestInfo();
		QuestPlayerData data = QuestManager.playerData.get(player.getUniqueId());
		if (data.isInQuest()) {
			book.setLore("§aQuest: " + data.getActiveQuest().getName(),"§aProgress: " + data.getActiveQuest().readableProgress(),data.getActiveQuest().canFinish() ? "§aReturn to " + data.getActiveQuest().getNpc() : null);
		} else {
			book.setLore("§cNo quest active!");
		}
		player.getInventory().setItem(17, book);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlot() == 17)
			e.setCancelled(true);
		if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().getDisplayName().equals(Items.createQuestInfo().getItemMeta().getDisplayName())) e.setCancelled(true);
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
