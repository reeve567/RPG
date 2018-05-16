package me.imunsmart.rpg.events;

import static me.imunsmart.rpg.mechanics.Health.calculateMaxHealth;
import static me.imunsmart.rpg.mechanics.Health.damage;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.util.Util;

public class DamageEvents implements Listener {
	private Main pl;

	public DamageEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if(Util.inSafeZone(p)) {
				e.setCancelled(true);
				e.setDamage(0);
				return;
			}
			if (e.getCause() == DamageCause.FALL) {
				double real = (e.getDamage() / 20.0d);
				damage(p, (int) Math.round(real * (calculateMaxHealth(p) / 2)));
			}
			e.setDamage(0);
		}
	}
}
