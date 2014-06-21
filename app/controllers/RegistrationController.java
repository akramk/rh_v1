package controllers;

import groovyjarjarcommonscli.ParseException;

import java.awt.Panel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ivy.util.Message;

import models.Address;
import models.Mate;
import models.SeekerPostTable;
import models.Seeker;
import models.User;
import models.UserInfo;
import play.cache.Cache;
import play.data.validation.Email;
import play.data.validation.Validation;
import play.mvc.Controller;


public class RegistrationController extends Controller {

	
	public static void register(String ssID,String type,String email,String pwd) throws java.text.ParseException{
	             
		          if((ssID==null)||(ssID.isEmpty()))
		          {
		               render();
		          }
		          else 
		          {
			             String errorMessage=null;
			             List<UserInfo> userInfo = UserInfo.find("ssid = ?", ssID).fetch();
			             User user = new User(null, null, null, null, null, null, null);
			             
			             List<User> userObj = User.find("ssid = ?", ssID).fetch();
			             if(userObj.size()==1)
			             {
			            	 errorMessage="You are a registered user!!!";
				     		 render(errorMessage);
				     		 
			             }
			             Address addressObj = new Address(null, null, null, null, null);
			             
			             if(userInfo.size() == 1)
			     		{	
			            	
			     			 String userName=userInfo.get(0).firstName+" "+userInfo.get(0).lastName;
			     			 String birthDate=userInfo.get(0).dateofBirth.toString();
			     			 
			     			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			     			 Date userBirthDate = null; 
			     			 userBirthDate = dateFormat.parse(birthDate);
			     			 
			     			 String address=userInfo.get(0).houseNumber+" "+userInfo.get(0).street+" "+userInfo.get(0).zipCode+" "+userInfo.get(0).city+" "+userInfo.get(0).country;
			     			 
			     			 user.firstName=userInfo.get(0).firstName;
			     			 user.lastName=userInfo.get(0).lastName;
			     			 user.type=type;
			     			 user.email=email;
			     			 user.pass=pwd;
			     			 user.ssid=ssID;
			     			 user.dateofBirth=userBirthDate;
			     			 
			     			 
			     			 addressObj.houseNumber=userInfo.get(0).houseNumber;
			     			 addressObj.street=userInfo.get(0).street;
			     			 addressObj.zipCode=userInfo.get(0).zipCode;
			     			 addressObj.city=userInfo.get(0).city;
			     			 addressObj.country=userInfo.get(0).country;
			     			 
			     			 addressObj.userAddress = user;
			     			 user.address = addressObj;
			     			 
			     			 user.create();	
			     			 addressObj.create();
			     			 if(type.equalsIgnoreCase("Seeker"))
			     			 {
			     				 Seeker seeker = new Seeker();
			     				 seeker.userSeeker=user;
			     				 user.seeker=seeker;
			     				 
			     				 seeker.create();
			     			 }
			     			if(type.equalsIgnoreCase("Helpmate"))
			     			 {
			     				 Mate mate = new Mate();
			     				 
			     				 mate.userMate=user;
			     				 user.mate=mate;
			     				 
			     				 mate.create();
			     			 }
			     			 
			     			 
			     			 
			     			 
			     			 //addressObj.save();
			     					     			 
			     			 //user.save();
			     			 
			     			 errorMessage="Registration completed successfully ";			     			 
			     			 render(userName,birthDate,address,errorMessage);
			     			 
			     		}
			     		else
			     		{
			     			errorMessage="Wrong Social Security ID ";
			     			render(errorMessage);
			     		}	             
		          }
	}

}