package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Models.Product;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Service.VendorService;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("vendor")
public class Vendor {

	private static VendorService vs = new VendorService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getLoginPage()
	{
		return "Should return login page for vendor";
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)	//MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public org.IoT_Project.Scenario_Engine.Models.User fetchVendor(User i_user) throws Exception
	{
		return vs.fetch(i_user);
	}
	
	@Path("/{id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String addProduct(Product i_product, @PathParam("id") int i_id)
	{
		
		try {
			vs.addProduct(i_id, i_product);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "added new product";
	}
	
	@Path("{id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateProduct(Product i_prod, @PathParam("id") int i_id, @PathParam("dev_id") int i_deviceId)
	{
		int prodToUpdate = 1, origionalProd = 0;
		try {
			vs.updateProduct(i_id, i_deviceId,  i_prod);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "updated product";
	}
	
	@Path("{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteProduct(Product i_prod2Remove, @PathParam("id") int i_id)
	{
		try {
			vs.removeProduct(i_id, i_prod2Remove);
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		return "product delted";
	}
	
	@Path("/product/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> fetchProducts(@PathParam("user_id")short i_userId)
	{
		return vs.fetchProducts(i_userId);
	}
}
