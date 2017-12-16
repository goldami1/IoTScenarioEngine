package engine;

import db.DBHandler;

public class Device {
	
	private short id, product_id, customer_id;
	private Product deviceProto;
	
	public Device(Product i_Product, Customer i_Customer)
	{
		deviceProto = i_Product;
		product_id = i_Product.getID();
		customer_id = i_Customer.getID();
		DBHandler db = DBHandler.getInstance();
		db.addDevice(this);
	}
}
