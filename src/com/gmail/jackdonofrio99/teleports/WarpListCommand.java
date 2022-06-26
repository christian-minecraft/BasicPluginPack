package com.gmail.jackdonofrio99.teleports;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpListCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public WarpListCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "warps.yml"));
		String message = String.join(ChatColor.DARK_AQUA + ", " + ChatColor.AQUA, config.getKeys(false));
		sender.sendMessage(ChatColor.DARK_AQUA + "Warps: " + ChatColor.AQUA + message);
		return true;
	}

}
