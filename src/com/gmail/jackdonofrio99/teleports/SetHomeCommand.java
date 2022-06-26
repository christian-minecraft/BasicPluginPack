package com.gmail.jackdonofrio99.teleports;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetHomeCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public SetHomeCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		String playerUUID = ((Player) sender).getUniqueId().toString();
		Location l = ((Player) sender).getLocation();
		config.set(playerUUID + ".home.location.world", l.getWorld().getName());
		config.set(playerUUID + ".home.location.x", l.getX());
		config.set(playerUUID + ".home.location.y", l.getY());
		config.set(playerUUID + ".home.location.z", l.getZ());
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(ChatColor.AQUA + "Home set.");
		return true;
	}

}
