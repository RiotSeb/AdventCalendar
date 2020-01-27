package de.riotseb.adventcalendar;

import de.riotseb.adventcalendar.commands.AdventCalendarCommand;
import de.riotseb.adventcalendar.commands.EditAdventCalendarCommand;
import de.riotseb.adventcalendar.listener.EntityListener;
import de.riotseb.adventcalendar.listener.InventoryListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class AdventCalendarMain extends JavaPlugin {

	@Getter
	private static AdventCalendarMain instance;

	public static final String PRESENT_HEAD_TEXTURE_URL = "https://textures.minecraft.net/texture/ef79f376f45cc3623f73c63de1427f7dd2acbaf8e5d7844d94d254924ba290";

	public static final String PERM_OPEN = "adventcalendar.open";
	public static final String PERM_EDIT = "adventcalendar.edit";

	@Override
	public void onEnable() {
		instance = this;

		setupFiles();
		getCommand("adventcalendar").setExecutor(new AdventCalendarCommand());
		getCommand("editadventcalendar").setExecutor(new EditAdventCalendarCommand());

		getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);

	}

	private void setupFiles() {
		this.saveResource("messages.yml", false);
		this.saveResource("adventcalendar.yml", false);
		this.saveResource("openedpresents.yml", false);
	}

}
