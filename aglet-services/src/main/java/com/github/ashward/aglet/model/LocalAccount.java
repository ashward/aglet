package com.github.ashward.aglet.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.log4j.Logger;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents an account with credentials held locally
 * @author ash
 */
@NodeEntity
public class LocalAccount extends Account {
	private static Logger log = Logger.getLogger(LocalAccount.class);

	private static int ITERATIONS = 15000;

	@GraphId @JsonIgnore
	private Long graphId;

	@JsonIgnore
	@Fetch
	private User associatedUser;

	@JsonIgnore
	private byte[] salt;
	
	@JsonIgnore
	private byte[] passwordHash;
	
	@JsonIgnore
	private int iterations;

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
		return Arrays.equals(hashPassword(password), passwordHash);
	}

	@JsonSetter
	public void setPassword(String password) {
		salt = generateSalt();

		iterations = ITERATIONS;

		passwordHash = hashPassword(password);
	}

	private byte[] generateSalt() {
		try {
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] saltBytes = new byte[256];
			sr.nextBytes(saltBytes);
			return saltBytes;
		} catch (NoSuchAlgorithmException e) {
			log.fatal(e);
			throw new RuntimeException(e);
		}
	}

	private byte[] hashPassword(final String password) {
		PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),
				salt, iterations, 1024);
		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return hash;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			log.fatal(e);
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LocalAccount [graphId=" + graphId + ", associatedUser="
				+ associatedUser + ", salt=" + Arrays.toString(salt)
				+ ", passwordHash=" + Arrays.toString(passwordHash)
				+ ", iterations=" + iterations + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graphId == null) ? 0 : graphId.hashCode());
		result = prime * result + iterations;
		result = prime * result + Arrays.hashCode(passwordHash);
		result = prime * result + Arrays.hashCode(salt);
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
		LocalAccount other = (LocalAccount) obj;
		if (graphId == null) {
			if (other.graphId != null)
				return false;
		} else if (!graphId.equals(other.graphId))
			return false;
		if (iterations != other.iterations)
			return false;
		if (!Arrays.equals(passwordHash, other.passwordHash))
			return false;
		if (!Arrays.equals(salt, other.salt))
			return false;
		return true;
	}
}
