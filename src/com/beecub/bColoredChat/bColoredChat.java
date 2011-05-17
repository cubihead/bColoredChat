package com.beecub.bColoredChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import util.bChat;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.util.logging.Logger;


public class bColoredChat extends JavaPlugin {
	private final bColoredChatPlayerListener playerListener = new bColoredChatPlayerListener(this);
	private final bColoredChatBlockListener signListener = new bColoredChatBlockListener(this);
	public static Logger log = Logger.getLogger("Minecraft");
	public static PluginDescriptionFile pdfFile;
	public static PermissionHandler Permissions;
	public static boolean permissions = false;

	@SuppressWarnings("unused")
	public void onEnable() {

		pdfFile = this.getDescription();
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Lowest, this);
	    pm.registerEvent(Event.Type.SIGN_CHANGE, signListener, Event.Priority.Lowest, this);
	    
		bConfigManager bConfigManager = new bConfigManager(this);
		bConfigManager.load();
		bChat bChat = new bChat(this.getServer());
		
		if(setupPermissions()){
		}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	public void onDisable() {
		log.info("[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " disabled!");
	}
		
	
	// setup permissions
	private boolean setupPermissions() {
		Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");
		if (bColoredChat.Permissions == null) {
			if (test != null) {
				bColoredChat.Permissions = ((Permissions)test).getHandler();
				log.info("[bColoredChat] Permission system found");
				permissions = true;
				return true;
			}
			else {
				//log.info("[bColoredChat] Permission system not detected, plugin disabled");
				//this.getServer().getPluginManager().disablePlugin(this);
				permissions = false;
				return false;
			}
		}
		return false;
	}
	
	// onCommand
	@Override
	public boolean onCommand(CommandSender sender, Command c, String commandLabel, String[] args) {
		
		String command = c.getName().toLowerCase();
		String parameter;
		String setplayer;
		log.info("command: " + command);
		if(sender instanceof Player) {
			Player player = (Player) sender;
		    // use Color
		    if(command.equals("usecolor")) {
		    	if(args.length == 1) {
		    		parameter = args[0];
					if(bConfigManager.setMyPlayerChatColor(player, parameter)) {
						return true;
					}
		    	}
		    }
		    // set Color
		    else if(command.equals("setcolor")) {
		        if(args.length == 1) {
                    parameter = args[0];
                    if(bConfigManager.setOtherPlayerChatColor(player, player.getName(), parameter)) {
                        return true;
                    }
                }
		        else if(args.length == 2) {
		    		setplayer = args[0];
		    		parameter = args[1];
					if(bConfigManager.setOtherPlayerChatColor(player, setplayer, parameter)) {
						return true;
					}
		    	}
		    }
		    // clear Color
		    else if(command.equals("clearcolor")) {
		        log.info("in here 1");
	               bChat.showColors(player);
		    	if(args.length == 0) {
		    		bConfigManager.clearPlayerChatColor(player, player.getName());
		    		return true;
		    	}
		    	else if(args.length == 1) {
		    		parameter = args[0];
		    		bConfigManager.clearPlayerChatColor(player, parameter);
		    		return true;
		    	}		    	
		    }
		    // random Color
            else if(command.equals("randomcolor")) {
                String message = "";
                boolean on = false;
                if (bColoredChat.permissions) {
                    if(Permissions.permission((Player) sender, "bColoredChat.randomColor") || sender.isOp()) {
                        on = true;
                    } else {
                        bChat.sendMessageToCommandSender(sender, "&6[bColoredChat] " + "You dont have permissions to this command.");
                        return true;
                    }
                }
                else {
                    on = true;
                }
                
                // do it?
                if( on ) {
                    for(int i = 0; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    message = bChat.replaceRandom(message);
                    player = (Player) sender;
                    player.chat(message);
                    return true;
                }
                return false;
            }
		    // rainbow Color
            else if(command.equals("rainbowcolor")) {
                String message = "";
                boolean on = false;
                if (bColoredChat.permissions) {
                    if(Permissions.permission((Player) sender, "bColoredChat.rainbowColor") || sender.isOp()) {
                        on = true;
                    } else {
                        bChat.sendMessageToCommandSender(sender, "&6[bColoredChat] " + "You dont have permissions to this command.");
                        return true;
                    }
                }
                else {
                    on = true;
                }
                
                // do it?
                if( on ) {
                    for(int i = 0; i < args.length; i++) {
                        message += args[i] + " ";
                    }
                    message = bChat.replaceRainbow(message);
                    player = (Player) sender;
                    player.chat(message);
                    return true;
                }
                return false;
            }
		    // show colors
            else if(command.equals("showcolors")) {
                bChat.showColors(player);
                return true;
            }
		    return false;
		} else {
			bChat.sendMessageToServer("&6Cannot use this from console");
			return true;
		}
	}
}
