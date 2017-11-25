package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.AdventCalendar;
import de.riotseb.adventcalendar.calendar.Calendar;
import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class InventoryCloseListener implements Listener {


    private File file = new File("plugins/AdventCalendar/adventcalendar.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();

        if (p.hasMetadata("editcalendar")) {

            List<MetadataValue> metadata = p.getMetadata("editcalendar");

            Integer day = metadata.get(0).asInt();
            

            List<ItemStack> items = new ArrayList<>();

            inv.forEach(itemStack -> {
                if (itemStack != null) {
                    items.add(itemStack);
                }
            });

            config.set("Day " + day, items);

            try {
                config.save(file);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            p.removeMetadata("editcalendar", AdventCalendar.getPlugin());

        }

        if (p.hasMetadata("calendar")) {

            Map<UUID, Calendar> calendars = AdventCalendarCommand.getCalendars();

            if (calendars.containsKey(p.getUniqueId())) {
                calendars.remove(p.getUniqueId());
            }

            p.removeMetadata("calendar", AdventCalendar.getPlugin());
        }


    }

}
