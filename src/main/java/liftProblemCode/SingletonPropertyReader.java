package liftProblemCode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class SingletonPropertyReader is simply a singleton class (i.e. there will only event be one - note the private constructor)
 * 
 * It has a simple role - to extend a public method to allow the user to get a specific property.
 * 
 * This could probably do with some refactoring in future to make it a static rather than singleton, and also the file where the properties live is stated here -  
 * 
 * The user will have to updated for their system.
 * 
 * Properties file has the following data - list may be extend in future.
 * 
 * highestFloor - the highest floor in the building
 * lowestFloor - the lowerfloor in the building
 * minWaitPeriodBetweenButtonPress=1000 - the minimum wait period - used with a random function - drives how often a floor press event may occur
 * maxWaitPeriodBetweenButtonPress=3000 - the maximum wait period - used with a random function - drives how often a floor press event may occur
 * longestLiftWaitAtFloor=3000 - used with a random function - drives how long a lift waits at a floor 
 * shortestLiftWaitAtFloor=1000 - used with a random function - drives how often a floor press event may occur
 * liftTimePerFloorTravelPeriod=500 - the time a lift takes to travel between floors.
 * 
 * @author Rob
 *
 *  TODO to refactor the property reader to be static class -  why create an instance.
 * 
 *  TODO - to add in some localisation
 *	
 *  TODO - to add in the log4j elements
 *  
 *  TODO - to add a better randomisation - probably use a Poisson distribution for time events
 */

public class SingletonPropertyReader {

	    /**
	     * Private variable to hold the single instance of the SingletonPropertyReader - initially set to null. 
	     */
	    private static SingletonPropertyReader single_instance = null;
	    
	    /**
	     * Set of properties that we obtain - we will later initialise these based on a file.
	     */
	    private static Properties prop;
	    
	    /**
	     * Constructor for the SingletonPropertyReader to create an instance of the SingletonPropertyReader. It will initialise the Properties list
	     * It will then load from the file of values. File approach may be updated in future to not use a system specific variable
	     */
	    private SingletonPropertyReader() 
	    { 
			prop = new Properties();

			//try (InputStream inputStream = PropertiesExample.class.getClassLoader().getResourceAsStream(filePath)) {
			try{
			InputStream inputStream = new FileInputStream(new File("C:\\Users\\Rob\\eclipse-workspace\\liftalgorithmproblem\\src\\main\\resources\\config.properties"));
				// Loading the properties.
				prop.load(inputStream);
			} 
		catch (Exception e) {
			System.out.println("Could Not Load the properties file");
			}
		}
			
	    /**
	     * A single getInstance method for the Singleton class - standard singleton pattern only creates it if instance is null.
	     * @return SingletonPropertyReader - the single instance of the SingletonPropertyReader
	     */
	    public static SingletonPropertyReader getInstance() 
	    { 
	        if (single_instance == null) 
	            single_instance = new SingletonPropertyReader(); 
	  
	        return single_instance; 
	    }
	    
	    /**
	     * A single getInstance method for the Singleton class - standard singleton pattern only creates it if instance is null.
	     * @param inPropertyName - the specific property that we are searching for. 
	     * @return String for the value of the specific property being requested.
	     */
	    public static String  getPropertyValue(String inPropertyName) {
	    	
	    	return prop.getProperty(inPropertyName);
	    }
}
