package de.riotseb.adventcalendar.calendar;


import de.riotseb.adventcalendar.util.ItemBuilder;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class AdventCalendarInventory {

    private Inventory inv;
    // Map syntax<Position in inventory, Adventday)
    private Map<Integer, Integer> positions = new HashMap<>();


    public AdventCalendarInventory() {
        Random random = new Random();
        MessageHandler msgHandler = new MessageHandler();
        List<Integer> availablePos = new ArrayList<>();

        inv = Bukkit.createInventory(null, 54, msgHandler.getPrefix());

        ItemStack filler = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 7)
                .withDisplayName(ChatColor.RED + "X")
                .build();

        for (Integer i = 0; i < inv.getSize(); i++) {
            inv.setItem(i, filler);
            availablePos.add(i);
        }

        for (Integer day = 1; day <= 24; day++) {

            Integer index = random.nextInt(availablePos.size());
            Integer position = availablePos.get(index);

            positions.put(position, day);
            inv.setItem(position, new ItemBuilder().getPresentHead(ChatColor.RESET + "" + ChatColor.BLUE + "Day " + day));
            availablePos.remove(position);

        }

    }

    public Inventory getCalendar() {
        return this.inv;
    }

    public Map<Integer, Integer> getPositions() {
        return positions;
    }

}
