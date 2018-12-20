package me.imunsmart.rpg.mechanics;

import me.imunsmart.rpg.events.Spawners;
import me.imunsmart.rpg.mechanics.loot.GemSpawners;
import me.imunsmart.rpg.mechanics.loot.LootChests;
import me.imunsmart.rpg.util.CustomItem;
import me.imunsmart.rpg.util.MessagesUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AdminTools implements Listener {

    public static final ItemStack delete = Items.createItem(Material.BARRIER, 1, 0, ChatColor.RED + "Deleter", ChatColor.GRAY + "Left click to remove.");

    public static final ItemStack lootChest = new CustomItem(Material.CHEST).addGlow().setName("§b&lTier 1 Lootchest");

    public static final ItemStack gemSpawner = new CustomItem(Material.DIAMOND_ORE).addGlow().setName("§b&lTier 1 Gem Spawner");

    public static final ItemStack mobSpawner = new CustomItem(Material.SPAWNER).addGlow().setName("§b&lTier 1 Mob Spawner");

    public static final ItemStack spawnerAmountTool = new CustomItem(Material.CREEPER_SPAWN_EGG).addGlow().setCustomAmount(1).setName("§a&lSpawner Amount Tool");

    public static final ItemStack[] spawnerTypeTool = {
            new CustomItem(Material.ZOMBIE_HEAD).addGlow().setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Zombie"),
            new CustomItem(Material.SKELETON_SKULL).addGlow().setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Skeleton"),
            new CustomItem(Material.PLAYER_HEAD).addGlow().setCustomAmount(1).setName("§a&lSpawner Type Tool").setLore(ChatColor.GRAY + "Type: " + ChatColor.AQUA + "Spider"),

    };

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getInventory().getItemInMainHand();
        if (i.hasItemMeta() && i.getItemMeta().hasDisplayName() && i.getItemMeta().getDisplayName().contains("Deleter")) {
            e.setCancelled(true);
            if (LootChests.removeChest(e.getBlock().getLocation())) {
                p.sendMessage(ChatColor.RED + "Lootchest removed.");
                return;
            }
            if (GemSpawners.removeSpawner(e.getBlock().getLocation())) {
                p.sendMessage(ChatColor.RED + "GemSpawner removed.");
                return;
            }
            if (Spawners.remove(e.getBlock().getRelative(BlockFace.UP).getLocation())) {
                p.sendMessage(ChatColor.RED + "Spawner removed.");
                return;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack stack = e.getItem();
        if (stack != null) {
            if (e.getAction() == Action.LEFT_CLICK_AIR) return;
            int a = 1;
            if (e.getAction() == Action.RIGHT_CLICK_AIR)
                a = -1;
            if (stack.hasItemMeta() && stack.getItemMeta().hasDisplayName()) {
                if (stack.getType() == Material.CREEPER_SPAWN_EGG) {
                    e.setCancelled(true);
                    CustomItem item = new CustomItem(stack);
                    int amount = item.getAmount();
                    amount += a;
                    amount = Math.min(64, amount);
                    amount = Math.max(1, amount);
                    item.setCustomAmount(amount);
                    e.getPlayer().setItemInHand(item);
                } else if (stack.getItemMeta().getDisplayName().contains("Spawner Type")) {
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
                    if (stack.getType() == Material.SPAWNER) {
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
                    case SPAWNER:
                        if (e.getPlayer().getInventory().getItem(4).getType() != Material.CREEPER_SPAWN_EGG) return;
                        if (!e.getPlayer().getInventory().getItem(5).getItemMeta().getDisplayName().contains("Spawner")) return;
                        int amount = e.getPlayer().getInventory().getItem(4).getAmount();
                        String type = ChatColor.stripColor(e.getPlayer().getInventory().getItem(5).getItemMeta().getLore().get(0)).split(" ")[1].toLowerCase();
                        Spawners.setSpawn(e.getBlockPlaced().getLocation(), tier, amount, type);
                        String message = ChatColor.GRAY + "Spawner created. (" + ChatColor.AQUA + "type: " + type + ", amount: " + amount + ", tier: " + tier + ChatColor.GRAY + ")";
                        e.getPlayer().sendMessage(message);
                        break;
                }
            }

        }
    }

}
