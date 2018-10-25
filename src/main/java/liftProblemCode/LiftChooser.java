package liftProblemCode;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/** This class selects a suitable Lift and schedules it to stop. 
 * It consider the floor waiting sets and the set of lifts (so it can get their state.
*/
public class LiftChooser implements Runnable {
	
	private static final Logger logger = LogManager.getLogger();
	
	/** The set of  Floors that are waiting for tiher up or down
	 * */ 
	 
	private FloorsWaitingSets fwsets;
	
	 /** A hashset of all the lifts
	  */
	  
	 private HashSet<Lift> liftset;

	public LiftChooser(FloorsWaitingSets inFWSets,HashSet<Lift> inLiftSet){
		this.fwsets = inFWSets;
		this.liftset = inLiftSet;

		logger.debug("Creating the lift chooser thread");
	}

	/** the lift chooser iterates tough all the floors waiting to go up and the applies a set of rules in order to try to find a candidate lift.

			//for all the floors waiting to go up

			// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.
			// opt 2 lets check if there is a lift with that floor in its stops and going up or down.
			// opt 3 then we allocate a lift moving up below
			// opt 4 find a stopped lift not on that floor and choose closest
 
     If successful in finding a candidate going up lift, it will add that floor to the waiting floors for that specific lift.`		
 
 	Then it iterates through all the floors waiting to go up.
 	
 					// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.
				// opt 2 lets check if there is a lift with that floor in its stops and going up or down.
				// opt 3 then we allocate a lift moving down above
				// opt 4 find a stopped lift not on that floor
 
 	   If successful in finding a candidate going up lift, it will add that floor to the waiting floors for that specific lift.`		
 
	 */
	
	
	public void run() {

		// we select candidate lifts to serve a floor

		// lets do up.
		// is there a lift on the way up that specific floor with less than n (say 3) stops - choose that one, no need to add to the queue stops 
		// 
		// if no candidate
		// get all the lifts whose next stop is below the floor and coming up - choose the one with fewest stops to make.
		// 
		// if no candidates
		// get all the list above who are resting (movement = 0) choose the closet, and sent up (positioning 2) or send down (positioning - 2)
		// if no candidates - leave until next time.



		// lets do down.
		// is there a lift on the way down to that specific floor with less than n (say 3) stops - choose that one, no need to add to the queue stops 
		// 
		// if no candidate
		// get all the lifts whose next stop is above the floor and coming down - choose the one with fewest stops to make.
		// 
		// if no candidates
		// floor_in_question

		/**lambda expression do not allow you to have local variables- do a horrible workaround
		
		"local variable defined in an enclosing scope must be final or effectively final" 
		 trick is to wrap it up in an array - int[] numArr = {7};
		 
		 Lambda are neat here as we need to sort collections lots here.
		
		*/
		
		
		while(true) {

			// doing up

			//for all the floors waiting to go up

			// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.
			// opt 2 lets check if there is a lift with that floor in its stops and going up or down.
			// opt 3 then we allocate a lift moving up below
			// opt 4 find a stopped lift not on that floor and choose closest

			
			Integer IntegerUp;
			int floor_up_in_question = 0;
			Iterator<Integer> itrUp = fwsets.floorsWaitingForDownList.iterator(); 
			while (itrUp.hasNext())
			{
				IntegerUp = itrUp.next(); 
				floor_up_in_question = IntegerUp.intValue();
				int[] numArrFloor_up_in_question = {floor_up_in_question};
						
			{

			// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.

			List<Lift> upCandidatesRule1 = liftset.stream()
					.filter( lift -> lift.getMovingdirection() == 0)
					.filter( lift -> lift.getCurrentFloor() ==  numArrFloor_up_in_question[0]).
					collect(Collectors.toList());

			// may have to use (numArr[0]+1));
			
			if (upCandidatesRule1.size() > 0) {

				Lift chosenLift  = upCandidatesRule1.get(0);
				fwsets.removeFloorWaitingtoGoUp(floor_up_in_question);
				chosenLift.setMovingdirection(1);
				logger.debug("Lift " + chosenLift.liftID + " already at floor and is travelling up");
				break;
			}

			// opt 2 lets check if there is a lift with that floor in its stops and going up or down.
			// check if we have scheduled lift on the way (e.g a button press by users)

			List<Lift> upCandidatesRule1B = liftset.stream()
					.filter( lift -> lift.getMovingdirection() == 1)
					.filter( lift -> lift.getQueuedFloors().contains(new Integer(numArrFloor_up_in_question[0]))).
					collect(Collectors.toList());

			if (upCandidatesRule1B.size() > 0) {
				Lift chosenLift  = upCandidatesRule1B.get(0);
				fwsets.removeFloorWaitingtoGoUp(floor_up_in_question);
				logger.debug("Lift " + chosenLift.liftID + " has floor added " + floor_up_in_question + " and is travelling up");
				break;
			}			 


			// opt 3 then we allocate a lift moving up below

			List<Lift> upCandidatesRule2 = liftset.stream()
					.filter( lift -> lift.getMovingdirection() == 1)
					.filter( lift -> lift.getNextStop() <  numArrFloor_up_in_question[0]).
					collect(Collectors.toList());

			if (upCandidatesRule2.size() > 0) {

				List<Lift> sortedLifts = upCandidatesRule2.stream().sorted(Comparator.comparing(Lift::sizeOfFloorQ)).collect(Collectors.toList());
				// to choose with fewest stops
				Lift chosenLift  = sortedLifts.get(0);
				chosenLift.addFloorToQueue(floor_up_in_question);
				fwsets.removeFloorWaitingtoGoUp(floor_up_in_question);
				logger.debug("Lift " + chosenLift.liftID + " has floor added " + floor_up_in_question + " and is travelling down");
				break;
			}

			//ok we did not find someone going up - let's look for a stationary below or above

			// opt 4 find a stopped lift not on that floor
			adjudicateAmongstStoppedLifts(floor_up_in_question);
			}
		}
			//for all the floors waiting to go down
			Integer IntegerDown;
			int floor_down_in_question = 0;
			Iterator<Integer> itrDown = fwsets.floorsWaitingForDownList.iterator(); 
			while (itrDown.hasNext())
			{
				IntegerDown = itrDown.next(); 
				floor_down_in_question = IntegerDown.intValue();
				 int[] numArrFloor_in_question = {floor_down_in_question};

				// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.
				// opt 2 lets check if there is a lift with that floor in its stops and going up or down.
				// opt 3 then we allocate a lift moving down above
				// opt 4 find a stopped lift not on that floor



				// opt 1 let see if there is a non moving lift there on that floor - if so we use one of those.
				List<Lift> downCandidatesRule1 = liftset.stream()
						.filter( lift -> lift.getMovingdirection() == 0)
						.filter( lift -> lift.getCurrentFloor() == numArrFloor_in_question[0]).
						collect(Collectors.toList());

				if (downCandidatesRule1.size() > 0) {

					Lift chosenLift  = downCandidatesRule1.get(0);
					fwsets.removeWaitingtoGoDown(floor_down_in_question);
					chosenLift.setMovingdirection(-1);
					logger.debug("Lift " + chosenLift.liftID + " already at floor and is travelling down");
					break;
				}

				// opt 2 lets check if there is a lift with that floor in its stops and going up or down.

				List<Lift> downCandidatesRule1B = liftset.stream()
						.filter( lift -> lift.getMovingdirection() == -1)
						.filter( lift -> lift.getQueuedFloors().contains(new Integer(numArrFloor_in_question[0]))).
						collect(Collectors.toList());

				if (downCandidatesRule1B.size() > 0) {

					Lift chosenLift  = downCandidatesRule1B.get(0);
					fwsets.removeWaitingtoGoDown(floor_down_in_question);
					logger.debug("Lift " + chosenLift.liftID + " has floor added " + floor_down_in_question + " and is travelling down");
					break;
				}


				// opt 3 then we allocate a lift moving down above
				List<Lift> downCandidatesRule2 = liftset.stream()
						.filter( lift -> lift.getMovingdirection() == -1)
						.filter( lift -> lift.getNextStop() >  numArrFloor_in_question[0]).
						collect(Collectors.toList());

				if (downCandidatesRule2.size() > 0) {

					List<Lift> sortedLifts = downCandidatesRule2.stream().sorted(Comparator.comparing(Lift::sizeOfFloorQ)).collect(Collectors.toList());
					// to choose with fewest stops
					Lift chosenLift  = sortedLifts.get(0);
					chosenLift.addFloorToQueue(floor_down_in_question);
					fwsets.removeWaitingtoGoDown(floor_down_in_question);
					logger.debug("Lift " + chosenLift.liftID + " has floor added " + floor_down_in_question + " and is travelling down");
					break;
				}

				// opt 4 find a stopped lift not on that floor
				adjudicateAmongstStoppedLifts(floor_down_in_question);
			}
		}
		
	}		

