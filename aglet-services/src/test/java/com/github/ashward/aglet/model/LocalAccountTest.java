package com.github.ashward.aglet.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocalAccountTest {

	@Test
	public void testCheckPassword() {
		// GIVEN:
		// We have an account
		LocalAccount localAccount = new LocalAccount(new User());
		
		// WHEN:
		// We set the password to password12345
		localAccount.setPassword("password12345");
		
		// THEN:
		assertTrue("Check password for 'password12345' should return true", localAccount.checkPassword("password12345"));
		assertFalse("Check password for 'password1234' should return false", localAccount.checkPassword("password1234"));
	}
}
