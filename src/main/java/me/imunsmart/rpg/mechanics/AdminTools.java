package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mechanics.loot.GemSpawners;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AdminTools implements Listener {
	
	public static final ItemStack[] lootChestTools = {
			new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 1 Lootchest"),
			new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 2 Lootchest"),
			new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 3 Lootchest"),
			new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 4 Lootchest"),
			new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 5 Lootchest")
	};
	
	public static final ItemStack[] gemSpawnerTools = {
			new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 1 Gem Spawner"),
			new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 2 Gem Spawner"),
			new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 3 Gem Spawner"),
			new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 4 Gem Spawner"),
			new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 5 Gem Spawner")
	};
	
	public static final ItemStack[] mobSpawnerTools = {
			new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 1 Mob Spawner"),
			new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 2 Mob Spawner"),
			new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 3 Mob Spawner"),
			new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 4 Mob Spawner"),
			new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 5 Mob Spawner")
	};
	
	public static final CustomItem spawnerAmountTool = new CustomItem(Material.MONSTER_EGG).addGlow().setCustomAmount(1).setName("§a&lSpawner amount Tool");
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().startsWith("§b§lTier ")) {
			e.setCancelled(true);
			switch (stack.getType()) {
				case CHEST:
					if (e.getBlock().getType().equals(Material.CHEST)) {
						if (LootChests.removeChest(e.getBlock().getLocation())) {
							e.getPlayer().sendMessage(MessagesUtil.lootChestRemoved);
							return;
						}
					}
					e.getPlayer().sendMessage(MessagesUtil.lootChestErrorOrNotFound);
					break;
				case DIAMOND_ORE:
					if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
						if (GemSpawners.removeSpawner(e.getBlock().getLocation())) {
							e.getPlayer().sendMessage("");
							return;
						}
					}
					break;
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		ItemStack stack = e.getItem();
		if (e.getAction() == Action.LEFT_CLICK_AIR) {
			if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().startsWith("§b§lTier ")) {
				int tier = Integer.parseInt(stack.getItemMeta().getDisplayName().substring(9, 10));
				if (stack.getType().equals(Material.MONSTER_EGG)) {
					CustomItem item = new CustomItem(stack);
					int amount = item.getAmount();
					if (amount != 64) {
						amount++;
					}
					else {
						amount = 1;
					}
					item.setCustomAmount(amount);
				} else if (stack.getType().equals(Material.MOB_SPAWNER)) {
					if (tier != 5) {
						tier += 1;
					}
					else {
						tier = 1;
					}
					new CustomItem(stack).setName("§b&lTier " + tier + " Mob Spawner");
				} else if (stack.getType().equals(Material.DIAMOND_ORE)) {
					if (tier != 5) {
						tier += 1;
					}
					else {
						tier = 1;
					}
					new CustomItem(stack).setName("§b&lTier " + tier + " Gem Spawner");
				}
				
			}
		} else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
		
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().isOp()) {
			ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
			if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().startsWith("§b§lTier ")) {
				e.setCancelled(true);
				int tier = Integer.parseInt(stack.getItemMeta().getDisplayName().substring(9, 10));
				switch (e.getBlockPlaced().getType()) {
					case CHEST:
						e.getBlockPlaced().setType(Material.CHEST);
						LootChests.addChest(e.getBlockPlaced().getLocation(), tier);
						e.getPlayer().sendMessage(MessagesUtil.lootChestCreated(tier));
						break;
					case DIAMOND_ORE:
						e.getBlockPlaced().setType(Material.DIAMOND_ORE);
						GemSpawners.addSpawner(tier, e.getBlockPlaced().getLocation());
						e.getPlayer().sendMessage(MessagesUtil.gemSpawnerCreated(tier));
						break;
				}
			}
			
		}
	}
	
}
