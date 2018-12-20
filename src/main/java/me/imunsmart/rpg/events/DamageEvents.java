package me.imunsmart.rpg.events;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Health;
import me.imunsmart.rpg.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import static me.imunsmart.rpg.mechanics.Health.calculateMaxHealth;
import static me.imunsmart.rpg.mechanics.Health.damage;

public class DamageEvents implements Listener {
	private Main pl;

	public DamageEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Util.inSafeZone(p)) {
				e.setCancelled(true);
				e.setDamage(0);
				return;
			}
			if (e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.ENTITY_SWEEP_ATTACK) {
				double real = (e.getDamage() / 20.0d);
				damage(p, (int) Math.round(real * (calculateMaxHealth(p) / 2)));
				Health.combat.put(p.getName(), 16);
				e.setDamage(0);
			} else {
				e.setCancelled(true);
			}
		}
	}
}
