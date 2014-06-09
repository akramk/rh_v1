package controllers;

import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ivy.util.Message;

import models.SeekerPostTable;
import models.Mate;
import models.Seeker;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Validation;
import play.mvc.Controller;
import controllers.SeekHelpController;


public class LogInController extends Controller {

	/*
	 * Log in via email,password and user type. Also session put in the session variable with user type key and his name
	 */

	public static void logIn(String email, String pwd, String type) throws ParseException, java.text.ParseException{
		String errorMessage=null;
		if(type != null)
		{			
			if( type.equalsIgnoreCase("seeker"))
			{
				List<Seeker> seeker = Seeker.find("email like ? and pass like ?", email,pwd).fetch();
				if(seeker.size()==1)
				{

					session.put("type", seeker.get(0).firstName +" "+ seeker.get(0).lastName);				 
					SeekHelpController.seekHelpRedir();	

				} 
				else
				{					
					errorMessage="User ID , password or user type missmatch";
					render(errorMessage);
				}
			}
			else if(type.equalsIgnoreCase("mate"))
			{
				List<Mate> mate = Mate.find("email like ? and pass like ?", email,pwd).fetch();
				if(mate.size()==1)							
				{
					session.put("type", mate.get(0).firstName +" "+ mate.get(0).lastName);								
					GiveHelpController.giveHelpSearch(null, null, null, null);	

				}
				else{						
					
					errorMessage="User ID , password or user type missmatch";
					render(errorMessage);
				}
			}
			else
			{
				errorMessage="You must be registered as Helpmate or Seeker";
				render(errorMessage);
			}	
		}

		else
		{
			render();
		}

	}

}