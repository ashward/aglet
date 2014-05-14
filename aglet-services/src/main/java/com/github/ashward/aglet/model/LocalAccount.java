package com.github.ashward.aglet.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class LocalAccount extends Account {
	private static String SALT = "some random salt string which we're using to pad out the password so that it's unlikely anyone will be able to brute force it at all. They would spend many lots of ages brute forcing this. Unless of course they have a copy of the code in which case it's all for nought.";
	
	@GraphId
	private Long graphId;
	
	@RelatedTo(type="BELONGS_TO", direction=Direction.OUTGOING)
	private User associatedUser;
	
	private String passwordHash;
	
	protected LocalAccount() {		
	}
	
	public LocalAccount(final User associatedUser) {
			this.associatedUser = associatedUser;
	}
	
	@Override
	public User getAssociatedUser() {
		return associatedUser;
	}

	public boolean checkPassword(String password) {
		return DigestUtils.sha1Hex(password + SALT).equals(passwordHash);
	}

	public void setPassword(String password) {
		passwordHash = DigestUtils.sha1Hex(password + SALT);
	}

}
