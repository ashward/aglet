package com.github.ashward.aglet.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "/applicationContext.xml")
public class AuthServiceImplTest {
	
	@Autowired
	@ReplaceWithMock
	UserDAO userDAO;

	@Autowired
	AuthServiceImpl authService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateAndVerifyToken() {
		String username = "test_user";
		String password = "passwordyword";
		
		// GIVEN:
		// We have a user
		User user = mock(User.class);
		when(userDAO.findUserByUsername(username)).thenReturn(new User(username));

		// With a local account
		LocalAccount localAccount = mock(LocalAccount.class);
		when(localAccount.checkPassword(password)).thenReturn(true);
		when(user.getLocalAccount()).thenReturn(localAccount);
		
		// WHEN we get a token
		String token = authService.authenticateLocalAccount(username, password);

		// THEN
		// The token can be verified
		String verified = authService.verifyToken(token);
		assertEquals("Token correctly verified", username, verified);
	}

}
