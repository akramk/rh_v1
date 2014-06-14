package controllers;

import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.ivy.util.Message;

import models.SeekerPostTable;
import models.Seeker;
import models.User;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Validation;
import play.mvc.Controller;


public class LogInController extends Controller {

	/*
	 * Log in via email,password and user type. Also session put in the session variable with user type key and his name
	 */

	public static void logIn(String email, String pwd, String type) throws ParseException, java.text.ParseException{
		if((email==null && pwd==null  && type==null) || (email.equals("") && pwd.equals("")  && type.equals(""))){
			System.out.println("Null or blank found all");
			render();
		}
		
		String errorMessage=null;
		session.clear();
		System.out.println(email+pwd+type);
		List<User> user = User.find("email = ? and pass = ?", email,pwd).fetch();
		if(user.size() == 1)
		{
			
			session.put("userType", user.get(0).type);
			session.put("id", user.get(0).id);
			session.put("userName", user.get(0).firstName +" "+ user.get(0).lastName);
			if(user.get(0).type.equals("seeker"))
			{
				SeekHelpController.seekHelpRedir();
			}
			if(user.get(0).type.equals("mate"))
			{
				GiveHelpController.giveHelp();
			}
		}
		else
		{
			errorMessage="Wrong User ID or password ";
			render(errorMessage);
		}

	}

}