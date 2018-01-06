package engine;
import java.util.*;

import db.DBHandler;

public class TimeDevice {
	private static TimeDevice instance;
	private  String connectionToServer = "";
	private Map<Short, Short> timeEvents;
	private TimeZone clock;
	
	protected TimeDevice()
	{
		instance = null;
		DBHandler db = DBHandler.getInstance();
		/*
		 * need to add getTimeEvents() to the data base handler
		 */
		//timeEvents = db.getTimeEvents();
	}
	
	public static TimeDevice getInstance()
	{
		if(instance==null)
		{
			instance = new TimeDevice();
		}
		return instance;
	}
	
	public Event addTimeEvent()
	{
		//todo: create a time_event and return it.
	}
	
}
