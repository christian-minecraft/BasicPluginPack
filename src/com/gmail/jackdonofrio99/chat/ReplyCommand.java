package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReplyCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public ReplyCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players can reply to messages.");
			return true;
		}
		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		String senderUUID = ((Player) sender).getUniqueId().toString();
		if (!config.contains(senderUUID + ".received_last_message_from")) {
			sender.sendMessage(ChatColor.RED + "You have no one to reply to.");
			return true;
		}

		String lastReceivedFrom;
		try {
			lastReceivedFrom = Bukkit
					.getPlayer(UUID.fromString(config.getString(senderUUID + ".received_last_message_from")))
					.getDisplayName();
		} catch (NullPointerException e) {
			sender.sendMessage(ChatColor.RED + "Player is offline.");
			return true;
		}

		String[] msgCmdArgs = new String[args.length + 1];
		msgCmdArgs[0] = lastReceivedFrom;
		for (int i = 1; i < args.length + 1; i++) {
			msgCmdArgs[i] = args[i - 1];
		}

		new MsgCommand(plugin).onCommand(sender, cmd, label, msgCmdArgs);

		return true;
	}

}
