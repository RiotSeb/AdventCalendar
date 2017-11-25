package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.AdventCalendar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class InventoryCloseListener implements Listener {


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();

        if (p.hasMetadata("editcalendar")) {

            List<MetadataValue> metadata = p.getMetadata("editcalendar");

            Integer day = metadata.get(0).asInt();

            p.sendMessage("Inventar geschlossen, Tag: " + day);


            p.removeMetadata("editcalendar", AdventCalendar.getPlugin());

        }
    }

}
