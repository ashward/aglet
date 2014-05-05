package com.github.ashward.aglet.services.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.RegistrationService;
import com.github.ashward.aglet.services.RegistrationService.UsernameCheckResult;

@Service
@Path("register")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationRestService {
	private Logger log = Logger.getLogger(RegistrationRestService.class);

	@Autowired
	private RegistrationService registrationService;
	
	@GET
	@Path("usernameExists")
	public UsernameCheckResult getUsernameExists(@QueryParam("username") String username) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return registrationService.checkUsername(username);
	}
	/*
	 * @GET
	 * 
	 * @Path("register") public User register(final MultivaluedMap<String,
	 * String> form) { return ; }
	 */
}
