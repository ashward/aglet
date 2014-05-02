package com.github.ashward.aglet.neo4j.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.github.ashward.aglet.neo4j.model.Neo4jUser;

public interface UserRepository extends GraphRepository<Neo4jUser> {

}
