package models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.data.binding.As;
import play.db.jpa.Model;

@Entity
public class UserInfo extends Model{
	
	public String ssid;
	public String firstName;
	public String lastName;
 	public String sex ;
	
	public @As("dd/MM/yyyy")
 	Date dateofBirth;
	
	public @OneToOne
	Address addressValue;

	/**
	 * @param ssid
	 * @param firstName
	 * @param lastName
	 * @param sex
	 * @param dateofBirth
	 */
	public UserInfo(String ssid, String firstName, String lastName, String sex,
			Date dateofBirth) {
		super();
		this.ssid = ssid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.dateofBirth = dateofBirth;
	}
	
	
	
	
	
}
