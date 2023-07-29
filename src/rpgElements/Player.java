package rpgElements;
/*
 * David Cruz
 * 7/22/2023
 * */

/** the player subclass of CharacterManger that allows for you to save characters */
public class Player extends CharacterManager{
	private SaveManager saveManager = new SaveManager();
	
	public Player(){
		
	}
	
	public Player(String fileName) {
		setFileName(fileName);
		loadGame();
	}
	
	public String getFileName() {
		return saveManager.getSaveName();
	}
	
	public void setFileName(String fileName) {
		saveManager.setSaveName(fileName);
	}
	
	public void saveGame() {
		saveManager.saveToFile(getCollection());
	}
	
	public void loadGame() {
		setCollection(saveManager.loadSaveGame());
		System.out.println(getCollection().size());
	}
	
	public boolean saveGameExists() {
		return saveManager.fileExists();
	}
}
