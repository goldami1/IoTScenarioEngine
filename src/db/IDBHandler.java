package db;

import engine.Customer;
import engine.User;
import engine.Vendor;

public interface IDBHandler {

	public void addCustomer(String i_firstName, String i_lastName, String i_userName, String i_userPassword, String i_email);
	public void addVendor(String i_vendorName, String i_userName, String i_userPassword, String i_email, String i_vendorDescription, String i_vendorLogoPic);
	public boolean customerConnectionAuthentication(String i_CustomerUsername, String i_CustomerPassword);
	public boolean vendorConnectionAuthentication(String i_VendorUsername, String i_VendorPassword);
	public boolean isExistVendor(String i_VendorUsername);
	public boolean isExistCustomer(String i_CustomerUsername);
}