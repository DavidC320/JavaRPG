/* Input Validation
 * David Cruz
 * 6/16/2023
 * 
 * This program is to find a way to get the correct input from the user.
 * Answered by Stephen Sheridan
 * https://youtu.be/dW-aWDLV44c
 */

import java.util.Scanner;

public class InputValidation {
	public static void main(String[] args) {
		boolean correct_input = false;
		int user_input = 0;
		String user_s_input;
		
		Scanner input = new Scanner(System.in);
		
		// loop till the user gives a number
		while (!correct_input) {
			System.out.print("Give a number: ");
			user_s_input = input.nextLine();
			
			try { // checks if the input is a integer
				user_input = Integer.parseInt(user_s_input);
				correct_input = true;
				}
			catch (NumberFormatException e){
				System.out.println("Input is not valid - enter a number");
				}
			
			}
		// done
		System.out.println("The user input is " + user_input);
		input.close();
	}
}
