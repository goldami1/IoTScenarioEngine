package DataBase;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.LinkedList;

import org.IoT_Project.Scenario_Engine.Models.Action;
import org.IoT_Project.Scenario_Engine.Models.Customer;
import org.IoT_Project.Scenario_Engine.Models.Device;
import org.IoT_Project.Scenario_Engine.Models.Event;
import org.IoT_Project.Scenario_Engine.Models.Product;
import org.IoT_Project.Scenario_Engine.Models.Scenario;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Models.Vendor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.util.Pair;

public class NDBHandler implements IDBHandler {
	private static NDBHandler m_Instance = null;
	protected SessionFactory m_sessionFactory = null;
	
	protected void setup()
	{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try
		{
			m_sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
		}
		catch(Exception e)
		{
			StandardServiceRegistryBuilder.destroy(registry);
			e.printStackTrace();
		}
	}
	
	protected void exit()
	{
		m_sessionFactory.close();
	}
	
	public static NDBHandler getInstance() {
		if (m_Instance == null)
			m_Instance = new NDBHandler();
		return m_Instance;
	}

	private NDBHandler() {
		this.setup();
	}
	
	
	public boolean userConnectionAuth(String i_Username, String i_UserPassword) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword,
			String i_email) throws SQLException
	{
		Customer customer = new Customer();
		
		customer.setName(i_firstName + " " + i_lastName);
		customer.setUserName(i_userName);
		customer.setPassword(i_userPassword);
		customer.setEmail(i_email);
		Session session=null;
		if(m_sessionFactory!=null)
			session = m_sessionFactory.openSession();
		else
		{
			this.setup();
			session = m_sessionFactory.openSession();
		}
		session.beginTransaction();
		
		session.save(customer);
		
		session.getTransaction().commit();
		session.close();
		return true;
	}

	
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email,
			String i_vendorDescription, String i_vendorLogoPic) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public LinkedList<String> selectQ(String i_selectQ) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean addDevice(Device i_dev) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean addScenario(Scenario i_Scenario) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email,
			String i_vendorDescription, BufferedImage i_logo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public short getIdForScenario() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public boolean addProduct(Product i_product) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public LinkedList<Pair<Short, String>> getVendors() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LinkedList<Product> getProductsByProdID(int i_prod_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LinkedList<Product> getProducts(int vendor_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean addDevice(int product_id, int customer_id, int device_serial) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void addVndor(Vendor i_vendor) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
	public boolean removeDevice(int device_id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public User getUser(String i_username, String i_userPassword) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Vendor getVendor(String i_username, String i_password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Vendor getVendor(int vendor_id) throws SQLException {
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

	
	public LinkedList<Scenario> getScenarios(int cust_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Customer addCustomer(Customer i_User) throws SQLException {
		if(addCustomer(i_User.getName(), i_User.getName(), i_User.getUserName(), i_User.getPassword(), i_User.getEmail()))
			return i_User;
		else
			return null;
	}

	
	public void addAction(Action action) {
		// TODO Auto-generated method stub
		
	}

	
	public Customer getCustomer(String i_username, String i_password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Customer getCustomer(int cust_id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void addEvent(Event event) {
		// TODO Auto-generated method stub
		
	}

	
	public boolean updateDevice(int origionalDevice_id, Device newDevice) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean updateScenario(int origionalScenario_id, Scenario newScenario) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean removeScenario(int scenarioToRemove_id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public Event getEvent(int event_id) throws SQLException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LinkedList<Device> getDevices(short i_UserID) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void removeProduct(Product productToRemove) {
		// TODO Auto-generated method stub
		
	}

}