	/** The adjudicate between stopped lifts gets the closest lift if we are using a stopped lift.
	 * 
	 * @param floor_in_question This is the floor we are trying to service.
	 */

	private void adjudicateAmongstStoppedLifts(int floor_in_question){	

		int distanceToSendUpwards = 100;
		int distanceToSendDownwards = 100;
		Lift RedirectClosestPutDownChosenLift = new Lift("AAA");
		Lift RedirectClosestPutUpChosenLift = new Lift("BBB"); //have  intilaise them -  if I really use them they will be deleted
		boolean suitableChosenLifttoMoveDown = false;
		boolean suitableChosenLifttoMoveUp = false;

		// let get a set of possible upwards candidates
		List<Lift> CandidatesRule3 = liftset.stream()
				.filter( lift -> lift.getMovingdirection() == 0)
				.filter( lift -> lift.getCurrentFloor() <  floor_in_question).
				collect(Collectors.toList());

		if (CandidatesRule3.size() > 0) {

			List<Lift> sortedLifts = CandidatesRule3.stream().sorted(Comparator.comparing(Lift::getCurrentFloor).reversed()).collect(Collectors.toList());
			// to choose with fewest stops
			RedirectClosestPutUpChosenLift  = sortedLifts.get(0);
			suitableChosenLifttoMoveUp = true;
			distanceToSendUpwards = RedirectClosestPutUpChosenLift.getCurrentFloor() - floor_in_question;
		}

		// let get a set of possible downwards candidates
		List<Lift> CandidatesRule4 = liftset.stream()
				.filter( lift -> lift.getMovingdirection() == 0)
				.filter( lift -> lift.getCurrentFloor() >  floor_in_question).
				collect(Collectors.toList());

		if (CandidatesRule4.size() > 0) {

			List<Lift> sortedLifts =CandidatesRule4.stream().sorted(Comparator.comparing(Lift::getCurrentFloor).reversed()).collect(Collectors.toList());
			// to choose with fewest stops
			RedirectClosestPutDownChosenLift  = sortedLifts.get(0);
			suitableChosenLifttoMoveDown = true;
			distanceToSendDownwards =  floor_in_question - RedirectClosestPutDownChosenLift.getCurrentFloor();
		}

		// if they are both equal and not 100 (ie no non moving lift) then we send up

		if ((distanceToSendUpwards < distanceToSendDownwards) || ((distanceToSendUpwards == distanceToSendDownwards) && (distanceToSendUpwards != 100)))
		{	 
			if (suitableChosenLifttoMoveUp)
			{
				RedirectClosestPutUpChosenLift.addFloorToQueue(floor_in_question);
				fwsets.removeFloorWaitingtoGoUp(floor_in_question);
				RedirectClosestPutUpChosenLift.setMovingdirection(2);
				logger.debug("Lift " + RedirectClosestPutDownChosenLift.liftID + " was stopped - has floor added " + floor_in_question + "and is being sent up");
			}
	
		}

		if (distanceToSendUpwards > distanceToSendDownwards)
		{
			RedirectClosestPutDownChosenLift.addFloorToQueue(floor_in_question);
			fwsets.removeWaitingtoGoDown(floor_in_question);
			RedirectClosestPutDownChosenLift.setMovingdirection(-2);
			logger.debug("Lift " + RedirectClosestPutDownChosenLift.liftID + " was stopped - has floor added " + floor_in_question + "and is being sent down");
			
		}
	}

}

