package liftProblemCode;

import java.util.HashSet;
import java.util.Set;

/** This is the main class -  its role is to kick off 3 sets of threads -
 *     one for the floors waiting
 *     another set for the various lifts, along with their identifiers
 * 	   a lift chooser that looks at the state of play and allocates lifts.
 * 
 * @author Rob
 *
 */

public class LiftProgramRunner {
	
	/** This is the main method to run the application. It creates
	 *     a Floor waiting set that is thread to allow random sets if floors to be created.
	 *     a lift thread is started for each lift
	 *     a lift chooser thread to emulate the lifts being chosen by users.
	 * 
	 * @param args - None
	 */
	
	public static void main(String[] args) {

		// start the process of calling lifts
		//System.out.println(PropertyReader.getProperty("time"));
		SingletonPropertyReader signPropReader = SingletonPropertyReader.getInstance();
		System.out.println(signPropReader.getPropertyValue("browser"));
		System.out.println(signPropReader.getPropertyValue("time"));
		//System.out.println(ResourceGetter.getProperty("browser"));
		
		/*
		FloorsWaitingSets fwsSets = new FloorsWaitingSets();
		Thread thread = new Thread(new LiftCaller(fwsSets));
		thread.start();

		//int number_of_lifts = 5;
		HashSet<Lift> liftset = new HashSet<Lift>();

		// start the lifts
		
		//lets give the lifts ids
		
		String[] LiftIds = { "A", "B", "C",
			    "D", "E" };
		
		for (String liftId : LiftIds) {
			LiftThread liftthread = new LiftThread(liftId);
			Lift liftCreated = liftthread.getLift();
			liftset.add(liftCreated);
			Thread threadlift = new Thread(liftthread);
			thread.start();
		}

		*/
		
		/*
		
		LiftChooser lftchsr = new LiftChooser(fwsSets, liftset);
		Thread threadliftchooser = new Thread(lftchsr);
		thread.start();
		
		*/
	}
}
