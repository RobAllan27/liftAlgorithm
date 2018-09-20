package liftProblemCode;

import java.util.TreeSet;

import visualControls.LiftMeterVisualFrame;

/** This is an entity that stores the state of the lift - we store things like<br>
 * 		the current floor<br>
 * 		the direction it is moving in<br>
 *      the lift number - a simple character lift identifier<br>
 *      the list of queued floors for the lift<br>
 *      the next positioning floor for the lift<br>
 * @author Rob
 *
 */
public class Lift {
	/**
	 * The current floor for the lift
	 */
	private int currentFloor;
	
	/**
	 * The moving direction for the lift<br>
	 *  - 1 down enroute, <br>
	 *  0 stopped, <br>
	 *  1 up enroute,<br> 
	 *  - 2 down positioning (ie no users), <br>
	 *  2 up positioning no users<br>
	 */
	private int movingdirection = 0; //
	
	/**
	 * The set of queued floors for the lift.
	 */
	private TreeSet<Integer> queuedFloors;
	
	/**
	 * The positioning floor for the lift.
	 */
	private int positionFloor; // Floor sent to for 'positioning flight
	
	/**
	 * A characters String to identify the lift.
	 */
	
	private String liftID;

	
	private LiftMeterVisualFrame liftvisFrame ;
	
	/** Constructor to create a lift, a visual control, and we assume that 
	 *  - the lift starts at floor zero, 
	 *  - has no queued floors
	 *  - is not moving
	 *  - an identifier for the lift.
	 * 
	 * @param inliftID - the identifier for the lift
	 */
	
	public Lift(String inliftID) {
		this.liftID = inliftID;
		currentFloor = 0;
		movingdirection = 0;
		queuedFloors = new TreeSet<Integer>();
		
		LiftMeterVisualFrame liftvisFrame  = new LiftMeterVisualFrame(liftID);
	}
	
	/** A get method for the current floor for a lift
	 * @return an integer for the current floor
	 */
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	/** A set method for the lift current floor value.
	 * @param currentFloor -  the current floor of the lift
	 */
	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
		liftvisFrame.setCurrentFloorTF(currentFloor);
	}
	
	/** This returns a int to reflect the direction the lift is moving in. 2 for positioning up, 1 for up, 0 for stopped, -1 for down, -2 for positioning down.
	 * @return int - the moving direction.
	 */
	public int getMovingdirection() {
		return movingdirection;
	}
	
	/** This sets the moving direction for the lift.
	 * 
	 * @param movingdirection - an int 2 for positioning up, 1 for up, 0 for stopped, -1 for down, -2 for positioning down.
	 */
	public void setMovingdirection(int movingdirection) {
		this.movingdirection = movingdirection;
		liftvisFrame.setMovingDirectionTF(movingdirection);
	}
	
	/** A query method to return the size of the floor queue.
	 * @return an integer reflecting the size of the queued floors for a lift - this enables us to send choose a lift whose itinary is less busy for example
	 */
	public int sizeOfFloorQ() {
		return queuedFloors.size();
	}
	
	/** A get method to obtain the set of queued floors.
	 * @return TreeSet of integers of the floor list.
	 */
	public TreeSet<Integer> getQueuedFloors() {
		return queuedFloors;
	}
	//possibly synch this method
	
	/** This allows a floor to be removed from the queue of floors that a lift has - we have visited this floor.
	 * @param floorTobeRemoved - an int of floor to be removed.
	 */
	public void purgeFloorFromLiftQueue(int floorTobeRemoved) {
			queuedFloors.remove(new Integer(floorTobeRemoved));
			if (queuedFloors.size()== 0)
			{
				movingdirection = 0;
			}
			liftvisFrame.setQueuedFloors(queuedFloors);
	}

	/** A method to allow a floor to be added to the set of outstanding floors.
	 * 
	 * @param floorToBeAdded -  an integer representing the floor to be added.
	 */
	public void addFloorToQueue(int floorToBeAdded)
	{
		Integer IntegerFloortobeadded = new Integer(floorToBeAdded);
		queuedFloors.add(IntegerFloortobeadded);
		liftvisFrame.setQueuedFloors(queuedFloors);
	}
	
	/** A method to allow the next Stop for a lift to be identified
	 * @return int - next floor
	 */
	public int getNextStop() {
		if ((movingdirection == -1) || (movingdirection == -2)) {
			return queuedFloors.last().intValue();
		}
		if ((movingdirection == 1) || (movingdirection == 2)) {
			return queuedFloors.first().intValue();
		}
		if (movingdirection == 0) { // ie not en route
			return -1;
		}
		return -1;
	}
	
	/** a getter method to Set the Position Floor - i.e. where it it going to for positioning move
	 * 
	 * @return int for the position floor
	 * 
	 */
	public int getPositionFloor() {
		return positionFloor;
	}

	/** A setter method to set the floor that the lift will sue for its positioning move
	 * 
	 * @param positionFloor - an integer representing the floor where the lift is located
	 */
	public void setPositionFloor(int positionFloor) {
		this.positionFloor = positionFloor;
		liftvisFrame.setPositioningFloorTF(positionFloor);
		
	}
	
}