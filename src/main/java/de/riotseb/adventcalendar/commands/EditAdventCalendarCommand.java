package de.riotseb.adventcalendar.commands;

import de.riotseb.adventcalendar.AdventCalendarMain;
import de.riotseb.adventcalendar.handler.InventoryHandler;
import de.riotseb.adventcalendar.inventory.InventoryMenu;
import de.riotseb.adventcalendar.util.MessageHandler;
import de.riotseb.adventcalendar.util.Messages;
import de.riotseb.adventcalendar.util.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class EditAdventCalendarCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.PLAYER_ONLY.get());
			return true;
		}

		if (!sender.hasPermission(AdventCalendarMain.PERM_EDIT)) {
			sender.sendMessage(Messages.NO_PERMISSIONS.get());
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(Messages.EDIT_INSTRUCTIONS.get());
			MessageHandler.sendUsage(sender, "/editadventcalendar <Day>");
			return true;
		}

		Integer day = Utils.getInteger(args[0]);

		if (day == null) {
			sender.sendMessage(Messages.NOT_A_NUMBER.getReplaced("%string%", args[0]));
			return true;
		}

		if (day < 0 || day > 25) {
			sender.sendMessage(Messages.NUMBER_NOT_BETWEEN.get());
			return true;
		}

		Player player = (Player) sender;
		InventoryMenu menu = new InventoryMenu(5, Messages.EDIT_CALENDAR_INVENTORY_TITLE.getRawReplaced("%day%", "" + day),
				true, (items, player1) -> {

			boolean success = InventoryHandler.getInstance().saveContents(day, items);

			if (success) {
				player1.sendMessage(Messages.INVENTORY_SAVED.getReplaced("%day%", "" + day));
			} else {
				player1.sendMessage(Messages.INVENTORY_NOT_SAVED.get());
			}

		});

		AtomicInteger index = new AtomicInteger(0);

		InventoryHandler.getInstance().getContents(day)
				.forEach(itemStack -> {

					int i = index.getAndIncrement();
					menu.setItem(itemStack, i, null);

				});

		menu.openInventory(player);

		return true;
	}

}
