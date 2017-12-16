package db;

import utils.*;
import engine.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler implements IDBHandler 
{
	private ConnectionPool pool = ConnectionPool.getInstance();
	

	public boolean userConnectionAuth(String i_Username, String i_UserPassword) throws Exception
	{
		Connection connection = pool.getConnection();
		String sql = "SELECT user_name, user_password FROM CUSTOMERS WHERE user_name= ? and user_password= ? UNION SELECT user_name, user_password FROM VENDORS WHERE user_name= ? and user_password= ?;";
		boolean flag = false;
		
		try 
		{
			PreparedStatement queryingStatement = connection.prepareStatement(sql);
			queryingStatement.setString(1, i_Username);
			queryingStatement.setString(2, i_UserPassword);
			queryingStatement.setString(3, i_Username);
			queryingStatement.setString(4, i_UserPassword);
			ResultSet queryResult = queryingStatement.executeQuery();
			if (!queryResult.next()) 
			{
				throw new Exception("User info not found in DB exception!");
		}
			//	check if the password we got from the ResultSet equals to the password we got in this method:
				if (queryResult.getString(2).equals(i_UserPassword)) 
				{
					flag= true;
				}
				else
				{
					flag= false;
					throw new Exception("The password incorrect exception!");
				}
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			pool.returnConnection(connection);
		}

		return flag;
	}
	

	
	public static boolean isUsernameAvailable(String i_userName) 
	{
		// TODO Auto-generated method stub
		return false;
		

	}

	
	public void addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) 
	{
		// TODO Auto-generated method stub
		
	}

	
	public void addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) 
	{
		// TODO Auto-generated method stub
		
	}


	public boolean customerConnectionAuthentication(String i_CustomerUsername, String i_CustomerPassword) 
	{
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean vendorConnectionAuthentication(String i_VendorUsername, String i_VendorPassword) 
	{
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isExistVendor(String i_VendorUsername) 
	{
		// TODO Auto-generated method stub
		return false;
	}


	
	public boolean isExistCustomer(String i_CustomerUsername) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}