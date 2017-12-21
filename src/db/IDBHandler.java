package db;

import java.sql.SQLException;
import java.util.LinkedList;

import engine.Customer;
import engine.Device;
import engine.Event;
import engine.Scenario;
import engine.User;
import engine.Vendor;

public interface IDBHandler {
	public boolean userConnectionAuth(String i_Username, String i_UserPassword) throws Exception;
	public boolean addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email) throws Exception;
	public boolean addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic) throws SQLException;
	public LinkedList<String> selectQ(String i_selectQ) throws Exception;
	public boolean addDevice(Device i_dev);
	
	public boolean addScenario(Scenario i_Scenario);
	public LinkedList<Scenario> getScenariosByEvent(Event i_event);
	
	//TODO:
	public User getUser(String i_Username, String i_UserPassword) throws Exception;
	public LinkedList<Device> getDevices(short i_UserID);
	
}