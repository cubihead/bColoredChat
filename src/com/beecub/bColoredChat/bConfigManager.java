package com.beecub.bColoredChat;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class bConfigManager {
	
	protected static bColoredChat bColoredChat;
    protected static Configuration conf;
    protected File confFile;
    
    static List<String> userColor = new LinkedList<String>();
    
	
	@SuppressWarnings("static-access")
	public bConfigManager(bColoredChat bColoredChat) {
    	this.bColoredChat = bColoredChat;

    	File f = new File(bColoredChat.getDataFolder(), "config.yml");
    	conf = null;

        if (f.exists())
        {
        	conf = new Configuration(f);
        	conf.load();
        	
        }
        else {
        	this.confFile = new File(bColoredChat.getDataFolder(), "config.yml");
            this.conf = new Configuration(confFile);
            conf.save();
        }
        
    }    
    
	void load() {
    	conf.load();
    }
	
	public void reload() {
		load();
	}
	
	static String getPlayerColor(Player player, String message) {
		if(com.beecub.bColoredChat.bColoredChat.Permissions.permission(player, "bColoredChat.useColor")) {
			String color;
			color = (String) conf.getProperty("playerColor." + player.getName() );
			if(color != null) {	
				if(bChat.Colors.contains(color)) {
					message = color + message;
				}
			}
		}
		return message;
	}
	
	static boolean setPlayerColor(Player player, String color) {
		if(com.beecub.bColoredChat.bColoredChat.Permissions.permission(player, "bColoredChat.useColor")) {
			if(bChat.Colors.contains(color)) {
				conf.setProperty("playerColor." + player.getName(), color);
				bChat.sendMessageToPlayer(player, "&6Sucessfully set color.");
				return true;
			}
			return false;
		} else {
			bChat.sendMessageToPlayer(player, "&6[bColoredChat] " + "You dont have permissions to this command.");
			return true;
		}
	}
	
	static void clearPlayerColor(Player player) {
		conf.removeProperty("playerColor." + player.getName());
		bChat.sendMessageToPlayer(player, "&6Sucessfully removed color.");
	}
}
