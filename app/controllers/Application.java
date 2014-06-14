package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
	public static void seekHelpRedir(){
		render();
	}
	
//	public static void seekHelp(String seeker,  @Required String post_date, @Required String timeStart, @Required String timeEnd,
//			String location, int matesRequired,String title, String post) throws ParseException, java.text.ParseException{
//		
//		int mate_applied = 0;
//		
//		boolean all_check=false;
//		if(seeker.length() > 0 && post_date.length() > 0 && timeStart.length() > 0 && timeEnd.length() > 0 && 
//				location.length() > 0 && matesRequired >0 ){
//			all_check = true;
//		}
//		else{
//			flash.error("Please check your input data Again!");
//			seekHelpRedir();
//		}
//		
//		//page redirected and landed
//		if(all_check){
//			
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
//			boolean flag =false;
//			Date date;
//			if(!post_date.equalsIgnoreCase("") && all_check)
//			{
//				date = dateFormat.parse(post_date); 
//				flag = true;
//			}
//			else
//			{
//				date = new Date();
//			}
//
//			//SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//			Time timeS = null;
//			if(timeStart != null && flag == true){
//				timeS = java.sql.Time.valueOf(timeStart);
//				flag =true;
//			} 
//			else{
//				timeS =java.sql.Time.valueOf("00:00:00");
//			}
//
//			Time timeE = null;
//			if(timeStart != null && flag == true){
//				timeE = java.sql.Time.valueOf(timeEnd);
//				flag =true;
//			} 
//			else{
//				timeE =java.sql.Time.valueOf("00:00:00");
//			}
//			
//			
//			SeekerPostTable giveHelpPost = new SeekerPostTable(seeker, date, timeS, timeE, location, title, post, matesRequired, mate_applied);
//			System.out.println("Flag"+ flag);
//			if(flag == true)
//			{
//				giveHelpPost.create(); 
//				giveHelpSearch(null, null, null, null);
//				
//			}
//			else{
//				seekHelpRedir();
//			}
//	
//		}
//
// }
	
	public static void prettyprint(String seeker){
		System.out.println("Ready to Help button is being clicked to help: "+seeker);
		SeekerPostTable giveHelpPost = SeekerPostTable.find("seeker like ?", seeker).first();
		giveHelpPost.mateApplied = giveHelpPost.mateApplied +1;
		if(giveHelpPost.mateApplied == giveHelpPost.matesRequired){
			giveHelpPost.status = "close";
			System.out.println(seeker+" closed ");
		}
		giveHelpPost.save();
		System.out.println();
//		GiveHelpController.giveHelp();
	}

}