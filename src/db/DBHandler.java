package db;

import utils.*;
import engine.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBHandler implements IDBHandler 
{
	private ConnectionPool pool = ConnectionPool.getInstance();
	//<begin> Basic Singleton implementation <begin>
	private static DBHandler instance = null;
	
	protected DBHandler()
	{
		
	}
	
	public static DBHandler getInstance()
	{
		if(instance == null)
		{
			instance = new DBHandler();
		}
		return instance;
	}
	//<end> Basic Singleton implementation <end>
	
	//===================================
	//<begin> DB connection METHODS <begin>
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
	//<end> DB connection METHODS <end>
	//===================================
	
	//===================================
	//<begin> ADD C.R.U.D METHODS <begin>
	public boolean addDevice(Device i_dev)
	{
		Connection connection= pool.getConnection();
		String sql = "insert into devices(product_id, customer_id) values(?,?);";
		try 
		{
			PreparedStatement queryingStatement= connection.prepareStatement(sql);
			queryingStatement.setShort(1, i_dev.getProductID());
			queryingStatement.setShort(2, i_dev.getCustomerID());
			queryingStatement.executeUpdate();
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
		return true;
	}
	
	public boolean addScenario(Scenario i_Scenario)
	{
		// TODO Auto-generated method stub		
		return true;
	}
  
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) 
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) 
	{
		// TODO Auto-generated method stub
		return true;
	}
	//<end> ADD C.R.U.D METHODS <end>
	//===================================
	
  
	//===================================
	//<begin> get C.R.U.D METHODS <begin>
	public LinkedList<Scenario> getScenariosByEvent(Event i_event)
  {
  	// TODO Auto-generated method stub
		return new LinkedList<Scenario>();
  }
	//<end> get C.R.U.D METHODS <end>
	//===================================
	
  
	//===================================
	//<begin> static find C.R.U.D METHODS <begin>
	public boolean isUsernameAvailable(String i_userName) throws Exception
	{
		Connection connection = pool.getConnection();
		String sql = "SELECT user_name FROM CUSTOMERS WHERE user_name= ? UNION SELECT user_name FROM VENDORS WHERE user_name=? ;";
		boolean flag = false;
		
		try 
		{
			PreparedStatement queryingStatement = connection.prepareStatement(sql);
			queryingStatement.setString(1, i_userName);
			queryingStatement.setString(2, i_userName);
			ResultSet queryResult = queryingStatement.executeQuery();
			if (!queryResult.next())
			{
				throw new Exception("User info not found in DB exception!");
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
	//<end> static find C.R.U.D METHODS <end>
	//===================================
}