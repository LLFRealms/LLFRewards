package com.llfrealms.LLFRewards.util;

import org.bukkit.command.*;

import com.llfrealms.LLFRewards.LLFRewards;
import com.llfrealms.LLFRewards.util.Utilities;

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
        	sender.sendMessage("Config reloaded");
        	return true;
	    }
		if(cmd.getName().equalsIgnoreCase("llfadd"))
	    {
			// /<command> {rewardName} {powerlevel} r:{requirements yes/no} r1:{req1} r2:{req2} r3:{req3} r4:{req4} {commands}
			if(args.length > 0)
			{
				String reward = args[0], powerlvl = args[1], 
						req = args[2], none = "none";
				Integer plvl = Integer.parseInt(powerlvl);
				String req1 = none, req2 = none, req3 = none, req4 = none;
				String[] requirements, requirement1, requirement2, requirement3, requirement4;
				if(req.contains(":"))
				{
					requirements = req.split(":");
					req = requirements[1];
				}
				else
				{
					return false;
				}
				if(req.equalsIgnoreCase("yes") || req.equalsIgnoreCase("true") )
				{
					if(args.length == 8)
					{
						String commands = Utilities.getFinalArg(args, 7);
						requirement1 = args[3].split(":");
						requirement2 = args[4].split(":");
						requirement3 = args[5].split(":");
						requirement4 = args[6].split(":");
						req1 = requirement1[1];
						req2 = requirement2[1];
						req3 = requirement3[1];
						req4 = requirement4[1];
						plugin.getConfig().createSection("rewards."+reward);
						plugin.getConfig().createSection("rewards."+reward+".requirements");
						plugin.getConfig().createSection("rewards."+reward+".oneStatAt");
						plugin.getConfig().createSection("rewards."+reward+".twoStatsAt");
						plugin.getConfig().createSection("rewards."+reward+".threeStatsAt");
						plugin.getConfig().createSection("rewards."+reward+".allStatsAt");
						plugin.getConfig().createSection("rewards."+reward+".commands");
						plugin.getConfig().set("rewards."+reward+".requirements", req);
						plugin.getConfig().set("rewards."+reward+".oneStatAt", req1);
						plugin.getConfig().set("rewards."+reward+".twoStatsAt", req2);
						plugin.getConfig().set("rewards."+reward+".threeStatsAt", req3);
						plugin.getConfig().set("rewards."+reward+".allStatsAt", req4);
						plugin.getConfig().set("rewards."+reward+".commands", commands);
					}
					else if(args.length < 8)
					{
						Utilities.sendMessage(sender, "&4Not enough arguments");
						return false;
					}else if(args.length > 8)
					{
						Utilities.sendMessage(sender, "&4Too many arguments");
						return false;
					}
				}
				else if(req.equalsIgnoreCase("no") || req.equalsIgnoreCase("false"))
				{
					String commands = Utilities.getFinalArg(args, 3);
					plugin.plvl.add(plvl);
					plugin.name.add(reward);
					plugin.getConfig().set("rSetup.plvl", plugin.plvl);
					plugin.getConfig().set("rSetup.name", plugin.name);
					plugin.getConfig().createSection("rewards."+reward);
					plugin.getConfig().createSection("rewards."+reward+".requirements");
					plugin.getConfig().createSection("rewards."+reward+".oneStatAt");
					plugin.getConfig().createSection("rewards."+reward+".twoStatsAt");
					plugin.getConfig().createSection("rewards."+reward+".threeStatsAt");
					plugin.getConfig().createSection("rewards."+reward+".allStatsAt");
					plugin.getConfig().createSection("rewards."+reward+".commands");
					plugin.getConfig().set("rewards."+reward+".requirements", req);
					plugin.getConfig().set("rewards."+reward+".oneStatAt", req1);
					plugin.getConfig().set("rewards."+reward+".twoStatsAt", req2);
					plugin.getConfig().set("rewards."+reward+".threeStatsAt", req3);
					plugin.getConfig().set("rewards."+reward+".allStatsAt", req4);
					plugin.getConfig().set("rewards."+reward+".commands", commands);
					
				}
	        	return true;
			}
	    }
        return false;
    }

}