package com.llfrealms.LLFRewards.util;

import org.bukkit.command.*;

import com.llfrealms.LLFRewards.LLFRewards;

public class LLFRewardsCommands implements CommandExecutor 
{
	private LLFRewards plugin;
	public LLFRewardsCommands(LLFRewards plugin) {
		this.plugin = plugin;
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("llfsave"))
	    {
			plugin.saveConfig();
        	sender.sendMessage("Config saved");
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("llfload"))
	    {
			plugin.reloadConfig();
			plugin.connect(); //connect to database
	        plugin.tableCheck(); //check to make sure our table exists and if not creates it.
        	sender.sendMessage("Config reloaded");
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("llfadd"))
	    {
			plugin.reloadConfig();
			plugin.connect(); //connect to database
	        plugin.tableCheck(); //check to make sure our table exists and if not creates it.
        	sender.sendMessage("Config reloaded");
        	return true;
	    }
        return false;
    }

}