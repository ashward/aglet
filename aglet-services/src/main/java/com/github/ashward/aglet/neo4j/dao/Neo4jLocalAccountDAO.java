package com.github.ashward.aglet.neo4j.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.ashward.aglet.dao.LocalAccountDAO;
import com.github.ashward.aglet.model.LocalAccount;
import com.github.ashward.aglet.model.User;
import com.github.ashward.aglet.neo4j.repository.LocalAccountRepository;

@Repository
public class Neo4jLocalAccountDAO implements LocalAccountDAO {

	@Autowired
	private LocalAccountRepository repository;
	
	@Override
	public void save(LocalAccount account) {
		repository.save(account);
	}

	@Override
	public LocalAccount createNew(User associatedUser) {
		return new LocalAccount(associatedUser);
	}

}
