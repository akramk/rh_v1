package external;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import models.Address;
import models.Admin;
import models.Mate;
import models.Seeker;
import models.User;
import models.UserInfo;

public class DataFill {
	public void dataFiller() throws ParseException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		//insert data in user info
		Date dateOfBirth =  dateFormat.parse("25/05/1965");
		UserInfo userInfo= new UserInfo("BD1024", "Asgar", "Ali", "male", dateOfBirth, "15", "Mirpur", "1240", "Dhaka", "Bangladesh");
		userInfo.create();
		
		Date date =  dateFormat.parse("25/05/1975");
		User userseeker = new User("12345ks","Ahmed", "Rabbi", date, "rab@gmail.com", "seeker1", "seeker", "accepted");
		Address address = new Address("21", "ABC", "87/A", "Rajshahi", "Bangladesh");
		address.userAddress = userseeker;
		userseeker.address = address;
		userseeker.create();
		Seeker seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		userseeker = new User("25546s", "Riz", "Bon", date, "riz@gmail.com", "seeker2", "seeker", "applied");
		address = new Address("42", "Selim street", "43d", "Netrokona", "Bangladesh");
		address.userAddress = userseeker;
		userseeker.address = address;
		userseeker.create();
		seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		User usermate = new User("25s4sk","Rashed", "Amir", date, "jeff@gmail.com", "mate1", "mate", "accepted");
		address = new Address("3", "Gulshan", "5A", "Dhaka", "Bangladesh");
		address.userAddress = usermate;
		usermate.address = address;
		usermate.create();
		Mate mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
		usermate = new User("254s6e", "Abir", "Rasel",date, "rasel@gmail.com", "mate2", "mate", "applied");
		address = new Address("Court", "3B", "Park Street", "Rajshahi", "Bangladesh");
		address.userAddress = usermate;
		usermate.address = address;
		usermate.create();
		mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
		userseeker = new User("Martha", "Ahmed", "Azfar", date, "mar@gmail.com", "seeker3", "seeker", "applied");
		address = new Address("Dhanmondi", "27", "43D", "Dhaka", "Bangladesh");
		address.userAddress = userseeker;
		userseeker.address = address;
		userseeker.create();
		seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		// User Admin created
		User useradmin = new User("25s4sk","Mahabub", "Akram", date, "mak@gmail.com", "admin", "admin", "accepted");
		address = new Address("90", "Felsen", "5ds", "Dhaka", "Bangladesh");
		address.userAddress = useradmin;
		useradmin.address = address;
		useradmin.create();
		Admin admin = new Admin();
		admin.userAdmin = useradmin;
		admin.create();
		admin.save();		
	}

}

