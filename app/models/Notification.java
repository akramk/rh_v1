package models;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Notification extends Model{
	
	public String notificationMessage;
	public Date notificationDate;
	public String viewed;
	
    @ManyToOne
	public Mate notifyThisMate;
    @ManyToOne
    public SeekerPostTable seekerPostTable;
    
    @ManyToOne
	public Seeker notifyThisSeeker;
    @ManyToOne
    public MatePostTable matePostTable;
    
	
}
