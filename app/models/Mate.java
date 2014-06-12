package models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Mate extends Model{
	
	public String ssid;
	public String firstName;
	public String lastName;
	public int age;
	public String email;
	public String pass;
	
	@ManyToMany
    public List<SeekerPostTable>postsWantTohelp = new LinkedList<>();
	@OneToMany 
    public List<MatePostTable> posts = new LinkedList<>();
	
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
	
	
	public Mate addPost(MatePostTable newPost){//the post this seeker gave
		this.posts.add(newPost);
        this.save();
		return this;
	}
	
	public Mate addPostWantTohelp(SeekerPostTable post){//when mate press the "I want to help" button this function activates
		this.postsWantTohelp.add(post);
		this.save();
		return this;
	}
	public Mate removePostWantTohelp(SeekerPostTable post){//-When mate press "Revoke help" from where I wanted to help earlier
		this.postsWantTohelp.remove(post);
		this.save();
		return this;
	}
	
}
