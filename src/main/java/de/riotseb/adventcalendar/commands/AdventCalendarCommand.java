package de.riotseb.adventcalendar.commands;

import com.google.common.collect.Lists;
import de.riotseb.adventcalendar.AdventCalendarMain;
import de.riotseb.adventcalendar.handler.InventoryHandler;
import de.riotseb.adventcalendar.inventory.InventoryMenu;
import de.riotseb.adventcalendar.util.ItemBuilder;
import de.riotseb.adventcalendar.util.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.stream.IntStream;

public class AdventCalendarCommand implements CommandExecutor {

	private File file = new File(AdventCalendarMain.getInstance().getDataFolder() + File.separator + "openedpresents.yml");
	private YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.PLAYER_ONLY.get());
			return true;
		}

		if (!sender.hasPermission(AdventCalendarMain.PERM_OPEN)) {
			sender.sendMessage(Messages.NO_PERMISSIONS.get());
			return true;
		}

		Player player = (Player) sender;

		InventoryMenu menu = new InventoryMenu(5, Messages.GUI_INVENTORY_TITLE.getRaw());
		menu.fill();

		List<Integer> availableSlots = Lists.newArrayList();

		IntStream.range(0, 5 * 9)
				.forEach(availableSlots::add);

		IntStream.rangeClosed(1, 24)
				.forEach(index -> {

					int random = ThreadLocalRandom.current().nextInt(availableSlots.size() - 1);
					int slot = availableSlots.remove(random);

					menu.setItem(ItemBuilder.getPresentHead(Messages.PRESENT_NAME_GUI.getRawReplaced("%day%", "" + index)),
							slot, (integer, player1) -> {

								LocalDate today = LocalDate.now();

								if (today.getMonth() != Month.DECEMBER) {
									player1.sendMessage(Messages.WRONG_MONTH.get());
									return;
								}

								if (today.getDayOfMonth() != index) {
									player1.sendMessage(Messages.WRONG_DAY.getDateReplaced(index));
									return;
								}

								List<Integer> acquiredDays = config.getIntegerList("" + player1.getUniqueId());

								if (acquiredDays == null) {
									acquiredDays = Lists.newArrayList();
								}

								if (acquiredDays.contains(index)) {
									player1.sendMessage(Messages.PRESENT_ALREADY_CLAIMED.get());
									return;
								}

								List<ItemStack> contents = InventoryHandler.getInstance().getContentsList(index);

								if (!hasEnoughSpace(player1.getInventory(), contents.size())) {
									player1.sendMessage(Messages.NOT_ENOUGH_SPACE.get());
									return;
								}

								contents.forEach(item -> player1.getInventory().addItem(item));
								acquiredDays.add(index);

								player1.sendMessage(Messages.ITEMS_GRANTED.get());

								player1.closeInventory();

								config.set("" + player1.getUniqueId(), acquiredDays);

								try {
									config.save(file);
								} catch (IOException exception) {
									AdventCalendarMain.getInstance().getLogger().log(Level.SEVERE, "Error when saving an acquired day", exception);
								}

							});

				});

		menu.openInventory(player);

		return true;
	}

	private boolean hasEnoughSpace(Inventory inv, int needed) {

		int ingredients = Arrays.stream(inv.getContents())
				.filter(Objects::nonNull)
				.mapToInt(item -> 1)
				.sum();

		return (inv.getSize() - ingredients) >= needed;
	}

}
