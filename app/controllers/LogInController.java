package controllers;

import external.DataFill;
import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.ivy.util.Message;

import models.Mate;
import models.Notification;
import models.SeekerPostTable;
import models.Seeker;
import models.User;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Validation;
import play.mvc.Controller;


public class LogInController extends Controller {

	/* public static void logInHome() {
		render();
	} */
	
	/*
	 * Log in via email,password and user type. Also session put in the session variable with user type key and his name
	 */

	public static void logIn(String email, String pwd, String type) throws ParseException, java.text.ParseException{
		if((email==null && pwd==null  && type==null) || (email.equals("") && pwd.equals("")  && type.equals(""))){
			System.out.println("Null or blank found all");
//			DataFill dt = new DataFill();
//			dt.dataFiller();
			session.clear();
			render();

		}
		
		String errorMessage=null;
		session.clear();
		System.out.println(email+pwd+type);
		List<User> user = User.find("email = ? and pass = ? and userStatus = ?", email,pwd, "accepted").fetch();
		System.out.println(user.size());
		if(user.size() == 1)
		{	
			session.put("userType", user.get(0).type);
			session.put("userName", user.get(0).firstName +" "+ user.get(0).lastName);
			if(user.get(0).type.equals("seeker"))
			{
				session.put("id", user.get(0).seeker.id);
				session.put("user_id", user.get(0).id);
				session.put("notification", notificationCounter());
				SeekHelpController.seekHelpRedir(null,null,null,null);
			}
			if(user.get(0).type.equals("mate"))
			{
				session.put("id", user.get(0).mate.id);
				session.put("user_id", user.get(0).id);
				session.put("notification", notificationCounter());
				GiveHelpController.giveHelp();
			}
			
			if(user.get(0).type.equals("admin"))
			{
				session.put("id", user.get(0).admin.id);
				session.put("user_id", user.get(0).id);
				session.put("notification", notificationCounter());
				AdminController.adminPanel();
			}
		}
		else
		{
			errorMessage="Wrong User ID or password ";
			render(errorMessage);
		}

	}
	
	public static int notificationCounter(){
		
		List<Notification> notificationofSeekerPost = new LinkedList<Notification>();
		List<Notification> notificationofMatePost = new LinkedList<Notification>();
		System.out.println(Long.parseLong(session.get("id")));
		if(session.get("userType").equals("mate"))
		{
			notificationofSeekerPost = Notification.find("notifyThisMate_id=? and viewed=?", 
					Long.parseLong(session.get("id")),"false").fetch();
			return notificationofSeekerPost.size();
		}
		
		if(session.get("userType").equals("seeker"))
		{
			notificationofMatePost = Notification.find("notifyThisSeeker_id=? and viewed=?", 
					Long.parseLong(session.get("id")),"false").fetch();
			return notificationofMatePost.size();
		}
		
		return 0;
	
		
		
	}
	

}