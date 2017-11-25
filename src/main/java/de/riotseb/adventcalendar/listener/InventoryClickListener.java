package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.calendar.Calendar;
import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class InventoryClickListener implements Listener {

    private Map<UUID, Calendar> calendars = AdventCalendarCommand.getCalendars();
    private File file = new File("plugins/AdventCalendar/adventcalendar.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private MessageHandler msgHandler = new MessageHandler();


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getWhoClicked() instanceof Player) {

            Player p = (Player) e.getWhoClicked();

            if (calendars.containsKey(p.getUniqueId())) {

                e.setCancelled(true);

                Calendar calendar = calendars.get(p.getUniqueId());


                Map<Integer, Integer> positions = calendar.getPositions();

                Integer day = positions.get(e.getSlot());

                Bukkit.broadcastMessage("day: " + day);

                if (config.get("Day " + day) != null) {

                    List<ItemStack> items = (List<ItemStack>) config.getList("Day " + day);

                    if (!checkSpace(p.getInventory(), items.size())) {
                        p.closeInventory();
                        p.sendMessage(msgHandler.getMessage("not enough space"));
                        return;
                    }

                    items.forEach(itemStack -> p.getInventory().addItem(itemStack));

                    p.sendMessage(msgHandler.getMessage("items granted"));

                } else {
                    p.sendMessage(msgHandler.getMessage("no presents set for day"));
                }

            }


        }

    }

    private boolean checkSpace(Inventory inv, Integer neededspace) {

        int free = 0;
        for (int slot = 0; slot < inv.getSize() - 5; slot++) {
            if (inv.getItem(slot) == null) {
                free++;
            }
        }

        Bukkit.broadcastMessage("Free: " + free + "| Needed: " + neededspace);

        return neededspace <= free;

    }


}
