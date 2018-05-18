package me.imunsmart.rpg.mechanics;

import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleMessage {
	
	private PacketPlayOutTitle packet;
	
	public TitleMessage(String text) {
		this.packet = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"));
	}
	
	public void sendToAll() {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void sendToPlayer(Player p) {
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
	}
	
}
