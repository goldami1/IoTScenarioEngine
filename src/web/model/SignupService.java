package web.model;

import java.sql.SQLException;
import java.util.Map;

import db.DBHandler;

public class SignupService {
		
		private DBHandler db;
	
		public SignupService(){
			db = DBHandler.getInstance();
		}
	
	
		private boolean addVendor(String name, String uname, String pass,String email,String desc,String logo) {
			boolean ok = false;
			try {
				ok = db.addVendor(name, uname, pass, email, desc, logo);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ok;
		}
		
		private boolean addCustomer(String uname, String pass,String email,String fname,String lname) {
			boolean ok = false;

			try {
				ok = db.addCustomer(fname, lname, uname, pass, email);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return ok;
		}		
		
		
		
		public boolean addUser(String type, String fname, String lname,String uname,String pass,String companyname,String description,String email,String logo) {
			
			if (type == "vendor") {
				return addVendor(companyname,uname,pass,email,description,logo);
			}else{
				return addCustomer(uname,pass,email,fname,lname);
			}	
		}


}
