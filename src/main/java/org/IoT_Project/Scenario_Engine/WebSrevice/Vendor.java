package org.IoT_Project.Scenario_Engine.WebSrevice;

import org.IoT_Project.Scenario_Engine.Models.ActionEventProto;
import org.IoT_Project.Scenario_Engine.Models.Event;
import org.IoT_Project.Scenario_Engine.Models.Product;
import org.IoT_Project.Scenario_Engine.Models.User;
import org.IoT_Project.Scenario_Engine.Service.UserService;
import org.IoT_Project.Scenario_Engine.Service.VendorService;

import DataBase.DBHandler;
import DataBase.NDBHandler;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.LinkedList;
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
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Path("vendor")
public class Vendor{

	private static VendorService vs = new VendorService();
	private static UserService us = new UserService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLogInPageVendor() 
	{
		//return Response.status(Status.OK).entity("Should return the login page").build();
		/*
		 *  For testing product creation
		 */ 
		
		List<String> paramsName = new LinkedList<String>();
		List<String> types = new LinkedList<String>();
		paramsName.add("value");
		types.add("int");
		List<ActionEventProto> a = new LinkedList<ActionEventProto>();
		ActionEventProto aep = new ActionEventProto((short)1,
			     "Is TV on channel X",
			    "description of event",
			     types,
			     null,
			     paramsName,
			     null,
			     null,
			     null,
			    (short)1,
			    "http://localhost:8000",
			    true);
		a.add(aep);
		Product p = new Product((short)1,
								(short)2,
								"LG",
								"LG TV",
								"LG TV mannual",
								null,
								"http://localhost:8080/Scenario_Engine/webapi",
								(LinkedList<ActionEventProto>) a);
		return Response.status(Status.OK).entity(p).build();
		//*/
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	//MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchVendor(org.IoT_Project.Scenario_Engine.Models.User i_user) 
	{
		try {
			return fetch(i_user);
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	@Path("/new")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addVendor(org.IoT_Project.Scenario_Engine.Models.Vendor i_user)
	{
		try {
			org.IoT_Project.Scenario_Engine.Models.Vendor userToAdd = us.addVendor(i_user);
			NDBHandler.getInstance().addVendor(userToAdd);
			org.IoT_Project.Scenario_Engine.Models.Vendor userAdded = NDBHandler.getInstance().getVendor(i_user.getUserName(), i_user.getPassword());
			return Response.status(Status.CREATED).entity(userAdded).build();
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
	public Response addProduct(@PathParam("vendor_id") short ven_id, Product i_product)
	{
		try
		{
			i_product.setVendor_id(ven_id);
			List<Product> products = vs.addProduct(i_product);
			return Response.status(Status.OK).entity(products).build();
		}
		catch(Exception ex)
		{
			return this.handleError(ex);
		}
	}
	
	@Path("{vendor_id}/{dev_id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProduct(Product i_prod, @PathParam("vendor_id") short vendor_id, @PathParam("dev_id") short i_deviceId)
	{
		try {
			List<Product> updatedProducts = vs.updateProduct(vendor_id, i_deviceId,  i_prod);
			return Response.status(Status.OK).entity(updatedProducts).build();
		}
		catch(Exception ex)
		{
			return this.handleError(ex);
		}
	}
	
	@Path("{vendor_id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProduct(Product i_prod2Remove, @PathParam("vendor_id") short vendor_id)
	{
		try {
			List<Product> updatedProducts = vs.removeProduct(vendor_id, i_prod2Remove);
			return Response.status(Status.OK).entity(updatedProducts).build();
		}
		catch(Exception ex)
		{
			return this.handleError(ex);
		}
	}
	
	@Path("/product/{vendor_id}/{isFullData}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchProducts(@PathParam("vendor_id") short i_userId, @PathParam("isFullData") boolean i_isFullData)
	{
		try {
			List<Product> products = vs.fetchProducts(i_userId);
			Response result; 
			if(!i_isFullData)
			{
				LinkedList<tmpContainers.ProdNameIDContainer> formattedProdLST = new LinkedList<tmpContainers.ProdNameIDContainer>();

				for(Product prod: products)
				{
					formattedProdLST.add(new tmpContainers.ProdNameIDContainer(prod.getName(), prod.getId()));
				}
						
			 	result = Response.status(Status.OK).entity(formattedProdLST).build();
			}
			else
			{
				result = Response.status(Status.OK).entity(products).build();
			}
			
			return result;
		}
		catch(Exception ex)
		{
			return this.handleError(ex);
		}
	}
	
	private Response fetch(User i_user)
	{
		try {
			User user = us.fetch(i_user);
			return Response.status(Status.OK).entity(user).build();
		}
		catch(Exception ex)
		{
			return handleError(ex);
		}
	}
	
	protected Response handleError(Exception ex)
	{
		org.IoT_Project.Scenario_Engine.Models.Error er = new org.IoT_Project.Scenario_Engine.Models.Error();
		er.setDescription(ex.getMessage());
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(er).build();
	}
}
