package com.github.ashward.aglet.neo4j.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.github.ashward.aglet.model.User;

/**
 * Represents a user of the system
 * 
 * @author ash
 */
@NodeEntity
public class Neo4jUser implements User {
	@GraphId
	private Long graphId;

	/**
	 * The username
	 */
	@Indexed(unique = true)
	private String username;

	private String name;

	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Neo4jUser create(final String username) {
		Neo4jUser user = new Neo4jUser();
		user.username = username;
		
		return user;
	}
}
