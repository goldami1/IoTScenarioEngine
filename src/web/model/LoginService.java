package web.model;

import db.DBHandler;
import engine.User;

public class LoginService {

	public User getUser(String username, String password) 
	{
			DBHandler db = DBHandler.getInstance();
			
			boolean isOk = false;
			
			User user = null;
			
			try
			{
				user = db.getUser(username, password);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			return user;
	}
}
