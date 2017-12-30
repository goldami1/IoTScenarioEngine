package DataBase;

import java.math.BigDecimal;
import utils.*;
import org.IoT_Project.Scenario_Engine.Models.*;


import javafx.util.Pair;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class DBHandler implements IDBHandler 
{
	private static DBHandler instance;
	private static Connection currSession;
	private final static String k_driver = "com.mysql.jdbc.Driver";
	private final static String k_userConnectionAuth = "SELECT user_name, user_password FROM CUSTOMERS WHERE user_name= ? and user_password= ? UNION SELECT user_name, user_password FROM VENDORS WHERE user_name= ? and user_password= ?;";
	private final static String k_isUsernameAvailable = "SELECT user_name FROM CUSTOMERS WHERE user_name= ? UNION SELECT user_name FROM VENDORS WHERE user_name=? ;";
	
	protected DBHandler()
	{
		instance = null;
		currSession=null;
	}
	
	public static DBHandler getInstance()
	{
		if(instance == null)
		{
			instance = new DBHandler();
		}
		return instance;
	}
	
	//===================================
	//<begin> DB connection METHODS <begin>
	private static Connection openConnection() throws Exception
	{
		try
		{
			String url = Constants.getDatabaseConnectionURL();
			String username = Constants.getDatabaseUserName();
			String password = Constants.getDatabasePassword();	
			Class.forName(k_driver);
				
			Connection conn = DriverManager.getConnection(url, username, password);
			
			currSession = conn;
			return conn;
		} catch(Exception ex){System.out.println(ex);}
			
		return null;
	}
	
	private static void closeConnection() throws SQLException
	{
		if(currSession!=null)
		{
			currSession.close();
		}
	}
	
	public boolean userConnectionAuth(String i_userName, String i_password) throws Exception
	{
		String sqlQuery = k_userConnectionAuth;
		boolean flag = true;
		
		try 
		{
			Connection connection = openConnection();
			java.sql.PreparedStatement connectionStat  =connection.prepareStatement(sqlQuery);
			connectionStat.setString(1, i_userName);
			connectionStat.setString(2, i_password);
			connectionStat.setString(3, i_userName);
			connectionStat.setString(4, i_password);
			ResultSet connectionRes = connectionStat.executeQuery();
			
			if (!connectionRes.next()) 
			{
				throw new Exception("User info not found in DB exception!");
			}
			if (!connectionRes.getString(2).equals(i_password)) 
			{
				flag= false;
				throw new Exception("The password incorrect exception!");
			}
		}
		catch (SQLException e) {System.out.println(e);}

		closeConnection();
		return flag;
	}
	//<end> DB connection METHODS <end>
	//===================================

	//===================================
	//<begin> ADD C.R.U.D METHODS <begin>
	public boolean addDevice(Device i_dev)
	{
		return false;
	}
	
	public boolean addProduct(Product i_prod)		//NOT IMPLEMENTED YET
	{
		//Continue implementation!
		addProdsEve_Act(i_prod);
		return false;
	}
	private boolean addProdsEve_Act(Product i_prod)	//NOT IMPLEMENTED YET
	{
		//Continue implementation!
		return false;
	}
	
	public boolean addScenario(Scenario i_scenario)
	{	
		return true;
	}
  
	@SuppressWarnings("finally")
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) throws Exception 
	{
		final String sqlQuery =	"insert into CUSTOMERS (first_name, last_name, user_name, user_password, email)"
								+ "values ("
								+"'"+i_firstName+"',"
								+"'"+i_lastName+"',"
								+"'"+i_userName+"',"
								+"'"+i_userPassword+"',"
								+"'"+i_email+"'"
								+")";
		
		boolean flag = false;
		
		try 
		{
			Connection connection = openConnection();
			java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
			insertStat.executeUpdate();
			flag = true;
		}
		catch (SQLException e) {System.out.println(e);}
		finally
		{
			closeConnection();
			return flag;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) throws SQLException 
	{
		final String sqlQuery =	"insert into CUSTOMERS (i_vendorName, i_userName, i_userPassword, i_email, i_vendorDescription, i_vendorLogoPic)"
				+ "values ("
				+"'"+i_vendorName+"',"
				+"'"+i_userName+"',"
				+"'"+i_userPassword+"',"
				+"'"+i_email+"',"
				+"'"+i_vendorDescription+"',"
				+"'"+i_vendorLogoPic+"'"
				+")";

		boolean flag = false;

		try 
		{
				Connection connection = openConnection();
				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				flag = true;
		}
		catch (SQLException e) {System.out.println(e);}
		finally
		{
			closeConnection();
			return flag;
		}
	}
	//<end> ADD C.R.U.D METHODS <end>
	//===================================
	
  
	//===================================
	//<begin> get C.R.U.D METHODS <begin>
	
	public LinkedList<String> selectQ(String i_selectQ) throws Exception
	{
		Connection connection = openConnection();
		List<Row> table = new ArrayList<Row>();
		LinkedList<String> res = new LinkedList<String>();
		StringBuilder rowBuilder = new StringBuilder();
		PreparedStatement st = connection.prepareStatement(i_selectQ);
		ResultSet rs = st.executeQuery();

		Row.formTable(rs, table);

		for (Row row : table)
		{
		    for (Entry<Object, Class> col: row.row)
		    {
		        rowBuilder.append((" > " + ((col.getValue()).cast(col.getKey()))));
		    }
		    res.add(rowBuilder.toString());
		    rowBuilder = new StringBuilder();
		}
		
		return res;
	}
	//<end> get C.R.U.D METHODS <end>
	//===================================
	
  
	//===================================
	//<begin> static find C.R.U.D METHODS <begin>
	public boolean isUsernameAvailable(String i_userName) throws Exception
	{
		Connection connection = openConnection();
		String sqlQuery = k_isUsernameAvailable;
		boolean flag = false;
		
		try 
		{
			PreparedStatement queryingStatement = connection.prepareStatement(sqlQuery);
			queryingStatement.setString(1, i_userName);
			queryingStatement.setString(2, i_userName);
			ResultSet queryResult = queryingStatement.executeQuery();
			if (!queryResult.next())
			{
				throw new Exception("User info not found in DB exception!");
			}
		} 
		catch (SQLException e) {System.out.println(e);}
		
		closeConnection();
		return flag;
	}
	//<end> static find C.R.U.D METHODS <end>
	//===================================


	@Override
	public LinkedList<Device> getDevices(short i_UserID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Pair<Integer, String>> getVendors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Product> getProducts(int vendor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedList<Device> getDevices(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addDevice(int product_id, int user_id, int device_serial) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDevice(int device_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(String i_Username, String i_UserPassword) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vendor getVendor(String name, String password) {
		//TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean updateProduct(int prod_id, Product new_product) {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vendor getVendor(int vendor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeProduct(int productToRemove_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LinkedList<Scenario> getScenarios(int cust_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer addCustomer(Customer i_User) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomer(int cust_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateDevice(int origionalDevice_id, Device newDevice) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateScenario(int origionalScenario_id, Scenario newScenario) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeScenario(int scenarioToRemove_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Event getEvent(int event_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public short getScenariosMaxAvailableIdx() {
		// TODO Auto-generated method stub
		return 0;
	}

	public short getCustomersMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.CUSTOMERS_TABLE.idx]+1); 
		return 0;
	}

	public short getProductsMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.PRODUCTS_TABLE.idx]+1);
		return 0;
	}

	public short getDevicesMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.DEVICES_TABLE.idx]+1);
		return 0;
	}
	
	public short getEventsMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.EVENTS_TABLE.idx]+1);
		return 0;
	}

	public short getVendorsMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.VENDORS_TABLE.idx]+1);
		return 0;
	}

	public short getActionsMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.ACTIONS_TABLE.idx]+1);
		return 0;
	}

	public boolean isEventUpdated(Event event) {
		// TODO Auto-generated method stub
		return false;
	}

	public Scenario getScenarioByEvent(Event i_event) {
		// TODO Auto-generated method stub
		return null;
	}

	public short getEventsProtoMaxAvailableIdx() {
		//return (short) (TABLE_maxID[EntityAndIdxValue.EVENTS_PROTO_TABLE.idx]+1);
		return 0;
	}


	@Override
	public void removeProduct(Product productToRemove) {
		// TODO Auto-generated method stub
		
	}
}