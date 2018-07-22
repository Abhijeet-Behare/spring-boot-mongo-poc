package com.abhijeet.poc.springbootmongodbpoc.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhijeet.poc.springbootmongodbpoc.model.Person;
import com.abhijeet.poc.springbootmongodbpoc.repo.PersonRepository;

@RestController
public class MongoPocController {

	final Logger logger = LoggerFactory.getLogger(MongoPocController.class);

	String errorMessage = "INTERNAL SERVER ERROR";

	@Autowired
	PersonRepository personRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/savepersoninfo")
	public ResponseEntity savePersonInfo(@RequestBody Person person) {

		try {

			Person personResult = personRepository.save(person);

			return new ResponseEntity("Person Info Saved with id" + personResult.getId(), HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Person Info Not Saved Due to Error -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/getpersonbyid")

	public ResponseEntity getPersonInfoById(@RequestHeader String id) {

		try {

			Optional<Person> personResult = personRepository.findById(id);

			return new ResponseEntity(personResult, HttpStatus.OK);

		} catch (Exception e) {

			logger.error(" Error in getPersonInfoById -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/getpersonbyfirstname")

	public ResponseEntity getPersonInfoByFirstName(@RequestHeader String firstName) {

		try {

			List<Person> personResult = personRepository.findByFirstname(firstName);

			return new ResponseEntity(personResult, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error infindByFirstname  -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.POST, path = "/updatepersoninfo")

	public ResponseEntity updatePersonInfo(@RequestBody Person person) {

		try {

			if (personRepository.existsById(person.getId())) {
				Person personResult = personRepository.save(person);
				return new ResponseEntity("Person Info Saved with id" + personResult.getId(), HttpStatus.OK);
			} else {
				return new ResponseEntity("No Data Found For this Id" + person.getId(), HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			logger.error("Person Info Not Saved Due to Error -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, path = "/deletepersoninfo")

	public ResponseEntity deletePersonInfoById(@RequestHeader String id) {

		try {

			if (personRepository.existsById(id)) {
				personRepository.deleteById(id);
				return new ResponseEntity("Data Person Info with  id " + id + " is Deleted", HttpStatus.OK);
			} else {
				return new ResponseEntity("No Data Found For this Id" + id, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {

			logger.error("Error in deletePersonInfoById is -->  " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// Using Jdbc Template //jdbcTemplate

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/personbyid")

	public ResponseEntity getPersonInfoByIdUsingTemplate(@RequestHeader String id) {

		try {

			Query query = new Query();

			query.addCriteria(Criteria.where("_id").is(id));

			List<Person> personResult = mongoTemplate.find(query, Person.class);

			return new ResponseEntity(personResult, HttpStatus.OK);

		} catch (Exception e) {

			logger.error(" Error in getPersonInfoById -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET, path = "/personbyfirstname")

	public ResponseEntity getPersonInfoByFirstNameUsingTemplate(@RequestHeader String firstName) {

		try {

			Query query = new Query();

			query.addCriteria(Criteria.where("firstname").is(firstName));

			List<Person> personResult = mongoTemplate.find(query, Person.class);

			return new ResponseEntity(personResult, HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error infindByFirstname  -- > " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@RequestMapping(method = RequestMethod.DELETE, path = "/deletepersoninfobyid")

	public ResponseEntity deletePersonInfoByIdUsingTemplate(@RequestHeader String id) {

		try {
			Query query = new Query();

			query.addCriteria(Criteria.where("_id").is(id));
			mongoTemplate.remove(query, Person.class);

			return new ResponseEntity("Data Person Info with  id " + id + " is Deleted", HttpStatus.OK);

		} catch (Exception e) {

			logger.error("Error in deletePersonInfoById is -->  " + e);

			return new ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}