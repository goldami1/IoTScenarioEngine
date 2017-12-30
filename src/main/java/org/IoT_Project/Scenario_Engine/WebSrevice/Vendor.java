package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Models.Product;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Service.UserService;
import org.IoT_Project.Scenario_Engine.Service.VendorService;

import java.sql.SQLException;
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
public class Vendor{

	private static VendorService vs = new VendorService();
	private static UserService us = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogInPageVendor() 
	{
		//return "Should return the login page";
		try {
			return Response.status(Status.OK).entity((User)new org.IoT_Project.Scenario_Engine.Models.Vendor(new User("gal", "open613", "gal rotenberg", "gal@walla.com", ""))).build();
		}
		catch(Exception ex)
		{
			return Response.status(Status.NOT_ACCEPTABLE).entity(ex.getMessage()).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	//MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchVendor(org.IoT_Project.Scenario_Engine.Models.User i_user) throws Exception
	{
		return fetch(i_user);
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVendor(User i_user)
	{
		try {
			User userToAdd = us.addVendor(i_user);
			return Response.status(Status.CREATED).entity(userToAdd).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("/{vendor_id}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
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
	@Produces(MediaType.APPLICATION_JSON)
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
	@Produces(MediaType.APPLICATION_JSON)
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
	@Produces(MediaType.APPLICATION_JSON)
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
	
	private Response fetch(User i_user)
	{
		try {
			User user = us.fetch(i_user);
			return Response.status(Status.FOUND).entity(user).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	protected Response handleError(Exception ex)
	{
		Response res = null;
		org.IoT_Project.Scenario_Engine.Models.Error er = new org.IoT_Project.Scenario_Engine.Models.Error();
		er.setDescription(ex.getMessage());
		res = Response.status(Status.NOT_FOUND).entity(er).build();
		return res;
	}
}
