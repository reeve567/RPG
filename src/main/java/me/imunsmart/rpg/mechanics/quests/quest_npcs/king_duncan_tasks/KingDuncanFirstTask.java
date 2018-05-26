package me.imunsmart.rpg.mechanics.quests.quest_npcs.king_duncan_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.FirstTask;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.mechanics.quests.trackers.KillTracker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KingDuncanFirstTask extends Quest implements FirstTask {
	
	public static final String[] STARTDIAL = new String[]{
			"Ah! You must be the one I've been waiting for.",
			"I'm sure you have plenty of questions to ask, but for now they will have to wait.",
			"I'm sure after your travels the last thing you want to do is run errands, but I need you to clear some monsters out of the mine.",
			"Go ahead and clear 10 zombies from the mines.",
			"It's just to the left out the door, you can't miss it.",
			"And while you're there you can grab some gems.",
			"Good Luck! Don't screw this up!"
	};
	public static final String[] ENDDIAL = new String[]{
			"Great! Just what we needed!",
			"Here's some gems to get yourself situated.",
			"Come back when you need another job!"
	};
	public static final String NOTDONE = "Not quite done yet! You need to kill more zombies.";
	public static final String NPCNAME = "King Duncan";
	public static final ItemStack[] REWARDS = new ItemStack[]{Items.createGems(30)};
	public final String progress;
	
	public KingDuncanFirstTask(Player player, String progress) {
		super(player, "KingDuncanFirstTask", REWARDS, STARTDIAL, ENDDIAL, NOTDONE, NPCNAME);
		this.progress = progress;
	}
	
	public KingDuncanFirstTask(Player player) {
		super(player, "KingDuncanFirstTask", REWARDS, STARTDIAL, ENDDIAL, NOTDONE, NPCNAME);
		this.progress = null;
	}
	
	public String readableProgress() {
		return "§e" + KillTracker.killMap.get(player.getUniqueId()) + "§7/§e10 §amonsters killed.";
	}
	
	@Override
	public void setStarted(boolean progress) {
		super.setStarted(progress);
		if (progress && this.progress != null) KillTracker.killMap.put(player.getUniqueId(), Integer.valueOf(this.progress));
		else KillTracker.killMap.put(player.getUniqueId(), 0);
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
