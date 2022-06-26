package com.gmail.jackdonofrio99.playerutils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerLogOutListener implements Listener {

	private JavaPlugin plugin;

	public PlayerLogOutListener(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerLogOut(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		File configFile = new File(plugin.getDataFolder(), "player_data.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		Date currentTime = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy HH:mm", Locale.US);
		config.set(player.getUniqueId().toString() + ".lastseen", format.format(currentTime) + " EST");
		try {
			config.save(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
