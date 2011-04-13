package com.beecub.bColoredChat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
		log.info( "[" + pdfFile.getName() + "]" + " version " + pdfFile.getVersion() + " is enabled!" );
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
	    // use Color
		if(sender instanceof Player) {
			Player player = (Player) sender;
		    if((command.equals("usecolor"))) {
		    	if(args.length > 0) {
		    		parameter = args[0];
					if(bConfigManager.setPlayerColor(player, parameter)) {
						return true;
					}
		    	}
		    }
		    // clear Color
		    else if(command.equals("clearcolor")) {
		    	bConfigManager.clearPlayerColor(player);
		    }
		} else {
			bChat.sendMessageToServer("&6Cannot use this from console");
		}
		return false;
	}
}
