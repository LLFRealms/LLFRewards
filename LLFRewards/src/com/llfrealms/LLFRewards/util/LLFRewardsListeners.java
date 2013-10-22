package com.llfrealms.LLFRewards.util;

import java.sql.ResultSet;
import java.sql.SQLException;




import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.llfrealms.LLFRewards.LLFRewards;

public class LLFRewardsListeners implements Listener {
 
	private LLFRewards plugin; // pointer to your main class, not required if you don't need methods from the main class

		public LLFRewardsListeners(LLFRewards plugin)
		{
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			this.plugin = plugin;
		}
	    @EventHandler // EventPriority.NORMAL by default
	    public void onLogin(PlayerLoginEvent event) 
	    {
	        Player player = event.getPlayer();
	        String play = player.toString();
		    play = play.replaceAll("CraftPlayer\\{name=", "");
		    play = play.replaceAll("\\}", "");
		    ResultSet rs = plugin.query("SELECT user FROM LLFReward_users WHERE user = \'" + play + "\'");
		    if(play.equalsIgnoreCase("L337Lobster"))
		    {
		    	for(Player player2: plugin.getServer().getOnlinePlayers()) {
		    		 
		    	    if(player2.hasPermission("jack.message")) {
		    	        player2.sendMessage("Jack Logged in!");
		    	    }
		    	 
		    	}
		    }
		    try {
		    	rs.first();
				if(rs.getString("user").equalsIgnoreCase(play))
				{
				}
			} catch (SQLException e) {
			    String sql = "INSERT INTO "+plugin.pluginname+"_users VALUES(\'" + play + "\')";
			    String sql2 = "INSERT INTO "+plugin.pluginname+"_rewarded VALUES(\'" + play + "\', \'Default\')";
				plugin.addRecord(sql2);
			    plugin.addRecord(sql);
			}
	    }

}
