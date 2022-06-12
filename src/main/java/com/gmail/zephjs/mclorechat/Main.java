package com.gmail.zephjs.mclorechat;


import java.io.File;
import java.io.IOException;


import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;


public class Main extends JavaPlugin {

    DiscordApi api = this.api;
    private File configFile;
    private FileConfiguration config;
    
    
    
    
    public FileConfiguration getConfig() {
    	
		   return this.config;
	
    }
    
    public static Main Plugin;
	

    @Override
    public void onEnable() {
    	
    	Plugin = this;
    	createConfig();
    	this.getCommand("sendlore").setExecutor(new showItemCommand());
    	
        // Connect to Discord
    	
        new DiscordApiBuilder()
                .setToken(this.getConfig().getString("bot-token")) // Set the token of the bot here
                .login() // Log the bot in
                .thenAccept(this::onConnectToDiscord) // Call #onConnectToDiscord(...) after a successful login
                .exceptionally(error -> {
                    // Log a warning when the login to Discord failed (wrong token?)
                    getLogger().warning("Failed to connect to Discord, did you add the bot token in the config? Disabling plugin!");
                    getPluginLoader().disablePlugin(this);
                    return null;
                });
    }

    
    
    
    private void createConfig() {
    	
    	configFile = new File(getDataFolder(), "config.yml");
    	
    	if (!configFile.exists()) {
    		configFile.getParentFile().mkdirs();
    		saveResource("config.yml", false);
    		
    	}
    	
    	config = new YamlConfiguration(); 
    	try {
    		config.load(configFile);
    		
    	} catch (IOException | InvalidConfigurationException e) {
    		e.printStackTrace();
    	}
    	
    	
    	if (this.getConfig().getString("bot-token") == null) {
    		this.getConfig().set("bot-token", "[Please put bot token here before starting the plugin]");
    		this.getConfig().set("designated-channel", "lore-channel");
    		this.getConfig().set("enable-loreadd", true);
    		this.saveConfig();
    	}
    	
    	
    	
    }
    
    
    
    
    
    @Override
    public void onDisable() {
        if (api != null) {
            // Make sure to disconnect the bot when the plugin gets disabled
            api.disconnect();
            api = null;
        }
    }

    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        // Log a message that the connection was successful and log the url that is needed to invite the bot
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());

        // Register listeners
       
        api.addMessageCreateListener(new inHandLore());
        api.addMessageCreateListener(new addLoreItem());
        api.addMessageCreateListener(new showHelp());
        
    }
}
