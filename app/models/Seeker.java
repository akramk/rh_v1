package models;

import java.util.LinkedList;
import java.util.List;

import javax.mail.Session;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Seeker extends Model{
//	public String ssid;
	@OneToOne
	public User userSeeker;
	
	@OneToMany 
    public List<SeekerPostTable> posts = new LinkedList<>();
    
    @ManyToMany
    public List<MatePostTable>postsAppliedforHelp = new LinkedList<>();
    
    
    @OneToMany(mappedBy="notifyThisSeeker")
	public List<Notification> notifySeeker = new LinkedList<Notification>();
    
	/**
	 * @param ssid
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param pass
	 */
//	public Seeker(String ssid, String firstName, String lastName, int age,
//			String email, String pass) {
//		super(firstName, lastName, age, email, pass, "seeker");
//	}
//	
	public Seeker addPost(SeekerPostTable newPost){//the post this seeker gave
		System.out.println(newPost.seekerWhoPosted.userSeeker.email);
		this.posts.add(newPost);
        this.save();
		return this;
 }
	
	public Seeker addPostNeedHelp(MatePostTable post){//when seeker press the "I need help" button this function activates
		this.postsAppliedforHelp.add(post);
		this.save();
		return this;
	}
	public Seeker removePostNeededHelp(MatePostTable post){//-When seeker press "Revoke need help" from where I wanted to help earlier
		this.postsAppliedforHelp.remove(post);
		this.save();
		return this;
	}
	

}
