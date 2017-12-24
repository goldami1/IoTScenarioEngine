package org.IoT_Project.Scenario_Engine.Models;

import DataBase.DBHandler;

public class Device {
	
	private short id, serial_number, product_id, customer_id;
	private Product deviceProto;
	
	public Device(Product i_Product, Customer i_Customer, short serial_number)
	{
		deviceProto = i_Product;
		product_id = i_Product.getID();
		customer_id = i_Customer.getID();
		this.serial_number = serial_number;
		DBHandler db = DBHandler.getInstance();
		db.addDevice(this);
	}
	
	//Getters&Setters
	public short getProductID()
	{
		return this.product_id;
	}
	
	public short getSerialNumber()
	{
		return this.serial_number;
	}
	
	public short getCustomerID()
	{
		return this.customer_id;
	}
}
