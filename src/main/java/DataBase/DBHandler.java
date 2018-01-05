package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.IoT_Project.Scenario_Engine.Models.Action;
import org.IoT_Project.Scenario_Engine.Models.ActionEventProto;
import org.IoT_Project.Scenario_Engine.Models.Customer;
import org.IoT_Project.Scenario_Engine.Models.Device;
import org.IoT_Project.Scenario_Engine.Models.Event;
import org.IoT_Project.Scenario_Engine.Models.Product;
import org.IoT_Project.Scenario_Engine.Models.Scenario;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Models.Vendor;

import javafx.util.Pair;
import utils.Constants;

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
		Connection conn = null;
		try
		{
			String url = Constants.getDatabaseConnectionURL();
			String username = Constants.getDatabaseUserName();
			String password = Constants.getDatabasePassword();	
			Class.forName(k_driver);
				
			conn = DriverManager.getConnection(url, username, password);
			
			currSession = conn;
		} catch (ClassNotFoundException e) {
			System.out.println("Class Loader Exception!");
		}
		finally
		{
			return conn;
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
		return addDevice(i_dev.getProtoDevice().getId(), i_dev.getCustomer_id(), i_dev.getSerial_number());
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
				if(currentAction.getDevice_serialNum()<0 || currentAction.getActionDescription().getProdId()<0)
					throw new Exception("Incorrect Action object - no matching Product, or no Action instance available!");
			
			
				final String sqlQuery = "insert into ACTIONS (device_id, actionproto_id, param_val)"
						+ "values ("
						+"'"+currentAction.getDevice_serialNum()+"',"
						+"'"+currentAction.getActionDescription().getProdId()+"',"
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
				if(currentEvent.getDevice_serialNum()<0 || currentEvent.getActionDescription().getProdId()<0)
					throw new Exception("Incorrect Event object - no matching Product, or no Event instance available!");
			
			
				final String sqlQuery = "insert into EVENTS (device_id, eventproto_id, param_val, logicOper)"
						+ "values ("
						+"'"+currentEvent.getDevice_serialNum()+"',"
						+"'"+currentEvent.getActionDescription().getId()+"',"
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

	public LinkedList<Pair<Short, String>> getVendors() 
	{
		//TODO
		return null;
	}

	public LinkedList<Product> getProducts(int vendor_id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	//****************	CHANGED UNCERTAIN	*********************\\
	@SuppressWarnings("finally")
	public LinkedList<Device> getDevices(short user_id) throws SQLException	//final&complete IMPL
	{
		short serial_num=-1, dev_id=-1, prod_id=-1, cust_id=-1;
		Product prod= null;
		
		
		LinkedList<Device> res = null;
		
		try
		{
			Connection connection = openConnection();
			PreparedStatement devQStatement, prodQStatement;
			ResultSet devsQResult, prodsQResult;
			
			devQStatement  =connection.prepareStatement("select * from DEVICES where customer_id=?;");
			devQStatement.setString(1, Integer.toString(user_id));
			devsQResult = devQStatement.executeQuery();
				
			while (devsQResult.next())
			{
				if(res==null)
					res = new LinkedList<Device>();
				
				dev_id = Short.parseShort(devsQResult.getString(1));
				prod_id = Short.parseShort(devsQResult.getString(2));
				cust_id = Short.parseShort(devsQResult.getString(3));
				serial_num = Short.parseShort(devsQResult.getString(4));
				
				prodQStatement  =connection.prepareStatement("select * from PRODUCTS where product_id=?;");
				prodQStatement.setString(1, Integer.toString(prod_id));
				prodsQResult = prodQStatement.executeQuery();
					
					
				if (prodsQResult.next())
				{
					short vendor_id = Short.parseShort(prodsQResult.getString(2));
					String product_name = prodsQResult.getString(3);
					String product_pic = prodsQResult.getString(4);
					//prod = new Product(product_name, product_pic, null, null);
					//prod = new Product(prod, vendor_id, prod_id);
					String prod_description = null;
					/*
					 * need to add product_description for each product
					 */
					prod = new Product(prod_id, vendor_id, product_name, prod_description, product_pic, null, null);
					/*
					 * NOTES:
					 * 1. added description for product.
					 * 2. the nulls in the c'tor(line 489) are the endpoint(for the device) and the supported Action/Event list.
					 */
				}
				else
					throw new SQLException("relevant product for device couldn't be found! DB isn't normalized!");
					
				Device devToAdd =new Device(dev_id, serial_num, cust_id, prod);
				res.add(devToAdd);
			}
		}	
		finally
		{
			closeConnection();
			return res;
		}
	}

	@SuppressWarnings("finally")
	public boolean addDevice(int product_id, int customer_id, int device_serial) throws SQLException //final&complete IMPL
	{ 
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

	//****************** CHANGED UNCERTAIN (added notes where needed) *********************\\
	@SuppressWarnings("finally")
	public User getUser(String i_username, String i_userPassword) throws SQLException
	{
		User res=null;
		
		try
		{
			Connection connection = openConnection();
			PreparedStatement queryStatement;
			ResultSet queryResult;
			
			queryStatement  =connection.prepareStatement("select * from CUSTOMERS where user_name=? and user_password=?;");
			queryStatement.setString(1, i_username);
			queryStatement.setString(2, i_userPassword);
			queryResult = queryStatement.executeQuery();
			
			if(queryResult.next())
			{
				String user_email=null, user_PicURL=null;
				String cust_fname=null, cust_lname=null;
				short cust_id=-1;
				//need to add isCustomer
				boolean isCustomer = true;
				
				cust_id = Short.parseShort(queryResult.getString(1));
				cust_fname = queryResult.getString(2);
				cust_lname = queryResult.getString(3);
				user_email = queryResult.getString(6);
				
				Customer curUser = new Customer(cust_id, i_username, i_userPassword, cust_fname + cust_lname, user_email, user_PicURL, isCustomer, null, null);
				//curUser.setCustomer(true);
				//curUser.setId(cust_id);
				//res = new Customer(curUser);
				res = curUser;
				
				/*
				 * NOTES:
				 * the 2 nulls in the c'tor are the scenarios list and the event list of the customer.
				 */
			}
			else
			{
				queryStatement  =connection.prepareStatement("select * from VENDORS where user_name=? and user_password=?;");
				queryStatement.setString(1, i_username);
				queryStatement.setString(2, i_userPassword);
				queryResult = queryStatement.executeQuery();
				
				if(queryResult.next())
				{
					String user_email=null;
					String ven_name=null, ven_desc=null, ven_logo_pic=null;
					short ven_id=-1;
					
					ven_id = Short.parseShort(queryResult.getString(1));
					ven_name = queryResult.getString(2);
					user_email = queryResult.getString(5);
					ven_desc = queryResult.getString(6);
					ven_logo_pic = queryResult.getString(7);
					boolean isCustomer = false;
					
					Vendor curVend = new Vendor(ven_id, i_username, i_userPassword, ven_name, user_email, null, isCustomer, ven_desc, ven_logo_pic, null);
					//curUser.setCustomer(false);
					//curUser.setId(ven_id);
					//Vendor curVend = new Vendor(curUser);
					//curVend.setDescription(ven_desc);
					res = curVend;
					
					/*
					 * NOTES:
					 * first null in c'tor = User_PicURL
					 * second null in c'tor = Vendor_ListOfProducts
					 */
				}
			}
		}	
		finally
		{
			closeConnection();
			if(res==null)
				throw new SQLException("No user found for provided credantials!");
			else
				return res;
		}
	}

	public Vendor getVendor(String name, String password)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Vendor getVendor(int vendor_id) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean removeDevice(int device_id)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean removeProduct(int productToRemove_id) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean updateProduct(int prod_id, Product new_product) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	public LinkedList<Scenario> getScenarios(int cust_id) 
	{
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

	public Customer getCustomer(String i_username, String i_password) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Customer getCustomer(int cust_id) 
	{
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
	
	//**************** CHANGE UNCERTAIN(included notes) ******************\\
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
			
			boolean isTriggered = false;
			boolean isEvent = true;
			
			ActionEventProto eve_prototype = new ActionEventProto(eve_proto, eve_name, eve_type, eve_prod_id, eve_deviceEP, isEvent);
			//Action eveWithParamAndDevID = new Action(eve_id, eve_deviceId, eve_param, eve_prototype);
			Event completeEvent = new Event(eve_id, eve_deviceId, eve_param, eve_prototype, eve_logicOperator, isTriggered);
			res = completeEvent;
			//res = new Event(eve_name, eve_type, eve_param, eve_logicOperator, eve_deviceEP, eve_deviceId);
			/*
			 * NOTES:
			 * 1. no need to create action and then deliver it to event.
			 * 2. need to fetch isTriggered from DB in case that the event has already happened.
			 * 3. need to fetch isEvent from DB to place in isEvent.
			 * watch booleans isTriggered, and boolean isEvent in c'tors lines 747, 749.
			 */
		}
		finally
		{
			closeConnection();
			return res;
		}
	}

	public void removeProduct(Product productToRemove)
	{
		
	}

	public short getIdForScenario() 
	{
		return getScenariosMaxAvailableIdx();
	}

	@SuppressWarnings("finally")
	public boolean addProduct(Product i_product) throws SQLException
	{
		final String sqlQuery =	"insert into PRODUCTS (vendor_id, product_name, product_pic, events_state, actions_state)"
				+ "values ("
				+"'"+i_product.getVendor_id()+"',"
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
		PreparedStatement queryingStatement=null;
		ResultSet queryResult=null;
		
		for(EntityAndIdxValue ev:EntityAndIdxValue.values())
		{
			queryingStatement = connection.prepareStatement(String.format(k_selectMaxID, ev.idxValColumn, ev.Entity));
			
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

	public LinkedList<Scenario> getScenariosByEvent(Event i_event)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEventUpdated(Event event) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}