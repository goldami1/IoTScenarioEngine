package org.IoT_Project.Scenario_Engine.Service;

import java.util.List;

import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.DBHandler;

public class VendorService {

	public org.IoT_Project.Scenario_Engine.Models.Vendor fetch(String i_name, String i_pswd) throws Exception{
		return DBHandler.getInstance().getVendor(i_name, i_pswd);
	}

	public  void addProduct(int i_id, Product i_product) throws Exception{		
		DBHandler.getInstance().addProduct(i_product);
	}


	public  void removeProduct(int i_id, Product i_prod2Remove) throws Exception{
		DBHandler.getInstance().removeProduct(i_prod2Remove.getID());
	}

	public Vendor fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getVendor(i_user.getName(), i_user.getPassword());
	}

	public void updateProduct(int i_id, int i_deviceToUpdateId, Product i_prod) throws Exception{
		DBHandler.getInstance().updateProduct(i_deviceToUpdateId, i_prod);
	}

	public List<Product> fetchProducts(short i_userId) {
		return DBHandler.getInstance().getProducts(i_userId);
	}

}
