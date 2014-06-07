package com.github.ashward.aglet.auth;

import java.lang.reflect.Method;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.ashward.aglet.services.AuthService;
import com.github.ashward.aglet.services.UserService;

@Provider
public class AuthenticationFeature implements DynamicFeature {
	private static final Logger log = Logger.getLogger(AuthenticationFilter.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        log.trace("Determining whether auth required for " + resourceInfo.getResourceMethod().toString());
    	Method method = resourceInfo.getResourceMethod();
        if (!method.isAnnotationPresent(AuthenticationNotRequired.class)) {
        	log.trace("No AuthNotRequired found - authenticating");
        	AuthenticationFilter authenticationFilter = new AuthenticationFilter(authService, userService);
        	
        	context.register(authenticationFilter);
        } else {
        	log.trace("AuthNotRequired annotation found - skipping authentication");
        }
    }
}