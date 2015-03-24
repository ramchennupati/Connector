package com.cookbook.tutorial;

import com.cookbook.tutorial.service.*;
import org.parboiled.common.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/ingredient")
public class IngredientResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("token") String token,@QueryParam("query") String query, @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			if(StringUtils.isEmpty(query)){
				query="GET ALL FROM INGREDIENT";
			}
			if(page==null){
				page = 0;
			}
			if(pageSize==null){
				pageSize = 0;
			}
			return Response.status(200).entity(DALProvider.getInstance().searchWithQuery(query,page,pageSize)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id,@HeaderParam("token") String token) {

		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(200).entity(DALProvider.getInstance().get(id)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Ingredient ingredient,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(201).entity(DALProvider.getInstance().create(ingredient)).build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") String id,Ingredient ingredient,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(200).entity(DALProvider.getInstance().update(ingredient)).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity(e.getMessage()).build();

		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id,@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			DALProvider.getInstance().delete(id);
			return Response.status(200).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		}
	}

	@GET
	@Path("describe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@HeaderParam("token") String token) {
		if(!Constants.DEFAULT_TOKEN.equals(token)){
			return Response.status(401).entity("Invalid Token").build();
		}
		try {
			return Response.status(200).entity(DALProvider.getInstance().describeEntity(new Ingredient())).build();
		} catch (NoSuchEntityException e) {
			return Response.status(404).entity("No such entity").build();
		} catch (InvalidEntityException e) {
			return Response.status(400).entity("Error").build();
		}
	}
}