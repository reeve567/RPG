package me.imunsmart.rpg.mechanics;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mobs.DropManager;
import net.md_5.bungee.api.ChatColor;

public class Health {

	public static HashMap<String, Integer> health = new HashMap<String, Integer>();
	public static HashMap<String, BossBar> bar = new HashMap<String, BossBar>();
	public static HashMap<String, Integer> combat = new HashMap<String, Integer>();

	public static void task(Main pl) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Double.MAX_VALUE);
					if (!health.containsKey(p.getName())) {
						health.put(p.getName(), calculateMaxHealth(p));
					}
					int max = calculateMaxHealth(p);
					int hp = health.get(p.getName());

					if (hp > max)
						hp = max;

					if (hp != max && !p.isDead()) {
						int regen = calculateHealthRegen(p);
						if (combat.containsKey(p.getName())) {
							if (combat.get(p.getName()) == 0) {
								heal(p, regen);
							} else {
								combat.put(p.getName(), combat.get(p.getName()) - 1);
							}
						} else {
							heal(p, regen);
						}
					}

					if (!p.isDead()) {
						double per = ((double) hp / (double) max);
						if (per > 1)
							per = 1;
						p.setHealth(1 + (19 * per));
						BossBar b = null;
						if (!bar.containsKey(p.getName()))
							b = Bukkit.createBossBar("health", BarColor.GREEN, BarStyle.SOLID);
						else
							b = bar.get(p.getName());
						b.setTitle(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Health: " + hp + " / " + max);
						b.setProgress(per);
						b.setVisible(true);
						if (!bar.containsKey(p.getName())) {
							b.addPlayer(p);
							bar.put(p.getName(), b);
						} else {
							b.removeAll();
							b.addPlayer(p);
						}
					}
				}
			}
		}, 0, 10);
	}

	public static void disable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (bar.containsKey(p.getName())) {
				BossBar b = bar.remove(p.getName());
				b.removeAll();
			}
		}
	}

	public static int calculateMaxHealth(LivingEntity e) {
		int maxhp = e instanceof Player ? 50 : 20;
		for (int i = 0; i < e.getEquipment().getArmorContents().length; i++) {
			ItemStack it = e.getEquipment().getArmorContents()[i];
			if (it != null) {
				if (!it.hasItemMeta()) {
					e.getEquipment().getArmorContents()[i].setType(Material.AIR);
				} else {
					maxhp += Integer.parseInt(ChatColor.stripColor(it.getItemMeta().getLore().get(0).split(" ")[1]));
				}
			}
		}
		return maxhp;
	}

	public static int calculateHealthRegen(Player p) {
		PlayerInventory pi = p.getInventory();
		int regen = 2;
		for (int i = 0; i < pi.getArmorContents().length; i++) {
			ItemStack it = pi.getArmorContents()[i];
			if (it != null) {
				if (it.hasItemMeta()) {
					regen += getAttributeI(it, "Regen");
				}
			}
		}
		return regen;
	}

	public static boolean hasAttribute(ItemStack i, String name) {
		if (!i.hasItemMeta()) {
			return false;
		}
		for (String s : i.getItemMeta().getLore()) {
			String l = ChatColor.stripColor(s);
			if (l.split(":")[0].equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

	public static int getAttributeI(ItemStack i, String name) {
		if (!hasAttribute(i, name)) {
			return 0;
		} else {
			for (String s : i.getItemMeta().getLore()) {
				String line = ChatColor.stripColor(s);
				if (line.split(":")[0].equalsIgnoreCase(name)) {
					String val = line.split(" ")[1];
					if (val.contains("+"))
						val.substring(1);
					if (val.contains("-")) {
						int min = Integer.parseInt(val.split("-")[0]);
						return (min + (int) Math.round(Math.random() * (Integer.parseInt(val.split("-")[1]) - min)));
					}
					return Integer.parseInt(val);
				}
			}
		}
		return 0;
	}

	public static double getAttributeP(ItemStack i, String name) {
		if (!hasAttribute(i, name)) {
			return 0;
		} else {
			for (String s : i.getItemMeta().getLore()) {
				String line = ChatColor.stripColor(s);
				if (line.split(":")[0].equalsIgnoreCase(name)) {
					String val = s.split(" ")[1];
					if (val.contains("%"))
						val = val.replaceAll("%", "");
					return Double.parseDouble(val) / 100.0d;
				}
			}
		}
		return 0;
	}

	public static void damage(Player p, int i) {
		int hp = health.get(p.getName());
		hp -= i;
		if (hp < 0)
			hp = 0;
		if (hp == 0) {
			p.setHealth(0);
		}
		health.put(p.getName(), hp);
		if ((double) hp / calculateMaxHealth(p) < 0.2) {
			Sounds.play(p, Sound.ENTITY_PLAYER_BIG_FALL, 1);
		}
	}

	public static void heal(Player p, int i) {
		int hp = health.get(p.getName());
		int max = calculateMaxHealth(p);
		hp += i;
		if (hp > max)
			hp = max;
		health.put(p.getName(), hp);
	}

	public static final Location SPAWN = new Location(Bukkit.getWorld("world"), 0.5, 4.5, 0.5);

	public static void resetPlayer(Player p) {
		p.teleport(SPAWN);
		p.getInventory().clear();
		ItemStack i = Items.createWeapon("sword", 1, 4, 8, "");
		i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		p.getInventory().addItem(i);
		i = Items.createArmor("boots", 1, DropManager.MAX_HEALTH_1B / 2, "Regen:2");
		i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		p.getInventory().addItem(i);
		i = Items.createArmor("leggings", 1, DropManager.MAX_HEALTH_1L / 2, "Regen:4");
		i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		p.getInventory().addItem(i);
		i = Items.createArmor("chestplate", 1, DropManager.MAX_HEALTH_1C / 2, "Regen:5");
		i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		p.getInventory().addItem(i);
		i = Items.createArmor("helmet", 1, DropManager.MAX_HEALTH_1H / 2, "Regen:3");
		i.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
		p.getInventory().addItem(i);
	}
}
