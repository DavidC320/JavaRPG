/**
 * Java RPG Debug Test / 06 Test Classes
 * David Cruz
 * 7/15/2023
 * 
 * This file is to debug and test classes.
 * for the 06 Assignment I just need to create 3 test classes which I will need to do as I wasted Wednesday and was sluggish on Thursday.
 * Here the classes I want to test.
 * 1. Character class
 * 2. Character Manager class / collection class - this will not be abstract so that I can test it now and not have to make both the Player and Enemy class.
 * 3. Save Game class - this will be the hardest to make.
 * 
 * These will be located in the rpgElements package
 */
import rpgElements.*;
import java.util.Scanner;

public class JavaRPGTest {
	
	public static void main(String[] args) {
		// need to get user input
		Scanner input = new Scanner(System.in);
		
		// get user name
		System.out.print("Enter your name: ");
		String userName = input.nextLine();
		
		// test Characters
		// test Randomizer
		rpgElements.CharacterBase randomTest = new rpgElements.CharacterBase("RandomTest");
		rpgElements.CharacterBase randomTest1 = new rpgElements.CharacterBase("RandomTest1");
		// rpgElements.Character randomTest2 = new rpgElements.Character("RandomTest2"); // RIP
		
		System.out.printf("Random Characters test\n\n" + 
				"Expansive strings - a more expansive version of toString\n%s \n%s\n\n" + 
				"standard strings - just toString\n%s \n%s\n\n" + 
				"Consise Strings - Condensed version of toString\n%s \n%s\n\n"+
				"Save Strings - the save data for a character\n%s \n%s", 
				randomTest.toExpansiveString(), randomTest1.toExpansiveString(),
				randomTest.toString(), randomTest1.toString(),
				randomTest.toConsiseString(), randomTest1.toConsiseString(),
				randomTest.toSaveString(), randomTest1.toSaveString());
		
		
		System.out.print("\n\nNext is the manager test\nPress enter to continue:");
		input.nextLine();
		
		// test manager
		rpgElements.Player collection = new rpgElements.Player();
		collection.addCharacter(randomTest);
		collection.addCharacter(randomTest1);
		// add more characters
		for (int num = 0; num < 10; num++) {
			collection.addCharacter(new CharacterBase("RandomTest" + num));
		}
		// print manager
		System.out.println("\n\nCharacterManager Test\nGenerated collection");
		for (CharacterBase character: collection.getCollection()) {
			System.out.println(character.toConsiseString());
		}
		// remove a character
		System.out.println("\nRemove last from collection");
		collection.removeCharacter(collection.getCollection().size() - 1);
		for (CharacterBase character: collection.getCollection()) {
			System.out.println(character.toConsiseString());
		}
		
		
		// get the user to enter 4 ints for party
		System.out.print("\nEnter 4 ints for party, the collection size is " + (collection.getCollection().size()) + ": ");
		for (int num = 0; num < 4; num++) {
			collection.changePartyIndex(num, input.nextInt());
		}
		System.out.print("\nHere is your party is ");
		for (int index :collection.getParty()) {
			System.out.print(index + " ");
		}
		System.out.print(" or in indexes ");
		for (int index :collection.getParty()) {
			System.out.print((index - 1) + " ");
		}
		System.out.println("\nWhich is these Characters ");
		for (CharacterBase character :collection.getPartyAsList()) {
			System.out.println(character.toConsiseString());
		}
		
		
		// how removing a character from the collection affects the party
		System.out.print("\n\nNext is removing a character that is in the party\nPress enter to continue:");
		input.nextLine();
		
		// Character being removed
		int removeCharacterIndex = collection.getParty()[(int)Math.random() * 5];
		CharacterBase removingCharacter = collection.getCollection().get(removeCharacterIndex);
		System.out.printf("%d which is %s was selected to be removed.\n", removeCharacterIndex, removingCharacter.toConsiseString());
		
		collection.removeCharacter(removeCharacterIndex);
		
		System.out.println("\nHere is the collection");
		for (CharacterBase character: collection.getCollection()) {
			System.out.println(character.toConsiseString());
		}
		System.out.print("\nHere is your party is ");
		for (int index :collection.getParty()) {
			System.out.print(index + " ");
		}
		System.out.print(" or in indexes ");
		for (int index :collection.getParty()) {
			System.out.print((index - 1) + " ");
		}
		System.out.println("\nWhich is these Characters ");
		for (CharacterBase character :collection.getPartyAsList()) {
			System.out.println(character.toConsiseString());
		}
		
		
		// saving and loading
		System.out.println("\nContinue to see your character");
		System.out.print("Press enter to continue:");
		input.nextLine();
		
		CharacterBase youCharacter = new CharacterBase(userName);
		System.out.printf("\nHere is your character\n%s", youCharacter.toString());
		
		collection.addCharacter(youCharacter);
		
		System.out.print("\nEnter a name for your save game: ");
		String fileName = input.next();
		
		rpgElements.SaveManager saveManager = new rpgElements.SaveManager();
		saveManager.setSaveName(fileName);
		System.out.println("\nThis is your filePath: " + saveManager.getSaveGamePath());
		
		System.out.println("\nNow it's time to test the save manager.");
		System.out.print("Press enter to continue:");
		input.nextLine();
		
		// saving the game
		System.out.println();
		saveManager.saveToFile(collection.getCollection());
		System.out.println("this should be completed and visible in the path");
		
		System.out.println("\nNow it's time to load the file after clearing everything.");
		System.out.print("Press enter to continue:");
		input.nextLine();
		
		// loading game
		collection.clearCollection();
		System.out.println("\n\nThe collection is now " + collection.getCollection().size());
		System.out.println("Time to load it back up\n");
		for (CharacterBase character: saveManager.loadSaveGame()) {
			System.out.println(character.toConsiseString());
		}
		
		// end
		System.out.println("\nThis test is complete.\ncode by David Cruz\nGo to javaRPGLoadTest to review your characters");
		input.close();
		
		
	}
}
