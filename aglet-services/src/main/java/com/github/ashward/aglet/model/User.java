package com.github.ashward.aglet.model;

import java.util.Iterator;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * Represents a user of the system backed by a Neo4j
 * data store
 * 
 * @author ash
 */
@NodeEntity
public class User {
	@GraphId
	private Long graphId;

	@RelatedTo(type="BELONGS_TO", direction=Direction.INCOMING)
	private LocalAccount localAccount;
	
	/**
	 * The username
	 */
	@Indexed(unique = true)
	private String username;

	private String name;

	public User() {
	}
	
	public User(final String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public LocalAccount getLocalAccount() {
		return localAccount;
	}
}
