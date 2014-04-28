package com.github.ashward.aglet.services;

import org.json.JSONObject;

import com.github.ashward.aglet.model.User;

public interface UserService {
	/**
	 * Updates the given User with data from the json string.
	 * @param user
	 * @param json
	 */
	void updateFromJSON(User user, JSONObject json);
}
