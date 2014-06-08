package controllers;

import play.*;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.mvc.*;
import groovyjarjarcommonscli.ParseException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.Query;

import models.*;

public class Application extends Controller {
    
    public static void giveHelp(){
		render();
    }
    
	public static void giveHelpSearch(String location, Date search_date, String time_start, String time_end) throws java.text.ParseException{
    	System.out.println(time_start + "----"+ time_end +"Location"+location+"Date"+search_date);
    	
    	if(location == null && search_date == null && time_start == null && time_end == null){
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
			System.out.println("ALL NULL");
			render(giveHelpPost);
    	}
    	else if((!location.equalsIgnoreCase("")) &&(search_date != null) 
				&& (!time_start.equals("")) && (!time_end.equals(""))){
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.find("postdate >= ? and location like ? and timeStart >= ? and timeEnd >= ?", search_date, location,
					java.sql.Time.valueOf(time_start), java.sql.Time.valueOf(time_end)).fetch();
			System.out.println("ALL NOT NULL");
			render(giveHelpPost);
		}
		
		else if((location == null || location.equalsIgnoreCase("")) && (search_date == null || search_date.equals(""))
				&& (time_start == null || time_start.equals("")) && (time_end == null || time_end.equals(""))){
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
			System.out.println("Condition 3");
			render(giveHelpPost);
		}
		
		else if(!location.equalsIgnoreCase("") || search_date != null
				|| !time_start.equals("") || !time_end.equals("")){
			System.out.println("Value Found: "+time_start + "----"+ time_end +"Location"+location+"Date"+search_date);
			
			if(search_date == null){
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
				search_date = dateFormat.parse("01/01/1990");
			}
			
			if(time_start.equalsIgnoreCase("")){
				time_start = "00:00:00";
			}
			
			if(time_end.equalsIgnoreCase("")){
				time_end = "00:00:00";
			}
			
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.find("postdate >= ? OR location like ? OR timeStart >= ? OR timeEnd<= ?", search_date, location,
					java.sql.Time.valueOf(time_start),java.sql.Time.valueOf(time_end)).fetch();
			render(giveHelpPost);
		}else{
			List<GiveHelpBody> giveHelpPost = GiveHelpBody.findAll();
			System.out.println("Condition Final");
			render(giveHelpPost);
		}
		
    }
    
	public static void seekHelpRedir(){
		render();
	}
	
	public static void seekHelp(String seeker,  @Required String post_date, @Required String timeStart, @Required String timeEnd,
			String location, int mates_Required,String title, String post) throws ParseException, java.text.ParseException{
		
		int mate_applied = 0;
		
		boolean all_check=false;
		if(seeker.length() > 0 && post_date.length() > 0 && timeStart.length() > 0 && timeEnd.length() > 0 && 
				location.length() > 0 && mates_Required >0 ){
			all_check = true;
		}
		else{
			flash.error("Please check your input data Again!");
			seekHelpRedir();
		}
		
		//page redirected and landed
		if(all_check){
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
			boolean flag =false;
			Date date;
			if(!post_date.equalsIgnoreCase("") && all_check)
			{
				date = dateFormat.parse(post_date); 
				flag = true;
			}
			else
			{
				date = new Date();
			}

			//SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Time timeS = null;
			if(timeStart != null && flag == true){
				timeS = java.sql.Time.valueOf(timeStart);
				flag =true;
			} 
			else{
				timeS =java.sql.Time.valueOf("00:00:00");
			}

			Time timeE = null;
			if(timeStart != null && flag == true){
				timeE = java.sql.Time.valueOf(timeEnd);
				flag =true;
			} 
			else{
				timeE =java.sql.Time.valueOf("00:00:00");
			}
			
			
			GiveHelpBody giveHelpPost = new GiveHelpBody(seeker, date, timeS, timeE, location, title, post, mates_Required, mate_applied);
			System.out.println("Flag"+ flag);
			if(flag == true)
			{
				giveHelpPost.create(); 
				giveHelpSearch(null, null, null, null);
				
			}
			else{
				seekHelpRedir();
			}
	
		}

 }
	
	public static void prettyprint(String seeker){
		System.out.println("Ready to Help button is being clicked to help: "+seeker);
		GiveHelpBody giveHelpPost = GiveHelpBody.find("seeker like ?", seeker).first();
		giveHelpPost.mateApplied = giveHelpPost.mateApplied +1;
		if(giveHelpPost.mateApplied == giveHelpPost.matesRequired){
			giveHelpPost.status = "close";
			System.out.println(seeker+" closed ");
		}
		giveHelpPost.save();
		System.out.println();
		GiveHelpController.giveHelp();
	}
}