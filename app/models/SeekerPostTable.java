package models;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import play.data.binding.As;
import play.db.jpa.Model;

/*This is an Entity of the Message Post of the GiveHelp Page. Please check the MockUp if you fail to understand.
 And This post will be filled from the SeekHelp post window in the seekhelp page. That post will be this format except 
 there will be no mate_applied variable. As for the beginning obviously there will be no mates applied.*/

@Entity
public class SeekerPostTable extends Model {

	public String seeker;
	public @As("dd/MM/yyyy")
	// This the Date format, if you intend to do so please use this type of
	// format always.
	Date postdate;
	public Time timeStart;
	public Time timeEnd;
	public String location;
	public String post;
	public String title;
	public Integer matesRequired;
	public Integer mateApplied;
	public String status;
	@OneToOne
	public Seeker seekerWhoPosted;
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL) //mappedBy post, and cascade=CascadeType.ALL means if the post is deleted, then all comments will be deleted 
    public List<SeekerPostComment> comments;
	
	@ManyToMany
    public List<Mate> matesWantToHelp;
	/**
	 * @param seeker
	 * @param date
	 * @param timeStart
	 * @param timeEnd
	 * @param location
	 * @param post
	 * @param matesRequired
	 * @param mateApplied
	 */
	public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd,
			String location, String title, String post, Integer matesRequired,
			Integer mateApplied) {
		this.seeker = seeker;
		this.seekerWhoPosted=seekerWhoPosted;
		this.postdate = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.location = location;
		this.title=title;
		this.post = post;
		this.matesRequired = matesRequired;
		this.mateApplied = mateApplied;
		this.status = "open";
	}
	
    public SeekerPostTable addComment(String userType, Long userId, String content) {
    	SeekerPostComment newComment=null;
    	if(userType.equals("seeker")){
    		Seeker author=Seeker.findById(userId);
    		//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
    		newComment= new SeekerPostComment(this, userType, author,null, content).save();//I dont know what this save does, it works same without save()!!
    	}
    	else if(userType.equals("mate")){
    		Mate author=Mate.findById(userId);
    		//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
    		newComment= new SeekerPostComment(this, userType, null, author, content).save();//I dont know what this save does, it works same without save()!!
    	}
    	
        this.comments.add(newComment);
        this.save();
        return this;
    }
    public SeekerPostTable addHelpMate(Mate mate){    	
    	this.matesWantToHelp.add(mate);
    	this.save();
    	mate.addPostWantTohelp(this);
    	return this;
    }
    public SeekerPostTable removeHelpMate(Mate mate){    	
    	this.matesWantToHelp.remove(mate);
    	this.save();
    	mate.removePostWantTohelp(this);
    	return this;
    }
    
}
