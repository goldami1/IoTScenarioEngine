package org.IoT_Project.Scenario_Engine.Models;
import java.sql.SQLException;
import java.util.*;
import DataBase.DBHandler;

public class TimeDevice {
	private static TimeDevice instance;
	private  String connectionToServer = "";
	private Map<Short, Short> timeEvents;
	private TimeZone clock;
	
	protected TimeDevice() throws SQLException
	{
		instance = null;
		DBHandler db = DBHandler.getInstance();
		/*
		 * need to add getTimeEvents() to the data base handler
		 */
		//timeEvents = db.getTimeEvents();
	}
	
	public static TimeDevice getInstance() throws SQLException
	{
		if(instance==null)
		{
			instance = new TimeDevice();
		}
		return instance;
	}
	
}
