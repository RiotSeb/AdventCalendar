package de.riotseb.adventcalendar;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Class created by RiotSeb on 23.11.2017.
 */
public class AdventCalendar extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("disabled!");
    }

}
