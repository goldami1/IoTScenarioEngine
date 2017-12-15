package db;

import utils.Constants;
import engine.User;
import engine.Vendor;
import engine.Action;
import engine.Customer;
import engine.Event;
import engine.Range;
import engine.Scenario;

public class DBHandler implements IDBHandler {
	String connectionURL;
	
	public DBHandler getInstance()
	{
		//Implement it!
		return new DBHandler();
	}
	
	//the method "connectionAuthentication" gets Customer or a Vendor instance declared as User (polymorphism)
	public boolean connectionAuthentication(User i_user)
	{
		//Implement it!
		return true;
	}
	
	
	
	
	//Constractors -----------------------------------------------------------------------------
	//Private constructors to implement singleton DesignPattern
	private DBHandler()
	{
		//Implement it!
		connectionURL = Constants.getDatabaseConnectionURL(); 
	}	
}