package me.imunsmart.rpg.util;

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
    public static final String flyToggle = prefix('b') + "You have toggled flight.";
    public static final String inCombat = prefix('c') + "You cannot do that while in combat.";

    public static final String failedEnchant = prefix('c') + "§cDue to a failure during enchantment, the item has vanished.";

    // just for copy-paste use
    // colorChar = '§';

    private static String prefix(char color) {
        return "§8(§" + color + "§li§8) §7»» ";
    }

    public static String bankUpgradeConfirm(int amount) {
        return prefix('6') + "A bank upgrade will cost §a" + amount + "§7 gems. Type '§aconfirm§7' to upgrade.";
    }

    public static String bankUpgradeNotEnoughGems(int amount) {
        return prefix('c') + "§cInsufficient gem balance. You need §l" + amount + "§c for that. Try depositing more gems.";
    }

    public static String notEnoughGems(int amount) {
        return prefix('c') + "§cInsufficient gem balance. You need §l" + amount + "§c for that.";
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
    
    public static String npcMessage(String name, String message) {
        return name + "§f: §7" + message;
    }

    public static String questStarted(String name) {
        return prefix('a') + "Started quest §a" + name + "§7.";
    }

    public static String questFinished(String name) {
        return prefix('a') + "Finished quest §a" + name + "§7.";
    }

    public static String gemSpawnerCreated(int tier) {
        return prefix('a') + "Successfully created a tier §a" + tier + "§7 gem spawner.";
    }

    public static String pickaxeTier(int tier) {
        return prefix('b') + "Your pickaxe has advanced to tier §b" + tier + "§7.";
    }

    public static String mobDefeated(String name, String deathMessage) {
        return prefix('4') + "§4" + deathMessage + " " + name + " §4has finally been slain.";
    }

    public static String damage(int hp, int damage) {
        return "§c-" + damage + "HP §7(§a" + hp + "HP§7)";
    }
}
