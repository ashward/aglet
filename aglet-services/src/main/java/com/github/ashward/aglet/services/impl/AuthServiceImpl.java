package com.github.ashward.aglet.services.impl;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.SystemClock;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

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

	@Value("${auth.jwt.audience}")
	private String jwtAudience;
	
	@Value("${auth.jwt.expiryMillis}")
	private long expiryMillis;

	private VerifierProviders verifierProviders;

	private HashMap<String, List<Verifier>> verifiersMap;
	
	private JsonTokenParser tokenParser;
	
	@PostConstruct
	public void init() {
		verifierProviders = new VerifierProviders();
		
		ArrayList<Verifier> verifiers = new ArrayList<>();
		
		try {
			verifiers.add(new HmacSHA256Verifier(jwtSigningKey.getBytes()));
		} catch (InvalidKeyException e) {
			log.error(e);
			throw new RuntimeException(e);
		}
		
		verifiersMap = new HashMap<>();
		
		verifiersMap.put(jwtIssuer, verifiers);
		
		verifierProviders.setVerifierProvider(SignatureAlgorithm.HS256, new VerifierProvider() {
			
			@Override
			public List<Verifier> findVerifier(String issuer, String keyId) {
				log.debug("Finding verifier for " + issuer + " / " + keyId);
				List<Verifier> verifiers = verifiersMap.get(issuer);
				if(verifiers != null) {
					log.debug("Found verifiers");
					return verifiersMap.get(issuer);
				} else {
					log.warn("No verifiers found");
					return Collections.emptyList();
				}
			}
		});
		
		tokenParser = new JsonTokenParser(new SystemClock(), verifierProviders);
	}
	
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

	@Override
	public String verifyToken(final String tokenString) {
		try {
			JsonToken token = tokenParser.verifyAndDeserialize(tokenString);
			
			JsonObject payload = token.getPayloadAsJsonObject();
			if(log.isDebugEnabled()) {
				log.debug(payload);
			}
			return payload.get("username").getAsString();
		} catch (SignatureException e) {
			log.warn(e);
			return null;
		}
	}
}
