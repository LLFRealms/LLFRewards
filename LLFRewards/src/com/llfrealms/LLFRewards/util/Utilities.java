package com.llfrealms.LLFRewards.util;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.api.ExperienceAPI;


public class Utilities {
	
    public static String colorChat(String msg) 
    {
    	return msg.replace('&', (char) 167);
    }
    public static boolean sendMessage(CommandSender p, String message)
    {
        if (message ==null || message.isEmpty()) return true;
        if (message.contains("\n"))
                return sendMultilineMessage(p,message);
        if (p instanceof Player){
                if (((Player) p).isOnline())
                        p.sendMessage(colorChat(message));
        } else {
                p.sendMessage(colorChat(message));
        }
        return true;
    }
    public static boolean allTheSame(ArrayList<Boolean> l)
    {
    	for(int i = 1; i < l.size(); i++)
    	{
    		if(l.get(i) != l.get(0))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    public static boolean sendMultilineMessage(CommandSender p, String message)
    {
        if (message ==null || message.isEmpty()) return true;
        String[] msgs = message.split("\n");
        for (String msg: msgs){
                if (p instanceof Player){
                        if (((Player) p).isOnline())
                                p.sendMessage(colorChat(msg));
                } else {
                        p.sendMessage(colorChat(msg));
                }
        }
        return true;
    }	
    public static int GetSkillLevel(Player player, String skill) {
        if (skill.equalsIgnoreCase("POWERLEVEL")) {
            return getPowerLevel(player);
        } else {
            return getSkillLevel(player, skill);
        }
    }
    public static int getPowerLevel(Player player) {
        return ExperienceAPI.getPowerLevel(player);
    }

    public static int getSkillLevel(Player player, String skill) {
        return ExperienceAPI.getLevel(player, skill);
    }
}
