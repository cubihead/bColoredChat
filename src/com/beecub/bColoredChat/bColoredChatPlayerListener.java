package com.beecub.bColoredChat;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

import util.bChat;


public class bColoredChatPlayerListener extends PlayerListener {
	@SuppressWarnings("unused")
	private final bColoredChat plugin;

	public bColoredChatPlayerListener(bColoredChat instance) {
		plugin = instance;
	}
	
	public void onPlayerChat(PlayerChatEvent event) {
	
	Player player = event.getPlayer();
	String message = event.getMessage();
		
		if(bConfigManager.OPOnly) {
			if(player.isOp()) {
				colorMessage(event, player, message);
			}
		}
		else {
			if (bColoredChat.permissions) {
				if(bColoredChat.Permissions.permission(player, "bColoredChat.chatColor") || player.isOp()) {
					colorMessage(event, player, message);
				}
			}
			else {
				colorMessage(event, player, message);
			}
		}
	}
	
	// color up message
	public void colorMessage(PlayerChatEvent event, Player player, String message) {
		message = bConfigManager.getPlayerChatColor(player, message);
		message = bChat.replaceColorCodes(message);
		event.setMessage(message);
	}
}

