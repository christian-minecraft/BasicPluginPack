package com.gmail.jackdonofrio99.teleports;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HomeCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public HomeCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(new File(plugin.getDataFolder(), "player_data.yml"));
		Player player = (Player) sender;
		String playerUUID = player.getUniqueId().toString();
		if (config.contains(player.getUniqueId().toString() + ".home")) {
			Location locationToTeleportTo = new Location(
					sender.getServer().getWorld(config.getString(playerUUID + ".home.location.world")),
					config.getDouble(playerUUID + ".home.location.x"),
					config.getDouble(playerUUID + ".home.location.y"),
					config.getDouble(playerUUID + ".home.location.z"));
			player.teleport(locationToTeleportTo);
			sender.sendMessage(ChatColor.AQUA + "Teleported home.");
		} else {
			sender.sendMessage(ChatColor.DARK_AQUA
					+ "Your home has not been set yet. Use /sethome at your base to teleport back at any time.");
		}
		return true;
	}
}
