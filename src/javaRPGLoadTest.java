import rpgElements.*;
import java.util.Scanner;
/*
 * This file is just to load created characters
 * David Cruz 
 * 7/15/2023
 */

public class javaRPGLoadTest {
	public static void main(String[] args) {
		// vars
		Scanner input = new Scanner(System.in);
		SaveManager saveManager = new SaveManager();
		
		System.out.println("Welcome to the file loader\nIf you haven't seen the demo test, go to javaRPGTest to see it");
		System.out.print("Enter the name of the file you want to see, do not inlude the path or .txt: ");
		String filename = input.next();
		saveManager.setSaveName(filename);
		
		// check if it exits
		if (!saveManager.fileExists()) {
			System.out.println(saveManager.getSaveGamePath() + " Does not exist");
		}
		else {
			System.out.println();
			for (CharacterBase character: saveManager.loadSaveGame()) {
				System.out.println(character.toExpansiveString() + "\n");
			}
			System.out.println("\nLoad complete");
		}
		input.close();
		
		
	}
}
