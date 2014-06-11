package models;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.binding.As;
import play.db.jpa.Model;

@Entity
public class MatePostTable extends Model{
	
	public String mate;
	public @As("dd/MM/yyyy")
	// This the Date format, if you intend to do so please use this type of
	// format always.
	Date postdate;
	public Time timeStart;
	public Time timeEnd;
	public String location;
	public String post;
	public String title;
	public String status;
	
	@OneToOne
	public Mate mateWhoPosted;
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL) //mappedBy post, and cascade=CascadeType.ALL means if the post is deleted, then all comments will be deleted 
    public List<MatePostComment> comments;
	@ManyToMany
    public List<Seeker> seekerNeedHelp;
	
	/**
	 * @param mate
	 * @param postdate
	 * @param timeStart
	 * @param timeEnd
	 * @param location
	 * @param post
	 * @param title
	 * @param status
	 */
	public MatePostTable(String mate, Date postdate, Time timeStart,
			Time timeEnd, String location, String post, String title,
			String status) {
		super();
		this.mate = mate;
		this.postdate = postdate;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.location = location;
		this.post = post;
		this.title = title;
		this.status = status;
	}


    public MatePostTable addComment(String userType, Long userId, String content) {
    	MatePostComment newComment=null;
    	if(userType.equals("seeker")){
    		Seeker author=Seeker.findById(userId);
    		//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
    		newComment= new MatePostComment(this, userType, author,null, content).save();//I dont know what this save does, it works same without save()!!
    	}
    	else if(userType.equals("mate")){
    		Mate author=Mate.findById(userId);
    		//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
    		newComment= new MatePostComment(this, userType, null, author, content).save();//I dont know what this save does, it works same without save()!!
    	}
    	
        this.comments.add(newComment);
        this.save();
        return this;
    }
    public MatePostTable addHelpSeeker(Seeker seeker){    	
    	this.seekerNeedHelp.add(seeker);
    	this.save();
    	seeker.addPostNeedhelp(this);
    	return this;
    }
    public MatePostTable removeHelpMate(Seeker seeker){    	
    	this.seekerNeedHelp.remove(mate);
    	this.save();
    	seeker.removePostNeedhelp(this);
    	return this;
    }
	
}
