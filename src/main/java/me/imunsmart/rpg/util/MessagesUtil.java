package me.imunsmart.rpg.util;

import net.md_5.bungee.api.ChatColor;

public class MessagesUtil {
	
	public static final String classMenuTitle = "§7Class Selector";
	
	//bank ones with no variables
	public static final String notEnoughSpace = prefix('c') + "§cYou don't have space for that. Try emptying space or using a gem note.";
	public static final String bankUpgradeSuccess = prefix('a') + "Successfully upgraded bank by §a9§7 slots.";
	public static final String bankEnterAmountFailure = prefix('c') + "§cFailed to withdraw, enter a valid gem amount or you don't have enough gems.";
	public static final String bankUpgradeFailure = prefix('c') + "§cUpgrade cancelled.";
	public static final String bankEnterAmount = prefix('6') + "Enter the amount you'd like to withdraw or move to cancel.";
	
	public static final String lootChestRemoved = prefix('c') + "§cRemoved loot chest.";
	public static final String lootChestErrorOrNotFound = prefix('c') + "§cEither this block is not a loot chest, or there was an error removing it.";
	//just for copy-paste use
	private static final char colorChar = '§';
	
	public static String bankUpgradeConfirm(int amount) {
		return prefix('6') + "A bank upgrade will cost §a" + amount + "§7 gems. Type '§aconfirm§7' to upgrade.";
	}
	
	private static String prefix(char color) {
		return "§8(§" + color + "§li§8) §7»» ";
	}
	
	public static String bankUpgradeNotEnoughGems(int amount) {
		return prefix('c') + "§cInsufficient gem balance. You need §l" + amount + "§c for that. Try depositing more gems.";
	}
	
	public static String bankWithdrawSuccess(int amount) {
		return prefix('a') + "Successfully withdrew bank note with worth of §a" + amount + " gems§7.";
	}
	
	public static String moveCancel(String what) {
		return prefix('c') + "§cYou moved, cancelling " + what + ".";
	}
	
	public static String lootChestCreated(int tier) {
		return prefix('a') + "Successfully created a tier §a" + tier + "§7 loot chest.";
	}
	
	public static String gemSpawnerCreated(int tier) {
		return prefix('a') + "Successfully created a tier §a" + tier +  "§7 gem spawner.";
	}
}
