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
	
	public Device(Product i_product, short cust_id, short serial_num)
	{
		this.deviceProto = i_product;
		this.customer_id = cust_id;
		this.id = DBHandler.getInstance().getIdForDevice();
		this.serial_number = serial_number;
	}
	
	public Device(Device i_device, short cust_id)
	{
		this.id = DBHandler.getInstance().getIdForDevice();
		this.serial_number = i_device.getSerialNumber();
		this.product_id = i_device.getProductID();
		this.customer_id = cust_id;
		this.deviceProto = i_device.getProduct();
	}
	
	//Getters&Setters
	public short getProductID()
	{
		return this.product_id;
	}
	
	public Product getProduct()
	{
		return this.deviceProto;
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
