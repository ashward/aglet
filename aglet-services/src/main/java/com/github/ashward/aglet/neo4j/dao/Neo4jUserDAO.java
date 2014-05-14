package com.github.ashward.aglet.neo4j.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.neo4j.repository.UserRepository;

@Repository
public class Neo4jUserDAO implements UserDAO {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findUserByUsername(String username) {
		return userRepository.findBySchemaPropertyValue("username", username);
	}
	
	@Override
	public void save(final User user) {
		userRepository.save(user);
	}

	@Override
	public User createNew(String username) {
		return new User(username);
	}
}
