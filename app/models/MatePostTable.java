package models;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;

import play.data.binding.As;
import play.db.jpa.Model;

/*This is an Entity of the Message Post of the GiveHelp Page. Please check the MockUp if you fail to understand.
 And This post will be filled from the SeekHelp post window in the seekhelp page. That post will be this format except 
 there will be no mate_applied variable. As for the beginning obviously there will be no mates applied.*/

@Entity
public class MatePostTable extends Model {
//	public String seeker;
	public @As("MM/dd/yyyy")
	// This the Date format, if you intend to do so please use this type of
	// format always.
	Date postdate;
	public Time timeStart;
	public Time timeEnd;
	public String location;
	public String post;
	public String title;
	public Integer seekersRequired;
	public Integer seekersApplied;
	public String status;
	@ManyToOne
	public Mate postedBy;

	@OneToMany(mappedBy="post", cascade=CascadeType.ALL) //mappedBy post, and cascade=CascadeType.ALL means if the post is deleted, then all comments will be deleted 
	public List<MatePostComment> comments = new LinkedList<>();

	@ManyToMany(mappedBy="postsAppliedforHelp")
	public List<Seeker> seekersWantHelp = new LinkedList<>();
	

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
	public MatePostTable(Mate postedBy, Date date, Time timeStart, Time timeEnd,
			String location, String title, String post, Integer matesRequired,
			Integer mateApplied) {
//		this.seeker = seeker;
		this.postedBy=postedBy;
		this.postdate = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.location = location;
		this.title=title;
		this.post = post;
		this.seekersRequired = matesRequired;
		this.seekersApplied = mateApplied;
		this.status = "open";
	}

	public MatePostTable addComment(String userType, Long userId, String content) {
		MatePostComment newComment = null;
		if(userType.equals("seeker")){
			Seeker author=Seeker.findById(userId);
			//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
			newComment= new MatePostComment(this, userType, author,null, content);//I dont know what this save does, it works same without save()!!
		}
		else if(userType.equals("mate")){
			Mate author=Mate.findById(userId);
			//SeekerPostComment(SeekerPostTable post, String userType, Seeker seekerAuthor,Mate mateAuthor, String content) {
			newComment= new MatePostComment(this, userType, null, author, content);//I dont know what this save does, it works same without save()!!
		}
		System.out.println(newComment.content);
		this.comments.add(newComment);
		this.save();
		return this;
	}
	public MatePostTable addSeeker(Seeker seeker){//seeker can want help under a post    	
		this.seekersWantHelp.add(seeker);
		this.save();
		seeker.addPostNeedHelp(this);
		return this;
	}
	public MatePostTable removeSeeker(Seeker seeker){//Seeker who wanted help under a post can be removed in future, or he can revoke his request of getting help    	
		this.seekersWantHelp.remove(seeker);
		this.save();
		seeker.removePostNeededHelp(this);
		return this;
	}

}
