package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Admin extends Model{
	
	@OneToOne
	public User userAdmin;
	
}
