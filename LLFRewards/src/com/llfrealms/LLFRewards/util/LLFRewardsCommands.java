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
			// /<command> {rewardName} {powerlevel} {commands} r:{requirements yes/no} r1:{req1} r2:{req2} r3:{req3} r4:{req4}
			String reward = args[0], powerlvl = args[1], commands = args[2], 
					req = args[3], none = "none";
			String req1 = none, req2 = none, req3 = none, req4 = none;
			String[] requirements, requirement1, requirement2, requirement3, requirement4;
			requirements = req.split(":");
			req = requirements[1];
			if(req.equalsIgnoreCase("yes") || req.equalsIgnoreCase("true") )
			{
				if(args.length == 8)
				{
					requirement1 = args[4].split(":");
					requirement2 = args[5].split(":");
					requirement3 = args[6].split(":");
					requirement4 = args[7].split(":");
					req1 = requirement1[1];
					req2 = requirement2[1];
					req3 = requirement3[1];
					req4 = requirement4[1];
				}
				else if(args.length < 8)
				{
					Utilities.sendMessage(sender, "&4Not enough arguments");
				}else if(args.length > 8)
				{
					Utilities.sendMessage(sender, "&4Too many arguments");
				}
			}
			else if(req.equalsIgnoreCase("no") || req.equalsIgnoreCase("true"))
			{
				
			}
        	return true;
	    }
        return false;
    }

}