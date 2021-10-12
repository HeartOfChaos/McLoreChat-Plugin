package com.gmail.zephjs.mclorechat;


import java.util.Collection;
import java.util.List;



import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;



public class showItemCommand implements CommandExecutor {

	 
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		
		if (sender.hasPermission("loreitems.send")) {
			
			
			
			 if (!(sender instanceof Player)) {

		            sender.sendMessage("This command cannot be run from the console.");
		            return false;
		        }
			
			Player player = Bukkit.getPlayer(sender.getName());
			ItemStack heldItem = player.getInventory().getItemInMainHand();
			
			   if (heldItem.getType().equals(Material.AIR)) {
				
				   sender.sendMessage("You must be holding an item to send its lore to the lore channel!");
				   return false;
				}
			
			ItemMeta heldItemMeta = heldItem.getItemMeta();
			
			String heldItemName = heldItemMeta.getDisplayName();
			StringBuilder mb = new StringBuilder();
			DiscordApi api = Main.Plugin.api;
			
			
			
			
			
			
			mb.append("**" + player.getName() + " has run the /sendlore command!**" + System.lineSeparator());
			mb.append("Name:" + heldItemName.replace('§', '&')  + System.lineSeparator());
			
			
			if (heldItemMeta.hasLore()) {
				List<String> heldItemLore = heldItemMeta.getLore();
				if (!heldItemLore.isEmpty()) {
					for(int i = 0; i < heldItemLore.size(); i++) {
				
						mb.append(heldItemLore.get(i).replace('§', '&') + System.lineSeparator());
				
					}
				}
			}
			
			
			Collection<TextChannel> channels = api.getTextChannelsByName(Main.Plugin.getConfig().getString("designated-channel"));
			
			
			for (TextChannel e: channels) {
				e.sendMessage(mb.toString());
			}
				
			
			return true;
			
		}
		
		
		return false;
	}
	
	
	
	
	
}
