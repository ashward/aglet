package com.github.ashward.aglet.dao;

import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;

public interface LocalAccountDAO {
	void save(LocalAccount account);

	LocalAccount createNew(User associatedUser);
}
