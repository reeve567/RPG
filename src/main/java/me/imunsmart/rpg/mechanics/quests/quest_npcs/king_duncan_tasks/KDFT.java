package me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.FirstTask;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KDFT extends Quest implements FirstTask {
	
	public KDFT(Player player, String progress) {
		this(player);
		KillTracker.killMap.put(player.getUniqueId(), Integer.valueOf(progress));
	}
	
	public KDFT(Player player) {
		super(player, "KDFT", new ItemStack[]{Items.createGems(30)}, 7, 2);
		KillTracker.killMap.put(player.getUniqueId(), 0);
	}
	
	public String readableProgress() {
		return "§e" + KillTracker.killMap.get(player.getUniqueId()) + "§7/§e10 §amonsters killed.";
	}
	
	@Override
	public boolean canFinish() {
		return KillTracker.killMap.get(player.getUniqueId()) >= 10;
	}
	
	@Override
	public void prepareFinish() {
		KillTracker.killMap.remove(player.getUniqueId());
	}
	
	@Override
	protected String getProgress() {
		return String.valueOf(KillTracker.killMap.get(player.getUniqueId()));
	}
}
