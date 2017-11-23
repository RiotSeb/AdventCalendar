package de.riotseb.adventcalendar.commands;


import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Class created by RiotSeb on 23/11/2017.
 */
public class AdventCalendarCommand extends BukkitCommand {

    private MessageHandler msgHandler = new MessageHandler();

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
            sender.sendMessage("§CNur als Spieler nutzbar.");
            return true;
        }

        Player p = (Player) sender;

        if (p.hasPermission("")) {

            if (args.length == 0) {


            } else {
                msgHandler.sendUsage(p, "");
                return true;
            }

        } else {
            p.sendMessage(msgHandler.getMessage("no perms"));
            return true;
        }
        return false;
    }


}
