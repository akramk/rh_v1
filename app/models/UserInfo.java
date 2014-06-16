package models;

import java.util.Date;

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
	
	public String houseNumber;
	public String street;
	public String zipCode;
	public String city;
	public String country;
	
	
	/**
	 * @param ssid
	 * @param firstName
	 * @param lastName
	 * @param sex
	 * @param dateofBirth
	 * @param houseNumber
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param country
	 */
	public UserInfo(String ssid, String firstName, String lastName, String sex,
			Date dateofBirth, String houseNumber, String street,
			String zipCode, String city, String country) {
		super();
		this.ssid = ssid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.dateofBirth = dateofBirth;
		this.houseNumber = houseNumber;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}


	
	
	
	
	
}
