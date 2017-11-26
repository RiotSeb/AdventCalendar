package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.AdventCalendar;
import de.riotseb.adventcalendar.calendar.AdventCalendarInventory;
import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.Sound;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class InventoryClickListener implements Listener {

    private Map<UUID, AdventCalendarInventory> calendars = AdventCalendarCommand.getCalendars();
    private static File file = new File("plugins/AdventCalendar/adventcalendar.yml");
    private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private MessageHandler msgHandler = new MessageHandler();


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getWhoClicked() instanceof Player) {

            Player p = (Player) e.getWhoClicked();

            if (calendars.containsKey(p.getUniqueId())) {

                e.setCancelled(true);

                AdventCalendarInventory calendar = calendars.get(p.getUniqueId());
                Map<Integer, Integer> positions = calendar.getPositions();
                Integer day = positions.get(e.getSlot());

                if (day != null) {

                    Calendar cal = Calendar.getInstance();

                    if (cal.get(Calendar.MONTH) == AdventCalendar.getPlugin().getConfig().getInt("month")) {

                        if (cal.get(Calendar.DAY_OF_MONTH) == day || AdventCalendar.getPlugin().getConfig().getBoolean("bypass day")) {


                            File file = new File("plugins/AdventCalendar/openedpresents.yml");
                            YamlConfiguration configuuid = YamlConfiguration.loadConfiguration(file);
                            List<String> uuids;


                            if (configuuid.get("Day " + day) != null) {
                                uuids = (List<String>) configuuid.get("Day " + day);
                            } else {
                                uuids = new ArrayList<>();
                            }

                            if (!uuids.contains(p.getUniqueId().toString())) {

                                if (config.get("Day " + day) != null) {

                                    List<ItemStack> items = (List<ItemStack>) config.getList("Day " + day);

                                    if (checkSpace(p.getInventory(), items.size())) {

                                        items.forEach(itemStack -> p.getInventory().addItem(itemStack));
                                        p.sendMessage(msgHandler.getMessage("items granted"));
                                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BELL, 5, 5);
                                        p.closeInventory();

                                        uuids.add(p.getUniqueId().toString());
                                        configuuid.set("Day " + day, uuids);

                                        try {
                                            configuuid.save(file);
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }

                                    } else {
                                        p.closeInventory();
                                        p.sendMessage(msgHandler.getMessage("not enough space"));
                                    }
                                } else {
                                    p.sendMessage(msgHandler.getMessage("no presents set for day"));
                                }
                            } else {
                                p.sendMessage(msgHandler.getMessage("present already claimed"));
                            }
                        } else {

                            if (day == 1) {
                                p.sendMessage(msgHandler.getMessage("wrong day").replaceAll("%day%", "1st"));
                            } else if (day == 2) {
                                p.sendMessage(msgHandler.getMessage("wrong day").replaceAll("%day%", "2nd"));
                            } else if (day == 3) {
                                p.sendMessage(msgHandler.getMessage("wrong day").replaceAll("%day%", "3rd"));
                            } else {
                                p.sendMessage(msgHandler.getMessage("wrong day").replaceAll("%day%", day + "th"));
                            }
                        }
                    } else {
                        p.sendMessage(msgHandler.getMessage("wrong month"));
                    }
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
        return neededspace <= free;
    }

    static void reloadConfig() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


}
