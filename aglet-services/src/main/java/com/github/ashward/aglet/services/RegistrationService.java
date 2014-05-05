package com.github.ashward.aglet.services;

import java.util.List;
import java.util.Map;

import com.github.ashward.aglet.model.User;

public interface RegistrationService {
	public interface UsernameCheckResult {
		Boolean getExists();
		List<String> getSuggestions();
	}
	
	UsernameCheckResult checkUsername(String username);
	
	User register(Map<String,String> data); 
}
