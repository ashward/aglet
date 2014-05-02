package com.github.ashward.aglet.dao;

import com.github.ashward.aglet.model.User;

public interface UserDAO {
	User createNew(String username);
	User findUserByUsername(String username);
	void save(User user);
}
