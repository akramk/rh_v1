package controllers;

import groovyjarjarcommonscli.ParseException;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.GiveHelpBody;
import models.Seeker;
import play.data.validation.Required;
import play.mvc.Controller;

public class SeekHelpController extends Controller {

	public static void seekHelpRedir() {
		render();
	}

	public static void seekHelp(String seeker, @Required String post_date,@Required String timeStart, @Required String timeEnd,
			String location, int mates_Required, String post) throws ParseException, java.text.ParseException {

		
		
		System.out.println(session.get("type"));
		int mate_applied = 0;
		boolean all_check = false;
		if (seeker.length() > 0 && post_date.length() > 0 && timeStart.length() > 0 && timeEnd.length() > 0
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
			if (!post_date.equalsIgnoreCase("") && all_check) {
				date = dateFormat.parse(post_date);
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

			GiveHelpBody giveHelpPost = new GiveHelpBody(seeker, date, timeS,
					timeE, location, post, mates_Required, mate_applied);
			System.out.println("Flag" + flag);
			if (flag == true) {
				giveHelpPost.create();
				GiveHelpController.giveHelpSearch(null, null, null, null);

			} else {
				seekHelpRedir();
			}

		}

	}

}
