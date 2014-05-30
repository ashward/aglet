package com.github.ashward.aglet.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ashward.aglet.dao.LocalAccountDAO;
import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private LocalAccountDAO accountDAO;

	@Override
	@Transactional
	public User register(final Registration registration) {
		User user = registration.getUser();
		LocalAccount account = registration.getAccount();		
		userDAO.save(user);
		accountDAO.save(account);
		user.setLocalAccount(account);
		userDAO.save(user);
		return user;
	}

	@Override
	public UsernameCheckResult checkUsername(String username) {
		final User user = userDAO.findUserByUsername(username);
		
		UsernameCheckResult result = new UsernameCheckResult() {
			
			@Override
			public List<String> getSuggestions() {
				if(user == null) {
					return null;
				}
				
				// TODO Auto-generated method stub
				return new ArrayList<String>() {{
					add(user.getUsername() + "1" );
					add(user.getUsername() + "2" );
					add(user.getUsername() + "3" );
					add(user.getUsername() + "cholmondleywarner" );
				}};
				
			}
			
			@Override
			public Boolean getExists() {
				return user != null;
			}
		};
		
		return result;
	}

	@Override
	public LocalAccount createLocalAccount(final User associatedUser) {
		return accountDAO.createNew(associatedUser);
	}
}
