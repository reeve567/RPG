package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.Glow;
import me.imunsmart.rpg.util.MessagesUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().isOp()) {
			ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
			if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().startsWith("§b§lTier ") && stack.getItemMeta().hasEnchant(new Glow(999))) {
				e.setCancelled(true);
				int tier = Integer.parseInt(stack.getItemMeta().getDisplayName().substring(9, 10));
				switch (e.getBlockPlaced().getType()) {
					case CHEST:
						e.getBlockPlaced().setType(Material.CHEST);
						LootChests.addChest(e.getBlockPlaced().getLocation(), tier);
						e.getPlayer().sendMessage(MessagesUtil.lootChestCreated(tier));
						break;
				}
			}
			
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
		if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().startsWith("§b§lTier ") && stack.getItemMeta().hasEnchant(new Glow(999))) {
			e.setCancelled(true);
			switch (stack.getType()) {
				case CHEST:
					if (e.getBlock().getType().equals(Material.CHEST)) {
						LootChests.removeChest(e.getBlock().getLocation());
						e.getPlayer().sendMessage(MessagesUtil.lootchestRemoved());
					} else e.setCancelled(false);
					break;
				case DIAMOND_ORE:
					if (e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
						GemSpawners.removeSpawner(e.getBlock().getLocation());
					}
					else e.setCancelled(false);
					break;
			}
		}
	}
	
}
