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
	
	/** creates an explore object */
	public Explore(Player player) {
		this.player = player;
	}
	
	// getters
	/** get how much damage has been dealt */
	public int getDamageDealt() {
		return damageDealt;
	}
	
	/** get how many enemies has been defeated */
	public int getEnemiesDefeated() {
		return enemiesDefeated;
	}
	
	/** gets the current floor */
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	/** gets the max difficulty */
	public int getMaxDifficulty() {
		return maxDifficulty;
	}
	
	/** gets the current floor difficulty */
	public int getCurrentFloorDifficualty() {
		return currentFloor + exploreDifficulty;
	}
	
	/** gets the enemy's team */
	public ArrayList<CharacterBase> getEnemyteam(){
		return enemy.getPartyAsList();
	}
	
	// setters
	/** sets how much damage has been dealt */
	public void setDamageDealt(int damage) {
		this.damageDealt = damage;
	}
	
	/** sets the amount of defeated enemies */
	public void setDefeatedEnemeis(int enemies) {
		this.enemiesDefeated = enemies;
	}
	/** sets the max difficulty*/
	public void setMaxDifficulty(int max) {
		this.maxDifficulty = max;
	}
	
	/** sets the explore difficulty */
	public void setExploreDifficulty(int difficulty) {
		this.exploreDifficulty = difficulty;
	}
	
	// methods
	/** Increment the current floor by 1 */
	public void incrementFloor() {
		this.currentFloor++;
	}
	
	/** resets the current expedition */
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
	
	/** have two parties fight a round and return how much damage and how many were defeated by the attacking party */
	public static int[] attackTeam(CharacterManager attackingManager, CharacterManager defendingManager) {
		int[] damageDefeated = new int[2];
		ArrayList<CharacterBase> attackingTeam = attackingManager.getLivingPartyMemebrs();
		ArrayList<CharacterBase> defendingTeam = defendingManager.getLivingPartyMemebrs();
		ArrayList<CharacterBase> targets = defendingManager.getPartyAsList();
		
		for (CharacterBase character: attackingTeam) {
			if (!defendingManager.hasLivingPartyMembers())
				return damageDefeated;
			
			// get a random target and attack
			if (character.currentHealth > 0) {
				CharacterBase target = targets.get((int)(Math.random() * defendingTeam.size()));
				int targetInt = targets.indexOf(target);
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
	
	/** give a team xp and kills */
	public void ditributeXPAndEnemies(ArrayList<CharacterBase> team) {
		double teamSplit = (double)getDamageDealt() / 2 / team.size();
		int expericeFound = (int) Math.ceil(teamSplit);
		int enemiesDefeated = getEnemiesDefeated();
		setDamageDealt(0);
		setDefeatedEnemeis(0);
		
		for (CharacterBase character: player.getPartyAsList()) {// give then xp and defeated enemies
			character.addDefeatedEnemies(enemiesDefeated);
			character.addExperience(expericeFound);
			}
		
	}
	
	/** a exception if there is no members in a party */
	public class NoPartyMembers extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NoPartyMembers(String message) {
			super(message);
		}
	}
}
