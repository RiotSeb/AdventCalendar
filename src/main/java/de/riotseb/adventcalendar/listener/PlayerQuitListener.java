package de.riotseb.adventcalendar.listener;

import de.riotseb.adventcalendar.AdventCalendar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Class created by RiotSeb on 25.11.2017.
 */
public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player p = e.getPlayer();

        if (p.hasMetadata("editcalendar")) {
            p.removeMetadata("editcalendar", AdventCalendar.getPlugin());
        }

    }

}
