package com.gmail.zephjs.mclorechat;


import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.javacord.api.entity.Nameable;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class addLoreItem implements MessageCreateListener {
	
	
	public static String colorize(final String msg) {
		
		
	       return ChatColor.translateAlternateColorCodes('&', msg);
	       
	   }
	

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        
    	
    	
    	if (Main.Plugin.getConfig().getBoolean("enable-loreadd") == true && event.getMessageContent().split(" ")[0].equalsIgnoreCase("!LoreAdd")) {
    	
    	if (!event.getChannel().asServerTextChannel().map(Nameable::getName).map(str -> str.equals(Main.Plugin.getConfig()
        		.getString("designated-channel"))).orElse(false)) {
    		event.getChannel().sendMessage("You must be in the **#" + Main.Plugin.getConfig()
    		.getString("designated-channel") + "** channel to use this bot.");
    		return;
    	}
    	
    	
    	if (event.getMessageContent().split(" ").length < 3) {
    		if (event.getMessageContent().split(" ")[0].equalsIgnoreCase("!LoreAdd")) {
    			event.getChannel().sendMessage("Syntax is:" + System.lineSeparator() + "```!LoreAdd [Playername] [ItemName]" + System.lineSeparator() 
    			+ "Name:[Name] (Optional, names the item)" + System.lineSeparator() + ">&[color Code] (Optional, inserts whatever's after '>' in front of every line)" + System.lineSeparator() + "[Lore]```");
    			return;
    		}
    	}
    	
    	
        if (!event.getMessageContent().split(" ")[0].equalsIgnoreCase("!LoreAdd")) {
        	
            return;
        }
        
     
        
    
        Player player = Bukkit.getPlayer(event.getMessageContent().split(" ")[1].split("\n")[0]);
        
        
        
      
        if (player == null) {
        	event.getChannel().sendMessage("Couldn't find a player called **" + 
        event.getMessageContent().split(" ")[1].split("\n")[0] + "**.");
        	return;
        }
        
        if (Material.getMaterial(event.getMessageContent().split(" ")[2].split("\n")[0].toUpperCase()) == null) {
			
     			event.getChannel().sendMessage(event.getMessageContent().split(" ")[2].split("\n")[0].toUpperCase() + " is not a valid Spigot item enumerator.");
     			return;
     			
     		}
        
        
      
        if (event.getMessageContent().split(" ")[0].equalsIgnoreCase("!LoreAdd")) {
        	
        	if (player.getInventory().firstEmpty() == -1) {
        		event.getChannel().sendMessage(player.getName() + " has no inventory space for the item!");
        		return;
        	}
        	
        	
        }
        
       
        ItemStack newItem = new ItemStack(Material.getMaterial(event.getMessageContent().split(" ")[2].split("\n")[0].toUpperCase()));
        ItemMeta newItemMeta = newItem.getItemMeta();
        ArrayList<String> currentColor = new ArrayList<String>();
        currentColor.add("&f");
        ArrayList<String> newLore = new ArrayList<String>();
        String message = event.getMessageContent();
    	
        ArrayList<String> splitMessage = new ArrayList<String>(Arrays.asList(message.split("\n")));
       
        
        for(int i = 0; i < splitMessage.size(); i++) {
        	
        	
        	if (i == 0) {
        		continue;
        	}
        	
        	
        	
        	if (splitMessage.get(i).split(":")[0].equalsIgnoreCase("Name")) {
        		newItemMeta.setDisplayName(colorize(splitMessage.get(i).split(":")[1]));
        		continue;
        	} else if (splitMessage.get(i).startsWith(">")) {
        		currentColor.clear();
        		currentColor.add(splitMessage.get(i).replaceFirst(">", ""));
        		continue;
        	}  else {
        		
        		
        		
        		newLore.add(colorize(currentColor.get(0) + splitMessage.get(i)));
        		
        	}
        	
        	
        }
        	
       
      
        newItemMeta.setLore(newLore);
        newItem.setItemMeta(newItemMeta);
        event.getChannel().sendMessage("Lore item added!");
        player.getInventory().addItem(newItem);
        
        
        	
         
    	} else if (event.getMessageContent().split(" ")[0].equalsIgnoreCase("!LoreAdd")) {
    		
    		event.getChannel().sendMessage("This command has been disabled in the config.");
    		return;
    		
    		
    		
    	}
    }
}
