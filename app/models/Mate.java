package models;

import java.util.List;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Mate extends User{
	
	public String ssid;
	public String firstName;
	public String lastName;
	public int age;
	public String email;
	public String pass;
	
	@ManyToMany
    public List<SeekerPostTable>postsWantTohelp;
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
	
	public Mate addPostWantTohelp(SeekerPostTable post){//help a post
		this.postsWantTohelp.add(post);
		this.save();
		return this;
	}
	public Mate removePostWantTohelp(SeekerPostTable post){//-Revoke help from where I wanted to help earlier
		this.postsWantTohelp.remove(post);
		this.save();
		return this;
	}
	
}
