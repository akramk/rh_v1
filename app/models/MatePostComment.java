package models;

import javax.persistence.*;

import java.util.*;
import java.util.Date;
import java.util.List;

import play.data.binding.As;
import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class MatePostComment extends Model{
	
//    public String author;
    @Required
	public String who;//this person is mate or seeker, Admin(in future admin role will be implemented)  
	@OneToOne
	public Mate authorMate;
	@OneToOne
	public Seeker authorSeeker;
    @Required
    public @As("dd/MM/yyyy")
	// This the Date format, if you intend to do so please use this type of
	// format always.
    Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public MatePostTable post;
    
    public MatePostComment(MatePostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
    	if(userType.equals("seeker")){
    		this.authorSeeker=authorSeeker;
    		this.who="seeker";
    	}
    	else if(userType.equals("mate")){
    		this.authorMate=authorMate;
    		this.who="mate";
    	}
        this.post = post;
//        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
}
