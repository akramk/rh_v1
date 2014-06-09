package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Mate extends User{
	
	public String ssid;
	public String firstName;
	public String lastName;
	public int age;
	public String email;
	public String pass;
	/**
	 * @param ssid
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param pass
	 */
	public Mate(String ssid, String firstName, String lastName, int age,
			String email, String pass) {
		super();
		this.ssid = ssid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.pass = pass;
	}
	
	
	
	
}
