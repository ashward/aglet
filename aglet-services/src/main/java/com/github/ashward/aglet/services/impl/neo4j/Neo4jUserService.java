package com.github.ashward.aglet.services.impl.neo4j;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.model.impl.neo4j.Neo4jUser;
import com.github.ashward.aglet.neo4j.repository.UserRepository;
import com.github.ashward.aglet.services.UserService;

public class Neo4jUserService implements UserService {

	@Autowired
	UserRepository userRepository;
	
	public User findUserByUsername(String username) {
		return userRepository.findByPropertyValue("username", username);
	}

	@Override
	public User createNew(String username) {
		return Neo4jUser.create(username);
	}

}
