package de.riotseb.adventcalendar;

import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import de.riotseb.adventcalendar.commands.EditCalendarCommand;
import de.riotseb.adventcalendar.listener.InventoryClickListener;
import de.riotseb.adventcalendar.listener.InventoryCloseListener;
import de.riotseb.adventcalendar.listener.PlayerQuitListener;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class AdventCalendar extends JavaPlugin {

    private static AdventCalendar plugin;


    @Override
    public void onEnable() {

        setupFiles();
        setupCommands();
        registerEvents();


        plugin = this;

        new MessageHandler(this);

        this.getLogger().info("enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("disabled!");
    }


    private void setupFiles() {
        this.saveResource("messages.yml", false);
        this.saveResource("adventcalendar.yml", false);
    }

    private void setupCommands() {
        registerCommand("adventcalendar", new AdventCalendarCommand("adventcalendar", "Open the adventcalendar", "ac"));
        registerCommand("editcalendar", new EditCalendarCommand("editcalendar", "Edit the Adventcalendar", "eac", "ec", "ecalendar"));
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new InventoryCloseListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new InventoryClickListener(), this);
    }

    private void registerCommand(String name, Command command) {
        ((CraftServer) this.getServer()).getCommandMap().register(name, command);
    }

    public static AdventCalendar getPlugin() {
        return plugin;
    }
}
