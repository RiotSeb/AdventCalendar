package de.riotseb.adventcalendar.commands;


import de.riotseb.adventcalendar.AdventCalendar;
import de.riotseb.adventcalendar.calendar.AdventCalendarInventory;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

/**
 * Class created by RiotSeb on 23/11/2017.
 */
public class AdventCalendarCommand extends BukkitCommand {

    private MessageHandler msgHandler = new MessageHandler();
    private static Map<UUID, AdventCalendarInventory> calendars = new HashMap<>();

    public AdventCalendarCommand(String command, String description, String... aliases) {
        super(command);
        this.description = description;
        List<String> aliasList = new ArrayList<>();
        aliasList.addAll(Arrays.asList(aliases));
        this.setAliases(aliasList);

    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(msgHandler.getMessage("player only use"));
            return true;
        }

        Player p = (Player) sender;

        if (p.hasPermission("AdventCalendar.use")) {

            if (args.length == 0) {

                AdventCalendarInventory calendar = new AdventCalendarInventory();

                p.openInventory(calendar.getCalendar());
                calendars.put(p.getUniqueId(), calendar);

                p.setMetadata("calendar", new FixedMetadataValue(AdventCalendar.getPlugin(), true));

                return true;
            } else {
                msgHandler.sendUsage(p, "/ac");
                return true;
            }

        } else {
            p.sendMessage(msgHandler.getMessage("no perms"));
            return true;
        }
    }

    public static Map<UUID, AdventCalendarInventory> getCalendars() {
        return calendars;
    }
}
