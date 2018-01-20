package org.IoT_Project.Scenario_Engine.Service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.IoT_Project.Scenario_Engine.Models.*;
import DataBase.DBHandler;
import DataBase.NDBHandler;

public class VendorService {

	public org.IoT_Project.Scenario_Engine.Models.Vendor fetch(String i_name, String i_pswd) throws Exception{
		try {
			return DBHandler.getInstance().getVendor(i_name, i_pswd);
		}
		catch(SQLException ex)
		{
			ErrorException eex = new ErrorException(ex.getMessage());
			eex.setStatus(Status.INTERNAL_SERVER_ERROR);
			throw eex;
		}
	}

	public  List<Product> addProduct(Product i_product) throws Exception{
		Product newProduct = new Product(i_product);
		if(NDBHandler.getInstance().addProduct(newProduct))
		{
			try {
				return NDBHandler.getInstance().getProducts(newProduct.getVendor_id());
			}
			catch(SQLException ex)
			{
				ErrorException eex = new ErrorException(ex.getMessage());
				eex.setStatus(Status.INTERNAL_SERVER_ERROR);
				throw eex;
			
			}
		}
		else
		{
			ErrorException ex = new ErrorException("no user");
			ex.setStatus(Status.NOT_FOUND);
			throw ex;
		}
	}


	public  List<Product> removeProduct(int vendor_id, Product i_prod2Remove) throws Exception{
		if(DBHandler.getInstance().removeProduct(i_prod2Remove.getId()))
		{
			try {
				return DBHandler.getInstance().getProducts(vendor_id);
			}
			catch(SQLException ex)
			{
				ErrorException eex = new ErrorException(ex.getMessage());
				eex.setStatus(Status.INTERNAL_SERVER_ERROR);
				throw eex;
			
			}
		}
		else
		{
			ErrorException ex = new ErrorException("no user");
			ex.setStatus(Status.NOT_FOUND);
			throw ex;
		}
	}

	public Vendor fetch(User i_user) throws Exception{
		return DBHandler.getInstance().getVendor(i_user.getName(), i_user.getPassword());
	}

	public List<Product> updateProduct(short i_id, short i_deviceToUpdateId, Product i_prod) throws Exception{
		if(DBHandler.getInstance().updateProduct(i_deviceToUpdateId, i_prod))
		{
			try {
				return DBHandler.getInstance().getProducts(i_id);
			}
			catch(SQLException ex)
			{
				ErrorException eex = new ErrorException(ex.getMessage());
				eex.setStatus(Status.INTERNAL_SERVER_ERROR);
				throw eex;
			}
		}
		else
		{
			ErrorException ex = new ErrorException("no user");
			ex.setStatus(Status.NOT_FOUND);
			throw ex;
		}
		
	}

	public List<Product> fetchProducts(short i_userId) throws SQLException {
		return DBHandler.getInstance().getProducts(i_userId);
	}
}
