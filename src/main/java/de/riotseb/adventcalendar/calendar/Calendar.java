package de.riotseb.adventcalendar.calendar;


import de.riotseb.adventcalendar.util.ItemBuilder;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class Calendar {

    private Inventory inv;
    private Random random = new Random();
    private File file = new File("plugins/AdventCalendar/adventcalendar.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
    private MessageHandler msgHandler = new MessageHandler();
    // Map<Position in Inventory, Adventday)
    private Map<Integer, Integer> positions = new HashMap<>();
    private List<Integer> availablePos = new ArrayList<>();


    public Calendar() {


        inv = Bukkit.createInventory(null, 54, msgHandler.getPrefix());

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7)
                .withDisplayName(ChatColor.RED + "X")
                .build();

        for (Integer i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, filler);
            availablePos.add(i);
        }

        fillInventory();


    }


    public Inventory getCalendar() {
        return this.inv;
    }

    public Map<Integer, Integer> getPositions() {
        return positions;
    }

    private void fillInventory() {


        for (Integer x = 1; x <= 24; x++) {

            Integer index = random.nextInt(availablePos.size());

            Integer day = availablePos.get(index);

            positions.put(day, x);

            inv.setItem(day, new ItemBuilder().getPresentHead2(ChatColor.RESET + "Day " + x));

            availablePos.remove(day);


        }


    }

}
