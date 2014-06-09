package controllers;

import groovyjarjarcommonscli.ParseException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.SeekerPostTable;
import models.Seeker;
import play.data.validation.Required;
import play.mvc.Controller;

public class SeekHelpController extends Controller {

	public static void seekHelpRedir() {
		render();
	}

	public static void seekHelp(String seeker, @Required String postDate,@Required String timeStart, @Required String timeEnd,
			String location, int matesRequired, String title, String post) throws ParseException, java.text.ParseException {

		
		
		System.out.println(session.get("type"));
		int mate_applied = 0;
		boolean all_check = false;
		if (session.get("id")!=null && post_date.length() > 0 && timeStart.length() > 0 && timeEnd.length() > 0
				&& location.length() > 0 && mates_Required > 0) 
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

			SeekerPostTable giveHelpPost = new SeekerPostTable(seeker, date, timeS,
					timeE, location, title, post, matesRequired, mate_applied);
			System.out.println("Flag" + flag);
			if (flag == true) {
				//find the user who post this and save this post under this user 
				Long seekerId=Long.parseLong(session.get("id"));
				Seeker seeker=Seeker.findById(seekerId);
				SeekerPostTable giveHelpPost = new SeekerPostTable(session.get("userName"),seeker, date, timeS,
						timeE, location, title, post, mates_Required, mate_applied);
				giveHelpPost.create();
				
				seeker.addPost(giveHelpPost);
				GiveHelpController.giveHelpSearch(null, null, null, null);

			} else {
				seekHelpRedir();
			}

		}

	}
	
	//this will show the detail of a seeker's post
	public static void seekerPostShowDetail(Long id){
		SeekerPostTable post=SeekerPostTable.findById(id);
		render(post);
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
}
