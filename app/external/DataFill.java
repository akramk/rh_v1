package external;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Address;
import models.Mate;
import models.Seeker;
import models.User;
import models.UserInfo;

public class DataFill {
	public void dataFiller() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//insert data in user info
		Date dateOfBirth =  dateFormat.parse("25/05/1965");
		UserInfo userInfo= new UserInfo("DE1024", "Richard", "John", "male", dateOfBirth, "15", "Dulferstrasse", "80937", "Munich", "Germany");
		userInfo.create();
		
		Date date =  dateFormat.parse("25/05/1965");
		User userseeker = new User("12345ks","Mask", "Rip", date, "mask@gmail.com", "seeker1", "seeker");
		Address address = new Address("21", "ABC", "sadhj", "Rajshahi", "Bangladesh");
		address.userAddress = userseeker;
		userseeker.address = address;
		userseeker.create();
		Seeker seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		userseeker = new User("25546s", "Riz", "Bon", date, "riz@gmail.com", "seeker2", "seeker");
		address = new Address("42", "3sd", "43dsa", "Rajshahi", "Bangladesh");
		address.userAddress = userseeker;
		userseeker.address = address;
		userseeker.create();
		seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		User usermate = new User("25s4sk","John", "Fred", date, "jeff@gmail.com", "mate1", "mate");
		address = new Address("3", "dasds", "5ds", "Dhaka", "Bangladesh");
		address.userAddress = usermate;
		usermate.address = address;
		usermate.create();
		Mate mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
		usermate = new User("254s6e", "Alex", "Rass",date, "alex@gmail.com", "mate2", "mate");
		address = new Address("das", "re", "re", "Rajshahi", "Bangladesh");
		address.userAddress = usermate;
		usermate.address = address;
		usermate.create();
		mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
		
	}

}

