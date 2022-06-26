package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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


public class IgnoreCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public IgnoreCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;

		if (args.length == 0) {
			sender.sendMessage(ChatColor.DARK_AQUA + "Please specify a player to ignore.");
			return true;
		}
		String usernameToIgnore = args[0];
		String userToIgnoreUUIDString;
		try {
			userToIgnoreUUIDString = NameToUUID.getUUID(usernameToIgnore);
		} catch (IOException | NullPointerException e) {
			sender.sendMessage(ChatColor.DARK_AQUA + "Player cannot be found.");
			return true;
		}
		String senderUUID = ((Player) sender).getUniqueId().toString();
		if (senderUUID.equals(userToIgnoreUUIDString)) {
			sender.sendMessage(ChatColor.RED + "You can't ignore yourself.");
			return true;
		}

		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

		List<String> ignoredByList;
		String ignoredByPath = userToIgnoreUUIDString + ".ignoredBy";
		if (config.contains(ignoredByPath)) {
			ignoredByList = config.getStringList(ignoredByPath);
			if (ignoredByList.contains(senderUUID)) {
				sender.sendMessage(ChatColor.RED + "You are already ignoring this player.");
				return true;
			}
		} else {
			ignoredByList = new ArrayList<>();
		}
		ignoredByList.add(senderUUID);
		config.set(ignoredByPath, ignoredByList);

		List<String> ignoring;
		String ignoringPath = senderUUID + ".ignoring";
		if (config.contains(ignoringPath)) {
			ignoring = config.getStringList(ignoringPath);
			if (ignoring.contains(userToIgnoreUUIDString)) {
				sender.sendMessage(ChatColor.RED + "You are already ignoring this player.");
				return true;
			}
		} else {
			ignoring = new ArrayList<>();
		}
		ignoring.add(userToIgnoreUUIDString);
		config.set(ignoringPath, ignoring);

		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		sender.sendMessage(
				ChatColor.DARK_AQUA + "You will no longer see messages from " + ChatColor.AQUA + usernameToIgnore);
		return true;
	}

}
