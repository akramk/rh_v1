import org.junit.*;

import java.sql.Time;
import java.util.*;

import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;
import play.test.*;
import models.*;
import play.mvc.Controller;
import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.ivy.util.Message;

import models.SeekerPostTable;
import models.Mate;
import models.Seeker;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Validation;
import play.mvc.Controller.*;
import controllers.GiveHelpController;
import controllers.LogInController;
import controllers.SeekHelpController;

public class FuncTest extends FunctionalTest {

	@Before
	// to delete the database before any test
	public void setup() {
		Fixtures.deleteDatabase(); // Fixtures class is a helper to deal with
									// your database during tests.
	}

	@Test
	@SuppressWarnings("deprecation")
	public void loginUser() {
		// Create a new user and save it
		Seeker bob = new Seeker("12", "Jun", "Seeker", 25,
				"abm.junaed@gmail.com", "123").save();
		try {
			Request req = newRequest();
//			req.action = "http://localhost:9000/";
			req.actionMethod = "GET";
			req.args = new HashMap<>();
			req.args.put("email", "abm.junaed@gmail.com");
			req.args.put("pwd", "123");
			req.args.put("type", "seeker");

			Response res = GET(req, "/");
			System.out.println(res.status);
			
//			LogInController.logIn("abm.junaed@gmail.com", "123", "seeker");
		} catch (play.mvc.results.Redirect e) {
			Date startDate = new Date("10/07/20");
			Time startTime = new Time(9, 30, 00);
			Time endTime = new Time(10, 30, 00);
			// public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date
			// date, Time timeStart, Time timeEnd,
			// String location, String title, String post, Integer
			// matesRequired,Integer mateApplied) {
			SeekerPostTable seekerPost = new SeekerPostTable("bob1", bob,
					startDate, startTime, endTime, "Munich", "Help 1",
					"This is a help seek post for help 1", 2, 0).save();
			// System.out.println("Works here" + e);
			try {
				System.out.println("ID: " + seekerPost.id);
				GiveHelpController.mateIncrementer1(seekerPost.id);
			} catch (play.mvc.results.Redirect e2) {
				seekerPost = SeekerPostTable.findById(seekerPost.id);
				assertEquals(1, seekerPost.matesWantToHelp.size());
			}

			catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("Here the test should fail" + e);
			e.printStackTrace();
		}
	}

}
