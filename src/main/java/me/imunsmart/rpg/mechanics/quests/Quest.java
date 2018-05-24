package me.imunsmart.rpg.mechanics.quests;

import me.imunsmart.rpg.util.MessagesUtil;
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
	private boolean finished = false;
	private int nextDialog = 0;
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
	
	protected abstract String getProgress();
	
	public abstract boolean canFinish();
	
	public void finish() {
		prepareFinish();
		QuestManager.playerData.get(player.getUniqueId()).finishQuest();
		player.sendMessage(MessagesUtil.questFinished(name));
		player.getInventory().addItem(rewards);
	}
	
	public ArrayList<String> getEndDialog() {
		return endDialog;
	}
	
	public String getName() {
		return name;
	}
	
	public String getNextDialog() {
		
		if (!started) {
			if (nextDialog == startDialog.size()) {
				nextDialog = 0;
				started = true;
				return null;
			}
			return startDialog.get(nextDialog++);
		} else if (finished) {
			if (nextDialog == endDialog.size()) {
				finish();
				nextDialog = 0;
				return null;
			}
			return endDialog.get(nextDialog++);
		}
		else return null;
	}
	
	public String getNotDone() {
		return notDone;
	}
	
	public String getNpc() {
		return npc;
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
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished() {
		finished = true;
	}
	
	public abstract void prepareFinish();
	
	public abstract String readableProgress();
	
	@Override
	public String toString() {
		if (!getProgress().equals("")) {
			return name + "-" + getProgress();
		} else {
			return name;
		}
	}
}

