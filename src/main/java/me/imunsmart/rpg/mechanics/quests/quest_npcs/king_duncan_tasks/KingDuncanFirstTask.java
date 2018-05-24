package me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.FirstTask;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KingDuncanFirstTask extends Quest implements FirstTask {
	
	public KingDuncanFirstTask(Player player, String progress) {
		this(player);
		KillTracker.killMap.put(player.getUniqueId(), Integer.valueOf(progress));
	}
	
	public KingDuncanFirstTask(Player player) {
		super(player, "KingDuncanFirstTask", new ItemStack[]{Items.createGems(30)},
				new String[]{
						"Ah! You must be the one I've been waiting for.",
						"I'm sure you have plenty of questions to ask, but for now they will have to wait.",
						"I'm sure after your travels the last thing you want to do is run errands, but I need you to clear some monsters out of the mine.",
						"Go ahead and clear 10 zombies from the mines.",
						"It's just to the left out the door, you can't miss it.",
						"And while you're there you can grab some gems.",
						"Good Luck! Don't screw this up!"
				},
				new String[]{
						"Great! Just what we needed!",
						"Here's some gems to get yourself situated.",
						"Come back when you need another job!"
				},
				"Not quite done yet! You need to kill more zombies.",
				"King Duncan");
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
