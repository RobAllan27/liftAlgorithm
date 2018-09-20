package visualControls;

import java.util.Iterator;
import java.util.TreeSet;

//import java.awt.BorderLayout;
//import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/** This provides a visual frame for the lift to allow the user to see the state of play for that list.
 *  It will show the moving direction, the queued floors, the current floor, and positioning floor (being sent their empty)
 *  The title indicates the lift number.
 * 
 * @author Rob
 *
 */

@SuppressWarnings("serial")
public class LiftMeterVisualFrame extends JFrame {

	private JPanel contentPane;
	private JTextField currentFloorTF;
	private JTextField movingDirectionTF;
	private JTextField positioningFloorTF;
	private JTextArea queuedFloorsTF;


	/**Constructor method that creates the lift frame, sets a title for the frame and the size, as well as visibility.
	 * Create the frame.
	 * 
	 * @param inLiftID The lift id for lift - e,g if there are 8 lifts numbers 1 to 8. Set as a string to allow for character ids - like Lift 'A'
	 */
	public LiftMeterVisualFrame(String inLiftID) {
		setTitle("Lift Status - Lift Number - " + inLiftID);
		
		//LiftMeterVisualFrame frame = new LiftMeterVisualFrame();
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCurrentFloor = new JLabel("Current Floor");
		lblCurrentFloor.setBounds(5, 5, 94, 16);
		contentPane.add(lblCurrentFloor);
		
		JLabel lblJlabel = new JLabel("JLabel");
		lblJlabel.setBounds(12, 48, 56, 16);
		contentPane.add(lblJlabel);
		
		JLabel lblQueuedFloors = new JLabel("Queued Floors");
		lblQueuedFloors.setBounds(5, 96, 107, 16);
		contentPane.add(lblQueuedFloors);
		
		JLabel lblPositionFloor = new JLabel("Position Floor");
		lblPositionFloor.setBounds(5, 177, 100, 16);
		contentPane.add(lblPositionFloor);
		
		currentFloorTF = new JTextField();
		currentFloorTF.setBounds(111, 2, 116, 22);
		contentPane.add(currentFloorTF);
		currentFloorTF.setColumns(10);
		currentFloorTF.setEditable(false);
		
		
		movingDirectionTF = new JTextField();
		movingDirectionTF.setBounds(111, 45, 116, 22);
		contentPane.add(movingDirectionTF);
		movingDirectionTF.setColumns(10);
		movingDirectionTF.setEditable(false); ;
		
		queuedFloorsTF = new JTextArea();
		queuedFloorsTF.setBounds(111, 93, 116, 67);
		contentPane.add(queuedFloorsTF);
		queuedFloorsTF.setEditable(false);
		
		positioningFloorTF = new JTextField();
		positioningFloorTF.setBounds(111, 174, 116, 22);
		contentPane.add(positioningFloorTF);
		positioningFloorTF.setColumns(10);
		positioningFloorTF.setEditable(false);
	}

	/** Simple method to set the queued floors. Client should pass in a integer array, so formatting can be done here.
	 * 
	 * @param queuedFloors - a TreeSet of integers (inherently sorted)
	 */
	
	public void setQueuedFloors(TreeSet<Integer> queuedFloors) {
		//TODO some things in here
		StringBuilder sbTextField =  new StringBuilder();
		Iterator<Integer> itr=queuedFloors.iterator();
		while(itr.hasNext()){
			sbTextField.append(itr.next());
			sbTextField.append(" , ");
		}
				
		if (sbTextField.length() > 0) {
			sbTextField.setLength(sbTextField.length() - 3);
			}
		queuedFloorsTF.setText(sbTextField.toString());
	}
	
	
	/** Simple method to set the current Floor in the frame and correct text field.
	 * 
	 * @param inCurrentFloor - the current floor for this lift.
	 */
	public void setCurrentFloorTF(int inCurrentFloor) {
		currentFloorTF.setText(String.valueOf(inCurrentFloor));
	}
	
	/** Simple method to set the moving Direction in the frame and correct text field.
	 * 
	 * @param inMovingDirection -  the moving direction for this lift
	 */
	public void setMovingDirectionTF(int inMovingDirection) {
		movingDirectionTF.setText(String.valueOf(inMovingDirection));
	}
	
	/** Simple method to set the Positioning the frame and correct text field.
	 * 
	 * @param inPositioningFloor THe positioning floor that a lift is going to.
	 */
	public void setPositioningFloorTF(int inPositioningFloor) {
		positioningFloorTF.setText(String.valueOf(inPositioningFloor));
	}
}
