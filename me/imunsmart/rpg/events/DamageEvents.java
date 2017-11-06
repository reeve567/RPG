package me.imunsmart.rpg.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.imunsmart.rpg.Main;
import me.imunsmart.rpg.mechanics.Health;

public class DamageEvents implements Listener {
	private Main pl;

	public DamageEvents(Main pl) {
		this.pl = pl;
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
			Player p = (Player) e.getEntity();
			if (Health.health.containsKey(p.getName())) {
				LivingEntity le = (LivingEntity) e.getDamager();
				double damage = 1;
				if (le.getEquipment().getItemInMainHand() != null)
					damage = Health.getAttributeI(le.getEquipment().getItemInMainHand(), "Damage");
				Health.damage(p, (int) Math.round(damage));
				Health.combat.put(p.getName(), 16);
			}
		}
	}
}
