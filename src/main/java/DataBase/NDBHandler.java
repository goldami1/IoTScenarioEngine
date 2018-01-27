package DataBase;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import org.eclipse.persistence.internal.jaxb.many.MapEntry;
import org.hibernate.query.Query;
import javafx.util.*;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;



public class NDBHandler implements IDBHandler {
	private static NDBHandler m_Instance = null;
	protected SessionFactory m_sessionFactory = null;
	private Session m_Session = null;
	
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
	
	
	public boolean userConnectionAuth(String i_Username, String i_UserPassword) throws Exception
	{
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<User> query = m_Session.createQuery("FROM User WHERE user_name = :username AND user_password = :userpass", User.class);
		query.setParameter("username", i_Username);
		query.setParameter("userpass", i_UserPassword);
		List<User> res = query.getResultList();
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.size()>0;
	}

	
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword,
			String i_email) throws Exception
	{
		Customer customer = new Customer();		
		
		customer.setName(i_firstName + " " + i_lastName);
		customer.setUserName(i_userName);
		customer.setPassword(i_userPassword);
		customer.setEmail(i_email);
		
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(customer);
		
		try
		{
		m_Session.getTransaction().commit();
		}catch(ConstraintViolationException e) {throw new Exception("Can't create a user with existing email / username");}
		m_Session.close();
		return true;
	}

	
	public Customer addCustomer(Customer i_User) throws Exception {
		if(addCustomer(i_User.getName(), i_User.getName(), i_User.getUserName(), i_User.getPassword(), i_User.getEmail()))
			return i_User;
		else
			return null;
	}
	
	
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email,
			String i_vendorDescription, String i_vendorLogoPic) throws Exception {
		Vendor vendor = new Vendor();		
		
		vendor.setName(i_vendorName);
		vendor.setUserName(i_userName);
		vendor.setPassword(i_userPassword);
		vendor.setEmail(i_email);
		vendor.setDescription(i_vendorDescription);
		vendor.setLogoPicURL(i_vendorLogoPic);
		
		
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(vendor);
		
		try
		{
		m_Session.getTransaction().commit();
		}catch(ConstraintViolationException e) {throw new Exception("Can't create a user with existing email / username");}
		m_Session.close();
		return true;
	}
	
	
	public void addVndor(Vendor i_vendor) throws Exception
	{
		addVendor(i_vendor.getName(), i_vendor.getUserName(), i_vendor.getPassword(), i_vendor.getEmail(), i_vendor.getDescription(), i_vendor.getLogoPicURL());
	}
	
	
	public Vendor addVendor(Vendor i_vendor) throws Exception
	{
		if(addVendor(i_vendor.getName(), i_vendor.getUserName(), i_vendor.getPassword(), i_vendor.getEmail(), i_vendor.getDescription(), i_vendor.getLogoPicURL()))
			return i_vendor;
		else
			return null;
	}
	
	
	//Images support
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email,
			String i_vendorDescription, BufferedImage i_logo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public User getUser(String i_username, String i_userPassword) throws Exception
	{				
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		/*
		CriteriaBuilder critbuilder = m_Session.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = critbuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		criteriaQuery.where(critbuilder.equal(root.get("user_name"), "goldami1"));
		*/
		Query<User> query = m_Session.createQuery("FROM User WHERE user_name = :username AND user_password = :userpass", User.class);
		query.setParameter("username", i_username);
		query.setParameter("userpass", i_userPassword);
		List<User> res = query.getResultList();
		
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception(i_username + "user couldn't be found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.get(0);
	}
	
	
	public Customer getCustomer(String i_username, String i_password) throws Exception {
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Customer> query = m_Session.createQuery("FROM Customer WHERE user_name = :username AND user_password = :userpass", Customer.class);
		query.setParameter("username", i_username);
		query.setParameter("userpass", i_password);
		List<Customer> res = query.getResultList();
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception(i_username + "user couldn't be found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.get(0);
	}

	
	public Customer getCustomer(int cust_id) throws Exception {
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Customer> query = m_Session.createQuery("FROM Customer WHERE customer_id = :custid", Customer.class);
		query.setParameter("custid", cust_id);
		List<Customer> res = query.getResultList();
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("a customer with id:"+cust_id+" couldn't be found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.get(0);
	}


	public LinkedList<Pair<Short, String>> getVendors() throws Exception
	{
		LinkedList<Pair<Short, String>> res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Vendor> query = m_Session.createQuery("FROM Vendor", Vendor.class);
		List<Vendor> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = new LinkedList<Pair<Short, String>>();
			for(Vendor v: tmpres)
			{
				res.add(new Pair<Short, String>(v.getId(), v.getName()));
			}
		}
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("No vendors found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res;
	}

	
	public Vendor getVendor(String i_username, String i_password) throws Exception {
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Vendor> query = m_Session.createQuery("FROM Vendor WHERE user_name = :username AND user_password = :userpass", Vendor.class);
		query.setParameter("username", i_username);
		query.setParameter("userpass", i_password);
		List<Vendor> res = query.getResultList();
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception(i_username + "user (vendor) couldn't be found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.get(0);
	}

	
	public Vendor getVendor(int vendor_id) throws Exception {
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Vendor> query = m_Session.createQuery("FROM Vendor WHERE vendor_id = :venid", Vendor.class);
		query.setParameter("venid", vendor_id);
		List<Vendor> res = query.getResultList();
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("a customer (vendor) with id:"+vendor_id+" couldn't be found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res.get(0);
	}
	
	
	public boolean addProduct(Product i_product) throws Exception
	{
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(i_product);
		for(ActionEventProto aep : i_product.getActionAndEventList())
		{
			m_Session.save(aep);
		}
		try
		{
		m_Session.getTransaction().commit();
		}catch(Exception e) {throw new Exception("Can't add given product!");}
		m_Session.close();
		return true;
	}

	
	public LinkedList<Product> getProductsByProdID(int i_prod_id) throws Exception
	{
		List<Product> res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Product> query = m_Session.createQuery("FROM Product WHERE product_id = :prodid", Product.class);
		query.setParameter("prodid", i_prod_id);
		List<Product> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = tmpres;
		}
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("No products found with product id "+i_prod_id+"!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return new LinkedList<Product>(res);
	}

	
	public List<Product> getProducts(int vendor_id) throws Exception
	{
		List<Product> res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Product> query = m_Session.createQuery("FROM Product WHERE vendor_id = :venid", Product.class);
		query.setParameter("venid", (short)vendor_id);
		List<Product> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = tmpres;
		}
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("No products found!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res;
	}

	
	public boolean addDevice(Device i_dev) throws Exception {
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(i_dev);
		try
		{
			m_Session.getTransaction().commit();
		}catch(Exception e) {throw new Exception("Can't add given device!");}
		m_Session.close();
		return true;
	}
	
	
	public boolean addScenario(Scenario i_Scenario) throws Exception
	{
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(i_Scenario);
		for(Action act : i_Scenario.getActions())
		{
			m_Session.save(act);
		}
		for(Event eve : i_Scenario.getEventsToHappen().values())
		{
			m_Session.save(eve);
		}
		try
		{
		m_Session.getTransaction().commit();
		}catch(Exception e) {throw new Exception("Can't add provided scenario!");}
		m_Session.close();
		return true;
	}

	
	public LinkedList<Scenario> getScenarios(int cust_id) throws Exception
	{
		List<Scenario> res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Scenario> query = m_Session.createQuery("FROM Scenario WHERE customer_id = :custid", Scenario.class);
		query.setParameter("custid", cust_id);
		List<Scenario> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = tmpres;
		}
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("No scenarios found for customer id:"+cust_id+"!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return new LinkedList<>(res);
	}
	
	
	public void addAction(Action i_action) throws Exception
	{
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(i_action);
		/*
		for(String param : i_action.getParameters())
		{
			m_Session.save(param);
		}*/
		
		try
		{
		m_Session.getTransaction().commit();
		}catch(Exception e) {throw new Exception("Can't add provided action!");}
		m_Session.close();
	}

	
	public void addEvent(Event i_event) throws Exception
	{
		m_Session = m_sessionFactory.openSession();
		m_Session.beginTransaction();
		
		m_Session.save(i_event);
		/*
		for(String param : i_action.getParameters())
		{
			m_Session.save(param);
		}*/
		
		try
		{
		m_Session.getTransaction().commit();
		}catch(Exception e) {throw new Exception("Can't add provided event!");}
		m_Session.close();
	}
	
	
	public Event getEvent(int i_event_id) throws Exception
	{
		Event res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Event> query = m_Session.createQuery("FROM Event WHERE event_id = :eveid", Event.class);
		query.setParameter("custid", i_event_id);
		List<Event> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = tmpres.remove(0);
		}
		
		
		if(tmpres.size()==0)
		{
			m_Session.close();
			throw new Exception("No event found for event id:"+i_event_id+"!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return res;
	}

	
	public LinkedList<Device> getDevices(short i_UserID) throws Exception
	{
		List<Device> res = null;
		
		try
		{
		m_Session = m_sessionFactory.openSession();
		}catch(NullPointerException e) {throw new Exception("DB Critical Error# SessionFactory isn't initialized");}

		m_Session.beginTransaction();
		
		Query<Device> query = m_Session.createQuery("FROM Device WHERE customer_id = :userid", Device.class);
		query.setParameter("userid", i_UserID);
		List<Device> tmpres = query.getResultList();
		
		if(tmpres.size()>0)
		{
			res = tmpres;
		}
		
		
		if(res.size()==0)
		{
			m_Session.close();
			throw new Exception("No devices found for user id:"+i_UserID+"!");
		}
		
		m_Session.getTransaction().commit();
		m_Session.close();
		
		return new LinkedList<>(res);
	}
	}
