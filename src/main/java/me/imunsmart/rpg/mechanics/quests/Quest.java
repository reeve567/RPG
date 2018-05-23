package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.ResourceBundle;

public abstract class Quest {
	protected ResourceBundle resource;
	private String name;
	private ItemStack[] rewards;
	private ArrayList<String> startDialog;
	private String notDone;
	private ArrayList<String> endDialog;
	private boolean started = false;
	private int nextDialog = 1;
	
	Quest(String name, ItemStack[] rewards, int start, int end) {
		this.name = name;
		this.rewards = rewards;
		resource = ResourceBundle.getBundle(name);
		startDialog = getList("start", start);
		endDialog = getList("end", end);
		notDone = resource.getString("notDone");
	}
	
	public ArrayList<String> getList(String st, int end) {
		ArrayList<String> temp = new ArrayList<>();
		for (int i = 1; i <= end; i++) {
			temp.add(resource.getString(st + i));
		}
		return temp;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack[] finish() {
		return rewards;
	}
	
	public ArrayList<String> getStartDialog() {
		return startDialog;
	}
	
	public ArrayList<String> getEndDialog() {
		return endDialog;
	}
	
	public String getNotDone() {
		return notDone;
	}
	
	public boolean isStarted() {
		return started;
	}
	
	public void setStarted(boolean started) {
		this.started = started;
	}
	
	public int getNextDialog() {
		return nextDialog;
	}
	
	public void setNextDialog(int nextDialog) {
		this.nextDialog = nextDialog;
	}
	
	public void nextDialog() {
		nextDialog++;
	}
}

