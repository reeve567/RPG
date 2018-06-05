package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import org.bukkit.Location;
import org.bukkit.entity.Villager;

public class FarmerBill extends NPCS.QuestGiver {
	
	private static final String[] strings = {
			"Eat your fruits!",
			"I'd love to go on your adventures.",
			"What all is out there?"
	};
	
	public FarmerBill(Location location) {
		super(location, Villager.Profession.FARMER, "§aFarmer Bill", strings);
	}
	
	@Override
	protected String setOther() {
		return "farmer_bill";
	}
	
}
