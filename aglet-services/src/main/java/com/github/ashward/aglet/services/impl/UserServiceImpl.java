package com.github.ashward.aglet.services.impl;

import org.json.JSONObject;

import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void updateFromJSON(final User user, final JSONObject json) {
		user.setName(json.getString("name"));
	}

}
