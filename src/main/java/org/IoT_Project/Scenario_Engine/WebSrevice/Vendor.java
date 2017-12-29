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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("vendor")
public class Vendor extends org.IoT_Project.Scenario_Engine.WebSrevice.User{

	private static VendorService vs = new VendorService();
	
	@Path("/{vendor_id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product i_product, @PathParam("vendor_id") short vendor_id)
	{
		Response res = null;
		try {
			List<Product> products = vs.addProduct(vendor_id, i_product);
			res = Response.status(Status.FOUND).entity(products).build();
		}
		catch(Exception ex)
		{
			res = this.handleError(ex);
		}
		return res;
	}
	
	@Path("{vendor_id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product i_prod, @PathParam("vendor_id") short vendor_id, @PathParam("dev_id") short i_deviceId)
	{
		Response res = null;
		try {
			List<Product> updatedProducts = vs.updateProduct(vendor_id, i_deviceId,  i_prod);
			res = Response.status(Status.OK).entity(updatedProducts).build();
		}
		catch(Exception ex)
		{
			res = this.handleError(ex);
		}
		return res;
	}
	
	@Path("{vendor_id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(Product i_prod2Remove, @PathParam("vendor_id") short vendor_id)
	{
		Response res = null;
		try {
			List<Product> updatedProducts = vs.removeProduct(vendor_id, i_prod2Remove);
			res = Response.status(Status.OK).entity(updatedProducts).build();
		}
		catch(Exception ex)
		{
			res = this.handleError(ex);
		}
		return res;
	}
	
	@Path("/product/{vendor_id}")
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public Response fetchProducts(@PathParam("vendor_id") short i_userId)
	{
		Response res = null;
		try {
			List<Product> products = vs.fetchProducts(i_userId);
			res = Response.status(Status.FOUND).entity(products).build();
		}
		catch(Exception ex)
		{
			res = this.handleError(ex);
		}
		return res;
	}
}
