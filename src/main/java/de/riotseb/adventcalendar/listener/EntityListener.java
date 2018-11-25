package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.handler.InventoryHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EntityListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {

		InventoryHandler.getInstance().getOpenMenus().remove(event.getPlayer().getUniqueId());

	}

}
