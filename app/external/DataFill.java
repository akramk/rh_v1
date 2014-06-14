package external;

import models.Mate;
import models.Seeker;
import models.User;

public class DataFill {
	public void dataFiller(){
		User userseeker = new User("Mask", "Rip", 50, "mask@gmail.com", "seeker1", "seeker");
		userseeker.create();
		Seeker seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		userseeker = new User("Riz", "Bon", 62, "riz@gmail.com", "seeker2", "seeker");
		userseeker.create();
		seeker = new Seeker();
		seeker.userSeeker = userseeker;
		seeker.create();
		seeker.save();
		
		
		User usermate = new User("John", "Fred", 20, "jeff@gmail.com", "mate1", "mate");
		usermate.create();
		Mate mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
		usermate = new User("Alex", "Rass", 25, "alex@gmail.com", "mate2", "mate");
		usermate.create();
		mate = new Mate();
		mate.userMate = usermate;
		mate.create();
		mate.save();
		
	}

}

