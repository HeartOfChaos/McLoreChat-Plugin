package com.gmail.zephjs.mclorechat;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class showHelp implements MessageCreateListener {

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		
		if (event.getMessageContent().equalsIgnoreCase("!LoreHelp")) {
			event.getChannel().sendMessage("The command !LoreAdd inserts an item with the lore into the named player's inventory, while !LoreHand places the lore onto the item that the player is holding. The item name is only required if using !LoreAdd. !LoreAdd can be disabled in the config if you'd like to restrict users to only loring existing items." + System.lineSeparator() + "```!Lore[Add/Hand] [Playername] [ItemName]" + System.lineSeparator() 
			+ "Name:[Name] (Optional, names the item)" + System.lineSeparator() + ">&[color Code] (Optional, inserts whatever's after it in front of every line)" + System.lineSeparator() + "[Lore]```");
		}
		
	}
	
	

}
