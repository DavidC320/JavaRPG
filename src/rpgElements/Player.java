package rpgElements;
/*
 * David Cruz
 * 7/22/2023
 * */

/** the player subclass of CharacterManger that allows for you to save characters */
public class Player extends CharacterManager{
	private SaveManager saveManager = new SaveManager();
	
	/** creates a default player */
	public Player(){
		
	}
	
	/** creates a player with save data */
	public Player(String fileName) {
		setFileName(fileName);
		loadGame();
	}
	
	/** get the current save file name */
	public String getFileName() {
		return saveManager.getSaveName();
	}
	
	/** set the save file name */
	public void setFileName(String fileName) {
		saveManager.setSaveName(fileName);
	}
	
	/** saves your game */
	public void saveGame() {
		saveManager.saveToFile(getCollection());
	}
	
	/** load a different game */
	public void loadGame() {
		this.clearCollection();
		setCollection(saveManager.loadSaveGame());
	}
	
	/** checks the save game already exists */
	public boolean saveGameExists() {
		return saveManager.fileExists();
	}
}
