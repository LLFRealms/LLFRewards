package com.llfrealms.LLFRewards;

//import java.util.*;
//java imports
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



//bukkit imports
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;
import org.bukkit.Bukkit;

//my own imports


import com.llfrealms.LLFRewards.McMMO.McMMOListener;
import com.llfrealms.LLFRewards.util.LLFRewardsCommands;
import com.llfrealms.LLFRewards.util.LLFRewardsListeners;
import com.llfrealms.LLFRewards.util.Utilities;


/**
 *
 * @author Jack/L337Lobster
 */
public final class LLFRewards extends JavaPlugin 
{
	public Statement stmt = null;
	public Connection connection = null;
	public ResultSet result = null;
	private String database, dbusername, dbpassword, host;
	private int port;
	public String prefix;
	public ConsoleCommandSender consoleMessage = Bukkit.getConsoleSender();
	public ArrayList<Integer> plvl = new ArrayList<Integer>();
	public ArrayList<String> name = new ArrayList<String>();
	public String pluginname = "LLFRewards";
	public boolean isSkill = false;
	
	@Override
    public void onEnable()
    {
    	this.saveDefaultConfig();
    	this.getConfig();
        new LLFRewardsListeners(this);
    	new McMMOListener(this);
        
    	Utilities.sendMessage(consoleMessage,"[LLFPlugin] &aWelcome Jack's first plugin! It totally sucks!!");
        
        getCommand("llfadd").setExecutor(new LLFRewardsCommands(this));
        getCommand("llfload").setExecutor(new LLFRewardsCommands(this));
        getCommand("llfsave").setExecutor(new LLFRewardsCommands(this));
        
        mcmmoRewardSetup();
        
        prefix = getConfig().getString("MySQL.database.mcmmoPrefix");
        host = getConfig().getString("MySQL.server.address");
        port = getConfig().getInt("MySQL.server.port");
        database = getConfig().getString("MySQL.database.database");
        dbusername = getConfig().getString("MySQL.database.username");
        dbpassword = getConfig().getString("MySQL.database.password");
       
        connect(); //connect to database
        tableCheck(); //check to make sure our table exists and if not creates it.
    }

    @Override
    public void onDisable() 
    {
        getLogger().info("Closing "+pluginname);
    }
    public final Runnable OnTimeRunnable = new Runnable()
    {
    	public void run()
    	{
    		String i = "&dasdf";
    		Utilities.sendMessage(consoleMessage, i);
    	}
    };
    public void tableCheck()
    {
    	Utilities.sendMessage(consoleMessage, "Making sure our table exists");
    	String sql = "CREATE TABLE IF NOT EXISTS "+pluginname+"_rewarded" +
    				 "(user varchar(255),"+
    				 "reward varchar(255))";
    	String sql2 = "CREATE TABLE IF NOT EXISTS "+pluginname+"_users" +
				 "(user varchar(255))";
    	try {
    		stmt = connection.createStatement();
    		stmt.executeUpdate(sql);
    		stmt.executeUpdate(sql2);
		} catch (SQLException ex) {
            // handle any errors
        	getLogger().info("SQLException: " + ex.getMessage());
        	getLogger().info("SQLState: " + ex.getSQLState());
        	getLogger().info("VendorError: " + ex.getErrorCode());
        }
    	if (stmt != null) 
	    {
	        try {
	        	stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
    }
	public ResultSet query(String query)
	{
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException ex) {
            // handle any errors
        	getLogger().info("SQLException: " + ex.getMessage());
        	getLogger().info("SQLState: " + ex.getSQLState());
        	getLogger().info("VendorError: " + ex.getErrorCode());
        }
		return rs;
	}
	public void mcmmoRewardSetup()
	{
		List<Integer> power = getConfig().getIntegerList("rSetup.plvl");
		List<String> reward = getConfig().getStringList("rSetup.name");
		for(int s : power)
		{
			plvl.add(s);
		}
		for(String s : reward)
		{
			name.add(s);
		}
	}
    public void addRecord(String sql)
    {
    	try {
			stmt = connection.createStatement();
			 stmt.executeUpdate(sql);
		} catch (SQLException ex) {
            // handle any errors
			getLogger().info("SQLException: " + ex.getMessage());
			getLogger().info("SQLState: " + ex.getSQLState());
			getLogger().info("VendorError: " + ex.getErrorCode());
        }
    	if (stmt != null) 
	    {
	        try {
	        	stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
    }
	public int skillLookUp(String skill)
	{
		Integer level = null;
		ResultSet rs = query("SELECT u.user, s." + skill + " FROM "+prefix+"skills AS s JOIN "+prefix+"users AS u ON id");
		try 
		{
			rs.last();
			level = rs.getInt(skill);
		} 
		catch (SQLException e) 
		{
			getLogger().info(e.getMessage());
			isSkill = false;
		}
		if (rs != null) 
		{
	        try {
	            rs.close();
	        } catch (SQLException sqlEx) { } // ignore

	        rs = null;
	    }

	    if (stmt != null) 
	    {
	        try {
	        	stmt.close();
	        } catch (SQLException sqlEx) { } // ignore

	        stmt = null;
	    }
	    return level;
	}
    public void connect() {
        String connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            getLogger().info("Attempting connection to MySQL...");

            // Force driver to load if not yet loaded
            Class.forName("com.mysql.jdbc.Driver");
            Properties connectionProperties = new Properties();
            connectionProperties.put("user", dbusername);
            connectionProperties.put("password", dbpassword);
            connectionProperties.put("autoReconnect", "false");
            connectionProperties.put("maxReconnects", "0");
            connection = DriverManager.getConnection(connectionString, connectionProperties);
            Utilities.sendMessage(consoleMessage,"["+pluginname+"] &aConnection to MySQL was a success!");
        }
        catch (SQLException ex) {
            connection = null;
            Utilities.sendMessage(consoleMessage, "&4[SEVERE] Connection to MySQL failed!");
            getLogger().info("SQLException: " + ex.getMessage());
        	getLogger().info("SQLState: " + ex.getSQLState());
        	getLogger().info("VendorError: " + ex.getErrorCode());
        }
        catch (ClassNotFoundException ex) {
            connection = null;
            getLogger().severe("MySQL database driver not found!");
        }
    }
}
