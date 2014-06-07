package com.github.ashward.aglet.services.rest;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.github.ashward.aglet.auth.AuthenticationNotRequired;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.RegistrationService;
import com.github.ashward.aglet.services.RegistrationService.Registration;
import com.github.ashward.aglet.services.UserService;

@Service
@Path("register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationRestService {
	private Logger log = Logger.getLogger(RegistrationRestService.class);

	@Autowired
	private RegistrationService registrationService;
	
	@Autowired
	private UserService userService;

	@GET
	@Path("usernameExists")
	@AuthenticationNotRequired
	public void getUsernameExists(
			@QueryParam("username") final String username,
			@Suspended final AsyncResponse response) {
		Timer t = new Timer();
		// We will introduce a synthetic delay to help prevent dictionary
		// attacks revealing used usernames
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					response.resume(registrationService.checkUsername(username));
				} catch(Exception e) {
					log.error("Error caught in usernameExists", e);
					response.cancel();
				}
			}

		}, 1000); // TODO Unhardcode this value
	}

	@POST
	@Path("register")
	@AuthenticationNotRequired
	public User register(final String json) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode rootNode = objectMapper.readTree(json);
		
		JsonNode userNode = rootNode.get("user");
		JsonNode accountNode = rootNode.get("account");
		
		String username = userNode.get("username").asText();
		
		final User user = userService.createNew(username);
		final LocalAccount account = registrationService.createLocalAccount(user);
		
		ObjectReader userReader = objectMapper.readerForUpdating(user);
		ObjectReader accountReader = objectMapper.readerForUpdating(account);
		
		userReader.readValue(userNode);
		accountReader.readValue(accountNode);
		
		Registration registration = new Registration() {

			@Override
			public User getUser() {
				return user;
			}

			@Override
			public LocalAccount getAccount() {
				return account;
			}
			
		};
		
		return registrationService.register(registration);
	}

}
