package com.github.ashward.aglet.model;

/**
 * Represents a user of the system
 * @author ash
 */
public interface User {
	/**
	 * Returns the User's username, which is unique across the system.
	 * @return the User's username
	 */
	public String getUsername();
	
	public String getName();
	public void setName(String name);
}
