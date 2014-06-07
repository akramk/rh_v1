import models.GiveHelpBody;
import models.Address;
import models.Mate;
import models.Seeker;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job{

	@Override
	public void doJob() throws Exception {
		if(GiveHelpBody.count() == 0){
			Fixtures.loadModels("GiveHelpBody.yml");
		}
		Fixtures.loadModels("Address.yml");
		Fixtures.loadModels("Mate.yml");
		Fixtures.loadModels("Seeker.yml");
	}
	
	
}
