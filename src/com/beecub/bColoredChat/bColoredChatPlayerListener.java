package com.beecub.bColoredChat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;


public class bColoredChatPlayerListener extends PlayerListener {
	@SuppressWarnings("unused")
	private final bColoredChat plugin;

	public bColoredChatPlayerListener(bColoredChat instance) {
		plugin = instance;
	}
	
	public void onPlayerChat(PlayerChatEvent event) {
		
	Player player = event.getPlayer();
		
		if (bColoredChat.permissions) {
			if(bColoredChat.Permissions.permission(player, "bColoredChat.color")) {
				String message = event.getMessage();
				message = bConfigManager.getPlayerColor(player, message);
				message = bChat.replaceColorCodes(message);
				event.setMessage(message);
			}
		}
		else {
			String message = event.getMessage();
			message = bConfigManager.getPlayerColor(player, message);
			message = bChat.replaceColorCodes(message);
			event.setMessage(message);
		}		
	}
}

