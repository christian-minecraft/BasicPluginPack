package com.gmail.jackdonofrio99.teleports;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetSpawnCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public SetSpawnCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Location l = ((Player) sender).getLocation();
		FileConfiguration config = plugin.getConfig();
		config.set("server.spawn.location.world", l.getWorld().getName());
		config.set("server.spawn.location.x", l.getX());
		config.set("server.spawn.location.y", l.getY());
		config.set("server.spawn.location.z", l.getZ());
		plugin.saveConfig();
		sender.sendMessage(ChatColor.AQUA + "Spawn point set.");
		return true;
	}

}
