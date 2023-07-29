package rpgElements;
import java.util.ArrayList;
/*
 * David Cruz
 * 7/22/2023
 * */

/** The game controller that manages the game's events */
public class Explore {
	private Player player;
	private Enemy enemy = new Enemy();
	private int currentFloor = 0;
	private int maxDifficulty = 10;
	private int exploreDifficulty = 1;
	private int currentCharactersFound  = 0;
	
	private int damageDealt = 0;
	private int enemiesDefeated = 0;
	
	public static final int MAX_FLOORS = 10;
	
	public Explore(Player player) {
		this.player = player;
	}
	
	// getters
	public int getDamageDealt() {
		return damageDealt;
	}
	
	public int getEnemiesDefeated() {
		return enemiesDefeated;
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	public int getMaxDifficulty() {
		return maxDifficulty;
	}
	
	public int getCurrentFloorDifficualty() {
		return currentFloor + exploreDifficulty;
	}
	
	public ArrayList<CharacterBase> getEnemyteam(){
		return enemy.getPartyAsList();
	}
	
	// setters
	public void setDamageDealt(int damage) {
		this.damageDealt = damage;
	}
	
	public void setDefeatedEnemeis(int enemies) {
		this.enemiesDefeated = enemies;
	}
	
	public void setMaxDifficulty(int max) {
		this.maxDifficulty = max;
	}
	
	public void setExploreDifficulty(int difficulty) {
		this.exploreDifficulty = difficulty;
	}
	
	// methods
	
	
	/** Increment the current floor by 1 */
	public void incrementFloor() {
		this.currentFloor++;
	}
	
	/** resets the current floor */
	public void resetFloors() {
		this.currentFloor = 0;
		this.currentCharactersFound = 0;
		this.enemy.clearCollection();
	}
	
	/** increase the maxDifficulty by 10 until max level */
	public void increaseMaxDifficulty() {
		int newDifficulty = maxDifficulty + 10;
		if (newDifficulty > CharacterBase.MAX_LEVEL)
			newDifficulty = CharacterBase.MAX_LEVEL;
		
		this.maxDifficulty = newDifficulty;
	}
	
	/** gives a random or predetermined event 
	 * 0: nothing happens
	 * 1: new character
	 * 2: fight
	 * 
	 * The random Event has predetermined outcomes
	 * if floor is the last floor than it will be a fight
	 * if the player hasn't gotten a character during a run, than given them a new character
	 * */
	public int randomEvent() {
		if (currentFloor >= MAX_FLOORS - 1) // if it is the last floor
			return 2;
		else if (currentFloor == MAX_FLOORS - 1 && currentCharactersFound == 0) // of the user hasn't found a new character yet;
			return 1;
		else {
			return (int)(Math.random() * 3);
		}
	}
	
	/** have the player's team and enemy team fight 1 round*/
	public int fight() throws NoPartyMembers {
		// throws an exception if  either party has no members.
		if (!player.hasPartyMembers()) {
			throw new NoPartyMembers("The player has no party Members");
		}
		else if (!enemy.hasPartyMembers())
			throw new NoPartyMembers("The enemy has no party Members");
			
		// fighting
		int[] damageDefeated = attackTeam(player, enemy);
		attackTeam(enemy, player);
		
		// update damage and defeated enemies
		setDamageDealt(getDamageDealt() + damageDefeated[0]);
		setDefeatedEnemeis(getEnemiesDefeated() + damageDefeated[1]);
		
		// results
		if (!player.hasLivingPartyMembers())  // if the player party has fainted
			return -1;
		else if (!enemy.hasLivingPartyMembers()) // if the enemy party has fainted
			return 1;
		else // if neither party has fainted.
			return 0; 
	}
	
	/** a method that allows for two managers to fight */
	public static int[] attackTeam(CharacterManager attackingManager, CharacterManager defendingManager) {
		int[] damageDefeated = new int[2];
		ArrayList<CharacterBase> attackingTeam = attackingManager.getLivingPartyMemebrs();
		ArrayList<CharacterBase> defendingTeam = defendingManager.getLivingPartyMemebrs();
		
		for (CharacterBase character: attackingTeam) {
			if (!defendingManager.hasLivingPartyMembers())
				return damageDefeated;
			// get a random target and attack
			if (character.currentHealth > 0) {
				int targetInt = (int)(Math.random() * defendingTeam.size());
				int damage = character.attackDamage();
				damageDefeated[0] += damage;
				defendingTeam.get(targetInt).takeDamage(damage);
				
				if (defendingTeam.get(targetInt).getCurrentHealht() == 0)
					damageDefeated[1]++;
			defendingTeam = defendingManager.getLivingPartyMemebrs();
			}
		}
		return damageDefeated;
	}
	
	/** generates a standard enemy team */
	public void generateEnemyTeam() {
		this.enemy.generateTeam(getCurrentFloorDifficualty());
	}
	
	/** generates a boss team */
	public void generateBossTeam() {
		this.enemy.generateBossTeam(getCurrentFloorDifficualty());
	}
	
	/** generates a final Boss team */
	public void generateFinalBossTeam() {
		this.enemy.generateFinalBossTeam(getCurrentFloorDifficualty());
	}
	
	/** generate a character with the current floor difficulty */
	public CharacterBase generateCharacter() {
		currentCharactersFound++;
		return new CharacterBase(getCurrentFloorDifficualty());
	}
	
	/** a exception if there is no members in a party */
	public class NoPartyMembers extends Exception{
		public NoPartyMembers(String message) {
			super(message);
		}
	}
}
