package com.gmail.jackdonofrio99.teleports;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TeleportAskCommand implements CommandExecutor {

	private JavaPlugin plugin;

	public TeleportAskCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can perform this command.");
			return true;
		}

		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Please specify a player to teleport to.");
			return true;
		}
		String playerName = args[0];
		Player requestedPlayer = Bukkit.getPlayer(playerName);
		if (requestedPlayer != null) {
			File configFile = new File(plugin.getDataFolder(), "player_data.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
			config.set(requestedPlayer.getUniqueId().toString() + ".last_tp_requester",
					((Player) sender).getUniqueId().toString());
			try {
				config.save(configFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			requestedPlayer.sendMessage(ChatColor.DARK_AQUA + ((Player) sender).getName() + ChatColor.AQUA
					+ " would like to teleport to you! Type /tpaccept or /tpdeny to respond!");
			return true;

		} else {
			sender.sendMessage(ChatColor.RED + "Player " + playerName + " is not currently online.");
			return true;
		}
	}
}
