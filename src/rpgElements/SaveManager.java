/*
 * This is the manager for loading and saving game data. 
 * David Cruz
 * 7/14/2023
 */

package rpgElements;
// based off of Bro code java FileWriter https://youtu.be/kjzmaJPoaNc
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Looking at the Intrduction to Java Programing and stuff book on page 483.
 * This is how I'm going to be creating and saving files
 * */

public class SaveManager {
	private String saveName = "TestGame";
	final static String SAVEPATH = "SavedGames";
	
	public SaveManager() {
		createSavePathIfNotPresent();
	}
	
	// getters
	
	public String getSaveName() {
		return saveName;
	}
	
	public String getSaveGamePath() {
		createSavePathIfNotPresent();
		return SAVEPATH + "\\" + saveName + ".txt";
	}
	
	// setters
	
	public void setSaveName(String name) {
		saveName = name;
	}
	
	
	/** create the dir path for save games if it doesn't exist */
	public void createSavePathIfNotPresent() {
		// based off of these sources
		// page 479 intro to java
		// https://stackoverflow.com/questions/3634853/how-to-create-a-directory-in-java
		
		// check if this does exist and is a directory
		File saveGamePath = new File(SAVEPATH);
		if (!saveGamePath.exists() || !saveGamePath.isDirectory())
			saveGamePath.mkdir();
	}
	
	
	/** checks if the save file exists */
	public boolean fileExists() {
		File saveGamePath = new File(getSaveGamePath());
		return (saveGamePath.exists() && saveGamePath.isFile());
	}
	
	// code by dhandush
	// https://intellipaat.com/community/69798/how-to-clear-a-text-file-without-deleting-it
	/** Clears the file to save */
	public void clearFile() {  // this method is slated for deletion
		try {
			FileWriter fw = new FileWriter(getSaveGamePath(), false); // what is the boolean value... what is auto flush
			PrintWriter pw = new PrintWriter(fw, false);
			pw.flush();  // what is flushing the stream? so it's out put as said in https://stackoverflow.com/questions/44843252/what-is-meant-by-flushing-the-stream
			// I will assume this just clears the file.
			pw.close();
			fw.close();
			
		}
		catch (Exception e) {
			System.out.print("There was an error when clearing the file");
		}
	}
	
	public void saveToFile(ArrayList<CharacterBase> list) {
		try {
			// so this just overwrites the text file...
			// so I will get rid of the clearFile method later
			FileWriter writer = new FileWriter(getSaveGamePath()); 
			
			for (CharacterBase character: list) {
				writer.append(character.toSaveString() + "\n");
			}
			writer.close();
			System.out.println("save Complete");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<CharacterBase> loadSaveGame() {
		// based off of 483 listing 12.15
		ArrayList<CharacterBase> loadedCharacters = new ArrayList<>();
		File file = new File(getSaveGamePath());
		
		if (!file.exists()) {
			System.out.println("This file doesn't exit.");
			return loadedCharacters;
			
		}
		try {
			Scanner input = new Scanner(file);
			
			while (input.hasNext()) {
				
				// get character data
				int rank = input.nextInt();
				String name = input.next();
				int level = input.nextInt();
				int currentExperience = input.nextInt();
				int defeatedEnemies = input.nextInt();
				int healthPercent = input.nextInt();
				int attackPercent = input.nextInt();
				int criticalPercent = input.nextInt();
				
				// create loaded character
				CharacterBase loadedCharacter = new CharacterBase(name, rank, currentExperience, defeatedEnemies, level, healthPercent, attackPercent, criticalPercent);
				
				// add character to list
				loadedCharacters.add(loadedCharacter);
			}
			input.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("This is salvaged.");
			return loadedCharacters;
		}
		
		return loadedCharacters;
		
	}
	
	
}


/* Notes 
 * 
 *
 * taken from Bro Code
public static void main(String[] args) {
		try {
		
			// Most important part here
			FileWriter writer = new FileWriter("SavedGames\\Poem.txt"); 
			// the default path for created text files are direcatly in the JavaRPG direcotry. by creating a Saved Games folder and
			// placing it in the path allows for files to be directed to the directory.
			
			writer.write("Roses are red");
			writer.append("\nspinks 12");
			writer.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/