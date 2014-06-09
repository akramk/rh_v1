package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.db.jpa.Model;


@Entity
public class Seeker extends User{
	public String ssid;
	public String firstName;
	public String lastName;
	public int age;
	public String email;
	public String pass;
	
	@OneToMany 
    public List<SeekerPostTable> posts;
	/**
	 * @param ssid
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param pass
	 */
	public Seeker(String ssid, String firstName, String lastName, int age,
			String email, String pass) {
		super();
		this.ssid = ssid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.pass = pass;
	}
	
	public Seeker addPost(SeekerPostTable newPost){//the post this seeker gave
		this.posts.add(newPost);
        this.save();
		return this;
	}
	
	

}
