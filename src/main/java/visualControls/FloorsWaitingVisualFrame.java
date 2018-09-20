package visualControls;

//import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;


/** A floors waiting frame that can be sued to display the details of the floors that are waiting for a lift in either direction
 * @author Rob
 *
 */

@SuppressWarnings("serial")
public class FloorsWaitingVisualFrame extends JFrame {

	private JPanel contentPane;
	private JTextPane textPaneFWGUp;
	private JTextPane textPaneFWGDown;
	/**
	 * Launch the application.
	 */

	/** Constructor method that creates the floors waiting frame, sets a title for the frame and the size, as well as visibility.
	 * Create the frame.
	 */
	public FloorsWaitingVisualFrame() {
		FloorsWaitingVisualFrame frame = new FloorsWaitingVisualFrame();
		frame.setVisible(true);
		setTitle("Floors Waiting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFloorswaitingToGo = new JLabel("Floors Waiting to Go Up");
		lblFloorswaitingToGo.setBounds(5, 5, 147, 16);
		contentPane.add(lblFloorswaitingToGo);
		
		JLabel lblFloorsWaitingTo = new JLabel("Floors Waiting to Go Down");
		lblFloorsWaitingTo.setBounds(226, 5, 179, 16);
		contentPane.add(lblFloorsWaitingTo);
		
		textPaneFWGUp = new JTextPane();
		textPaneFWGUp.setBounds(5, 47, 179, 74);
		contentPane.add(textPaneFWGUp);
		
		JTextPane textPaneFWGDown = new JTextPane();
		textPaneFWGDown.setBounds(243, 47, 177, 74);
		contentPane.add(textPaneFWGDown);
	}
	
	/** A public method to allow controllers or models to set the list of floors that are waiting to go up - this updates the 'textPaneFWGUp' text box in the frame..
	 * 
	 * @param floorsWaitingForUpList - the current list of floors that are waiting to go up as a treeset.
	 */
	
	
	public void setTextPaneFWGUp(TreeSet<Integer> floorsWaitingForUpList) {
		 
			StringBuilder sbTextField =  new StringBuilder();
			Iterator<Integer> itr=floorsWaitingForUpList.iterator();
			while(itr.hasNext()){
				sbTextField.append(itr.next());
				sbTextField.append(" , ");
			}
					
			if (sbTextField.length() > 0) {
				sbTextField.setLength(sbTextField.length() - 3);
				}
			textPaneFWGUp.setText(sbTextField.toString());
	}
	
	/** A public method to allow controllers or models to set the list of floors that are waiting to go down - this updates the 'textPaneFWGDown' text box in the frame..
	 * 
	 * @param floorsWaitingForDownList - the current list of floors that are waiting to go up as a treeset.
	 */	
	public void setTextPaneFWGDown(TreeSet<Integer> floorsWaitingForDownList) {
		 
			StringBuilder sbTextField =  new StringBuilder();
			Iterator<Integer> itr=floorsWaitingForDownList.iterator();
			while(itr.hasNext()){
				sbTextField.append(itr.next());
				sbTextField.append(" , ");
			}
					
			if (sbTextField.length() > 0) {
				sbTextField.setLength(sbTextField.length() - 3);
				}
			textPaneFWGDown.setText(sbTextField.toString());
	}
}
