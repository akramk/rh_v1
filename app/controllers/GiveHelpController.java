package controllers;

import java.awt.Panel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.GiveHelpBody;
import play.mvc.Controller;

public class GiveHelpController extends Controller {

/*For the beginning as we dont have any navigation panel but we need to have something to navigate through the pages so I have made
this form this will redirect you to the giveHelp page Where you will be able to see all the posts asked for help. Then you can 
filter using the search panel. Also you can go to the SeekHelp page through pusing the button "SeekHelp". As usual this is used 
for navigation. As we dont have any navigation panel now. But those who will design the interface can followi this apporach
to navigate the links.*/
	public static void giveHelp() {
		render();
	}
	
/*
@param location
@param search_date
@param time_start
@param time_end
This function searches on these parameter basis. And there are different criterias for searche queries.	
*/
	
	/**
	 * @param location
	 * @param search_date
	 * @param time_start
	 * @param time_end
	 * @throws java.text.ParseException
	 */
	/**
	 * @param location
	 * @param search_date
	 * @param time_start
	 * @param time_end
	 * @throws java.text.ParseException
	 */
	public static void giveHelpSearch(String location, Date search_date, String time_start, String time_end) 
			throws java.text.ParseException {
		System.out.println(time_start + "----" + time_end + "Location"+ location + "Date" + search_date);

/*When the page will be reloaded from the navigation panel through clicking any link or button then this page will be reloaded.
so then this condition will be checked. As at that time all the parameters will be null. Because search panel will return
no outputs but null.
*/
		if (location == null && search_date == null && time_start == null&& time_end == null) 
		{
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
			// Find those posts which have status = open. We don't need to fetch the whole dataset of GiveHelpBody 
			String status ="open";
			giveHelpPost = GiveHelpBody.find("status like ?", status).fetch();
			System.out.println("ALL NULL and status is OPEN");
			render(giveHelpPost);
		} 
		
/*When user will give all the values in the search panel then this will be the search query condition. And all the parameter will be
merged with "AND" . Keep in mind that we have time attribute in table that is timeStart and timeEnd so we have to format the
time_start and time_end string to Time variable.*/
		else if ((!location.equalsIgnoreCase("")) && (search_date != null)&& (!time_start.equals("")) 
				&& (!time_end.equals(""))) 
		{
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.find("postdate >= ? and location like ? and timeStart >= ? and timeEnd >= ?",
							search_date, location,
							java.sql.Time.valueOf(time_start),
							java.sql.Time.valueOf(time_end)).fetch();
			System.out.println("ALL NOT NULL");
			render(giveHelpPost);
		}

/*This check occurs when user does not give any values in the search panel and press the submit button.	*/	
		else if ((location == null || location.equalsIgnoreCase("")) && (search_date == null || search_date.equals(""))
				&& (time_start == null || time_start.equals("")) && (time_end == null || time_end.equals(""))) 
		{
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
			System.out.println("Condition 3");
			render(giveHelpPost);
		}

/*This occurs when user gives any values in the search Panel. Not all the values but any values.*/
		else if (!location.equalsIgnoreCase("") || search_date != null || !time_start.equals("") || !time_end.equals("")) 
		{
			System.out.println("Value Found: " + time_start + "----" + time_end + "Location" 
		      + location + "Date" + search_date);

			if (search_date == null) {
				// if user does not give any date then it will automatically get the date of 01/01/1990. 
				//because we dont have any entry obviously before 1990
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				search_date = dateFormat.parse("01/01/1990");
			}

			if (time_start.equalsIgnoreCase("")) {//when user dont give any start time it gets the 00hh 00mm and 00ss
				time_start = "00:00:00";
			}

			if (time_end.equalsIgnoreCase("")) {//when user dont give any end time it gets the 00hh 00mm and 00ss
				time_end = "00:00:00";
			}

			List<GiveHelpBody> giveHelpPost = GiveHelpBody
					.find("postdate >= ? OR location like ? OR timeStart >= ? OR timeEnd<= ?",
							search_date, location,
							java.sql.Time.valueOf(time_start),
							java.sql.Time.valueOf(time_end)).fetch();
			render(giveHelpPost);
		}
	/*This works automatically when all cases fail then it will fetch all the messages post from the table.*/	
		else {
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
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
		GiveHelpBody giveHelpPost = GiveHelpBody.find("seeker like ?", seeker).first();
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

}
