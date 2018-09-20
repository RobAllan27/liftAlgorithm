package liftProblemCode;

import java.util.Random;

/** This threaded class reflects the use case of users calling the lift.
 * 
 * @author Rob
 *
 */
public class LiftCaller implements Runnable {

	/**
	 * Highest Floor - a integer representing the hughest floor
	 */
	private final int HighestFloor = 28;
	/**
	 * Lowest Floor -  an integer representing the lowest floor.
	 */
	private final int LowestFloor = 0;
	/**
	 * The instance of the floor waiting sets -  ie the entity holding the sets of floors waiting to go up or down.
	 */
	private FloorsWaitingSets floorsWaitingSets;
	
	/** Constructor - with an instance of the floor waiting sets - i.e. the entity holding the sets of floors waiting to go up or down
	 * @param inFloorsWaitingSets - this is the class holding list for floors waiting to go up and down.
	 */
	public LiftCaller(FloorsWaitingSets inFloorsWaitingSets) {
		this.floorsWaitingSets = inFloorsWaitingSets;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
	/** The run method - people can be continually calling lifts - has method calls to choose a random button 
	 * and then wait for semi random period of time and then repeat. 
	*/
	
	public void run() {
		while (true)
		{
			chooseRandomFloorButton();
			waitRandomPeriodForButtonPresses();
		}
	}
	
	
	/**
	 * A method to randomly choose a button on a floor to be pressed, top and bottom floors are one way only.
	 * otherwise on any floor random split 50:50.
	 */
	private void chooseRandomFloorButton() {
		
		// to get a Random Floor or up and down
		
		// Part 1  -  there will be lot more people wanting to up from the ground floor
		// we run a random and 50% of the times it will be up from ground floor
		
		Random r = new Random();
		int areWeOnGroundRandom = r.ints(1, (2 + 1)).findFirst().getAsInt();
		switch (areWeOnGroundRandom) {
			case 1:
				floorsWaitingSets.addFloorWaitingtoGoUp(0);
			return;
		}
		
	 // lets look for the other floors  - not ground floor.
		int floorNumber =  r.ints(2, (28 + 1)).findFirst().getAsInt();
		// To select the up or down randomly - 0 for down 1 for up -  
		// we can only go 1 way at the bottom and top floors
		int upOrDown;
		
		switch (floorNumber) {
		case LowestFloor:
			upOrDown = 1;
			break;
			// this specific case is defensive code frankly, we hsould not have come in here.
		case HighestFloor:
			upOrDown = 0;
			break;
		default:
			//double upOrDownAsFloat = 1 * (2 + Math.random( ) );
			//upOrDown = (int)upOrDownAsFloat;
			
			// ok we are on a not ground or non top floor. 75% of the time we will wish to go down,
			
			int shouldWeCallGoDown = r.ints(1, (4 + 1)).findFirst().getAsInt();
			
			switch(shouldWeCallGoDown) {
			 case 1:
				 upOrDown =1;
				 break;
			
			default:
				upOrDown =0;
				 break;
				 // for case 2/3/4 we call go down in the lift
			}
			
			break;
		}
		if (upOrDown == 1) {
			floorsWaitingSets.addFloorWaitingtoGoUp(floorNumber);
		} else {
			floorsWaitingSets.addFloorWaitingtoGoDown(floorNumber);	
		}
	}
	
	/**
	 * A method to wait for a random period of time between upper and lower limits before calling a lift.
	 */
	private void waitRandomPeriodForButtonPresses() {

		int min = 1000;
		int max = 3000;
	    int PeriodToWait = (int)(Math.random()*((max-min)+1))+min;
	// hmmm may not return an int
	    
			try {
			Thread.sleep(PeriodToWait);
			}
			catch(Exception e) {
				System.out.println("Problem in lift thread");
			}
		}
}
	