package com.app.entities;

import java.util.Date;

/*
 * id | first_name | last_name | email  | password | 
 * dob        | status | role
 */
public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date dob;
	private Double reg_amt;
	private String role;

	// parameterized ctor
	public User(int userId, String firstName, String lastName, String email, String password, Date dob, Double reg_amt,
			String role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dob = dob;
		this.reg_amt = reg_amt;
		this.role = role;
	}

	public User(String firstName, String lastName, String email, String password, double reg_amt, Date regDate,
			String role) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dob = regDate;
		this.reg_amt = reg_amt;
		this.role = role;
	}

	// getters n setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getReg_amt() {
		return reg_amt;
	}

	public void setReg_amt(Double reg_amt) {
		this.reg_amt = reg_amt;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", dob=" + dob + ", reg_amt=" + reg_amt + ", role=" + role + "]";
	}

}
