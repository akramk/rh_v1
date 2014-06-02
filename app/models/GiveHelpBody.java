package models;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OrderBy;

import org.hibernate.validator.constraints.NotEmpty;

import play.data.binding.As;
import play.db.jpa.Model;

/*This is an Entity of the Message Post of the GiveHelp Page. Please check the MockUp if you fail to understand.
 And This post will be filled from the SeekHelp post window in the seekhelp page. That post will be this format except 
 there will be no mate_applied variable. As for the beginning obviously there will be no mates applied.*/

@Entity
public class GiveHelpBody extends Model {

	public String seeker;
	public @As("dd/MM/yyyy")
	// This the Date format, if you intend to do so please use this type of
	// format always.
	Date postdate;
	public Time timeStart;
	public Time timeEnd;
	public String location;
	public String post;
	public Integer matesRequired;
	public Integer mateApplied;

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
	public GiveHelpBody(String seeker, Date date, Time timeStart, Time timeEnd,
			String location, String post, Integer matesRequired,
			Integer mateApplied) {
		this.seeker = seeker;
		this.postdate = date;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.location = location;
		this.post = post;
		this.matesRequired = matesRequired;
		this.mateApplied = mateApplied;
	}
}
