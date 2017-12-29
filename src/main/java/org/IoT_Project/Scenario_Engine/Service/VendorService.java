package org.IoT_Project.Scenario_Engine.Service;

import java.util.List;

import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.DBHandler;

public class VendorService {

	public org.IoT_Project.Scenario_Engine.Models.Vendor fetch(String i_name, String i_pswd) throws Exception{
		return DBHandler.getInstance().getVendor(i_name, i_pswd);
	}

	public  List<Product> addProduct(short vendor_id, Product i_product) throws Exception{		
		if(DBHandler.getInstance().addProduct(i_product))
		{
			return DBHandler.getInstance().getProducts(vendor_id);
		}
		else
			throw new Exception("no user");
	}


	public  List<Product> removeProduct(int vendor_id, Product i_prod2Remove) throws Exception{
		if(DBHandler.getInstance().removeProduct(i_prod2Remove.getID()))
		{
			return DBHandler.getInstance().getProducts(vendor_id);
		}
		else
		{
			throw new Exception("no user");
		}
	}

	public Vendor fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getVendor(i_user.getName(), i_user.getPassword());
	}

	public List<Product> updateProduct(short i_id, short i_deviceToUpdateId, Product i_prod) throws Exception{
		if(DBHandler.getInstance().updateProduct(i_deviceToUpdateId, i_prod))
		{
			return DBHandler.getInstance().getProducts(i_id);
		}
		else
			throw new Exception("no user");
		
	}

	public List<Product> fetchProducts(short i_userId) {
		return DBHandler.getInstance().getProducts(i_userId);
	}

}
