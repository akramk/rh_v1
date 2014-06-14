package models;

import java.util.LinkedList;
import java.util.List;

import javax.mail.Session;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Seeker extends User implements play.db.Model{
	public String ssid;
	
	@OneToMany 
    public List<SeekerPostTable> posts = new LinkedList<>();
    
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
		super(firstName, lastName, age, email, pass, "seeker");
	}
	
	public Seeker addPost(SeekerPostTable newPost){//the post this seeker gave
		System.out.println(newPost.seekerWhoPosted.email);
		this.posts.add(newPost);
        this.save();
		return this;
 }
	
	
	

}
