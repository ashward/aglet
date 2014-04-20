package com.github.ashward.aglet.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("want")
public class WantService {
	@GET
	@Produces("application/json")
	public String get() {
		return "Test";
	}
}
