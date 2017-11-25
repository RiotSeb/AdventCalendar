package de.riotseb.adventcalendar.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class MessageHandler {

    private File file = new File("plugins/AdventCalendar/messages.yml");
    private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

    /**
     * Get a message from the messages.yml
     *
     * @param key Key from the messages.yml
     * @return Chatcolor translated value of the key
     */

    public String getMessage(String key) {

        String message = config.getString(key);
        String prefix = getPrefix();

        if (message != null) {

            message = prefix + ChatColor.translateAlternateColorCodes('&', message);

            return message;
        } else {

            message = prefix + ChatColor.RED + "Error - Missing Message! Please contact an administrator. [" + key + "]";
            return message;

        }
    }

    /**
     * Get the prefix which is set in the messages.yml
     *
     * @return prefix as set in the config
     */

    public String getPrefix() {

        String prefix = config.getString("prefix");

        if (prefix != null) {

            prefix = ChatColor.translateAlternateColorCodes('&', prefix);

            return prefix;
        } else {

            prefix = ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "AdventCalendar" + ChatColor.GRAY + "]";
            return prefix;

        }

    }

    /**
     * Send usage instructions of a command to a commandsender
     *
     * @param sender       CommandSender the message is send to.
     * @param commandusage Command the usage instructions are send for.
     */

    public void sendUsage(CommandSender sender, String commandusage) {
        sender.sendMessage(ChatColor.GREEN + "Usage:");
        sender.sendMessage(ChatColor.GRAY + "    \u00BB " + ChatColor.RED + commandusage);
    }


}
