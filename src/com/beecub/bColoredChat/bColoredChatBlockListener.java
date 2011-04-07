package com.beecub.bColoredChat;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.SignChangeEvent;

public class bColoredChatBlockListener extends BlockListener {
	
	@SuppressWarnings("unused")
	private final bColoredChat plugin;

	public bColoredChatBlockListener(bColoredChat instance) {
		plugin = instance;
	}
	
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		String[] text = event.getLines();
	    int i = 0;
		if (bColoredChat.permissions) {
			if(bColoredChat.Permissions.permission(player, "bColoredChat.signColor")) {
			    for (String line : text)
			    {
			      line = line.replaceAll("&", "§");
			      line = line.replaceAll("§§", "&");
			      event.setLine(i, line);
			      i++;
			    }
			}
		}
		else {
		    for (String line : text)
		    {
		      line = line.replaceAll("&", "§");
		      line = line.replaceAll("§§", "&");
		      event.setLine(i, line);
		      i++;
		    }
		}
	}
}
