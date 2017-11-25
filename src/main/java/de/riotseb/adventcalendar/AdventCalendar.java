package de.riotseb.adventcalendar;

import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import de.riotseb.adventcalendar.commands.EditCalendarCommand;
import de.riotseb.adventcalendar.listener.InventoryClickListener;
import de.riotseb.adventcalendar.listener.InventoryCloseListener;
import de.riotseb.adventcalendar.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class AdventCalendar extends JavaPlugin {

    private static AdventCalendar plugin;


    @Override
    public void onEnable() {
        plugin = this;

        setupFiles();
        setupCommands();
        registerEvents();

        this.getLogger().info("enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("disabled!");
    }


    private void setupFiles() {
        this.saveResource("messages.yml", false);
        this.saveResource("adventcalendar.yml", false);
        this.saveResource("openedpresents.yml", false);
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

        Class<?> craftserver = Bukkit.getServer().getClass();

        try {

            Field commandMapField = craftserver.getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(name, command);


        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static AdventCalendar getPlugin() {
        return plugin;
    }


}
