package com.gmail.jackdonofrio99.playerutils;

import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListPlayersOnlineCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String message = ChatColor.DARK_AQUA + "Players Online: " + ChatColor.AQUA
				+ String.join(ChatColor.DARK_AQUA + ", " + ChatColor.AQUA,
						Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toList()));
		sender.sendMessage(message);
		return true;
	}

}
