package com.github.ashward.aglet.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
	@RelatedTo(type="BELONGS_TO", direction=Direction.INCOMING)
	@Fetch
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
	
	public void setLocalAccount(final LocalAccount localAccount) {
		this.localAccount = localAccount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [graphId=" + graphId + ", username=" + username
				+ ", name=" + name + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graphId == null) ? 0 : graphId.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (graphId == null) {
			if (other.graphId != null)
				return false;
		} else if (!graphId.equals(other.graphId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
