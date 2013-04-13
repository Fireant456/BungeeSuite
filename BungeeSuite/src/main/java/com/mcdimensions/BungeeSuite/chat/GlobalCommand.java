package com.mcdimensions.BungeeSuite.chat;

import com.mcdimensions.BungeeSuite.BungeeSuite;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class GlobalCommand extends Command {

	BungeeSuite plugin;
	
	public GlobalCommand(BungeeSuite bungeeSuite) {
		super(bungeeSuite.global, null, bungeeSuite.g);
		plugin = bungeeSuite;
	}
	
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if(!(arg0.hasPermission("BungeeSuite.global") || arg0.hasPermission("BungeeSuite.mod"))){
			arg0.sendMessage(plugin.NO_PERMISSION);
			return;
		}
		
		if(arg1.length==0){
			ChatChannel cc  = plugin.getChannel("Global");
			ChatPlayer cp = plugin.getChatPlayer(arg0.getName());
			if(plugin.globalToggleable){
				if(!cp.getCurrent().equals(cc)){
					cp.setCurrent(cc);
				}
			}
			return;
		}
		
		String message = "";
		for(String data: arg1){
			message+= data+" ";
		}
		
		ChatChannel cc  = plugin.getChannel("Global");
		ChatPlayer cp = plugin.getChatPlayer(arg0.getName());
		cc.sendGlobalMessage(cp, message);
		if(plugin.globalToggleable){
			if(!cp.getCurrent().equals(cc)){
				cp.setCurrent(cc);
			}
		}
	}

}