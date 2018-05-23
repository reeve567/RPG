package me.imunsmart.rpg.mechanics.quests;

import org.bukkit.entity.Player;
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
	private Player player;
	
	Quest(Player player, String name, ItemStack[] rewards, int start, int end) {
		this.name = name;
		this.rewards = rewards;
		this.player = player;
		resource = ResourceBundle.getBundle(name);
		startDialog = getList("start", start);
		endDialog = getList("end", end);
		notDone = resource.getString("notDone");
	}
	
	public ItemStack[] finish() {
		return rewards;
	}
	
	public ArrayList<String> getEndDialog() {
		return endDialog;
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
}

