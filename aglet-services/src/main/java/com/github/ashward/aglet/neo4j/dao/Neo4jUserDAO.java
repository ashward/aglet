package com.github.ashward.aglet.neo4j.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.neo4j.model.Neo4jUser;
import com.github.ashward.aglet.neo4j.repository.UserRepository;

public class Neo4jUserDAO implements UserDAO {

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
