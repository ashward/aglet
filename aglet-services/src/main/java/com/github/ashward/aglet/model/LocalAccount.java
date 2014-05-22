package com.github.ashward.aglet.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.log4j.Logger;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class LocalAccount extends Account {
	private static Logger log = Logger.getLogger(LocalAccount.class);

	private static int ITERATIONS = 15000;

	@GraphId
	private Long graphId;

	private User associatedUser;

	private byte[] salt;
	private byte[] passwordHash;
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
}
