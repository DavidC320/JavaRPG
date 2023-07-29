package rpgElements;
/*
 * This is the manager for characters allowing for manipulation of them. 
 * David Cruz
 * 7/14/2023
 */
import java.util.ArrayList;

abstract class CharacterManager {
	private ArrayList<CharacterBase> collection = new ArrayList<>();
	private int[] party = {-1, -1, -1, -1};
	
	// getters
	/** get a character from the collection using an array */
	public CharacterBase getCharacterFromCollection(int index) {
		return collection.get(index);
	}
	
	/** get your party’s index values where -1 is an empty spot */
	public int[] getParty(){
		return party;
	}
	
	/** returns the managers collection */
	public ArrayList<CharacterBase> getCollection(){
		return collection;
	}
	
	// setters
	/** replace your current collection with a new collection. */
	public void setCollection(ArrayList<CharacterBase> newCollection) {
		this.collection = newCollection;
	}
	
	// methods
	
	/** Converts the party into a list of characters */
	public ArrayList<CharacterBase> getPartyAsList(){
		ArrayList<CharacterBase> characterParty = new ArrayList<>();
		
		for (int characterIndex: party) {
			// checks if the index is not -1 which means empty or that the index is less than the size of the collection
			if (characterIndex > -1 && characterIndex < collection.size()) {
				characterParty.add(collection.get(characterIndex));
			}
		}
		
		return characterParty;
	}
	
	/** gets the living party members */
	public ArrayList<CharacterBase> getLivingPartyMemebrs(){
		ArrayList<CharacterBase> livingParty = new ArrayList<>();
		
		for (CharacterBase character: getPartyAsList()) {
			if (character.getCurrentHealht() > 0) {
				livingParty.add(character);
			}
		}
		
		return livingParty;
	}
	
	/** Checks if the party array has members */
	public boolean hasPartyMembers() {
		for (int characterIndex: party) {
			if (characterIndex > -1)
				return true;
		}
		
		return false;
	}
	
	/** checks if there are any living party members */
	public boolean hasLivingPartyMembers() {
		boolean hasLiving = getLivingPartyMemebrs().size() > 0;
		return hasLiving;
	}
	
	/** change the party index to a character. -1 means none */
	public void changePartyIndex(int partyIndex, int newCharacterIndex) {
		
		// checks to see if the index is already in the party
		for (int characterIndex: party) {
			if (characterIndex == newCharacterIndex && newCharacterIndex != -1) {
				System.out.print("This character is already in the party: " + newCharacterIndex);
				return;
			}
		}
		
		party[partyIndex] =  newCharacterIndex;
		
	}
	
	/** generate a random character */
	public CharacterBase generateCharacter() {
		return new CharacterBase();
	}
	
	/** generate random character with name */
	public CharacterBase generateCharacter(String name) {
		return new CharacterBase(name);
	}
	
	/** generate a random character with level */
	public CharacterBase generateCharacter(int level) {
		return new CharacterBase(level);
	}
	
	/** adds a character to the collection list */
	public void addCharacter(CharacterBase character) {
		collection.add(character);
	}
	
	/** removes a character from the collection list */
	public void removeCharacter(int index) {
		int partyIndex = index;
		collection.remove(index);
		
		// corrects the party
		for (int characterIndex = 0; characterIndex < party.length ;characterIndex++) {
			
			// removes the index of the removed character
			if (party[characterIndex] == partyIndex)
				party[characterIndex] = -1;
			
			// decrements the indexes of any member that was above the removed character
			else if (party[characterIndex] > partyIndex)
				party[characterIndex] -= 1;
		}
	}
	
	/** empties all the party indexes back to -1 */
	public void clearParty() {
		for (int characterIndex = 0; characterIndex < party.length ;characterIndex++) {
			party[characterIndex] = -1;
		}
	}
	
	
	/** empties the collection along with the party */
	public void clearCollection() {
		collection.clear();
		clearParty();
	}
	
	
	/** returns the saved data of all the characters in the user collection */
	public ArrayList<String> collectionSaveList(){
		ArrayList<String> saveList = new ArrayList<>();
		for (CharacterBase character: collection) {
			saveList.add(character.toSaveString());
		}
		return saveList;
	}
	
	/** mass heal your party members to max health */
	public void fullHealParty() {
		for (CharacterBase characterb: this.getPartyAsList()) {
			characterb.heal(characterb.getMaxHealth());
		}
	}
}
