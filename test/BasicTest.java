import org.junit.*;

import java.sql.Time;
import java.util.*;

import play.mvc.Scope.Session;
import play.test.*;
import models.*;
import play.mvc.Controller;
import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class BasicTest extends UnitTest {

	@Before
	// to delete the database before any test
	public void setup() {
		Fixtures.deleteDatabase(); // Fixtures class is a helper to deal with
									// database during tests.
	}

	@Test
	
	public void createSeeker() {// create a seeker
		// Create a new user and save it
		// Seeker(String ssid, String firstName, String lastName, int age,String email, String pass)
		new Seeker("12", "Jun", "Seeker", 25, "abm.junaed@gmail.com", "123").save();
		// Retrieve the user with e-mail and pass
		Seeker jun = Seeker.find("byEmailAndPass", "abm.junaed@gmail.com","123").first();

		// Test
		assertNotNull(jun);
		assertEquals("Jun", jun.firstName);
		// find all users using that email and pass
		List<Seeker> seekers = Seeker.find("byEmailAndPass","abm.junaed@gmail.com", "123").fetch();
		assertEquals(1, seekers.size());
	}

	@Test
	public void deleteSeeker() {// delete a seeker
		// Create a new user and save it
		new Seeker("12", "Jun", "Seeker", 25, "abm.junaed@gmail.com", "123")
				.save();
		// Retrieve the user with e-mail and pass
		Seeker jun = Seeker.find("email = ? and pass = ?",
				"abm.junaed@gmail.com", "123").first();

		// Test
		assertNotNull(jun);
		assertEquals("Jun", jun.firstName);
		// fetch all seekers
		List<Seeker> seekers = Seeker.find("email like ? and pass like ?",
				"abm.junaed@gmail.com", "123").fetch();
		// delete 1st seeker
		seekers.get(0).delete();
		// fetch all seekers
		seekers = Seeker.find("email = ? and pass = ?", "abm.junaed@gmail.com",
				"123").fetch();
		assertEquals(0, seekers.size());
	}

	@Test
	public void createMate() {// create a mate
		// Create a new user and save it
		new Mate("12", "Jun", "Mate", 25, "abm.junaed@gmail.com", "123").save();
		// Retrieve the user with e-mail address bob@gmail.com
		Mate junaed = Mate.find("byEmail", "abm.junaed@gmail.com").first();

		// Test
		assertNotNull(junaed);
		assertEquals("Jun", junaed.firstName);
	}

	@Test
	public void deleteMate() {// delete a mate
		// Create a new user and save it
		new Mate("12", "Jun", "Seeker", 25, "abm.junaed@gmail.com", "123")
				.save();
		// Retrieve the user with e-mail and pass
		Mate jun = Mate.find("email = ? and pass = ?", "abm.junaed@gmail.com",
				"123").first();

		// Test
		assertNotNull(jun);
		assertEquals("Jun", jun.firstName);
		// fetch all mates
		List<Mate> mates = Mate.find("email = ? and pass = ?",
				"abm.junaed@gmail.com", "123").fetch();
		// delete 1st mates
		mates.get(0).delete();
		// fetch all mates
		mates = Seeker.find("email = ? and pass = ?", "abm.junaed@gmail.com",
				"123").fetch();
		assertEquals(0, mates.size());
	}
	@Test
	public void createPost() {// create a post
		// Create a new user and save it
		Seeker jun = new Seeker("12", "Jun", "Seeker", 25,
				"abm.junaed@gmail.com", "123").save();

		// Create fields for new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd, String location, String title, String post, Integer matesRequired,Integer mateApplied)
		// create a new post
		new SeekerPostTable("jun1", jun, startDate, startTime, endTime,"Munich", "Help 1", "This is a help seek post for help 1", 2, 0).save();

		// Test that the post has been created
		assertEquals(1, SeekerPostTable.count());

		// Retrieve all posts created by jun
		List<SeekerPostTable> junPosts = SeekerPostTable.find("bySeekerWhoPosted", jun).fetch();

		// Tests
		assertEquals(1, junPosts.size());
		SeekerPostTable firstPost = junPosts.get(0);
		assertNotNull(firstPost);
		assertEquals(jun, firstPost.seekerWhoPosted);// author property of Post class is User object.
		assertEquals("Help 1", firstPost.title);
		assertEquals("This is a help seek post for help 1", firstPost.post);
		assertNotNull(firstPost.postdate);
	}

	@Test
	public void deletePost() {// delete a post
		// Create a new user and save it
		Seeker jun = new Seeker("12", "Jun", "Seeker", 25,
				"abm.junaed@gmail.com", "123").save();

		// Create a new post
		// create fields for new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd,
						// String location, String title, String post, Integer, matesRequired,Integer mateApplied)
		new SeekerPostTable("jun1", jun, startDate, startTime, endTime,"Munich", "Help 1", "This is a help seek post for help 1", 2, 0).save();

		// Test that the post has been created
		assertEquals(1, SeekerPostTable.count());

		// Retrieve all posts created by jun
		List<SeekerPostTable> junPosts = SeekerPostTable.find("bySeekerWhoPosted", jun).fetch();

		SeekerPostTable firstPost = junPosts.get(0);

		// Now delete the post
		firstPost.delete();
		// Test
		// Retrieve all posts created by jun
		junPosts = SeekerPostTable.find("bySeekerWhoPosted", jun).fetch();
		assertEquals(0, junPosts.size());

	}

	@Test
	public void postComments() {// create comments under a post
		// // Create a new user and save it
		Seeker bob = new Seeker("12", "Jun", "Seeker", 25,
				"abm.junaed@gmail.com", "123").save();
		// create a new mate and save it
		// Mate(String ssid, String firstName, String lastName, int age,String
		// email, String pass)
		Mate mate = new Mate("13mate", "Akram", "Mate", 25, "akram@gmail.com",
				"123").save();
		// Create a new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd, String location, String title, String post, Integer matesRequired,Integer mateApplied)
		SeekerPostTable seekerPost = new SeekerPostTable("bob1", bob,
				startDate, startTime, endTime, "Munich", "Help 1",
				"This is a help seek post for help 1", 2, 0).save();

		// Post two comments, one from mate, one from seeker
		// SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
		new SeekerPostComment(seekerPost, "seeker", bob, null,
				"comment from seeker").save();
		new SeekerPostComment(seekerPost, "mate", null, mate,
				"comment from mate").save();

		// Retrieve all comments
		List<SeekerPostComment> seekerPostComments = SeekerPostComment.find(
				"byPost", seekerPost).fetch();

		// Tests
		assertEquals(2, seekerPostComments.size());

		SeekerPostComment firstComment = seekerPostComments.get(0);
		assertNotNull(firstComment);
		assertEquals("Jun", firstComment.authorSeeker.firstName);
		assertEquals("comment from seeker", firstComment.content);
		assertNotNull(firstComment.postedAt);

		SeekerPostComment secondComment = seekerPostComments.get(1);
		assertNotNull(secondComment);
		assertEquals("Akram", secondComment.authorMate.firstName);
		assertEquals("comment from mate", secondComment.content);
		assertNotNull(secondComment.postedAt);
	}

	@Test
	public void deletePostComments() {// create comments under a post and delete
										// them
		// // Create a new user and save it
		Seeker seeker = new Seeker("12", "Jun", "Seeker", 25,
				"abm.junaed@gmail.com", "123").save();
		// create a new mate and save it
		// Mate(String ssid, String firstName, String lastName, int age,String
		// email, String pass)
		Mate mate = new Mate("13mate", "Akram", "Mate", 25, "akram@gmail.com",
				"123").save();
		// Create a new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
		SeekerPostTable seekerPost = new SeekerPostTable("jun1", seeker,
				startDate, startTime, endTime, "Munich", "Help 1",
				"This is a help seek post for help 1", 2, 0).save();

		// Post two comments, one from seeker another from mate
		// SeekerPostComment(SeekerPostTable post, String userType, Seeker
		// authorSeeker,Mate authorMate, String content) {
		new SeekerPostComment(seekerPost, "seeker", seeker, null,
				"comment from seeker").save();
		new SeekerPostComment(seekerPost, "mate", null, mate,
				"comment from mate").save();

		// Retrieve all comments
		List<SeekerPostComment> seekerPostComments = SeekerPostComment.find(
				"byPost", seekerPost).fetch();

		// Tests
		assertEquals(2, seekerPostComments.size());
		// delete 2nd comment
		SeekerPostComment secondCmnt = seekerPostComments.get(1).delete();
		// find all comments
		seekerPostComments = SeekerPostComment.find("byPost", seekerPost)
				.fetch();
		assertEquals(1, seekerPostComments.size());
		// check if the first comment is ok
		SeekerPostComment firstComment = seekerPostComments.get(0);
		assertNotNull(firstComment);
		assertEquals("Jun", firstComment.authorSeeker.firstName);
		assertEquals("comment from seeker", firstComment.content);
		assertNotNull(firstComment.postedAt);

		// delete 1st comment also
		seekerPostComments.get(0).delete();
		// find all comments
		seekerPostComments = SeekerPostComment.find("byPost", seekerPost)
				.fetch();

		assertEquals(0, seekerPostComments.size());

	}

	@Test
	public void useTheCommentsRelation() {// if I delete a post, all of its comments will also be deleted,now test it
		// Create a new user and save it
		Seeker jun = new Seeker("12", "Jun", "Seeker", 25,"abm.junaed@gmail.com", "123").save();
		// create a new mate and save it
		// Mate(String ssid, String firstName, String lastName, int age,String email, String pass)
		Mate mate = new Mate("13mate", "Akram", "Mate", 25, "akram@gmail.com","123").save();
		// Create a new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// public SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
		SeekerPostTable seekerPost = new SeekerPostTable("jun1", jun,startDate, startTime, endTime, "Munich", "Help 1","This is a help seek post for help 1", 2, 0).save();

		// Post a first comment
		// addComment(String userType, Long userId, String content)
		List<Seeker> seeker = Seeker.find("email = ? and pass = ?","abm.junaed@gmail.com", "123").fetch();
		List<Mate> mates = Mate.find("email = ? and pass = ?","akram@gmail.com", "123").fetch();
		System.out.println(seeker.get(0).id);
		seekerPost.addComment("seeker", seeker.get(0).id, "comment from seeker");
		seekerPost.addComment("mate", mates.get(0).id, "hi");
		
		// Count things
		assertEquals(1, Mate.count());
		assertEquals(1, Seeker.count());
		assertEquals(1, SeekerPostTable.count());
		assertEquals(2, SeekerPostComment.count());

		// Retrieve jun's post
		SeekerPostTable junPost = SeekerPostTable.find("bySeekerWhoPosted", jun).first();
		assertNotNull(junPost);

		// Navigate to comments
		assertEquals(2, junPost.comments.size());
		assertEquals("Jun", junPost.comments.get(0).authorSeeker.firstName);
		assertEquals("Akram", junPost.comments.get(1).authorMate.firstName);

		// Delete the post
		junPost.delete();

		// Check that all comments have been deleted
		assertEquals(1, Mate.count());
		assertEquals(1, Seeker.count());
		assertEquals(0, SeekerPostTable.count());
		assertEquals(0, SeekerPostComment.count());
	}
	@Test
	public void addHelpMate(){//add a help mate to a post where seeker wanted help
		// Create a new user and save it
		Seeker jun = new Seeker("12", "Jun", "Seeker", 25,"abm.junaed@gmail.com", "123").save();
		// create a new mate and save it
		// Mate(String ssid, String firstName, String lastName, int age,String email, String pass)
		Mate mate = new Mate("13mate", "Akram", "Mate", 25, "akram@gmail.com","123").save();
		// Create a new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// public SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
		SeekerPostTable seekerPost = new SeekerPostTable("jun1", jun,startDate, startTime, endTime, "Munich", "Help 1","This is a help seek post for help 1", 2, 0).save();
		
		//add a helpmate to this post
		seekerPost.addHelpMate(mate);
		//test
		assertEquals(1, seekerPost.matesWantToHelp.size());
	}
	@Test
	public void removeHelpMate(){//add a help mate to a post where seeker wanted help and then remove this mate from that post
		// Create a new user and save it
		Seeker jun = new Seeker("12", "Jun", "Seeker", 25,"abm.junaed@gmail.com", "123").save();
		// create a new mate and save it
		// Mate(String ssid, String firstName, String lastName, int age,String email, String pass)
		Mate mate = new Mate("13mate", "Akram", "Mate", 25, "akram@gmail.com","123").save();
		// Create a new post
		@SuppressWarnings("deprecation")
		Date startDate = new Date("10/07/2014");
		Date endDate = new Date("10/07/2014");
		Time startTime = new Time(9, 30, 00);
		Time endTime = new Time(10, 30, 00);
		// public SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
		SeekerPostTable seekerPost = new SeekerPostTable("jun1", jun,startDate, startTime, endTime, "Munich", "Help 1","This is a help seek post for help 1", 2, 0).save();
		
		//add a helpmate to this post
		seekerPost.addHelpMate(mate);
		//test
		assertEquals(1, seekerPost.matesWantToHelp.size());
		//remove this mate's help
		seekerPost.removeHelpMate(mate);
		assertEquals(0, seekerPost.matesWantToHelp.size());
	}

}
