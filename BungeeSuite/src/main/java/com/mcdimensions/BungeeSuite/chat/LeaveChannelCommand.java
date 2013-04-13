package com.mcdimensions.BungeeSuite.chat;

import java.sql.SQLException;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class LeaveChannelCommand extends Command {

	BungeeSuite plugin;

	public LeaveChannelCommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.leaveChannel, null, bungeeSuite.leave);
		this.plugin = bungeeSuite;
	}

	@Override
	public void execute(CommandSender sender, String[] arg1) {
		if (arg1.length == 0) {
			ChatPlayer cp = plugin.getChatPlayer(sender.getName());
			ChatChannel cc = cp.getCurrent();
			if (cc.isServerChannel()) {
				cp.sendMessage(plugin.CHANNEL_NOT_LEAVE_SERVER);
				return;
			} else if (cc.getOwner().equalsIgnoreCase(cp.getName())) {
				String cmsg = plugin.CHANNEL_NOT_LEAVE_OWNER;
				cmsg = cmsg.replace("%channel", cc.getName());
				cp.sendMessage(cmsg);
			} else {
				cc.removeMember(cp.getName());
				cp.removeChannel(cc.getName());
				if (plugin.globalDefault) {
					cp.setCurrent(plugin.getChannel("Global"));
					return;
				} else {
					ChatChannel newc = plugin.getChannel(cp.getPlayer().getServer().getInfo().getName());
					newc.addMember(cp);
					cp.setCurrent(newc);
					return;
				}
			}
		} else if (arg1.length == 1) {
			String channelName = arg1[0];
			try {
				if (plugin.getUtilities().chatChannelExists(channelName)) {
					ChatChannel cc = plugin.getChannel(channelName);
					ChatPlayer cp = plugin.getChatPlayer(sender.getName());
					if (cc.isServerChannel()) {
						cp.sendMessage(plugin.CHANNEL_NOT_LEAVE_SERVER);
						return;
					}
					ChatChannel cur = cp.getCurrent();
					if (cc.equals(cur)) {
						cc.removeMember(cp.getName());
						cp.removeChannel(cc.getName());
						ChatChannel newc = plugin.getChannel(cp.getPlayer().getServer().getInfo().getName());
						newc.addMember(cp);
						cp.setCurrent(newc);
					} else {
						cc.removeMember(cp.getName());
						cp.removeChannel(cc.getName());
					}
				} else {
					sender.sendMessage(plugin.CHANNEL_NOT_EXIST);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			sender.sendMessage(ChatColor.RED + "/" + plugin.leaveChannel + " [channel]");
			return;
		}

	}

}
