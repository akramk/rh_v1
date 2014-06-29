package controllers;

import java.awt.Panel;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.Mate;
import models.MatePostTable;
import models.Notification;
import models.Seeker;
import models.SeekerPostTable;
import play.data.validation.Required;
import play.mvc.Controller;

public class GiveHelpController extends Controller {

/*For the beginning as we dont have any navigation panel but we need to have something to navigate through the pages so I have made
this form this will redirect you to the giveHelp page Where you will be able to see all the posts asked for help. Then you can 
filter using the search panel. Also you can go to the SeekHelp page through pusing the button "SeekHelp". As usual this is used 
for navigation. As we dont have any navigation panel now. But those who will design the interface can followi this apporach
to navigate the links.*/
	public static void giveHelp() throws ParseException {		
		//render();
		GiveHelpController.giveHelpSearch(null, null, null, null);
		
		
	}
	
	public static void giveHelpSearchMetaLocation(String location, String searchDateS, String timeStart, String timeEnd) throws ParseException{
		System.out.println("Meta search called Location");
		GiveHelpController.giveHelpSearch(location, "", "", "");
		
	}
	
	public static void giveHelpSearchMetaDate(String location, String searchDateS, String timeStart, String timeEnd) throws ParseException{
		System.out.println("Meta search called Date");
		GiveHelpController.giveHelpSearch("", searchDateS, "", "");
		
	}
	
	public static void giveHelpSearchMetaTimeStart(String location, String searchDateS, String timeStart, String timeEnd) throws ParseException{
		System.out.println("Meta search called TimeStart");
		GiveHelpController.giveHelpSearch("", "", timeStart, "");
		
	}
	
