package com.gmail.jackdonofrio99.teleports;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public SpawnCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		FileConfiguration config = plugin.getConfig();

		if (config.contains("server.spawn.location")) {
			Location locationToTeleportTo = new Location(
					sender.getServer().getWorld(config.getString("server.spawn.location.world")),
					config.getDouble("server.spawn.location.x"), config.getDouble("server.spawn.location.y"),
					config.getDouble("server.spawn.location.z"));
			((Player) sender).teleport(locationToTeleportTo);

		} else {
			((Player) sender).teleport(plugin.getServer().getWorld("world").getSpawnLocation());
		}
		sender.sendMessage(ChatColor.AQUA + "Teleported to spawn.");

		return true;
	}
}
