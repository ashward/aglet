package com.github.ashward.aglet.services;

public interface AuthService {
	
	/**
	 * Authenticates a username/password and returns a 
	 * @param username
	 * @param password
	 * @return
	 */
	String authenticateLocalAccount(String username, String password);
	
	/**
	 * Verifies that the given token is correctly signed
	 * and returns the username.
	 * @param token the json token
	 * @return the username that the token represents
	 */
	String verifyToken(String token);
}
