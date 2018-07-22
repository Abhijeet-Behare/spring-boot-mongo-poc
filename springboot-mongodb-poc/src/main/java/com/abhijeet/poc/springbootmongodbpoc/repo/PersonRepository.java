package com.abhijeet.poc.springbootmongodbpoc.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.abhijeet.poc.springbootmongodbpoc.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {

	List<Person> findByFirstname(String firstName);

}
