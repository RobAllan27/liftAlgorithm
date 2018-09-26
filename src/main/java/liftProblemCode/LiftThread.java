package liftProblemCode;

import java.util.Random;

// this thread emulates the actual routing of lift - 500 mSec between a floor 
// n * 500 mSec at a floor stop

/** This thread emulates the behaviour of the lift itself as it travels around and users press buttons on floors.
 * 
 * This thread emulates the actual routing of lift - 500 mSec between a floor 
 * n * 500 mSec at a floor stop
 * 
 * @author Rob
 *
 */
public class LiftThread implements Runnable {

	/**
	 * Lift - a private member reference to the lift that this thread is controlling the movement of. 
	 */
	private Lift lift;
	
	/**
	 * a constant effectively - the highest floor that this lift goes to. Comes from properties file
	 */
	private int highestFloor;

	/**
	 * The longest wait for a lift at a floor - actual value will use this with a random method to get a wait period. Comes from properties file
	 */
	private int longestLiftWaitAtFloor;
	
	/**
	 * The longest wait for a lift at a floor - actual value will use this with a random method to get a wait period. Comes from properties file
	 */
	private int shortestLiftWaitAtFloor;
	
	/**
	 * The time that it takes a lift to transition betwen floors Comes from properties file
	 */
	private int liftTimePerFloorTravelPeriod;

	
	/** A constructor for the lift thread which takes and identifier for the lift.
	 * @param inLiftID This is the identifier for the lift.
	 */
	
	public LiftThread(String inLiftID) {
		lift = new Lift(inLiftID);
		SingletonPropertyReader signPropReader = SingletonPropertyReader.getInstance();
		longestLiftWaitAtFloor = Integer.parseInt(signPropReader.getPropertyValue("longestLiftWaitAtFloor"));
		shortestLiftWaitAtFloor = Integer.parseInt(signPropReader.getPropertyValue("shortestLiftWaitAtFloor"));
		liftTimePerFloorTravelPeriod = Integer.parseInt(signPropReader.getPropertyValue("liftTimePerFloorTravelPeriod"));
	} 
	
	/** a simple get method to return the lift for this thread.
	 * @return a lift
	 */
	public Lift getLift() {
		return this.lift;

	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	/**
	 * The run method that periodically moves the lift between the floors
	 *    - it waits a period of time to travel between floors, then sets the current floor. 
	 *    - releases that floor for the lift queue
	 *    - allows user to select floors
	 *    - and waits for random period of time for users to carry out their actions.
	 * 
	 */
	
	public void run() {
		int countofFloorsToGoPast;
		while(true) {
			int nextFloorofInterest = -1;
			countofFloorsToGoPast = 0;
			
		// in action
			if (lift.getMovingdirection() == 1) {
				nextFloorofInterest = lift.getNextStop();
				countofFloorsToGoPast = nextFloorofInterest - lift.getCurrentFloor();
			}
		
		// in action
			if (lift.getMovingdirection() == -1) {
				nextFloorofInterest = lift.getNextStop();
				countofFloorsToGoPast = lift.getCurrentFloor() - nextFloorofInterest;
			}	
			
		//positioning movements - up
		if (lift.getMovingdirection() == 2) {
			nextFloorofInterest = lift.getNextStop();
			countofFloorsToGoPast =  nextFloorofInterest - lift.getCurrentFloor();
		}
		
		//positioning movements - down
		if (lift.getMovingdirection() == -2) {
			nextFloorofInterest = lift.getNextStop();
			countofFloorsToGoPast = lift.getCurrentFloor() -  nextFloorofInterest;
		}
		
		timePeriodtoTravel(countofFloorsToGoPast);
		lift.setCurrentFloor(nextFloorofInterest);
		lift.purgeFloorFromLiftQueue(nextFloorofInterest);
		usersSelectFloors();
		executeRandomWaitingPeriodAtFloor();
	  }
	}
	
	// Here we simulate the users selecting their floors for this lift
	/** This method lets a random number of separate floor selections be done. 
	 * If those selected floors are in the direction that lift is heading in, then it is added to the floor queue
	 * If the emulation simulates a user pressing a button in the reverse direction it is ignored. (just like real life)
	 * 
	 */
	private void usersSelectFloors() {
		Random r = new Random();
		int numberOfFloorSelections =  r.nextInt(4);
		
		for (int i = 0; i < numberOfFloorSelections;i++){
			// get new random floor above or below us depending on direction
			if (lift.getMovingdirection() == 1) {
			int floorNumber =  r.nextInt((highestFloor - lift.getCurrentFloor()) + 1) + lift.getCurrentFloor();
			lift.addFloorToQueue(floorNumber);
			}
			
			if (lift.getMovingdirection() == -1) {
				int floorNumber =  r.nextInt((lift.getCurrentFloor()) - 1);
				lift.addFloorToQueue(floorNumber);
				}
			i++;	
		}	
	}
		
	/**
	 * The random period do waiting simulates how long the lift is at a floor, 
	 * from a min to a max period  - just like a real system.
	 */
	private void executeRandomWaitingPeriodAtFloor() {
		int longestLiftWaitAtFloor = 30000;
		int shortestLiftWaitAtFloor = 5000;
		
		Random r = new Random();
		int wait =  r.nextInt((longestLiftWaitAtFloor - shortestLiftWaitAtFloor ) + 1) + shortestLiftWaitAtFloor ;
		
		try {
		Thread.sleep(wait);
		}
		catch(Exception e) {
			System.out.println("Problem in lift runner thread");
		}
	}

	/** This method works out how long it will take to go between floors
	 * @param countofFloorsToGoPast Thi is an integer of the number of floors that we are traveling across.
	 */
	private void timePeriodtoTravel(int countofFloorsToGoPast) {
		// this could be a periodToWait whilst moving between floors
		int liftTimePerFloorTravelPeriod = 500;
		try {
		Thread.sleep(countofFloorsToGoPast * liftTimePerFloorTravelPeriod);
		}
		catch(Exception e) {
			System.out.println("Problem in lift thread");
		}
	}
}