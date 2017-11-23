package de.riotseb.adventcalendar.commands;

import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class created by RiotSeb on 23/11/2017.
 */
public class EditCalendarCommand extends BukkitCommand {

    private MessageHandler msgHandler = new MessageHandler();

    public EditCalendarCommand(String command, String description, String... aliases) {
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

        if (p.hasPermission("AdventCalendar.edit")) {

            if (args.length == 0) {

                p.sendMessage(msgHandler.getMessage("edit calendar instructions"));
                return true;
            }

            if (args.length == 1) {

                Integer day;


                try {
                    day = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    p.sendMessage(msgHandler.getMessage("not a number").replaceAll("%string%", args[0]));
                    return true;
                }

                    for (Integer i = 1; i <= 24; i++) {
                        if (i.equals(day)){
                            p.sendMessage("Eingegebene Zahl: " + day);
                            return true;
                        }
                    }

                    p.sendMessage(msgHandler.getMessage("no number between 1 and 24").replaceAll("%number%", day.toString()));
                    return true;


            } else {
                msgHandler.sendUsage(p, "/ec");
                return true;
            }

        } else {
            p.sendMessage(msgHandler.getMessage("no perms"));
            return true;
        }

    }


}
