package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import utils.Constants;

public class ConnectionPool 
{
	final int poolSize=10;
	private static Set<Connection> connectionList= new HashSet<>();  
	private static ConnectionPool instance= new ConnectionPool();
	
	private ConnectionPool()
	{
			for (int i = 0; i < poolSize; i++) 
			{
				try 
				{
					Connection connection = DriverManager.getConnection(Constants.getDatabaseConnectionURL(), Constants.getDatabaseUserName(), Constants.getDatabasePassword());
					connectionList.add(connection);
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
		
	public static ConnectionPool getInstance() 
	{
		return instance;
	}
	
	public synchronized Connection getConnection()
	{
		Iterator<Connection> iter= connectionList.iterator();
		if(connectionList.isEmpty())
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
			}
		}
		Connection connection=iter.next();
		connectionList.remove(connection);
		return connection;
	}

	public synchronized void returnConnection(Connection connection)
	{
		if(connectionList.size()==poolSize)
		{
			try 
			{
				wait();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connectionList.add(connection);
		notify();
	}
	
	public static void closeAllConnections()
	{
		for (Connection connection : connectionList) 
		{
			try 
			{
				connection.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
}