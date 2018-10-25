package liftProblemCode;

import java.util.TreeSet;

import visualControls.FloorsWaitingVisualFrame;
import visualControls.LiftMeterVisualFrame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** This class holds lists of floors waiting to go up or down as 2 separate lists. 
 * 
 * @author Rob
 *
 */
public class FloorsWaitingSets {
	
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * A sorted treeset of all the floors waiting to go up  - at each of those floors someone has pressed
	 * to go up and they are waiting
	 */
	public TreeSet<Integer> floorsWaitingForUpList ; 
	
	/**
	 *  * A sorted treeset of all the floors waiting to go down  - at each of those floors someone has pressed
	 * to go down and they are waiting
	 */
	public TreeSet<Integer> floorsWaitingForDownList; 
	
	/** a visual frame representing all of floors that are waiting to go up or down.
	 * 
	 */
	private FloorsWaitingVisualFrame floorsWaitingVF;
	
	
	/**
	 * The constructor simply initialises the sets of floors waiting and he visual front end. 
	 */
	public FloorsWaitingSets() {
		floorsWaitingForDownList  = new TreeSet<Integer>();
		floorsWaitingForUpList = new TreeSet<Integer>();
		
		logger.debug("Created lists for the floors waiting - up and down");
		
		floorsWaitingVF = new FloorsWaitingVisualFrame();
		
		logger.debug("Creating the Visual Frame for the Floors waiting lists");

	}
	
	/** The add floor to the waiting set to go up will add the floor to the set of floors wating to go up.
	 * 
	 * @param FloorForUp - the actual floor that we want to add - a going up floor.
	 */
	public synchronized void addFloorWaitingtoGoUp(int FloorForUp) {
		floorsWaitingForUpList.add(new Integer(FloorForUp));
		logger.debug("added a floor to list of waiting to go up  + Floor is " + FloorForUp);
		
		floorsWaitingVF.setTextPaneFWGUp(floorsWaitingForUpList);
		
		
		/* to some test code to watch it
		if (floorsWaitingForUpList.size() > 10)
		{
			floorsWaitingForUpList.clear();
		}else {
			System.out.println("\nUp Set ");
			for(Integer IntUp: floorsWaitingForUpList) {
				System.out.println(IntUp.intValue());
			}
		}
		*/
	}
	
	/**
	 * The add floor to the waiting set to go down will add the floor to the set of floors waiting to go down.
	 * 
	 * 
	 * @param FloorForDown - - the actual floor that we want to add - a going down floor.
	 */
	public synchronized void addFloorWaitingtoGoDown(int FloorForDown) {
		floorsWaitingForDownList.add(new Integer(FloorForDown));
		floorsWaitingVF.setTextPaneFWGDown(floorsWaitingForDownList);
		
		logger.debug("added a floor to list of waiting to go down  + Floor is " + FloorForDown);
		
		/* to some test code to watch it
		if (floorsWaitingForDownList.size() > 10)
		{
			floorsWaitingForDownList.clear();
		}else {
			System.out.println("\nDown Set ");
			for(Integer IntDown: floorsWaitingForDownList) {
				System.out.println(IntDown.intValue());
			}
		}
		*/
	}
	

	/** When a floor has been waited in by a suitable lift going uo, that floor then does 
	 * not need another lift (until the next time someone presses the button.
	 *
	 * 
	 * @param FloorBeenVistedonUp - this floor has now been visited by a lift,
	 *  that is going in the right upwards direction
	 */
	public synchronized void removeFloorWaitingtoGoUp(int FloorBeenVistedonUp) {
		floorsWaitingForUpList.remove(new Integer(FloorBeenVistedonUp));
		logger.debug("Removed a floor from list of waiting to go up  + Floor is " + FloorBeenVistedonUp);
		floorsWaitingVF.setTextPaneFWGUp(floorsWaitingForUpList);
	}
	
	/** When a floor has been waited in by a suitable lift going down, that floor then does 
	 * not need another lift (until the next time someone presses the button.
	 * 
	 * @param FloorBeenVistedonDown - this floor has now been visited by a lift,
	 *  that is going in the right upwards direction
	 */
	public synchronized void removeWaitingtoGoDown(int FloorBeenVistedonDown) {
		floorsWaitingForUpList.remove(new Integer(FloorBeenVistedonDown));
		logger.debug("Removed a floor from list of waiting to go down  + Floor is " + FloorBeenVistedonDown);
		floorsWaitingVF.setTextPaneFWGDown(floorsWaitingForDownList);
	}

}
