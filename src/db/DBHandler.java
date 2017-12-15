package db;

import utils.*;
import engine.*;

public class DBHandler implements IDBHandler {
	//Constractors -----------------------------------------------------------------------------
	//Private constructors to implement singleton DesignPattern
	private DBHandler()
	{
		//Implement it!
		connectionURL = Constants.getDatabaseConnectionURL(); 
	}	
	//Constractors -----------------------------------------------------------------------------
	
	
	
	String connectionURL;
	
	public DBHandler getInstance()
	{
		//Implement it!
		return new DBHandler();
	}
	
	//************ Think about having this functionality *****************
	//the method "connectionAuthentication" gets Customer or a Vendor instance declared as User (polymorphism)
	//public boolean connectionAuthentication(User i_user)
	//{
	//	//Implement it!
	//	return true;
	//}
	
	public boolean vendorConnectionAuth(Vendor i_vendor) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean customerConnectionAuth(Customer i_customer) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isUsernameAvailable(String i_userName) {
		// TODO Auto-generated method stub
		return false;
}