package me.imunsmart.rpg.mechanics.quests.quest_npcs.farmer_bill_tasks;

import me.imunsmart.rpg.mechanics.Items;
import me.imunsmart.rpg.mechanics.quests.FirstTask;
import me.imunsmart.rpg.mechanics.quests.Quest;
import me.imunsmart.rpg.util.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FarmerBillFirstTask extends Quest implements FirstTask {
	
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
	
	public static final ItemStack pumpkingItem = new CustomItem(Material.PUMPKIN).setLore("§7Obtained from killing §6Pumpking§7.","§7Return to §aFarmer Bill §7for a reward.").setName("§6§lPumpking§7's Pumpkin");
	
	public FarmerBillFirstTask(Player player, String progress) {
		super(player,"FarmerBillFirstTask",REWARDS,STARTDIAL,ENDDIAL,NOTDONE,NPCNAME);
		this.progress = progress;
	}
	
	public FarmerBillFirstTask(Player player) {
		super(player,"FarmerBillFirstTask",REWARDS,STARTDIAL,ENDDIAL,NOTDONE,NPCNAME);
		this.progress = null;
	}
	
	@Override
	public boolean canFinish() {
		return player.getInventory().contains(Material.PUMPKIN);
	}
	
	@Override
	public void prepareFinish() {
	
	}
	
	@Override
	public String readableProgress() {
		return "none really";
	}
	
	@Override
	protected String getProgress() {
		return "none atm";
	}
}
