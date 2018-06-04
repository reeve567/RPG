package me.imunsmart.rpg.mechanics.quests.quest_npcs;

import me.imunsmart.rpg.mechanics.NPCS;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import java.util.ArrayList;

public class FarmerBill extends NPCS.QuestGiver {
	
	private static final String[] strings = {
			"Eat your fruits!",
			"I'd love to go on your adventures.",
			"What all is out there?"
	};
	private static ArrayList<String> quests = new ArrayList<>();
	private static int index = -1;
	
	public FarmerBill(Location location) {
		super(location,Villager.Profession.FARMER,"§aFarmer Bill",strings);
	}
	
	@Override
	protected String setOther() {
		return "farmer_bill";
	}
	
	
	@Override
	public void onClick(Player player) {
	
	}
}
