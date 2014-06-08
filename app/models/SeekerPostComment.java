package models;

import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.db.jpa.Model;

@Entity
public class SeekerPostComment extends Model{
	@Required
    public String author;
    
    @Required
    public Date postedAt;
     
    @Lob
    @Required
    @MaxSize(10000)
    public String content;
    
    @ManyToOne
    @Required
    public SeekerPostTable post;
    
    public SeekerPostComment(SeekerPostTable post, String author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
}
