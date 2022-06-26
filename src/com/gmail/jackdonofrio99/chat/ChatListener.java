package com.gmail.jackdonofrio99.chat;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatListener implements Listener {

	private JavaPlugin plugin;

	public ChatListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Method for handling chat messages as they come in.
	 * 
	 * @param event
	 */
	@EventHandler
	public void onChatMessageSend(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (checkIfMuted(player)) {
			player.sendMessage(ChatColor.RED + "Sorry, you are muted.");
			event.setCancelled(true);
			return;
		}
		for (String ignoringPlayerUUID : getIgnoredByList(player)) {
			try {
				Player p = Bukkit.getPlayer(UUID.fromString(ignoringPlayerUUID));
				event.getRecipients().remove(p);
			} catch (Exception e) {
			}
		}
		String message = event.getMessage();
		message = filterMessage(message);
		event.setFormat(ChatColor.GREEN + player.getDisplayName() + ChatColor.WHITE + ": " + message);
	}

	public List<String> getIgnoredByList(Player player) {
		try {
			FileConfiguration config = YamlConfiguration
					.loadConfiguration(new File(plugin.getDataFolder(), "player_data.yml"));
			return config.getStringList(player.getUniqueId().toString() + ".ignoredBy");
		} catch (Exception e) {
			return Arrays.asList();
		}
	}

	/**
	 * Check if a given player is muted in chat.
	 * 
	 * @param player
	 * @return whether player is set as muted in config.yml or not.
	 */
	public boolean checkIfMuted(Player player) {
		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		String playerUUID = player.getUniqueId().toString();
		String isMutedPath = playerUUID + ".isMuted";
		if (config.contains(isMutedPath) && config.getBoolean(isMutedPath)) {
			return true;
		}
		return false;
	}

	/**
	 * Filters inappropriate words from chat messages. Words deemed bad can be
	 * configured in badwords.txt, but I'm planning on changing it to a yml config
	 * file to allow plugin users an easy way to set bad words.
	 * 
	 * @param message
	 * @return message with inappropriate words filtered out
	 */
	public String filterMessage(String message) {
		InputStream is = plugin.getResource("badwords.txt");
		Scanner scanner = new Scanner(is);
		while (scanner.hasNextLine()) {
			String badWord = scanner.nextLine().trim();
			if (message.toLowerCase().contains(badWord.toLowerCase())) {
				message = message.replaceAll("(?i)" + badWord,
						new String(new char[badWord.length()]).replace('\0', '*'));
			}
		}
		scanner.close();
		return message;

	}

}
