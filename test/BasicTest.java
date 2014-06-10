import org.junit.*;

import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

	@Before //to delete the database before any test
    public void setup() {
        Fixtures.deleteDatabase(); //Fixtures class is a helper to deal with your database during tests. 
    }
	
	@Test
	//Seeker(String ssid, String firstName, String lastName, int age,String email, String pass)
	public void createUser(){
		// Create a new user and save it
		new Seeker("12","Jun","Seeker",25,"abm.junaed@gmail.com","123").save();
		// Retrieve the user with e-mail address bob@gmail.com
		Seeker bob = Seeker.find("byEmail", "abm.junaed@gmail.com").first();
   	 
    	// Test
    	assertNotNull(bob);
    	assertEquals("Jun", bob.firstName);
	}

}
