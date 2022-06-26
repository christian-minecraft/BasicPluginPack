package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MsgCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public MsgCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be a player");
			return true;
		}
		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "Please specify a player.");
			return true;
		}
		if (args.length == 1) {
			sender.sendMessage(ChatColor.RED + "Please include a message.");
			return true;
		}

		Player recipient = Bukkit.getPlayer(args[0]);
		if (recipient != null) {
			String message = "";
			for (int i = 1; i < args.length; i++) {
				message += args[i] + " ";
			}
			sender.sendMessage(ChatColor.DARK_AQUA + "To " + ChatColor.AQUA + args[0] + ChatColor.DARK_AQUA + ": "
					+ ChatColor.GRAY + message);
			recipient.sendMessage(ChatColor.AQUA + ((Player) sender).getDisplayName() + ChatColor.DARK_AQUA
					+ " whispers: " + ChatColor.GRAY + message);

			// save player who last messaged recipient so /reply can work

			File configFile = new File(plugin.getDataFolder(), "player_data.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			config.set(recipient.getUniqueId().toString() + ".received_last_message_from",
					((Player) sender).getUniqueId().toString());
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Could not find player " + args[0]);
		}

		return true;
	}

}
