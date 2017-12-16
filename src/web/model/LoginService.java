package web.model;

import db.DBHandler;

public class LoginService {

	public boolean authenticate(String username, String password) {
			DBHandler db = DBHandler.getInstance();
			
			boolean isOk = false;
			try {
				isOk = db.userConnectionAuth(username, password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return isOk;
	}
}
