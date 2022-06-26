package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jackdonofrio99.playerutils.NameToUUID;

public class UnMutePlayerCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public UnMutePlayerCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Proper usage: /unmute (username)");
			return true;
		}
		String username = args[0];
		String playerUUID;
		try {
			playerUUID = NameToUUID.getUUID(username);
		} catch (IOException e) {
			e.printStackTrace();
			sender.sendMessage(ChatColor.RED + "Cannot find player " + username);
			return true;
		}
		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		config.set(playerUUID + ".isMuted", false);
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(ChatColor.AQUA + username + " has been unmuted.");
		return true;
	}

}
