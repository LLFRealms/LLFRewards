package com.llfrealms.LLFRewards.McMMO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.util.player.UserManager;
import com.llfrealms.LLFRewards.LLFRewards;
import com.llfrealms.LLFRewards.util.Utilities;

public class McMMOListener implements Listener {

	private LLFRewards plugin; // pointer to your main class, not required if you don't need methods from the main class

	public McMMOListener(LLFRewards plugin)
	{
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	public String[] skills = {"taming", "mining", "woodcutting", "repair", "unarmed", "herbalism", "excavation", "archery", "swords", "axes", "acrobatics", "fishing"};
    public void message(CommandSender user, String msg)
    {
    	Utilities.sendMessage(user, msg);
    }
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLevelUp(McMMOPlayerLevelUpEvent event) 
	{
	    Player play =  event.getPlayer();
	    String user = play.toString();
	    user = user.replaceAll("CraftPlayer\\{name=", "");
	    user = user.replaceAll("\\}", "");
	    McMMOPlayer player = UserManager.getPlayer(play.getName());
	    int lvl = player.getPowerLevel();
	    String reward = null;
	    Integer[] levels = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	    final String none = "none";
	    String req = "no", req1 = none, req2 = none, req3 = none, req4 = none;
	    Integer lvl1 = null, lvl2 = null, lvl3 = null, lvl4 = null;
	    ArrayList<Boolean> check = new ArrayList<Boolean>();
	    ArrayList<Boolean> lvlCheck = new ArrayList<Boolean>();
	    for(int i = 0; i < plugin.plvl.size(); i++)
	    {
	    	check.add(false);
	    }
	    for(int i = 0; i < skills.length; i++)
	    {
	    	lvlCheck.add(false);
	    }
	    boolean check1 = false, check2 = false, check3 = false;
	    for(int i = 0; i < plugin.plvl.size(); i++)
	    {
	    	if(lvl >= plugin.plvl.get(i))
	    	 {
	    		  reward = plugin.name.get(i);
	    		  ResultSet rwCheck = plugin.query("SELECT reward FROM JacksPlugin_rewarded WHERE user =\'" + user + "\'");
	    		  try {
					while(rwCheck.next())
					  {
						  String rewardCheck = rwCheck.getString("reward");
						  if(rewardCheck.equalsIgnoreCase(reward))
						  {
							  check.set(i, true);
						  }
					  }
				} catch (SQLException e) {}//ignore
    			  req1 = plugin.getConfig().getString("rewards." + reward + ".oneStatAt");
    			  req2 = plugin.getConfig().getString("rewards." + reward + ".twoStatsAt");
    			  req3 = plugin.getConfig().getString("rewards." + reward + ".threeStatsAt");
    			  req4 = plugin.getConfig().getString("rewards." + reward + ".allStatsAt");
    			  req = plugin.getConfig().getString("rewards." + reward + ".requirements");
	    		  if(req.equalsIgnoreCase("false") && !check.get(i))
	    		  {
	    			  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
					  command = command.replaceAll("\\{player\\}", user);
					  String[] command2 = command.split("/");
					  for(int c = 1; c < command2.length; c++)
					  {
						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
					  }
					  check.set(i, false);
					  String sql = "INSERT INTO JacksPlugin_rewarded " +
							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
					  plugin.addRecord(sql);
	    		  }
	    		  else if(req.equalsIgnoreCase("true") && !check.get(i))
	    		  {
	    			  for(int l = 0; l < skills.length; l++)
	    			  {
	    				  levels[l] = 0;
	    				  levels[l] = Utilities.GetSkillLevel(play, skills[l]);
	    			  }
	    			  if(!req1.equalsIgnoreCase(none))
	    			  {
	    				  lvl1 = Integer.parseInt(req1);
	    			  }
	    			  else
	    			  {
	    				  lvl1 = 0;
	    			  }
	    			  if(!req2.equalsIgnoreCase(none))
	    			  {
	    				  lvl2 = Integer.parseInt(req2);
	    			  }
	    			  else
	    			  {
	    				  lvl2 = 0;
	    			  }
	    			  if(!req3.equalsIgnoreCase(none))
	    			  {
	    				  lvl3 = Integer.parseInt(req3);
	    			  }
	    			  else
	    			  {
	    				  lvl3 = 0;
	    			  }
	    			  if(!req4.equalsIgnoreCase(none))
	    			  {
	    				  lvl4 = Integer.parseInt(req4);
	    			  }
	    			  else
	    			  {
	    				  lvl4 = 0;
	    			  }
	    			  if(!req1.equalsIgnoreCase(none))
		    		  {
		    			  for(int k = 0; k < skills.length; k++)
		    			  {
	    					  if(levels[k] >= lvl1)
		    				  {
		    					  check1 = true;
		    					  break;
		    				  }
		    			  }
		    		  }
	    			  if(!req4.equalsIgnoreCase(none))
		    		  {
	    				  message(plugin.consoleMessage, "checking levels for req 4");
		    			  for(int k = 0; k < skills.length; k++)
		    			  {
	    					  if(levels[k] >= lvl4)
		    				  {
		    					  lvlCheck.set(k, true);
		    				  }
		    			  }
		    		  }
		    		  if (!req2.equalsIgnoreCase(none))
		    		  {	    			  
		    			  for(int k = 0; k < skills.length; k++)
		    			  {
		    				  for(int x = 0; x < skills.length; x++)
		    				  {
		    					  if(k != x)
		    					  {
			    					  if(levels[k] >= lvl2 && levels[x] >= lvl2)
				    				  {
				    					  check2 = true;
				    					  
				    					  break;
				    				  }
		    					  }
		    				  }
		    				  if(check2)
		    				  {
		    					  break;
		    				  }
		    			  }
		    		  }
		    		  if (!req3.equalsIgnoreCase(none))
		    		  {
		    			  for(int k = 0; k < skills.length; k++)
		    			  {
		    				  for(int x = 0; x < skills.length; x++)
		    				  {
		    					  for(int y = 0; y < skills.length; y++)
		    					  {
		    						  message(plugin.consoleMessage, "Checking: " + skills[k] + ":" + levels[k] + " " + skills[x] + ":" + levels[x] + " " + skills[y] + ":" + levels[y]);
		    						  if(k != x && k != y && x != y)
		    						  {
		    						  if(levels[k] >= lvl3 && levels[x] >= lvl3 && levels[y] >= lvl3)
				    				  {
				    					  check3 = true;
				    					  break;
				    				  }
		    						  }
		    					  }
		    					  if(check3)
		    					  {
		    						  break;
		    					  }
		    				  }
		    				  if(check3)
	    					  {
	    						  break;
	    					  }
		    			  }
		    		  }
		    		  if(!req1.equalsIgnoreCase(none) && check1 == true)
		    		  {
		    			  if(!req2.equalsIgnoreCase(none) && check2 == true)
		    			  {
		    				  if(!req3.equalsIgnoreCase(none) && check3 == true)
		    				  {
		    					  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
		    					  command = command.replaceAll("\\{player\\}", user);
		    					  String[] command2 = command.split("/");
		    					  for(int c = 1; c < command2.length; c++)
		    					  {
		    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
		    					  }
		    					  check.set(i, false);
		    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
		    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
		    					  plugin.addRecord(sql);
		    				  }
		    			  }
		    			  else if(!req3.equalsIgnoreCase(none) && check3 == true)
		    			  {
		    				  if(check2 == false)
		    				  {
		    					  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
		    					  command = command.replaceAll("\\{player\\}", user);
		    					  String[] command2 = command.split("/");
		    					  for(int c = 1; c < command2.length; c++)
		    					  {
		    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
		    					  }
		    					  check.set(i, false);
		    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
		    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
		    					  plugin.addRecord(sql);
		    				  }
		    			  }
		    			  else if(check2 == false && check3 == false)
		    			  {
		    				  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
	    					  command = command.replaceAll("\\{player\\}", user);
	    					  String[] command2 = command.split("/");
	    					  for(int c = 1; c < command2.length; c++)
	    					  {
	    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
	    					  }
	    					  check.set(i, false);
	    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
	    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
	    					  plugin.addRecord(sql);
		    			  }
		    		  }
		    		  else if(!req2.equalsIgnoreCase(none) && check2 == true)
		    		  {
		    			  if(!req3.equalsIgnoreCase(none) && check3 == true)
		    			  {
		    				  if(check1 == false)
		    				  {
		    					  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
		    					  command = command.replaceAll("\\{player\\}", user);
		    					  String[] command2 = command.split("/");
		    					  for(int c = 1; c < command2.length; c++)
		    					  {
		    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
		    					  }
		    					  check.set(i, false);
		    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
		    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
		    					  plugin.addRecord(sql);
		    				  }
		    			  }
		    			  else if(check1 == false && check3 == false)
		    			  {
		    				  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
	    					  command = command.replaceAll("\\{player\\}", user);
	    					  String[] command2 = command.split("/");
	    					  for(int c = 1; c < command2.length; c++)
	    					  {
	    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
	    					  }
	    					  check.set(i, false);
	    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
	    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
	    					  plugin.addRecord(sql);
		    			  }
		    		  }
		    		  else if(!req3.equalsIgnoreCase(none))
		    		  {
		    			  if(check1 == false && check2 == false)
		    			  {
		    				  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
	    					  command = command.replaceAll("\\{player\\}", user);
	    					  String[] command2 = command.split("/");
	    					  for(int c = 1; c < command2.length; c++)
	    					  {
	    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
	    					  }
	    					  check.set(i, false);
	    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
	    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
	    					  plugin.addRecord(sql);
		    			  }
		    		  }
		    		  else if(!req4.equalsIgnoreCase(none))
		    		  {
		    			  if(Utilities.allTheSame(lvlCheck))
		    			  {
		    				  String command = plugin.getConfig().getString("rewards." + reward + ".commands");
	    					  command = command.replaceAll("\\{player\\}", user);
	    					  String[] command2 = command.split("/");
	    					  for(int c = 1; c < command2.length; c++)
	    					  {
	    						  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command2[c]);
	    					  }
	    					  check.set(i, false);
	    					  String sql = "INSERT INTO JacksPlugin_rewarded " +
	    							  		"VALUES(\'"+user+"\', \'"+reward+"\')";
	    					  plugin.addRecord(sql);
		    			  }
		    		  }
    		  	  }
	    	  }
	      }
	  }

}
