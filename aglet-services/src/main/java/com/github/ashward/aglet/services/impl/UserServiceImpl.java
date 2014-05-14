package com.github.ashward.aglet.services.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User createNew(final String username) {
		return userDAO.createNew(username);
	}
}
