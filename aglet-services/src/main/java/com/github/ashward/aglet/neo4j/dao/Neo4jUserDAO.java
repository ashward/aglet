package com.github.ashward.aglet.neo4j.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.neo4j.model.Neo4jUser;
import com.github.ashward.aglet.neo4j.repository.UserRepository;

@Repository
public class Neo4jUserDAO implements UserDAO {

	@Autowired
	UserRepository userRepository;
	
	public User findUserByUsername(String username) {
		return userRepository.findBySchemaPropertyValue("username", username);
	}

	@Override
	public User createNew(String username) {
		return Neo4jUser.create(username);
	}
}