	public static void giveHelpSearchMetaTimeEnd(String location, String searchDateS, String timeStart, String timeEnd) throws ParseException{
		System.out.println("Meta search called TimeEnd");
		GiveHelpController.giveHelpSearch("", "", "", timeEnd);
		
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
	public static void giveHelpSearch(String location, String searchDateS, String timeStart, String timeEnd) 
			throws java.text.ParseException {
		
		SimpleDateFormat dateFormatS = new SimpleDateFormat("MM/dd/yyyy");
		Date searchDate = null;
		if (searchDateS != null && !searchDateS.equals("")) {
			searchDate = dateFormatS.parse(searchDateS);
		}
		
		
		//System.out.println(session.get("type"));
		
		
/*When the page will be reloaded from the navigation panel through clicking any link or button then this page will be reloaded.
so then this condition will be checked. As at that time all the parameters will be null. Because search panel will return
no outputs but null.
*/
		if (location == null && searchDate == null && timeStart == null&& timeEnd == null) 
		{
			List<SeekerPostTable> giveHelpPost = SeekerPostTable.findAll();
			// Find those posts which have status = open. We don't need to fetch the whole dataset of GiveHelpBody 
			String status ="open";
			giveHelpPost = SeekerPostTable.find("status like ?", status).fetch();
			/*System.out.println("ALL NULL and status is OPEN");
			System.out.println("SEEKER WHO POSTED ID: "+giveHelpPost.get(0).seekerWhoPosted.userSeeker.id);*/
			render(giveHelpPost);
		} 
		
/*When user will give all the values in the search panel then this will be the search query condition. And all the parameter will be
merged with "AND" . Keep in mind that we have time attribute in table that is timeStart and timeEnd so we have to format the
timeStart and timeEnd string to Time variable.*/
		else if ((!location.equalsIgnoreCase("")) && (searchDate != null)&& (!timeStart.equals("")) 
				&& (!timeEnd.equals(""))) 
		{
			System.out.println(searchDate);
			List<SeekerPostTable> giveHelpPost = SeekerPostTable.find("postdate >= ? and location like ? and timeStart >= ? and timeEnd >= ?",
							searchDate, location,
							java.sql.Time.valueOf(timeStart),
							java.sql.Time.valueOf(timeEnd)).fetch();
			
			render(giveHelpPost,location,searchDateS, timeStart, timeEnd);
		}

        /*This check occurs when user does not give any values in the search panel and press the submit button.	*/	
		else if ((location == null || location.equalsIgnoreCase("")) && (searchDate == null || searchDate.equals(""))
				&& (timeStart == null || timeStart.equals("")) && (timeEnd == null || timeEnd.equals(""))) 
		{
			List<SeekerPostTable> giveHelpPost = SeekerPostTable.findAll();
			System.out.println("Condition 3");
			System.out.println(location.length());
			render(giveHelpPost,location,searchDateS, timeStart, timeEnd);
		}

        /*This occurs when user gives any values in the search Panel. Not all the values but any values.*/
		else if (!location.equalsIgnoreCase("") || searchDate != null || !timeStart.equals("") || !timeEnd.equals("")) 
		{
			System.out.println("Last Else IF Value Found: " + timeStart + "----" + timeEnd + "Location" 
		      + location + "Date" + searchDate);

			if (searchDate == null || searchDateS.equals("")) {
				// if user does not give any date then it will automatically get the date of 01/01/1990. 
				//because we dont have any entry obviously before 1990
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				searchDate = dateFormat.parse("01/01/1990");
//				dateS = dateFormat.parse("01/01/1990");
			}

			if (timeStart.equalsIgnoreCase("")) {//when user dont give any start time it gets the 00hh 00mm and 00ss
				timeStart = "00:00:00";
			}

			if (timeEnd.equalsIgnoreCase("")) {//when user dont give any end time it gets the 00hh 00mm and 00ss
				timeEnd = "23:59:00";
				System.out.println("No time time End"+java.sql.Time.valueOf(timeEnd));
			}
			
			if(searchDate == null || searchDateS.equals(""))
			{
				List<SeekerPostTable> giveHelpPost = SeekerPostTable
						.find("postdate >= ?  and location like ? and timeStart >= ? and timeEnd <= ?",
								searchDate, '%'+location+'%',
								java.sql.Time.valueOf(timeStart),
								java.sql.Time.valueOf(timeEnd)).fetch();
//			System.out.println("SEEKER WHO POSTED ID: "+giveHelpPost.get(0).seekerWhoPosted.userSeeker.id);
				render(giveHelpPost, location,searchDateS, timeStart, timeEnd);
			}
			else
			{
				List<SeekerPostTable> giveHelpPost = SeekerPostTable
						.find("postdate = ? and location like ? and timeStart >= ? and timeEnd <= ?",
								searchDate, '%'+location+'%',
								java.sql.Time.valueOf(timeStart),
								java.sql.Time.valueOf(timeEnd)).fetch();
				render(giveHelpPost, location,searchDateS, timeStart, timeEnd);
			}

		}
	    /*This works automatically when all cases fail then it will fetch all the messages post from the table.*/	
		else {
			List<SeekerPostTable> giveHelpPost = SeekerPostTable.findAll();
			System.out.println("Condition Final");
			render(giveHelpPost);
		}

	}
	/*
	 * This function increments the number of mateApplied of that specific Seeker. And reload the page of the post.	
	 */
	public static void mateIncrementer(String seeker) throws ParseException{
		System.out.println("Ready to Help button is being clicked to help: "+seeker);
		//fetch the specific seeker object from the table.
		SeekerPostTable giveHelpPost = SeekerPostTable.find("seeker like ?", seeker).first();
		//updates the number of mateApplied in each click
		giveHelpPost.mateApplied = giveHelpPost.mateApplied +1;
		//Check for whether the mateApplied is equals to mateRequired or not then change the status to close 
		if(giveHelpPost.mateApplied == giveHelpPost.matesRequired){
			giveHelpPost.status = "close";
			System.out.println(seeker+" closed ");
		}
		//save the object in the database
		giveHelpPost.save();
		System.out.println();
		GiveHelpController.giveHelpSearch(null, null, null, null);
	}
	
	public static void mateIncrementer1(Long postId) throws ParseException{
		System.out.println("Ready to Help button is being clicked to help: "+ postId);
		//fetch the specific seeker object from the table.
		SeekerPostTable giveHelpPost = SeekerPostTable.findById(postId);
		//updates the number of mateApplied in each click
		giveHelpPost.mateApplied = giveHelpPost.mateApplied +1;
		//use this logged in mate as the helper
		Long userId=Long.parseLong(session.get("id"));
		Mate author=Mate.findById(userId);
		giveHelpPost.addHelpMate(author);
		giveHelpPost = SeekerPostTable.findById(postId);
//		System.out.println(giveHelpPost.matesWantToHelp.get(0).firstName);
		//Check for whether the mateApplied is equals to mateRequired or not then change the status to close 
		if(giveHelpPost.mateApplied == giveHelpPost.matesRequired){
			giveHelpPost.status = "close";
			System.out.println(giveHelpPost.seeker+" closed ");
		}
		//save the object in the database
		giveHelpPost.save();
		System.out.println();
		SeekHelpController.seekerPostShowDetail(postId);
	}
	
	public static void mateRevokeHelp(Long postId) throws ParseException{
		System.out.println("Rev0ke Help button is being clicked : "+ postId);
		//fetch the specific seeker object from the table.
		SeekerPostTable giveHelpPost = SeekerPostTable.findById(postId);
		//updates the number of mateApplied in each click
		giveHelpPost.mateApplied = giveHelpPost.mateApplied -1;
		//use this logged in mate as the helper
		Long userId=Long.parseLong(session.get("id"));
		Mate author=Mate.findById(userId);
//		giveHelpPost.addHelpMate(author);

//		giveHelpPost.removeHelpMate(author);
//		giveHelpPost = SeekerPostTable.findById(postId);

		//		System.out.println(giveHelpPost.matesWantToHelp.get(0).firstName);
		//Check for whether the mateApplied is equals to mateRequired or not then change the status to close 
		if(giveHelpPost.mateApplied >=0 ){
			giveHelpPost.removeHelpMate(author);
			giveHelpPost = SeekerPostTable.findById(postId);
			giveHelpPost.status = "open";
			System.out.println(giveHelpPost.seeker+" open ");
			giveHelpPost.save();
		}
		//save the object in the database
//		giveHelpPost.save();
		System.out.println();
		SeekHelpController.seekerPostShowDetail(postId);
	}
	//when I mate post to indicating when he is free, call this function and sane his data
	public static void giveHelpPost(String postDate,String timeStart,String timeEnd,
			String location, int seekersRequired, String title, String post) throws ParseException, java.text.ParseException {

		
		
		//System.out.println(session.get("type"));
		int seeker_applied = 0;
		boolean all_check = false;
		if (session.get("id")!=null && postDate != null && !timeStart.equals("") && !timeEnd.equals("") 
				&& !location .equals("")) 
		{
			all_check = true;
		} 
		else 
		{
			flash.error("Please check your input data Again!");
			GiveHelpController.giveHelpSearch(null, null, null, null);
		}

		// page redirected and landed
		if (all_check) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			boolean flag = false;
			Date date;
			if (!postDate.equalsIgnoreCase("") && all_check) {
				date = dateFormat.parse(postDate);
				System.out.println("Posting Event Date: "+ date);
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

			System.out.println("Flag" + flag);
			if (flag == true) {
				//find the user who post this and save this post under this user 
				try {
					Long mateId=Long.parseLong(session.get("id"));
					Mate mateObj=Mate.findById(mateId);
					//(Mate postedBy, Date date, Time timeStart, Time timeEnd,String location, String title, String post, Integer matesRequired,Integer mateApplied)
					MatePostTable giveHelpPostObj = new MatePostTable(mateObj, date, timeS,
							timeE, location, title, post, seekersRequired, seeker_applied);
					giveHelpPostObj.create();
//				System.out.println(giveHelpPostObj.location + giveHelpPostObj.seeker+giveHelpPostObj.id +seekerObj.userSeeker.firstName);
					mateObj.addPost(giveHelpPostObj);
					SeekHelpController.seekHelpRedir(null, null, null, null);// .giveHelpSearch(null, null, null, null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			} else {
				GiveHelpController.giveHelpSearch(null, null, null, null);
			}

		}

	}

	/*
	 * This function will get the input and will update the post posted by mate
	 */
	
	public static void updatepost(Long postId, String mate, String postDate,String timeStart,String timeEnd,
			String location, int seekersRequired,  int seekersApplied, String title, String post) throws ParseException{
		
		String changesMade = "";
//		SeekerPostTable seekerPost=SeekerPostTable.findById(postId);
		MatePostTable matePost = MatePostTable.findById(postId);
		
		System.out.println("postId "+ postId);
		System.out.println(mate + postDate+ timeStart +timeEnd+ location+ seekersRequired + seekersApplied+title + post);
		
		boolean all_check = false;
		if (session.get("id")!=null && postDate != null && !postDate.equals("") && !timeStart.equals("") && !timeEnd.equals("") 
				&& !location .equals("")) 
		{
			all_check = true;
			System.out.println("All check true");
		}
		//If all values are not null or empty then this update will take action
		if(all_check){
			//Location Update
//			SeekerPostTable seekerPost=SeekerPostTable.findById(postId);
			if(!matePost.location.equalsIgnoreCase(location)){
				matePost.location = location;
				matePost.save();
				changesMade += "Location Updated, ";
			}//End of location update
			
			//Date update
			System.out.println(postDate.contains("-"));
			SimpleDateFormat dateFormat = null;
			
			if(postDate.contains("-")){
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println("Entered is -------------");
			}
			if(postDate.contains("/"))
			{
				dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				System.out.println("Entered is ////////////////");
			}
			
			Date updatedDate;
			updatedDate = dateFormat.parse(postDate);
			if (!postDate.equalsIgnoreCase("") && all_check && matePost.postdate.compareTo(updatedDate) != 0) 
			{
				
				System.out.println("update Date: "+updatedDate);
				matePost.postdate = updatedDate;
				matePost.save();
				changesMade += "Event Updated, ";
			}//End of Event Date update
			
			//TimeStart Update
			Time timeS = null;
			timeS = java.sql.Time.valueOf(timeStart);
			if (matePost.timeStart.compareTo(timeS) != 0) 
			{
				matePost.timeStart = timeS;
				matePost.save();
				changesMade += "Event Start Time Updated, ";
			}//End of timeStart update
			
			//TimeEnd Update
			Time timeE = null;
			timeE = java.sql.Time.valueOf(timeEnd);
			if (matePost.timeEnd.compareTo(timeE) != 0) 
			{
				matePost.timeEnd = timeE;
				matePost.save();
				changesMade += "Event End Time Updated, ";
			}//End of timeEnd update
			
			//mates Required Update
			if(matePost.seekersRequired != seekersRequired)
			{
				if(seekersRequired > matePost.seekersRequired){
					matePost.seekersRequired = seekersRequired;
					matePost.status = "open";
					matePost.save();
					changesMade += "More mates Required, ";
				}
				
				if((seekersRequired - seekersApplied == 0)){
					matePost.seekersRequired = seekersRequired;
					matePost.status = "close";
					matePost.save();
					changesMade += "Less mates Required, ";
				}
				
			}//End of mates Required update
			System.out.println(matePost.seekersWantHelp.get(0).id);
		}
		if(!changesMade.equals("")){
			System.out.println("Notification: "+changesMade.substring(0, changesMade.length()-2));
			changesMade = changesMade.substring(0, changesMade.length()-2);
			List <Seeker> seekerlists = matePost.seekersWantHelp;
			for(Seeker s: seekerlists){
				
				Notification notify = new Notification();
				notify.notificationMessage = changesMade;
				notify.notificationDate = new Date();
				notify.notifyThisSeeker=s;
				notify.matePostTable=matePost;
				notify.viewed = "false";
				notify.create();
				notify.save();	
			}
			
		}

		matePostShowDetail(postId);
		
	}
	

	/*
	 * This function will remove the post of the Mate
	 */
	public static void removePost(Long postId) throws java.text.ParseException{
		MatePostTable matePost = MatePostTable.findById(postId);
		System.out.println("REMOVE REMOVE BY SEEKER: "+matePost.title);
		matePost.status = "removed";
		matePost.save();
		
		List <Seeker> seekerlists = matePost.seekersWantHelp;
		for(Seeker s: seekerlists){
			
			Notification notify = new Notification();
			notify.notificationMessage = matePost.title + " has been Removed";
			notify.notificationDate = new Date();
			notify.notifyThisSeeker=s;
			notify.matePostTable=matePost;
			notify.viewed = "false";
			notify.create();
			notify.save();	
		}
		SeekHelpController.seekHelpRedir(null, null, null, null);
		
		
	}	
	
	
	//this will show the detail of a seeker's post
	public static void matePostShowDetail(Long id){
		MatePostTable post=MatePostTable.findById(id);
		System.out.println(post.id+post.post);
		List<Seeker>seekersWantHelp = post.seekersWantHelp;
		boolean mateFound = false;
		for(int i =0; i<seekersWantHelp.size();i++){
			if(seekersWantHelp.get(i).id == Long.parseLong(session.get("id"))){
				System.out.println(i + "Found"+ seekersWantHelp.get(i).id);
				mateFound = true;
			}
		}
		
		render(post, mateFound);
	}

	public static void postComment(Long postId, @Required String content){
		MatePostTable post = MatePostTable.findById(postId);
	    if (validation.hasErrors()) {
	        render("GiveHelpController/matePostShowDetail.html", post);
	    }
	  //addComment is a function of SeekerPostTable model
	    post.addComment(session.get("userType"),Long.parseLong(session.get("id")), content);//id of SeekerPostTable is Long, but 
	    //session stores data in string so convert the id to long using Long.parseLong
	    
        System.out.println("Entered in the comment: "+post.post);
        if(post.postedBy.id == Long.parseLong(session.get("id"))){
        	List <Seeker> seekerlists = post.seekersWantHelp;
        	for(Seeker s: seekerlists){
        		String nameofmate = post.postedBy.userMate.firstName+" "+post.postedBy.userMate.lastName; 
        		Notification notify = new Notification();
        		notify.notificationMessage = "Commented by, "+nameofmate+": " + content ;
        		notify.notifyThisSeeker=s;
        		notify.notificationDate = new Date();
        		notify.matePostTable=post;
        		notify.viewed = "false";
				notify.create();
				notify.save();        		
				System.out.println("Post Notification Done");
        	}
        }
	    
	    
	    matePostShowDetail(postId);
	}

	
}
