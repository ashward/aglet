package com.github.ashward.aglet.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.ashward.aglet.model.User;

public interface UserRepository extends GraphRepository<User> {

}
