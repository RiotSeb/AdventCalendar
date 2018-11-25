package de.riotseb.adventcalendar.handler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.riotseb.adventcalendar.AdventCalendarMain;
import de.riotseb.adventcalendar.inventory.InventoryMenu;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InventoryHandler {

	@Getter(lazy = true)
	private static final InventoryHandler instance = new InventoryHandler();

	@Getter
	private Map<UUID, InventoryMenu> openMenus = Maps.newHashMap();

	private File file = new File(AdventCalendarMain.getInstance().getDataFolder() + File.separator + "adventcalendar.yml");
	private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

	public Stream<ItemStack> getContents(int day) {
		List<ItemStack> items = ((List<ItemStack>) config.getList("day " + day));

		if (items == null) {
			items = Lists.newArrayList();
		}
		return items.stream();
	}

	public List<ItemStack> getContentsList(int day) {
		return getContents(day).collect(Collectors.toList());
	}

	public boolean saveContents(int day, List<ItemStack> items) {
		config.set("day " + day, items);

		try {
			config.save(file);
		} catch (IOException exception) {
			AdventCalendarMain.getInstance().getLogger().log(Level.SEVERE, "An error occurred when saving contents for a day", exception);
			return false;
		}
		return true;
	}

}
