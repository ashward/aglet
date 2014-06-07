package com.github.ashward.aglet.services.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.ashward.aglet.auth.AuthenticationNotRequired;
import com.github.ashward.aglet.services.AuthService;

@Service
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthRestService {
	private static Logger log = Logger.getLogger(AuthRestService.class);

	@Autowired
	private AuthService authService;

	@Path("login")
	@POST
	@AuthenticationNotRequired
	public JsonNode login(JsonNode json) {
		String username = json.get("username").textValue();
		String password = json.get("password").textValue();

		if (log.isTraceEnabled()) {
			log.trace("Found username '" + username + "'");
		}

		String token = authService.authenticateLocalAccount(username, password);

		ObjectNode result = JsonNodeFactory.instance.objectNode();
		result.put("authenticated", token != null);
		result.put("token",  token);
		return result;
	}
}
