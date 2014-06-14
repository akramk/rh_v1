package models;

import javax.persistence.*;

import play.db.jpa.Model;


@Entity
public class User extends Model{
	public String firstName;
	public String lastName;
	public int age;
	public String email;
	public String pass;
	public String type;//mate or seeker
	
	@OneToOne(mappedBy="userMate")
	public Mate mate;
	
	@OneToOne(mappedBy="userSeeker")
	public Seeker seeker;
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param pass
	 * @param type
	 */
	public User(String firstName, String lastName, int age, String email,
			String pass, String type) {
//		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.pass = pass;
		this.type = type;
	}
			

}
