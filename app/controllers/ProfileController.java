package controllers;

import java.util.LinkedList;
import java.util.List;

import models.Mate;
import models.Seeker;
import models.SeekerPostTable;
import models.User;
import play.mvc.Controller;

public class ProfileController extends Controller {

/*
 * This function maintains the profile of mate and seeker. It loads the mate and seeker in their 
 * mate/seeker table then math with the user table to fetch the data and show it in the mate/seeker
 * details. 
 * 
 * Applied/Requested events 
 * Open Seeker events will be kept in a List of SeekerPosts called openedEvents
 * Close Seeker events will be kept in a List of SeekerPosts called closedEvents
 * Complete Seeker events will be kept in a List of SeekerPosts called completedEvents
 */
	public static void profileView() {
		System.out.println("Session id: " + session.get("id"));
		/*
		 * if userType is seeker then this condition will be true
		 */
		if (session.get("userType").equalsIgnoreCase("seeker")) {
			Seeker seeker = Seeker.find("id", Long.parseLong(session.get("id"))).first();

			System.out.println(seeker.id + " " + seeker.userSeeker.email);
			//It will fetch the user data through the user and seeker relationship
			User userseeker = seeker.userSeeker;
			//It will fetch all the posts given by the seeker(user) 
			List<SeekerPostTable> postsRequestedforHelp = seeker.posts;
			//Declaration and initialization of open,close,completed Events List
			List<SeekerPostTable> completedEvents = new LinkedList<>();
			List<SeekerPostTable> closedEvents = new LinkedList<>();
			List<SeekerPostTable> openedEvents = new LinkedList<>();

			//assigning the events to the separate List which have been already created.
			for (SeekerPostTable seekerpostIteraotr : postsRequestedforHelp) {

				if (seekerpostIteraotr.status.equalsIgnoreCase("open")) {
					openedEvents.add(seekerpostIteraotr);
				}

				if (seekerpostIteraotr.status.equalsIgnoreCase("close")) {
					closedEvents.add(seekerpostIteraotr);
				}

				if (seekerpostIteraotr.status.equalsIgnoreCase("complete")) {
					completedEvents.add(seekerpostIteraotr);
				}

			}
			//Rendering the seeker data, its details and its events
			render(userseeker, openedEvents, closedEvents, completedEvents);
		}
		
		/*
		 * if userType is mate then this condition will be true
		 */
		if (session.get("userType").equalsIgnoreCase("mate")) {
			Mate mate = Mate.find("id", Long.parseLong(session.get("id"))).first();

			System.out.println(mate.id + " " + mate.userMate.email);
			//It will fetch the user data through the user and mate relationship
			User usermate = mate.userMate;
			//It will fetch all the posts given by the mate(user) 
			List<SeekerPostTable> postsMateAppliedtoHelp = mate.postsWantTohelp;
			//Declaration and initialization of open,close,completed Events List
			List<SeekerPostTable> completedEvents = new LinkedList<>();
			List<SeekerPostTable> closedEvents = new LinkedList<>();
			List<SeekerPostTable> openedEvents = new LinkedList<>();
			//assigning the events to the separate List which have been already created.
			for (SeekerPostTable seekerpostIteraotr : postsMateAppliedtoHelp) {

				if (seekerpostIteraotr.status.equalsIgnoreCase("open")) {
					openedEvents.add(seekerpostIteraotr);
				}

				if (seekerpostIteraotr.status.equalsIgnoreCase("close")) {
					closedEvents.add(seekerpostIteraotr);
				}

				if (seekerpostIteraotr.status.equalsIgnoreCase("complete")) {
					completedEvents.add(seekerpostIteraotr);
				}

			}

			System.out.println(postsMateAppliedtoHelp.get(0).matesWantToHelp.get(0).userMate.firstName);
			System.out.println(postsMateAppliedtoHelp.get(0).matesWantToHelp.get(1).userMate.firstName);
			render(usermate, openedEvents, closedEvents, completedEvents);
		}

	}

}
