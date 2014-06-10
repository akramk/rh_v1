import org.junit.*;

import java.sql.Time;
import java.util.*;

import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

//	@Before //to delete the database before any test
//    public void setup() {
//        Fixtures.deleteDatabase(); //Fixtures class is a helper to deal with your database during tests. 
//    }
	
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
	@Test
	public void createPost() {
	    // Create a new user and save it
		Seeker bob = new Seeker("12","Jun","Seeker",25,"abm.junaed@gmail.com","123").save();
	    
	    // Create a new post
		@SuppressWarnings("deprecation")
		Date startDate=new Date("10/07/2014");
		Date endDate=new Date("10/07/2014");
		Time startTime=new Time(9, 30, 00);
		Time endTime=new Time(10, 30, 00);
		//public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd,
//		String location, String title, String post, Integer matesRequired,Integer mateApplied) {
	    new SeekerPostTable("bob1", bob,startDate,startTime, endTime,"Munich","Help 1","This is a help seek post for help 1",2,0).save();
	    
	    // Test that the post has been created
	    assertEquals(1, SeekerPostTable.count());
	    
	    // Retrieve all posts created by Bob
	    List<SeekerPostTable> bobPosts = SeekerPostTable.find("bySeekerWhoPosted", bob).fetch();
	    
	    // Tests
	    assertEquals(1, bobPosts.size());
	    SeekerPostTable firstPost = bobPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals(bob, firstPost.seekerWhoPosted);//author property of Post class is User object.
	    assertEquals("Help 1", firstPost.title);
	    assertEquals("This is a help seek post for help 1", firstPost.post);
	    assertNotNull(firstPost.postdate);
	}
	@Test
	public void postComments() {
	    // // Create a new user and save it
		Seeker bob = new Seeker("12","Jun","Seeker",25,"abm.junaed@gmail.com","123").save();
	    //create a new mate and save it
		//Mate(String ssid, String firstName, String lastName, int age,String email, String pass) 
		Mate mate = new Mate("13mate","Akram","Mate",25,"akram@gmail.com","123").save();
	    // Create a new post
		@SuppressWarnings("deprecation")
		Date startDate=new Date("10/07/2014");
		Date endDate=new Date("10/07/2014");
		Time startTime=new Time(9, 30, 00);
		Time endTime=new Time(10, 30, 00);
		//public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd,
//		String location, String title, String post, Integer matesRequired,Integer mateApplied) {
		SeekerPostTable seekerPost=new SeekerPostTable("bob1", bob,startDate,startTime, endTime,"Munich","Help 1","This is a help seek post for help 1",2,0).save();
	 
	    // Post a first comment
	    //SeekerPostComment(SeekerPostTable post, String userType, Seeker authorSeeker,Mate authorMate, String content) {
	    new SeekerPostComment(seekerPost,"seeker", bob,null, "comment from seeker").save();
	    new SeekerPostComment(seekerPost, "mate",null,mate, "comment from mate").save();
	 
	    // Retrieve all comments
	    List<SeekerPostComment> seekerPostComments = SeekerPostComment.find("byPost", seekerPost).fetch();
	 
	    // Tests
	    assertEquals(2, seekerPostComments .size());
	 
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
	public void useTheCommentsRelation() {//if I delete a post, all of its comments will also be deleted, now test it
	    // Create a new user and save it
		Seeker bob = new Seeker("12","Jun","Seeker",25,"abm.junaed@gmail.com","123").save();
		//create a new mate and save it
				//Mate(String ssid, String firstName, String lastName, int age,String email, String pass) 
				Mate mate = new Mate("13mate","Akram","Mate",25,"akram@gmail.com","123").save();
			    // Create a new post
				@SuppressWarnings("deprecation")
				Date startDate=new Date("10/07/2014");
				Date endDate=new Date("10/07/2014");
				Time startTime=new Time(9, 30, 00);
				Time endTime=new Time(10, 30, 00);
				//public SeekerPostTable(String seeker,Seeker seekerWhoPosted, Date date, Time timeStart, Time timeEnd,
//				String location, String title, String post, Integer matesRequired,Integer mateApplied) {
				SeekerPostTable seekerPost=new SeekerPostTable("bob1", bob,startDate,startTime, endTime,"Munich","Help 1","This is a help seek post for help 1",2,0).save();
			 
			    // Post a first comment
				//addComment(String userType, Long userId, String content)
				List<Seeker> seeker = Seeker.find("email like ? and pass like ?", "abm.junaed@gmail.com","123").fetch();
				List<Mate> mates = Mate.find("email like ? and pass like ?", "akram@gmail.com","123").fetch();
				System.out.println(seeker.get(0).id); 
//				seekerPost.addComment("seeker", seeker.get(0).id, "comment from seeker");
				seekerPost.addComment("mate",(long) 1, "hi");
//			    seekerPost.addComment("mate", mates.get(0).id,"comment from mate");	 
	    // Count things
	    assertEquals(2, User.count());
	    assertEquals(1, SeekerPostTable.count());
	    assertEquals(2, SeekerPostComment.count());
	 
	    // Retrieve Bob's post
	    SeekerPostTable bobPost = SeekerPostTable.find("bySeekerWhoPosted", bob).first();
	    assertNotNull(bobPost);
	 
	    // Navigate to comments
	    assertEquals(2, bobPost.comments.size());
	    assertEquals("Jun", bobPost.comments.get(0).authorSeeker.firstName);
	    assertEquals("Akram", bobPost.comments.get(0).authorMate.firstName);
	    
	    // Delete the post
	    bobPost.delete();
	    
	    // Check that all comments have been deleted
	    assertEquals(1, User.count());
	    assertEquals(0, SeekerPostTable.count());
	    assertEquals(0, SeekerPostComment.count());
	}

}
