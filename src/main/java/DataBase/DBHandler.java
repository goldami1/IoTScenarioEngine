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
	private final static String k_selectEvent="select * from EVENTS where event_id=?;";
	private final static String k_selectEventProto="select * from EVENTS_PROTO where eventproto_id=?;";
	private final static String k_userConnectionAuth = "SELECT user_name, user_password FROM CUSTOMERS WHERE user_name= ? and user_password= ? UNION SELECT user_name, user_password FROM VENDORS WHERE user_name= ? and user_password= ?;";
	private final static String k_doesCustomerExistByName = "SELECT user_name FROM CUSTOMERS WHERE user_name= ?;";
	private final static String k_doesCustomerExistByID = "SELECT customer_id FROM CUSTOMERS WHERE customer_id= ?;";
	private final static String k_doesVendorExistByName = "SELECT user_name FROM VENDORS WHERE user_name=? ;";
	private final static String k_doesVendorExistByID= "SELECT vendor_id FROM VENDORS WHERE vendor_id=? ;";
	private final static String k_selectMaxID = "SELECT MAX(%s) FROM %s;";
	
	
	private short[] TABLE_maxID;
	private enum EntityAndIdxValue
	{		
			CUSTOMERS_TABLE(1, "CUSTOMERS", "CUSTOMERS.customer_id"),
			VENDORS_TABLE(2, "VENDORS", "VENDORS.vendor_id"),
			ACTIONS_PROTO_TABLE(3, "ACTIONS_PROTO", "ACTIONS_PROTO.actionproto_id"),
			ACTIONS_TABLE(4, "ACTIONS", "ACTIONS.action_id"),
			EVENTS_PROTO_TABLE(5, "EVENTS_PROTO", "EVENTS_PROTO.eventproto_id"),
			EVENTS_TABLE(6, "EVENTS", "EVENTS.event_id"),
			PRODUCTS_TABLE(7, "PRODUCTS", "PRODUCTS.product_id"),
			DEVICES_TABLE(8, "DEVICES", "DEVICES.device_id"),
			SCENARIOS_TABLE(9, "SCENARIOS", "SCENARIOS.scenario_id");
			
			private final int idx;
			private final String Entity;
			private final String idxValColumn;
			
			private EntityAndIdxValue(int i_idx, String i_entity, String i_idxValColumn)
			{
				idx=i_idx;
				Entity = i_entity;
				idxValColumn= i_idxValColumn;
			}
	}

	
	protected DBHandler() throws SQLException
	{
		instance = null;
		currSession=null;
		TABLE_maxID = new short[EntityAndIdxValue.values().length+1];
		initializeAllMaxIds();
	}
	
	public static DBHandler getInstance() throws SQLException
	{
		if(instance == null)
		{
			instance = new DBHandler();
		}
		return instance;
	}
	
	//===================================
	//<begin> DB connection METHODS <begin>
	@SuppressWarnings("finally")
	private static Connection openConnection() throws SQLException
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
		}
		finally
		{
			throw new SQLException("Connection couldn't be established");
		}
	}
	
	private static void closeConnection() throws SQLException
	{
		if(currSession!=null)
		{
			currSession.close();
		}
	}
	
	@SuppressWarnings("finally")
	public boolean userConnectionAuth(String i_userName, String i_password) throws SQLException
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
				throw new SQLException("User info not found in DB exception!");
			}
			if (!connectionRes.getString(2).equals(i_password)) 
			{
				flag= false;
				throw new SQLException("The password incorrect exception!");
			}
		}
		finally
		{
			closeConnection();
			return flag;
		}
	}
	//<end> DB connection METHODS <end>
	//===================================

	//===================================
	//<begin> ADD C.R.U.D METHODS <begin>
 
	@SuppressWarnings("finally")
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) throws SQLException 
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
		finally
		{
			closeConnection();
			incrementMaxIdxValue(EntityAndIdxValue.CUSTOMERS_TABLE);
			System.out.println("Customer Added Successfully");
			return flag;
		}
	}
	
	@SuppressWarnings("finally")
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) throws SQLException 
	{
		final String sqlQuery =	"insert into VENDORS (vendor_name, user_name, user_password, email, vendor_description, vendor_logo_pic)"
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
			incrementMaxIdxValue(EntityAndIdxValue.VENDORS_TABLE);
			return flag;
		}
	}
	//<end> ADD C.R.U.D METHODS <end>
	//===================================
	
  
	//===================================
	//<begin> get C.R.U.D METHODS <begin>
	
	public LinkedList<String> selectQ(String i_selectQ) throws SQLException
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
		return doesCustVendExist((short) -1, i_userName, k_doesCustomerExistByName) || doesCustVendExist((short) -1, i_userName, k_doesVendorExistByName);
	}
	//<end> static find C.R.U.D METHODS <end>
	//===================================

	public boolean addDevice(Device i_dev) throws SQLException {
		return addDevice(i_dev.getProduct_id(), i_dev.getCustomer_id(), i_dev.getSerial_number());
	}

	@SuppressWarnings("finally")
	public boolean addScenario(Scenario i_Scenario) throws Exception 
	{
		if(!doesCustVendExist(i_Scenario.getCustomerID(), null, k_doesCustomerExistByID))
		{
			System.out.println("No such customer!");
			return false;
		}
	
		addActions(i_Scenario);
		addEvents(i_Scenario);
		addlogExpr(i_Scenario);
		
		final String sqlQuery =	"insert into SCENARIOS (scenario_id, customer_id, scenario_name, scenario_description)"
				+ "values ("
				+"'"+i_Scenario.getID()+"',"
				+"'"+i_Scenario.getCustomerID()+"',"
				+"'"+i_Scenario.getName()+"',"
				+"'"+i_Scenario.getDescription()+"',"
				+")";

		boolean flag = false;

		try 
		{
				Connection connection = openConnection();
				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				flag = true;
				incrementMaxIdxValue(EntityAndIdxValue.SCENARIOS_TABLE);
		}
		finally
		{
			closeConnection();
			return flag;
		}
	}

	
	private void addlogExpr(Scenario i_Scenario) throws SQLException
	{
		Iterator<Entry<Short,Event>> itr = i_Scenario.getEvents();
		try
		{
			Connection connection = openConnection();
			short i=1;
			while(itr.hasNext())
			{
				final String sqlQuery =	"insert into LOGEXPRS (scenario_id, loc_id, event_id)"
						+ "values ("
						+"'"+i_Scenario.getID()+"',"
						+"'"+Short.toString(i++)+"',"
						+"'"+itr.next().getValue().getId()+"',"
						+")";
				
						java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
						insertStat.executeUpdate();
			}
		}
		finally
		{
			closeConnection();
		}
	}

	private void addActions(Scenario i_Scenario) throws Exception 
	{
		Iterator<Entry<Short, Action>> itr = i_Scenario.getActions();
		try
		{
			Connection connection = openConnection();
			while(itr.hasNext())
			{
				Action currentAction = itr.next().getValue();
				if(currentAction.getDevice_serialNum()<0 || currentAction.getPrototype().getProdId()<0)
					throw new Exception("Incorrect Action object - no matching Product, or no Action instance available!");
			
			
				final String sqlQuery = "insert into ACTIONS (device_id, actionproto_id, param_val)"
						+ "values ("
						+"'"+currentAction.getDevice_serialNum()+"',"
						+"'"+currentAction.getPrototype().getProdId()+"',"
						+"'"+currentAction.getParameterToString()+"',"
						+")";

				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				incrementMaxIdxValue(EntityAndIdxValue.ACTIONS_TABLE);
			
				itr.next();
			}
		}
		finally
		{
			closeConnection();
		}
 
	}
	
	
	private void addEvents(Scenario i_Scenario) throws Exception 
	{
		Iterator<Entry<Short, Event>> itr = i_Scenario.getEvents();
		try
		{
			Connection connection = openConnection();
			while(itr.hasNext())
			{
				Event currentEvent = itr.next().getValue();
				if(currentEvent.getDevice_serialNum()<0 || currentEvent.getPrototype().getProdId()<0)
					throw new Exception("Incorrect Event object - no matching Product, or no Event instance available!");
			
			
				final String sqlQuery = "insert into EVENTS (device_id, eventproto_id, param_val, logicOper)"
						+ "values ("
						+"'"+currentEvent.getDevice_serialNum()+"',"
						+"'"+currentEvent.getPrototype().getId()+"',"
						+"'"+currentEvent.getParameterToString()+"',"
						+"'"+currentEvent.getLogicOperator()+"',"
						+")";

				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				incrementMaxIdxValue(EntityAndIdxValue.EVENTS_TABLE);
			
				itr.next();
			}
		}
		finally
		{
			closeConnection();
		}
 
	}
	

	@SuppressWarnings("finally")
	private boolean doesCustVendExist(short i_custVendID, String i_userName, String i_CustVendQuery) throws Exception
	{
		if(i_userName!=null && i_custVendID>0)
			throw new Exception("wrong use of method doesCustVendExist - choose either username, either userID");
		
		Connection connection=null;
		try
		{
			connection = openConnection();
			PreparedStatement queryingStatement = connection.prepareStatement(i_CustVendQuery);
			if(i_userName!=null)
				queryingStatement.setString(1,  i_userName);
			else
				queryingStatement.setString(1, Short.toString(i_custVendID));
			ResultSet queryResult = queryingStatement.executeQuery();
			if (!queryResult.next())
			{
				return false;
			}
			else
				return true;
		} 
		finally
		{
			if(connection!=null)
				closeConnection();
			return false;
		}
	}

	public LinkedList<Pair<Short, String>> getVendors() {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<Product> getProducts(int vendor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public LinkedList<Device> getDevices(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@SuppressWarnings("finally")
	public boolean addDevice(int product_id, int customer_id, int device_serial) throws SQLException {
		final String sqlQuery =	"insert into DEVICES (product_id, customer_id , serial_num)"
				+ "values ("
				+"'"+product_id+"',"
				+"'"+customer_id+"',"
				+"'"+device_serial+"'"
				+")";

		boolean flag = false;

		try 
		{
				Connection connection = openConnection();
				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				flag = true;
		}
		finally
		{
			closeConnection();
			incrementMaxIdxValue(EntityAndIdxValue.DEVICES_TABLE);
			return flag;
		}
	}

	public boolean removeDevice(int device_id) {
		// TODO Auto-generated method stub
		return false;
	}

	public IUser getUser(String i_username, String i_userPassword) throws SQLException {
		IUser a = new Vendor(null);
		return a;
	}

	public Vendor getVendor(String name, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public Vendor getVendor(int vendor_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeProduct(int productToRemove_id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateProduct(int prod_id, Product new_product) {
		// TODO Auto-generated method stub
		return false;
	}

	public LinkedList<Scenario> getScenarios(int cust_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Scenario getScenario(short i_event_id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Customer addCustomer(Customer i_User)
	{
		// TODO
		return null;
	}

	public Customer getCustomer(String i_username, String i_password) {
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getCustomer(int cust_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateDevice(int origionalDevice_id, Device newDevice) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateScenario(int origionalScenario_id, Scenario newScenario) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeScenario(int scenarioToRemove_id)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("finally")
	public Event getEvent(int event_id) throws Exception
	{
		Event res = null;

		String eve_name=null, eve_type=null, eve_param=null, eve_deviceEP=null;
		short eve_deviceId=-1, eve_id=-1, eve_proto=-1, eve_prod_id=-1;
		char eve_logicOperator=' ';
		
		try
		{
			Connection connection = openConnection();
			PreparedStatement queryingStatement;
			ResultSet queryResult;
			
			queryingStatement  =connection.prepareStatement(k_selectEvent);
			queryingStatement.setString(1, Integer.toString(event_id));
			queryResult = queryingStatement.executeQuery();
					
			if (!queryResult.next())
			{
				throw new SQLException("Wrong tables format - maximum ids fields weren't found!");
			}
			else
			{
				eve_id = Short.parseShort(queryResult.getString(1));
				eve_deviceId = Short.parseShort(queryResult.getString(2));
				eve_proto = Short.parseShort(queryResult.getString(3));
				eve_param = queryResult.getString(4);
				eve_logicOperator = queryResult.getString(5).charAt(0);
			} 
			
			queryingStatement  =connection.prepareStatement(k_selectEventProto);
			queryingStatement.setString(1, Short.toString(eve_proto));
			queryResult = queryingStatement.executeQuery();
					
			if (!queryResult.next())
			{
				throw new SQLException("Wrong tables format - maximum ids fields weren't found!");
			}
			else
			{
				eve_prod_id = Short.parseShort(queryResult.getString(2));
				eve_name = queryResult.getString(3);
				eve_type = queryResult.getString(5);
			} 
			
			ActionEventProto eve_prototype = new ActionEventProto(eve_name, eve_type, true, eve_prod_id, eve_deviceEP);
			Action eveWithParamAndDevID = new Action(eve_prototype, eve_param, eve_deviceId);
			Event completeEvent = new Event(eveWithParamAndDevID, eve_logicOperator);
			res = completeEvent;
			//res = new Event(eve_name, eve_type, eve_param, eve_logicOperator, eve_deviceEP, eve_deviceId);
		}
		finally
		{
			closeConnection();
			return res;
		}
	}

	public LinkedList<Device> getDevices(short i_UserID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeProduct(Product productToRemove)
	{
		
	}

	public short getIdForScenario() {
		return getScenariosMaxAvailableIdx();
	}

	@SuppressWarnings("finally")
	public boolean addProduct(Product i_product) throws SQLException
	{
		final String sqlQuery =	"insert into PRODUCTS (vendor_id, product_name, product_pic, events_state, actions_state)"
				+ "values ("
				+"'"+i_product.getVenID()+"',"
				+"'"+i_product.getName()+"',"
				+"'"+i_product.getPicURL()+"'"
				+"'"+i_product.getEAState()[0]+"'"
				+"'"+i_product.getEAState()[1]+"'"
				+")";
		
		boolean flag = false;

		try 
		{
				Connection connection = openConnection();
				java.sql.PreparedStatement insertStat  = connection.prepareStatement(sqlQuery);
				insertStat.executeUpdate();
				flag = true;
		}
		finally
		{
			closeConnection();
			return flag;
		}
	}
	
	private void initializeAllMaxIds() throws SQLException
	{
		Connection connection = openConnection();
		PreparedStatement queryingStatement;
		ResultSet queryResult;
		
		for(EntityAndIdxValue ev:EntityAndIdxValue.values())
		{
			queryingStatement = connection.prepareStatement(
								String.format(k_selectMaxID, ev.idxValColumn, ev.Entity));
				
			queryResult = queryingStatement.executeQuery();
				
			if (!queryResult.next())
			{
				throw new SQLException("Wrong tables format - maximum ids fields weren't found!");
			}
			else
			{
				String currentVal = queryResult.getString(1);
					
				if(currentVal!=null)
				{
					TABLE_maxID[ev.idx]= Short.parseShort(currentVal);
				}
				else
				{
					TABLE_maxID[ev.idx]=0;
				}
			} 
		}
		closeConnection();
	}		
	
	private void incrementMaxIdxValue(EntityAndIdxValue tableName)
	{
		TABLE_maxID[tableName.idx]++;
	}
	
	public short getCustomersMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.CUSTOMERS_TABLE.idx]+1); 
	}
	
	public short getVendorsMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.VENDORS_TABLE.idx]+1);
	}
	
	public short getEventsMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.EVENTS_TABLE.idx]+1);
	}
	
	public short getEventsProtoMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.EVENTS_PROTO_TABLE.idx]+1);
	}
	
	public short getActionsMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.ACTIONS_TABLE.idx]+1);
	}
	
	public short getActionsProtoMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.ACTIONS_PROTO_TABLE.idx]+1);
	}
	
	public short getProductsMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.PRODUCTS_TABLE.idx]+1);
	}
	
	public short getScenariosMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.SCENARIOS_TABLE.idx]+1);
	}
	
	public short getDevicesMaxAvailableIdx()
	{
		return (short) (TABLE_maxID[EntityAndIdxValue.DEVICES_TABLE.idx]+1);
	}

	@Override
	public LinkedList<Scenario> getScenariosByEvent(Event i_event) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEventUpdated(Event event) {
		// TODO Auto-generated method stub
		return false;
	}
}