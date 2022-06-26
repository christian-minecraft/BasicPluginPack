package com.gmail.jackdonofrio99;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jackdonofrio99.chat.ChatListener;
import com.gmail.jackdonofrio99.chat.DeathMessageListener;
import com.gmail.jackdonofrio99.chat.IgnoreCommand;
import com.gmail.jackdonofrio99.chat.IgnoreListCommand;
import com.gmail.jackdonofrio99.chat.MsgCommand;
import com.gmail.jackdonofrio99.chat.MutePlayerCommand;
import com.gmail.jackdonofrio99.chat.ReplyCommand;
import com.gmail.jackdonofrio99.chat.UnMutePlayerCommand;
import com.gmail.jackdonofrio99.chat.UnignoreCommand;
import com.gmail.jackdonofrio99.playerutils.JoinDateCommand;
import com.gmail.jackdonofrio99.playerutils.LastSeenCommand;
import com.gmail.jackdonofrio99.playerutils.ListPlayersOnlineCommand;
import com.gmail.jackdonofrio99.playerutils.NewPlayerJoinListener;
import com.gmail.jackdonofrio99.playerutils.PlayerLogOutListener;
import com.gmail.jackdonofrio99.teleports.HomeCommand;
import com.gmail.jackdonofrio99.teleports.SetHomeCommand;
import com.gmail.jackdonofrio99.teleports.SetSpawnCommand;
import com.gmail.jackdonofrio99.teleports.SetWarpCommand;
import com.gmail.jackdonofrio99.teleports.SpawnCommand;
import com.gmail.jackdonofrio99.teleports.TeleportAcceptCommand;
import com.gmail.jackdonofrio99.teleports.TeleportAskCommand;
import com.gmail.jackdonofrio99.teleports.TeleportDenyCommand;
import com.gmail.jackdonofrio99.teleports.WarpCommand;
import com.gmail.jackdonofrio99.teleports.WarpListCommand;
import com.gmail.jackdonofrio99.teleports.WhereHomeCommand;

public class BasicPluginPack extends JavaPlugin {

	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.getCommand("joindate").setExecutor(new JoinDateCommand());
		this.getCommand("lastseen").setExecutor(new LastSeenCommand(this));
		this.getCommand("list").setExecutor(new ListPlayersOnlineCommand());

		this.getCommand("sethome").setExecutor(new SetHomeCommand(this));
		this.getCommand("home").setExecutor(new HomeCommand(this));
		this.getCommand("wherehome").setExecutor(new WhereHomeCommand(this));
		this.getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
		this.getCommand("spawn").setExecutor(new SpawnCommand(this));
		this.getCommand("warp").setExecutor(new WarpCommand(this));
		this.getCommand("setwarp").setExecutor(new SetWarpCommand(this));
		this.getCommand("warplist").setExecutor(new WarpListCommand(this));
		this.getCommand("tpa").setExecutor(new TeleportAskCommand(this));
		this.getCommand("tpaccept").setExecutor(new TeleportAcceptCommand(this));
		this.getCommand("tpdeny").setExecutor(new TeleportDenyCommand(this));

		this.getCommand("mute").setExecutor(new MutePlayerCommand(this));
		this.getCommand("unmute").setExecutor(new UnMutePlayerCommand(this));
		this.getCommand("msg").setExecutor(new MsgCommand(this));
		this.getCommand("reply").setExecutor(new ReplyCommand(this));
		this.getCommand("ignore").setExecutor(new IgnoreCommand(this));
		this.getCommand("unignore").setExecutor(new UnignoreCommand(this));
		this.getCommand("ignorelist").setExecutor(new IgnoreListCommand(this));

		getServer().getPluginManager().registerEvents(new ChatListener(this), this);
		getServer().getPluginManager().registerEvents(new NewPlayerJoinListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerLogOutListener(this), this);
		getServer().getPluginManager().registerEvents(new DeathMessageListener(), this);
	}

	@Override
	public void onDisable() {

	}

}
