package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Quest {
	protected Player player;
	private String name;
	private ItemStack[] rewards;
	private ArrayList<String> startDialog;
	private String notDone;
	private ArrayList<String> endDialog;
	private boolean started = false;
	private int nextDialog = 1;
	private String npc;
	
	protected Quest(Player player, String name, ItemStack[] rewards, String[] startStrings, String[] endStrings, String notDone, String npc) {
		this.name = name;
		this.rewards = rewards;
		this.notDone = notDone;
		this.npc = npc;
		this.player = player;
		startDialog = new ArrayList<>(Arrays.asList(startStrings));
		endDialog = new ArrayList<>(Arrays.asList(endStrings));
	}
	
	public ItemStack[] finish() {
		prepareFinish();
		QuestManager.playerData.get(player.getUniqueId()).finishQuest();
		return rewards;
	}
	
	public abstract void prepareFinish();
	
	public ArrayList<String> getEndDialog() {
		return endDialog;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNextDialog() {
		return nextDialog;
	}
	
	public void setNextDialog(int nextDialog) {
		this.nextDialog = nextDialog;
	}
	
	public String getNotDone() {
		return notDone;
	}
	
	public ArrayList<String> getStartDialog() {
		return startDialog;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public void nextDialog() {
		nextDialog++;
	}
	
	public String getNpc() {
		return npc;
	}
	
	public abstract boolean canFinish();
	
	public abstract String readableProgress();
	
	@Override
	public String toString() {
		if (!getProgress().equals("")) {
			return name + "-" + getProgress();
		} else {
			return name;
		}
	}
	
	protected abstract String getProgress();
}

