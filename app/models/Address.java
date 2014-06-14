package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Address extends Model{
	
	public String ssid;
	public String houseNumber;
	public String street;
	public String zipCode;
	public String city;
	public String country;
	/**
	 * @param ssid
	 * @param houseNumber
	 * @param street
	 * @param zipCode
	 * @param city
	 * @param country
	 */
	public Address(String ssid, String houseNumber, String street,
			String zipCode, String city, String country) {
		super();
		this.ssid = ssid;
		this.houseNumber = houseNumber;
		this.street = street;
		this.zipCode = zipCode;
		this.city = city;
		this.country = country;
	}
	
	
	

}
