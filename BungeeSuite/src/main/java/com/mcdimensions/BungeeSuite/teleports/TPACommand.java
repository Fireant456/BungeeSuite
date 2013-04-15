package com.mcdimensions.BungeeSuite.teleports;

import com.mcdimensions.BungeeSuite.BungeeSuite;
import com.mcdimensions.BungeeSuite.utilities.CommandUtil;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TPACommand extends Command {

	BungeeSuite plugin;
	private static final String[] PERMISSION_NODES = { "bungeesuite.teleport.tpa", "bungeesuite.teleport.*",
		"bungeesuite.mod", "bungeesuite.admin", "bungeesuite.*" };

	public TPACommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.tpa);
		plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!CommandUtil.hasPermission(sender, PERMISSION_NODES)) {
			sender.sendMessage(plugin.NO_PERMISSION);
			return;
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/" + plugin.tpa + " (playername)");
			return;
		}
		
		ProxiedPlayer player = (ProxiedPlayer) sender;
		
		if (plugin.blockedTeleports.contains(player.getServer().getInfo().getName())) {
			sender.sendMessage(ChatColor.RED + "This server does not have teleports enabled");
			return;
		}
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.RED + "/tpa (PlayerName)");
			return;
		}
		
		ProxiedPlayer targetPlayer = plugin.getUtilities().getClosestPlayer(args[0]);
		
		if (targetPlayer == null) {
			sender.sendMessage(ChatColor.RED + "That player is not online!");
			return;
		}
		
		plugin.getUtilities().sendTpRequest(player, targetPlayer);
		sender.sendMessage(ChatColor.DARK_GREEN + "TP request sent!");
	}

}
