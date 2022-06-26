package com.gmail.jackdonofrio99.teleports;

import java.io.File;
import java.io.IOException;
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

public class TeleportAcceptCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public TeleportAcceptCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can perform this command.");
			return true;
		}

		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		Player player = (Player) sender;
		String storedUUID = config.getString(player.getUniqueId() + ".last_tp_requester");
		if (!storedUUID.equals("") && Bukkit.getPlayer(UUID.fromString(storedUUID)) != null) {
			sender.sendMessage(
					ChatColor.AQUA + "Teleporting to " + ChatColor.DARK_AQUA + player.getName() + ChatColor.AQUA + ".");
			Bukkit.getPlayer(UUID.fromString(storedUUID)).teleport(player);
			config.set(player.getUniqueId() + ".last_tp_requester", "");
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			sender.sendMessage(ChatColor.RED + "No player to teleport to.");
		}
		return true;
	}
}
