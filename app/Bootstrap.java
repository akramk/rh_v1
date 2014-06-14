import java.util.List;

import external.DataFill;
import models.Mate;
import models.Seeker;
import models.User;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job{

	@Override
	public void doJob() throws Exception {
		if(User.count() == 0){
//			Fixtures.loadModels("SeekerPostTable.yml");
//			Fixtures.loadModels("SeekerPostComment.yml");
//			Fixtures.loadModels("Address.yml");
//			Fixtures.loadModels("User.yml");
			DataFill dt = new DataFill();
			dt.dataFiller();
		}
		
	}
	
//	public void dataFiller(){
//		User userseeker = new User("Mask", "Rip", 50, "mask@gmail.com", "seeker1", "seeker");
//		userseeker.create();
//		Seeker seeker = new Seeker();
//		seeker.userSeeker = userseeker;
//		seeker.create();
//		seeker.save();
//		
//		
//		userseeker = new User("Riz", "Bon", 62, "riz@gmail.com", "seeker2", "seeker");
//		userseeker.create();
//		seeker = new Seeker();
//		seeker.userSeeker = userseeker;
//		seeker.create();
//		seeker.save();
//		
//		
//		User usermate = new User("John", "Fred", 20, "jeff@gmail.com", "mate1", "mate");
//		usermate.create();
//		Mate mate = new Mate();
//		mate.userMate = usermate;
//		mate.create();
//		mate.save();
//		
//		usermate = new User("Alex", "Rass", 25, "alex@gmail.com", "mate2", "mate");
//		usermate.create();
//		mate = new Mate();
//		mate.userMate = usermate;
//		mate.create();
//		mate.save();
//		
//	}
	
	
}
