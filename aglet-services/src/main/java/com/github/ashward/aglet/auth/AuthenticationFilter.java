package com.github.ashward.aglet.auth;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.AuthService;
import com.github.ashward.aglet.services.UserService;

@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter,
		ContainerResponseFilter {
	private final static Logger log = Logger
			.getLogger(AuthenticationFilter.class);

	private final static String HEADER_AUTHORIZATION = "Authorization";

	private final static Pattern BEARER_PATTERN = Pattern
			.compile("^\\s*Bearer\\s+(\\S+)\\s*$");

	public final static String USER_PROPERTY = "com.github.ashward.aglet.auth.user";

	private AuthService authService;

	private UserService userService;

	public AuthenticationFilter(final AuthService authService,
			final UserService userService) {
		this.authService = authService;
		this.userService = userService;
	}

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String authz = requestContext.getHeaderString(HEADER_AUTHORIZATION);

		if(log.isDebugEnabled()) log.debug("Attempting to authenticate");

		if (authz != null) {
			if(log.isDebugEnabled()) log.debug("Authorization header found");

			// we have an authorization header
			Matcher m = BEARER_PATTERN.matcher(authz);
			if (m.matches()) {
				String token = m.group(1);
				if(log.isDebugEnabled()) log.debug("Found Bearer token starting "
						+ token.substring(0, 4));

				String username = authService.verifyToken(token);

				if(log.isDebugEnabled()) log.debug("Username (null = not verified): " + username);

				if (username != null) {
					User user = userService.findUserByUsername(username);

					if (user != null) {
						if(log.isDebugEnabled()) log.debug("Found user " + user.getName());

						requestContext.setProperty(USER_PROPERTY, user);
						return;
					}
				}
			}
		}

		if (!requestContext.getMethod().equals("OPTIONS")) {
			requestContext.abortWith(Response.status(Status.FORBIDDEN).build());
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		responseContext.getHeaders().add("WWW-Authenticate", "Bearer");
	}

}
