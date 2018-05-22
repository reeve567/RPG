package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.mechanics.loot.GemSpawners;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.MessagesUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AdminTools implements Listener {

    public static final ItemStack lootChest = new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 1 Lootchest");

    public static final ItemStack gemSpawner = new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 1 Gem Spawner");

    public static final ItemStack mobSpawner = new CustomItem(Material.MOB_SPAWNER).addGlow().setName("§b&lTier 1 Mob Spawner");

    public static final ItemStack spawnerAmountTool = new CustomItem(Material.MONSTER_EGG).addGlow().setCustomAmount(1).setName("§a&lSpawner Amount Tool");

    public static final ItemStack[] spawnerTypeTool = {
            new CustomItem(Material.SKULL_ITEM).addGlow().setDurability(2).setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Zombie"),
            new CustomItem(Material.SKULL_ITEM).addGlow().setDurability(0).setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Skeleton"),
            new CustomItem(Material.SKULL_ITEM).addGlow().setDurability(3).setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Spider"),
    };

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
        if (stack != null) {
            int a = 1;
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)
                a = -1;
            if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
                if (stack.getType() == Material.MONSTER_EGG) {
                    e.setCancelled(true);
                    CustomItem item = new CustomItem(stack);
                    int amount = item.getAmount();
                    amount += a;
                    amount = Math.min(64, amount);
                    amount = Math.max(1, amount);
                    item.setCustomAmount(amount);
                    e.getPlayer().setItemInHand(item);
                } else if (stack.getType() == Material.SKULL_ITEM) {
                    e.setCancelled(true);
                    for (int i = 0; i < spawnerTypeTool.length; i++) {
                        if (e.getItem().getItemMeta().getLore().equals(spawnerTypeTool[i].getItemMeta().getLore())) {
                            i += a;
                            i = Math.min(spawnerTypeTool.length - 1, i);
                            i = Math.max(0, i);
                            e.getPlayer().getInventory().setItemInMainHand(spawnerTypeTool[i]);
                            e.getPlayer().updateInventory();
                            break;
                        }
                    }
                } else if (stack.getItemMeta().getDisplayName().startsWith("§b§lTier ")) {
                    int tier = Integer.parseInt(stack.getItemMeta().getDisplayName().substring(9, 10));
                    if (stack.getType() == Material.MOB_SPAWNER) {
                        e.setCancelled(true);
                        tier += a;
                        tier = Math.min(5, tier);
                        tier = Math.max(1, tier);
                        e.getPlayer().setItemInHand(new CustomItem(stack).setName("§b&lTier " + tier + " Mob Spawner"));
                    } else if (stack.getType() == Material.DIAMOND_ORE) {
                        e.setCancelled(true);
                        tier += a;
                        tier = Math.min(5, tier);
                        tier = Math.max(1, tier);
                        e.getPlayer().setItemInHand(new CustomItem(stack).setName("§b&lTier " + tier + " Gem Spawner"));
                    } else if (stack.getType() == Material.CHEST) {
                        e.setCancelled(true);
                        tier += a;
                        tier = Math.min(5, tier);
                        tier = Math.max(1, tier);
                        e.getPlayer().setItemInHand(new CustomItem(stack).setName("§b&lTier " + tier + " Lootchest"));
                    }
                }
            }
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
                    case MOB_SPAWNER:

                        break;
                }
            }

        }
    }

}
