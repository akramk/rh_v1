import models.Seeker;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job{

	@Override
	public void doJob() throws Exception {
		if(Seeker.count() == 0){
			Fixtures.loadModels("SeekerPostTable.yml");
			Fixtures.loadModels("SeekerPostComment.yml");
			Fixtures.loadModels("Address.yml");
			Fixtures.loadModels("Mate.yml");
			Fixtures.loadModels("Seeker.yml");
		}
		
	}
	
	
}
