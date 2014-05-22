package com.github.ashward.aglet.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.ashward.aglet.dao.LocalAccountDAO;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.UserService;

@Service
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthRestService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocalAccountDAO localAccountDAO;
	
	@Path("login")
	@POST
	public String login(JsonNode json) {
		String username = json.get("username").textValue();
		String password = json.get("password").textValue();

		User user = userService.findUserByUsername(username);
		
		if(user != null) {
			LocalAccount account = user.getLocalAccount();
			
			if(account != null && account.checkPassword(password)) {
				return "Yay";
			}
		}
		
		return "Nay";
	}
}
