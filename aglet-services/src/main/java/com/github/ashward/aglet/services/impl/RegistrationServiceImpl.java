package com.github.ashward.aglet.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	public User register(Map<String, String> data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsernameCheckResult checkUsername(String username) {
		final User user = userDAO.findUserByUsername(username);
		
		UsernameCheckResult result = new UsernameCheckResult() {
			
			@Override
			public List<String> getSuggestions() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Boolean getExists() {
				return user != null;
			}
		};
		
		return result;
	}

}
