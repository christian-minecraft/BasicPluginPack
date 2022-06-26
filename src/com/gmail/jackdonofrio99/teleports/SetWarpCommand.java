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

public class SetWarpCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public SetWarpCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player))
			return true;
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Proper usage: /setwarp (warp name)");
			return true;
		}
		String warpName = args[0];
		File configFile = new File(plugin.getDataFolder(), "warps.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		Location l = ((Player) sender).getLocation();
		config.set(warpName + ".location.world", l.getWorld().getName());
		config.set(warpName + ".location.x", l.getX());
		config.set(warpName + ".location.y", l.getY());
		config.set(warpName + ".location.z", l.getZ());
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sender.sendMessage(ChatColor.AQUA + "New warp set.");
		return true;
	}

}
