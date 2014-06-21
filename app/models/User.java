package models;

import java.util.Date;

import javax.persistence.*;

import play.data.binding.As;
import play.db.jpa.Model;


@Entity
public class User extends Model{
	public String ssid;
	public String firstName;
	public String lastName;
	public @As("dd/MM/yyyy")
 	Date dateofBirth;
	public String email;
	public String pass;
	public String type;//mate or seeker
	public String userStatus;//admin, applied, accepted, rejected, banned
	
	@OneToOne(cascade=CascadeType.ALL)
	public Address address;
	
	@OneToOne(mappedBy="userMate")
	public Mate mate;
	
	@OneToOne(mappedBy="userSeeker")
	public Seeker seeker;
	
	@OneToOne(mappedBy="userAdmin")
	public Admin admin;
	
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param age
	 * @param email
	 * @param pass
	 * @param type
	 */
	public User(String ssid, String firstName, String lastName, Date dateofBirth, String email,
			String pass, String type, String userStatus) {
//		super();
		this.ssid = ssid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateofBirth = dateofBirth;
		this.email = email;
		this.pass = pass;
		this.type = type;//mate, seeker, admin
		this.userStatus = userStatus;
	}
			

}
