package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.util.Collections;
import java.util.List;
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

public class IgnoreListCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public IgnoreListCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(new File(plugin.getDataFolder(), "player_data.yml"));
		String playerUUID = ((Player) sender).getUniqueId().toString();
		String message = ChatColor.DARK_AQUA + "";
		if (config.contains(playerUUID + ".ignoring")) {
			List<String> ignoreList = config.getStringList(playerUUID + ".ignoring");
			ignoreList.replaceAll(uuid -> Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
			ignoreList.removeAll(Collections.singleton(null));
			if (ignoreList.size() != 0)
				message += "You're ignoring: " + ChatColor.AQUA + String.join(", ", ignoreList);
			else
				message += "You are not ignoring anyone.";
		} else {
			message += "You are not ignoring anyone.";
		}
		sender.sendMessage(message);
		return true;
	}

}
