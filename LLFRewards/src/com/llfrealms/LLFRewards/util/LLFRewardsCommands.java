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
			///<command> {name} {powerlevel} {commands} {requirements yes/no} {req1} {req2} {req3} {req4}
			String reward = args[0];
			String plvl = args[1];
			String commands = args[2];
			String req = args[3];
        	return true;
	    }
        return false;
    }

}