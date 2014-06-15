package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Address extends Model{
	
	
	public String houseNumber;
	public String street;
	public String zipCode;
	public String city;
	public String country;
	
	@OneToOne
	public User userAddress;
	/**
	 * @param ssid
	 * @param houseNumber
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param country
	 */
	public Address(String houseNumber, String street,
			String zipCode, String city, String country) {
		this.houseNumber = houseNumber;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}
	
	
	

}
