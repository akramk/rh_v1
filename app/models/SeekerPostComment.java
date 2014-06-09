package models;

import javax.persistence.*;

import java.util.*;

import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class SeekerPostComment extends Model{
	@Required
    public String author;
	public String who;//this person is mate or seeker, Admin(in future admin role will be implemented)  
	@OneToOne
	public Mate authorMate;
	@OneToOne
	public Seeker authorSeeker;
    @Required
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public SeekerPostTable post;
    
    public SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
    	if(userType.equals("seeker")){
    		this.authorSeeker=authorSeeker;
    		this.who="seeker";
    	}
    	else if(userType.equals("mate")){
    		this.authorMate=authorMate;
    		this.who="mate";
    	}
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
}
