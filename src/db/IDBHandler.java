package db;

import engine.Vendor;
import engine.Customer;

public interface IDBHandler {

	public IDBHandler getInstance();
	public boolean vendorConnectionAuth(Vendor i_vendor);
	public boolean customerConnectionAuth(Customer i_customer);
	
}

