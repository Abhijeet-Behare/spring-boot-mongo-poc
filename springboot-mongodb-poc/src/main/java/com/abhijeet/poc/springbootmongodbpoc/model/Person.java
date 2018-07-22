package com.abhijeet.poc.springbootmongodbpoc.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Person {
	@Id
	private String id;
	private String firstname;
	private String lastname;

	private Date dob;
	private String gender;

	Person() {
	}

	public Person(String id, String firstname, String lastname, Date dob, String gender) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.gender = gender;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
