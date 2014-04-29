package com.github.ashward.aglet.services.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.UserService;

@Path("users")
@Component
public class UserRestService {
	private Logger log = Logger.getLogger(UserRestService.class);
	
	@Autowired(required=true)
	private UserDAO userDAO;

	@Autowired(required=true)
	private UserService userService;

	@GET
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User get(@PathParam("username") final String username) {
		log.debug(userDAO);
		return userDAO.findUserByUsername(username);
	}

	@PUT
	@Path("{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void put(@PathParam("username") final String username,
			final String json) throws JsonProcessingException, IOException {
		User user = userDAO.createNew(username);

		ObjectMapper mapper = new ObjectMapper();
		ObjectReader reader = mapper.readerForUpdating(user);
		reader.readTree(json);
	}
}
