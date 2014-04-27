package com.github.ashward.aglet.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.UserService;

@Path("users")
public class UserRestService {
	@Autowired
	private UserService userService;
	
	@GET @Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@PathParam("username") final String username)
	{
		return userService.findUserByUsername(username);
	}
	
	@PUT @Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void put(@PathParam("username") final String username, final User user)
	{
		
	}
}
