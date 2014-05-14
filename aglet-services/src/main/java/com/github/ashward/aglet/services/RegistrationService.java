package com.github.ashward.aglet.services;

import java.util.List;
import java.util.Map;

import com.github.ashward.aglet.model.Account;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;

public interface RegistrationService {
	public interface UsernameCheckResult {
		Boolean getExists();
		List<String> getSuggestions();
	}
	
	public interface Registration {
		User getUser();
		LocalAccount getAccount();
	}
	
	UsernameCheckResult checkUsername(String username);
	
	User register(Registration registration);
	
	LocalAccount createLocalAccount(User associatedUSer);
}
