package org.IoT_Project.Scenario_Engine.Service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.DBHandler;
import DataBase.NDBHandler;

public class VendorService {

	public org.IoT_Project.Scenario_Engine.Models.Vendor fetch(String i_name, String i_pswd) throws Exception{
			return NDBHandler.getInstance().getVendor(i_name, i_pswd);
	}

	public  List<Product> addProduct(Product i_product) throws Exception{
		Product newProduct = new Product(i_product);
		if(NDBHandler.getInstance().addProduct(newProduct))
		{
				return NDBHandler.getInstance().getProducts(newProduct.getVendor_id());
		}
		else
			throw new Exception("no User");
	}


	public  List<Product> removeProduct(int vendor_id, Product i_prod2Remove) throws Exception{
		if(NDBHandler.getInstance().removeProduct(i_prod2Remove.getId()))
		{
			return NDBHandler.getInstance().getProducts(vendor_id);
		}
		else
			throw new Exception("no User, or no Product to remove");
	}

	public Vendor fetch(User i_user) throws Exception{
		return NDBHandler.getInstance().getVendor(i_user.getName(), i_user.getPassword());
	}

	public List<Product> updateProduct(short i_id, short i_deviceToUpdateId, Product i_prod) throws Exception{
		if(NDBHandler.getInstance().updateProduct(i_deviceToUpdateId, i_prod))
		{
			return NDBHandler.getInstance().getProducts(i_id);
		}
		else
			throw new Exception("no User, or no Product to update");
		
	}

	public List<Product> fetchProducts(short i_userId) throws Exception {
		List<Product> res = null;
		/*
		List<Product> res = new LinkedList<Product>();
		LinkedList<String> typeslst = new LinkedList<String>();
		LinkedList<String> supportedParametersName = new LinkedList<String>();
		LinkedList<String> min = new LinkedList<String>();
		LinkedList<String> max = new LinkedList<String>();
		typeslst.add("int");
		typeslst.add("string");
		supportedParametersName.add("name of property no.1");
		supportedParametersName.add("name of property no.2");
		min.add("0");
		max.add("5");
		min.add(null);
		max.add(null);
		LinkedList<ActionEventProto> actionEveProto = new LinkedList<ActionEventProto>();
		ActionEventProto actionproto = new ActionEventProto((short)1, "lightSensor", "light Sensor description...", typeslst , null, supportedParametersName, min, max, (short)0, "http://www.honda.com/endpoint", false);
		actionEveProto.add(actionproto);
		Product prod = new Product((short)0, (short)12, "Honda", "Smart Lamp", "smart lamp description...", "http://www.honda.com/smartlamp.jpg", "http://www.honda.com/endpoint", actionEveProto);
		
		res.add(prod);
		
		return res;*/
		//TODO
		res = NDBHandler.getInstance().getProducts(i_userId);
		if(res != null)
		{
			return res;
		}
		else
		{
			throw new Exception("Couldn't fetch products and return a list of available products");
		}
	}
}
