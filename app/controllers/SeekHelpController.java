package controllers;

import groovyjarjarcommonscli.ParseException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Mate;
import models.MatePostTable;
import models.SeekerPostTable;
import models.Seeker;
import play.data.validation.Required;
import play.mvc.Controller;

public class SeekHelpController extends Controller {

	public static void seekHelpRedir() {
		try {
			List<MatePostTable> seekHelpPost=seekHelpSearch(null, null, null, null);
			render(seekHelpPost);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void seekHelp(String seeker, @Required String postDate,@Required String timeStart, @Required String timeEnd,
			String location, int matesRequired, String title, String post) throws ParseException, java.text.ParseException {

		
		
		//System.out.println(session.get("type"));
		int mate_applied = 0;
		boolean all_check = false;
		if (session.get("id")!=null && postDate != null && !timeStart.equals("") && !timeEnd.equals("") 
				&& !location .equals("")) 
		{
			all_check = true;
		} 
		else 
		{
			flash.error("Please check your input data Again!");
			seekHelpRedir();
		}

		// page redirected and landed
		if (all_check) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			boolean flag = false;
			Date date;
			if (!postDate.equalsIgnoreCase("") && all_check) {
				date = dateFormat.parse(postDate);
				flag = true;
			} else {
				date = new Date();
			}

			// SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Time timeS = null;
			if (timeStart != null && flag == true) {
				timeS = java.sql.Time.valueOf(timeStart);
				flag = true;
			} else {
				timeS = java.sql.Time.valueOf("00:00:00");
			}

			Time timeE = null;
			if (timeStart != null && flag == true) {
				timeE = java.sql.Time.valueOf(timeEnd);
				flag = true;
			} else {
				timeE = java.sql.Time.valueOf("00:00:00");
			}

			//SeekerPostTable giveHelpPost = new SeekerPostTable(seeker, date, timeS,timeE, location, title, post, matesRequired, mate_applied);
			System.out.println("Flag" + flag);
			if (flag == true) {
				//find the user who post this and save this post under this user 
				try {
					Long seekerId=Long.parseLong(session.get("id"));
					Seeker seekerObj=Seeker.findById(seekerId);
					SeekerPostTable giveHelpPostObj = new SeekerPostTable(session.get("userName"),seekerObj, date, timeS,
							timeE, location, title, post, matesRequired, mate_applied);
					giveHelpPostObj.create();
//				System.out.println(giveHelpPostObj.location + giveHelpPostObj.seeker+giveHelpPostObj.id +seekerObj.userSeeker.firstName);
					seekerObj.addPost(giveHelpPostObj);
					GiveHelpController.giveHelpSearch(null, null, null, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			} else {
				seekHelpRedir();
			}

		}

	}
	
	//this will show the detail of a seeker's post
	public static void seekerPostShowDetail(Long id){
		SeekerPostTable post=SeekerPostTable.findById(id);
		System.out.println(post.id+post.post);
		List<Mate>matesWantedtoHelp = post.matesWantToHelp;
		boolean mateFound = false;
		for(int i =0; i<matesWantedtoHelp.size();i++){
			if(matesWantedtoHelp.get(i).id == Long.parseLong(session.get("id"))){
				System.out.println(i + "Found"+ matesWantedtoHelp.get(i).id);
				mateFound = true;
			}
		}
		render(post, mateFound);
	}
	
	public static void postComment(Long postId, @Required String content){
		SeekerPostTable post = SeekerPostTable.findById(postId);
        if (validation.hasErrors()) {
            render("SeekHelpController/seekerPostShowDetail.html", post);
        }
      //addComment is a function of SeekerPostTable model
        post.addComment(session.get("userType"),Long.parseLong(session.get("id")), content);//id of SeekerPostTable is Long, but 
        //session stores data in string so convert the id to long using Long.parseLong
        seekerPostShowDetail(postId);
	}

	/*
	@param location
	@param searchDate
	@param timeStart
	@param timeEnd
	This function searches on these parameter basis. And there are different criterias for searche queries.	
	*/
		
		/**
		 * @param location
		 * @param searchDate
		 * @param timeStart
		 * @param timeEnd
		 * @throws java.text.ParseException
		 */
		/**
		 * @param location
		 * @param searchDate
		 * @param timeStart
		 * @param timeEnd
		 * @throws java.text.ParseException
		 */
	//find the posts of mates who are free to help
		public static List<MatePostTable> seekHelpSearch(String location, String searchDateS, String timeStart, String timeEnd) 
				throws java.text.ParseException {
			List<MatePostTable> seekHelpPost =null;
			SimpleDateFormat dateFormatS = new SimpleDateFormat("dd/MM/yyyy");
			Date searchDate = null;
			if (searchDateS != null && !searchDateS.equals("")) {
				searchDate = dateFormatS.parse(searchDateS);
			}
			System.out.println(searchDate);
			System.out.println(searchDateS);
			
			System.out.println(timeStart + "----" + timeEnd + "Location"+ location + "Date" + searchDate);
			
			//System.out.println(session.get("type"));
			
			
	/*When the page will be reloaded from the navigation panel through clicking any link or button then this page will be reloaded.
	so then this condition will be checked. As at that time all the parameters will be null. Because search panel will return
	no outputs but null.
	*/
			if (location == null && searchDate == null && timeStart == null&& timeEnd == null) 
			{
				seekHelpPost = MatePostTable.findAll();
				// Find those posts which have status = open. We don't need to fetch the whole dataset of GiveHelpBody 
				String status ="open";
				seekHelpPost = MatePostTable.find("status like ?", status).fetch();
				System.out.println("ALL NULL and status is OPEN");
				return seekHelpPost;
			} 
			
	/*When user will give all the values in the search panel then this will be the search query condition. And all the parameter will be
	merged with "AND" . Keep in mind that we have time attribute in table that is timeStart and timeEnd so we have to format the
	timeStart and timeEnd string to Time variable.*/
			else if ((!location.equalsIgnoreCase("")) && (searchDate != null)&& (!timeStart.equals("")) 
					&& (!timeEnd.equals(""))) 
			{
				seekHelpPost = MatePostTable.find("postdate >= ? and location like ? and timeStart >= ? and timeEnd >= ?",
								searchDate, location,
								java.sql.Time.valueOf(timeStart),
								java.sql.Time.valueOf(timeEnd)).fetch();
				System.out.println("ALL NOT NULL");
				return seekHelpPost;
			}
	
	/*This check occurs when user does not give any values in the search panel and press the submit button.	*/	
			else if ((location == null || location.equalsIgnoreCase("")) && (searchDate == null || searchDate.equals(""))
					&& (timeStart == null || timeStart.equals("")) && (timeEnd == null || timeEnd.equals(""))) 
			{
				seekHelpPost= MatePostTable.findAll();
				System.out.println("Condition 3");
				return seekHelpPost;
			}
	
	/*This occurs when user gives any values in the search Panel. Not all the values but any values.*/
			else if (!location.equalsIgnoreCase("") || searchDate != null || !timeStart.equals("") || !timeEnd.equals("")) 
			{
				System.out.println("Last Else IF Value Found: " + timeStart + "----" + timeEnd + "Location" 
			      + location + "Date" + searchDate);
	
				if (searchDate == null) {
					// if user does not give any date then it will automatically get the date of 01/01/1990. 
					//because we dont have any entry obviously before 1990
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					searchDate = dateFormat.parse("01/01/1990");
	//				dateS = dateFormat.parse("01/01/1990");
				}
	
				if (timeStart.equalsIgnoreCase("")) {//when user dont give any start time it gets the 00hh 00mm and 00ss
					timeStart = "00:00:00";
				}
	
				if (timeEnd.equalsIgnoreCase("")) {//when user dont give any end time it gets the 00hh 00mm and 00ss
					timeEnd = "00:00:00";
				}
				
				seekHelpPost = MatePostTable
						.find("postdate >= ? and location like ? and timeStart >= ? and timeEnd >= ?",
								searchDate, '%'+location+'%',
								java.sql.Time.valueOf(timeStart),
								java.sql.Time.valueOf(timeEnd)).fetch();
				return seekHelpPost;
			}
		/*This works automatically when all cases fail then it will fetch all the messages post from the table.*/	
			else {
				seekHelpPost = MatePostTable.findAll();
				System.out.println("Condition Final");
				return seekHelpPost;
			}
	
		}
		//when seeker clicks "I need help" under a post of mate, then this function increases number of seekers
		//wanted help under that post of mate
		public static void seekerIncrementer1(Long postId) throws ParseException{
				System.out.println("I want Help button is being clicked : "+ postId);
				//fetch the specific mates post object from the table.
				MatePostTable seekHelpPost = MatePostTable.findById(postId);
				//updates the number of seekerApplied in each click
				seekHelpPost.seekersApplied= seekHelpPost.seekersApplied+1;
				//use this logged in user as the seeker
				Long userId=Long.parseLong(session.get("id"));
				Seeker author=Seeker.findById(userId);
				seekHelpPost.addSeeker(author);
				//in database, this post has been updated. but seekHelpPost contains the data of old row.
				//So, again fetch the row from database.
				seekHelpPost = MatePostTable.findById(postId);
		//		System.out.println(giveHelpPost.matesWantToHelp.get(0).firstName);
				//Check for whether the mateApplied is equals to mateRequired or not then change the status to close 
				if(seekHelpPost.seekersApplied == seekHelpPost.seekersRequired){
					seekHelpPost.status = "close";
					System.out.println(" closed ");
				}
				//save the object in the database
				seekHelpPost.save();
				System.out.println();
				GiveHelpController.matePostShowDetail(postId);
			}
		//this seeker needed help under a post, now he dont want to take help. So revoke the request he needed here
		public static void seekerRevokeHelpNeeded(Long postId) throws ParseException{
				System.out.println("Rev0ke Help Needed button is being clicked : "+ postId);
				//fetch the specific post object from the matepost table.
				MatePostTable seekHelpPost = MatePostTable.findById(postId);
				//updates the number of seekerApplied in each click
				seekHelpPost.seekersApplied = seekHelpPost.seekersApplied-1;
				//use this logged in user as the seeker
				Long userId=Long.parseLong(session.get("id"));
				Seeker author=Seeker.findById(userId);
		//		giveHelpPost.addHelpMate(author);
		
		//		giveHelpPost.removeHelpMate(author);
		//		giveHelpPost = SeekerPostTable.findById(postId);
		
				//		System.out.println(giveHelpPost.matesWantToHelp.get(0).firstName);
				//Check for whether the mateApplied is equals to mateRequired or not then change the status to close 
				if(seekHelpPost.seekersApplied>=0 ){
					seekHelpPost.removeSeeker(author);
					seekHelpPost = MatePostTable.findById(postId);
					seekHelpPost.status = "open";
					System.out.println(" open ");
					seekHelpPost.save();
				}
				//save the object in the database
		//		giveHelpPost.save();
				System.out.println();
				GiveHelpController.matePostShowDetail(postId);
			}
}
