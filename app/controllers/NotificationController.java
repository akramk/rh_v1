package controllers;

import java.util.LinkedList;
import java.util.List;

import models.Mate;
import models.Notification;
import models.SeekerPostTable;
import play.mvc.Controller;

public class NotificationController extends Controller{
	
	public static void notificationView(){
		List<Notification> notificationofSeekerPost = new LinkedList<Notification>();
		notificationofSeekerPost = NotificationController.seekerPostNotification();
		
		Mate mate = Mate.find("id", Long.parseLong(session.get("id"))).first();
		List<SeekerPostTable> postsMateAppliedtoHelp = mate.postsWantTohelp;
		render(postsMateAppliedtoHelp);
	}
	
	
	public static List<Notification> seekerPostNotification(){
		List<Notification> notificationofSeekerPost = new LinkedList<Notification>();
		if (session.get("userType").equalsIgnoreCase("mate")){
			Mate mate = Mate.find("id", Long.parseLong(session.get("id"))).first();
			List<SeekerPostTable> postsMateAppliedtoHelp = mate.postsWantTohelp;
			System.out.println(postsMateAppliedtoHelp.size());
			
			notificationofSeekerPost = postsMateAppliedtoHelp.get(0).notifySeekerPost;
			System.out.println(notificationofSeekerPost.get(0).notificationMessage);
			
		}	
		return notificationofSeekerPost;
	}
	
	public static void notificationProfile(){
		List<Notification> notificationofPost = new LinkedList<Notification>();

		if(session.get("userType").equals("mate"))
		{
			System.out.println(Long.parseLong(session.get("id")));
			notificationofPost = Notification.find("notifyThisMate_id=? ORDER BY notificationDate DESC", Long.parseLong(session.get("id"))).fetch();
			for (Notification npost : notificationofPost)
			{
				System.out.println(npost.notificationMessage);
				npost.viewed = "true";
				npost.save();
			}
		}
		
		if(session.get("userType").equals("seeker"))
		{
			System.out.println("Seeker Entered");
			notificationofPost = Notification.find("notifyThisSeeker_id=? ORDER BY notificationDate DESC", Long.parseLong(session.get("id"))).fetch();
			for (Notification npost : notificationofPost)
			{
				System.out.println("message: "+ npost.notificationMessage);
				npost.viewed = "true";
				npost.save();
			}			
			
		}
		
		/////////////////notification reinitialize
		session.put("notification", LogInController.notificationCounter());
		//////////////////////////////////////////RE-Initialize done
		render(notificationofPost);

	}
}
