package liftProblemCode;

import java.util.Properties;
import java.util.Random;

public class TestRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties pros = System.getProperties();
	      pros.list(System.out);

	      System.out.println("\n\n\n******************");
	System.out.println(System.getProperty("java.home") + " JRE home directory");
	System.out.println(System.getProperty("java.library.path") + " JRE library search path for search native libraries");
	System.out.println(System.getProperty("java.class.path")	+ "  JRE classpath");
	System.out.println(System.getProperty("java.ext.dirs")	+ " JRE extension library pat");
	System.out.println(System.getProperty("java.version")	+ " JDK version, e.g., 1.7.0_09. ");
	System.out.println(System.getProperty("java.runtime.version")	+ " JRE version, e.g. 1.7.0_09-b05. ");
	System.out.println(System.getProperty("file.separator")	+ " symbol for file directory separator");
	System.out.println(System.getProperty("path.separator")	+ " symbol for separating path entries");
	System.out.println(System.getProperty("line.separator")	+ " symbol for end-of-line (or new line)");
	System.out.println(System.getProperty("user.name")	+ " the user’s name. ");
	System.out.println(System.getProperty("user.home")	+ " the user’s home directory. ");
	System.out.println(System.getProperty("user.dir")		+ " the user’s current working directory. ");
	System.out.println(System.getProperty("os.name")	+ " os name");
	System.out.println(System.getProperty("os.version")	+ " os version");
	System.out.println(System.getProperty("os.arch")	+ " os arch");

		
		int floorNumber;
		Random r = new Random();
		for (int i =0; i < 1; i++) {
				
				floorNumber = r.ints(1, (4 + 1)).findFirst().getAsInt();
				System.out.println(floorNumber);;
		}
		
        int[] numArr = {7};
        // lambda expression that implements the display method 
        // of the IFunc functional interface 
        IFunc ifunc = ()-> System.out.println("Value of i is " + (numArr[0]));
        // Calling the display method
        ifunc.display();
	}
	
	interface  IFunc{
		 void display();
		}

}
