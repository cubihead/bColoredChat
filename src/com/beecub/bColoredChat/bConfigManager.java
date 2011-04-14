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
    static boolean OPOnly;
    
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
            conf.setProperty("Options.OPOnly", false);
            conf.save();
        }
        
    }    
    
	void load() {
    	conf.load();
    	OPOnly = conf.getBoolean("Options.OPOnly", false);
    }
	
	public void reload() {
		load();
	}
	
	@SuppressWarnings("static-access")
	static String getPlayerColor(Player sender, String message) {
		boolean on = false;
		if (bColoredChat.permissions) {
			if(com.beecub.bColoredChat.bColoredChat.Permissions.permission(sender, "bColoredChat.useColor") || sender.isOp()) {
				on = true;
			}
		}
		else {
			on = true;
		}
		
		// do it?
		if( on ) {
			String color;
			color = (String) conf.getProperty("playerColor." + sender.getName() );
			if(color != null) {	
				if(bChat.Colors.contains(color)) {
					message = color + message;
				}
			}	
		}	
		return message;
	}
	
	@SuppressWarnings("static-access")
	static boolean setPlayerColor(Player sender, String color) {
		boolean on = false;
		if (bColoredChat.permissions) {
			if(com.beecub.bColoredChat.bColoredChat.Permissions.permission(sender, "bColoredChat.useColor") || sender.isOp()) {
				on = true;
			} else {
				bChat.sendMessageToPlayer(sender, "&6[bColoredChat] " + "You dont have permissions to this command.");
				return true;
			}
		}
		else {
			on = true;
		}
		
		// do it?
		if( on ) {
			if(bChat.Colors.contains(color)) {
				conf.setProperty("playerColor." + sender.getName(), color);
				bChat.sendMessageToPlayer(sender, "&6Sucessfully set color.");
				return true;
			}
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("static-access")
	static boolean setOtherPlayerColor(Player sender, String player, String color) {
		boolean on = false;
		if (bColoredChat.permissions) {
			if(com.beecub.bColoredChat.bColoredChat.Permissions.permission(sender, "bColoredChat.setColor") || sender.isOp()) {
				on = true;
			} else {
				bChat.sendMessageToPlayer(sender, "&6[bColoredChat] " + "You dont have permissions to this command.");
				return true;
			}
		}
		else {
			on = true;
		}
		
		// do it?
		if( on ) {
			if(bChat.Colors.contains(color)) {
				conf.setProperty("playerColor." + sender.getName(), color);
				bChat.sendMessageToPlayer(sender, "&6Sucessfully set color.");
				return true;
			}
			return false;
		}
		return true;
	}
	
	static void clearPlayerColor(Player sender) {
		conf.removeProperty("playerColor." + sender.getName());
		bChat.sendMessageToPlayer(sender, "&6Sucessfully removed color.");
	}
}
