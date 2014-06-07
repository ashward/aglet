package com.github.ashward.aglet.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ashward.aglet.dao.LocalAccountDAO;
import com.github.ashward.aglet.dao.UserDAO;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.services.RegistrationService.Registration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "/applicationContext.xml")
public class RegistrationServiceImplTest {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	LocalAccountDAO accountDAO;	
	
	@Autowired
	RegistrationServiceImpl registrationService;
	
	@Test
	public void testRegister() {
		// Given:
		// We have a new user
		final User user = new User("test");
		
		// With a new account
		final LocalAccount account = new LocalAccount(user);
		
		account.setPassword("password");
		
		Registration registration = new Registration() {
			
			@Override
			public User getUser() {
				return user;
			}
			
			@Override
			public LocalAccount getAccount() {
				return account;
			}
		};
		
		registrationService.register(registration);
		
//		verify(userDAO).save(user);
//		verify(accountDAO).save(account);
		
		assertEquals(account, user.getLocalAccount());
		assertEquals(user, account.getAssociatedUser());
		
		User user2 = userDAO.findUserByUsername("test");
		assertEquals("", user, user2);
		
		LocalAccount account2 = user2.getLocalAccount();
		assertEquals("", account, account2);
	}

	@Test @Ignore
	public void testCheckUsername() {
		fail("Not yet implemented");
	}

	@Test @Ignore
	public void testCreateLocalAccount() {
		fail("Not yet implemented");
	}

}
