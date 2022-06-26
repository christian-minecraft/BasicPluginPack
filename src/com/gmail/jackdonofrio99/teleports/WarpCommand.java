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

public class WarpCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public WarpCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Proper usage: /warp (warp name)");
			return true;
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "warps.yml"));
		String warpName = args[0];
		if (config.contains(warpName + ".location")) {
			Location locationToTeleportTo = new Location(
					sender.getServer().getWorld(config.getString(warpName + ".location.world")),
					config.getDouble(warpName + ".location.x"), config.getDouble(warpName + ".location.y"),
					config.getDouble(warpName + ".location.z"));
			((Player) sender).teleport(locationToTeleportTo);
			sender.sendMessage(ChatColor.YELLOW + "Teleported to " + ChatColor.GOLD + warpName);
		} else {
			((Player) sender).sendMessage(ChatColor.RED + "Warp location does not exist");
		}
		return true;
	}
}
