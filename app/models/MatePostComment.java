package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class MatePostComment extends Model{
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
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
}
