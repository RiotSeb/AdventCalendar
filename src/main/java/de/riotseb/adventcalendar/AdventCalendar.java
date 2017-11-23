package de.riotseb.adventcalendar;

import de.riotseb.adventcalendar.commands.EditCalendarCommand;
import de.riotseb.adventcalendar.util.MessageHandler;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class AdventCalendar extends JavaPlugin {


    @Override
    public void onEnable() {

        setupFiles();
        setupCommands();

        new MessageHandler(this);

        this.getLogger().info("enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("disabled!");
    }


    private void setupFiles(){
        this.saveResource("messages.yml", false);
    }

    private void setupCommands(){
        registerCommand("editcalendar", new EditCalendarCommand("editcalendar", "Edit the Adventcalendar", "eac", "ec", "ecalendar"));
    }

    private void registerCommand(String name, Command command){
        ((CraftServer) this.getServer()).getCommandMap().register(name, command);
    }

}
