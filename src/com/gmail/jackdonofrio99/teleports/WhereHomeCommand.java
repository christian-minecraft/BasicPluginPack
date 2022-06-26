package com.gmail.jackdonofrio99.teleports;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WhereHomeCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public WhereHomeCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		FileConfiguration config = YamlConfiguration
				.loadConfiguration(new File(plugin.getDataFolder(), "player_data.yml"));
		Player player = (Player) sender;
		String playerUUIDString = ((Player) sender).getUniqueId().toString();
		if (config.contains(player.getUniqueId().toString() + ".home")) {
			String world = config.getString(playerUUIDString + ".home.location.world");
			int x, y, z;
			x = (int) config.getDouble(playerUUIDString + ".home.location.x");
			y = (int) config.getDouble(playerUUIDString + ".home.location.y");
			z = (int) config.getDouble(playerUUIDString + ".home.location.z");
			sender.sendMessage(ChatColor.DARK_AQUA + "Your home is set in " + ChatColor.AQUA + world
					+ ChatColor.DARK_AQUA + " at x: " + ChatColor.AQUA + x + ChatColor.DARK_AQUA + " y: "
					+ ChatColor.AQUA + y + ChatColor.DARK_AQUA + " z: " + ChatColor.AQUA + z);
		} else {
			sender.sendMessage(ChatColor.DARK_AQUA
					+ "Your home has not been set yet. Use /sethome at your base to teleport back at any time.");
		}
		return true;
	}
}
