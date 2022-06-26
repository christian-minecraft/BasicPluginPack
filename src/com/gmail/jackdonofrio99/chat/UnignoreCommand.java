package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jackdonofrio99.playerutils.NameToUUID;

public class UnignoreCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public UnignoreCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (args.length == 0) {
			sender.sendMessage(ChatColor.DARK_AQUA + "Please specify a player to unignore.");
			return true;
		}
		String usernameToUnignore = args[0];
		String userToUnignoreUUIDString;
		try {
			userToUnignoreUUIDString = NameToUUID.getUUID(usernameToUnignore);
		} catch (IOException | NullPointerException e) {
			sender.sendMessage(ChatColor.DARK_AQUA + "Player cannot be found.");
			return true;
		}
		String senderUUID = ((Player) sender).getUniqueId().toString();

		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

		List<String> ignoredByList;
		String ignoredByPath = userToUnignoreUUIDString + ".ignoredBy";
		if (config.contains(ignoredByPath) && config.getStringList(ignoredByPath).contains(senderUUID)) {
			ignoredByList = config.getStringList(ignoredByPath);
		} else {
			sender.sendMessage(ChatColor.RED + "You are not ignoring this player.");
			return true;
		}
		ignoredByList.remove(senderUUID);
		config.set(ignoredByPath, ignoredByList);

		List<String> ignoring;
		String ignoringPath = senderUUID + ".ignoring";
		if (config.contains(ignoringPath) && config.getStringList(ignoringPath).contains(userToUnignoreUUIDString)) {
			ignoring = config.getStringList(ignoringPath);
		} else {
			sender.sendMessage(ChatColor.RED + "You are not ignoring this player.");
			return true;
		}
		ignoring.remove(userToUnignoreUUIDString);
		config.set(ignoringPath, ignoring);

		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(
				ChatColor.DARK_AQUA + "You will now see messages from " + ChatColor.AQUA + usernameToUnignore);
		return true;
	}

}
