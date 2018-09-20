package liftProblemCode;

import java.util.Random;

public class TestRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
