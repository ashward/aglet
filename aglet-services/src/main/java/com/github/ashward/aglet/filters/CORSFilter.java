package com.github.ashward.aglet.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {

		String origin = requestContext.getHeaderString("Origin");
		
		if(origin != null) {
			responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
	        responseContext.getHeaders().add("Access-Control-Allow-Headers",
	                "origin, content-type, accept, authorization");
	        responseContext.getHeaders().add("Access-Control-Allow-Credentials",
	                "true");
	        responseContext.getHeaders().add("Access-Control-Allow-Methods",
	                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		}
 	}
}