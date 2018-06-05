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
	
	public abstract boolean canFinish();
	
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
				setStarted(false);
				return null;
			}
			return startDialog.get(nextDialog++);
		} else {
			if (nextDialog == endDialog.size()) {
				finish();
				nextDialog = 0;
				return null;
			}
			return endDialog.get(nextDialog++);
		}
	}
	
	public void finish() {
		prepareFinish();
		QuestManager.playerData.get(player.getUniqueId()).finishQuest();
		player.sendMessage(MessagesUtil.questFinished(getReadableName()));
		player.getInventory().addItem(rewards);
	}
	
	public String getReadableName() {
		char[] chars = name.toCharArray();
		String s = name.substring(0, 1);
		for (int i = 1; i < chars.length; i++) {
			if (Character.isUpperCase(chars[i])) {
				s += " " + chars[i];
			} else {
				s += chars[i];
			}
		}
		return s;
	}
	
	public abstract void prepareFinish();
	
	public boolean isLastDialog(String s) {
		if (!started) {
			return startDialog.indexOf(s) - 1 == startDialog.size();
		}
		return endDialog.indexOf(s) - 1 == endDialog.size();
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
	
	public void setStarted(boolean progress) {
		this.started = true;
		if (progress) {
			player.sendMessage(MessagesUtil.questStarted(getReadableName()));
		}
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

