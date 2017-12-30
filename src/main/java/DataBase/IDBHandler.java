package DataBase;

import java.sql.SQLException;
import java.util.LinkedList;

import org.IoT_Project.Scenario_Engine.Models.*;
import javafx.util.Pair;

public interface IDBHandler {
	public boolean userConnectionAuth(String i_Username, String i_UserPassword) throws SQLException;
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) throws SQLException;
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) throws SQLException;
	public LinkedList<String> selectQ(String i_selectQ) throws SQLException;
	public boolean addDevice(Device i_dev) throws SQLException;	
	public boolean addScenario(Scenario i_Scenario) throws SQLException, Exception;
	public LinkedList<Scenario> getScenariosByEvent(Event i_event);
	public short getIdForScenario();
	public void addProduct(Product i_product) throws SQLException;
	public LinkedList<Pair<Short,String>> getVendors(); //pair(id,name)
	public LinkedList <Product> getProducts(int vendor_id);
	public LinkedList <Device> getDevices(int user_id);
	public boolean addDevice(int product_id,int customer_id,int device_serial) throws SQLException ;
	public boolean removeDevice(int device_id);
	public IUser getUser(String i_username, String i_userPassword) throws SQLException;
	public Vendor getVendor(String i_username, String i_password);
	public Vendor getVendor(int vendor_id);
	public boolean removeProduct(int productToRemove_id);
	public boolean updateProduct(int prod_id, Product new_product);
	public LinkedList<Scenario> getScenarios(int cust_id);
	public Customer addCustomer(Customer i_User);
	public Customer getCustomer(String name, String password);
	public Customer getCustomer(int cust_id);
	public boolean updateDevice(int origionalDevice_id, Device newDevice);
	public boolean updateScenario(int origionalScenario_id, Scenario newScenario);
	public boolean removeScenario(int scenarioToRemove_id);
	public Event getEvent(int event_id) throws SQLException, Exception;
	
	//TODO:
	//2. Make Exception classes for login/signin/
	
	
	public LinkedList<Device> getDevices(short i_UserID);
	void removeProduct(Product productToRemove);
	
}