package com.github.ashward.aglet.services.impl;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Calendar;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;

import org.apache.log4j.Logger;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.AuthService;
import com.github.ashward.aglet.services.UserService;
import com.google.gson.JsonObject;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger log = Logger.getLogger(AuthServiceImpl.class);

	@Autowired
	private UserService userService;

	@Value("${auth.jwt.key}")
	private String jwtSigningKey;

	@Value("${auth.jwt.issuer}")
	private String jwtIssuer;

	@Value("${auth.jwt.expiryMillis}")
	private long expiryMillis;

	@Override
	public String authenticateLocalAccount(String username, String password) {
		User user = userService.findUserByUsername(username);

		if (log.isTraceEnabled()) {
			log.trace("Found user '" + user + "'");
		}

		if (user != null) {
			LocalAccount account = user.getLocalAccount();

			if (log.isTraceEnabled()) {
				log.trace("Found account '" + account + "'");
			}

			if (account != null && account.checkPassword(password)) {
				try {
					JsonToken token = createJWT(user);
					return token.serializeAndSign();
				} catch (SignatureException | InvalidKeyException e) {
					log.error(e);
					throw new RuntimeException(e);
				}
			}
		}

		return null;
	}

	private JsonToken createJWT(final User user) throws InvalidKeyException {
		// Current time and signing algorithm
		Calendar cal = Calendar.getInstance();
		HmacSHA256Signer signer = new HmacSHA256Signer(jwtIssuer, null,
				jwtSigningKey.getBytes());

		// Configure JSON token
		JsonToken token = new JsonToken(signer);
		token.setParam("typ", "aglet/auth/v1");
		token.setIssuedAt(new Instant(cal.getTimeInMillis()));
		token.setExpiration(new Instant(cal.getTimeInMillis() + expiryMillis));

		// Add the username to the payload
		JsonObject payload = token.getPayloadAsJsonObject();
		payload.addProperty("username", user.getUsername());

		return token;
	}
}
