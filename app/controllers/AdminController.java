package controllers;

import java.util.LinkedList;
import java.util.List;

import models.User;
import play.mvc.Controller;

public class AdminController extends Controller{

	public static void adminPanel(){
		System.out.println(session.get("userName"));
		
		List<User> appliedUsers = new LinkedList<User>();
		appliedUsers = User.find("userStatus=?", "applied").fetch();
		System.out.println(appliedUsers.size());
		render(appliedUsers);	
	}
	
	public static void userStatusChange(String status, String ssid){
		User statusChangeofUser = User.find("ssid=?", ssid).first();
		statusChangeofUser.userStatus = status;
		statusChangeofUser.save();
		AdminController.adminPanel();
		
		
		
	}
	
}
