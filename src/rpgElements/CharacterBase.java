package rpgElements;
/* This has been recreated from the old Character.java class that can be found in old stuff.
 * David Cruz
 * 7/14/2023
 * */

/** used in combat and collecting*/
public class CharacterBase {
	// Saved data
	private int rank = 0;
	public String name = "Test";
	public int level = 1;
	public int currentExperience = 0;
	public int defeatedEnemies = 0;
	private int healthPercent = 1;
	private int attackPercent = 1;
	private int criticalPercent = 1;
	
	// Calculated data
	private int rankMaxHealth;
	private int rankAttack;
	private int rankCriticalChance;
	private int leveledMaxHealth;
	private int leveledAttack;
	private int leveledCriticalChance;
	public int leveledExperienceThreshold;
	public int rankedEnemiesThreshold;
	public int currentHealth;
	
	// constants
	// these are here for convenience
	public final static int MAX_LEVEL = 50;
	public final static char [] ICONS = {'☺', '☻', '♥', '♦', '♣', '♠'};
	public final String[] NAMES = {"Ron", "Bob", "John", "Jane", "Margret", "Daniel", "Mimkro", "Viper", "Chumly", "PineApple", "Apple Pie", "Rosy Custard", "Spiffo", "Sabeth", "Kekht", "Leviathan", "Q Master",
			"Lain", "George", "Woods", "Kenny", "Ragged Johns", "Danny", "Test", "Text", "The Alchemist", "Trikoa", "Mismier", "Toro", "Megan", "Dream C", "Miami"};
	
	/** Create a default test character */
	public CharacterBase() {
		randomName();
		randomLevel();
		levelStatCalculator();
		rankStatCalculator();
	}
	
	/** Creates a random character with a name*/
	public CharacterBase(String name) {
		this.name = name;
		// random icon
		randomRank();
		// random Percents
		randomizeStatisticsPercent();
		
		// calculate base stats
		// calculate ranked stats
		levelStatCalculator();
		rankStatCalculator();
	}
	
	/** Creates a random character with a level */
	public CharacterBase(int level) {
		this.level = level;
		randomName();
		
		// random icon
		randomRank();
		// random Percents
		randomizeStatisticsPercent();
		
		// calculate base stats
		// calculate ranked stats	
		levelStatCalculator();
		rankStatCalculator();
	}
	
	/** Create custom or loaded character */
	public CharacterBase(String name, int rank, int experience, int defeatedEnemies, int level, int healthPercent, int attackPercent, int criticalPercent) {
		this.name = name;
		this.rank = rank;
		this.currentExperience = experience;
		this.defeatedEnemies = defeatedEnemies;
		this.level = level;
		this.healthPercent = healthPercent;
		this.attackPercent = attackPercent;
		this.criticalPercent = criticalPercent;
		
		levelStatCalculator();
		rankStatCalculator();
	}
	
	// setters
	/** Change a character’s name */
	public void setName(String name) {
		this.name = name;
	}
	
	// getters
	
	/** get how much experience is needed to level up */
	public int getExperienceThreshold() {
		return this.leveledExperienceThreshold;
	}
	
	/** get how many enemies need to be defeated to rank up */
	public int getRankThreshold() {
		return this.rankedEnemiesThreshold;
	}
	
	/** gets the characters name*/
	public String getName() {
		return name;
	}
	
	/** gets the characters rank */
	public int getRank() {
		return rank;
	}
	
	/** get the characters icon */
	public char getIcon() {
		if (rank > ICONS.length)
			rank = ICONS.length - 1;
		return ICONS[rank];
	}
	
	/** get the characters level */
	public int getLevel() {
		return level;
	}
	
	/** get the characters experience */
	public int getExperience() {
		return currentExperience;
	}
	
	/** get the characters defeated enemy count */
	public int getDefeatedEnemies() {
		return defeatedEnemies;
	}
	
	/** get the characters health percent */
	public int getHealthPercent() {
		return healthPercent;
	}
	
	/** get the characters attack percent */
	public int getAttackPercent() {
		return attackPercent;
	}
	
	/** get the characters critical percent */
	public int getCriticalPercent() {
		return criticalPercent;
	}
	
	/** gets the combined value of baseMaxHealth(rank) and leveldMaxHealth*/
	public int getMaxHealth() {
		return (rankMaxHealth + leveledMaxHealth);
	}
	
	/** gets the characters current health */
	public int getCurrentHealht() {
		return currentHealth;
	}
	
	/** gets the combined value of baseAttack(rank) and leveleAttack..
	 * IF the character has a attack number lower or equal to 0 then
	 * the attack is set to 1.
	 * */
	public int getAttack() {
		int attack = rankAttack + leveledAttack;
		return (attack > 0) ? attack : 1;
	}
	
	/** gets the combined value of baseCriticalChance(rank) and leveldCriticalChance*/
	public int getCriticalChance() {
		return (rankCriticalChance + leveledCriticalChance);
	}
	
	// methods
	
	/** gives a random name to a character */
	private void randomName() {
		name = NAMES[(int)(Math.random() * NAMES.length)];
	}
	
	/** gives the character a random rank */
	private void randomRank() {
		rank = (int)(Math.random() * ICONS.length);
	}
	
	/** Generates random statistic percents for the character */
	private void randomizeStatisticsPercent() {
		healthPercent = 1 + (int)(Math.random() * 10);
		attackPercent = 1 + (int)(Math.random() * 10);
		criticalPercent = 1 + (int)(Math.random() * 10);
	}
	
	private void randomLevel() {
		level = 1 + (int)(Math.random() * MAX_LEVEL);
	}
	
	/** creates an array of 3 numbers after calculating the next number using the threshold and calculate a new threshold. */
	public static Integer[] calculateThreshold(int currentNumber, int threshold, int level, int thresholdOffset, int thresholdMultiplyer, int maxLevel) {
		if (level < maxLevel){
			do {
				currentNumber -= threshold;
				threshold = (int)(level + thresholdOffset * thresholdMultiplyer);
				level++;
			} while (currentNumber > threshold);
		}
		
		Integer[] calculatedData = {currentNumber, threshold, level};
		return calculatedData;
	}
	
	/** add Experience to the character */
	public void addExperience(int experienceGain) {
		currentExperience += experienceGain;
		if (currentExperience > leveledExperienceThreshold) {
			Integer[] levelData = calculateThreshold(currentExperience, leveledExperienceThreshold, level, 1, 5, MAX_LEVEL);
			currentExperience = levelData[0];
			leveledExperienceThreshold = levelData[1];
			level = levelData[2];
			
			// calculate level
			levelStatCalculator();
		}
	}
	
	/** add defeated enemies to the character */
	public void addDefeatedEnemies(int defeatedEnemiesGain) {
		defeatedEnemies += defeatedEnemiesGain;
		if (defeatedEnemies > rankedEnemiesThreshold) {
			Integer[] rankData = calculateThreshold(defeatedEnemies, rankedEnemiesThreshold, rank, 1, 10, ICONS.length - 1);
			defeatedEnemies = rankData[0];
			rankedEnemiesThreshold = rankData[1];
			rank = rankData[2];
			
			// calculate rank
			rankStatCalculator();
		}
	}
	
	/** calculates level stats */
	private void levelStatCalculator() {
		// set variables
		final int MAX_HEALTH = 100;
		final int MAX_ATTACK = 20;
		final int MAX_CRITICAL_CHANCE = 60;
		double percent = (double) level / MAX_LEVEL;
		
		// get new leveled statistics
		leveledMaxHealth = (int) (MAX_HEALTH * percent);
		leveledAttack = (int) ( MAX_ATTACK * percent );
		leveledCriticalChance = (int) (MAX_CRITICAL_CHANCE * percent);
		
		leveledExperienceThreshold = (level + 1) * 5;
		
		currentHealth = getMaxHealth();
	}
	
	/** calculates rank stats */
	private void rankStatCalculator() {
		// getting values
		//☺☻♥♣♦♠
		double trueHPPercent = (double) healthPercent / 10;
		double trueATPercent = (double) attackPercent / 10;
		double trueCCPercent = (double) criticalPercent / 10;
		
		// max rank stats
		final int MAX_HEALTH = 50;
		final int MAX_ATTACK = 10;
		final int MAX_CRITICAL_CHANCE = 40;
		
		// Giving points to statistics
		//System.out.printf("%s | %s\n", (10 * icon), trueHPPercent);
		//System.out.printf("%s | %s\n", (2 * icon), trueATPercent);
		//System.out.printf("%s | %s\n", (5 * icon), trueCCPercent);
		
		rankMaxHealth = (int) ((MAX_HEALTH * ((double)(rank + 1) / ICONS.length)) * trueHPPercent);
		rankAttack = (int) ((MAX_ATTACK * ((double)(rank + 1) / ICONS.length)) * trueATPercent);
		rankCriticalChance = (int) ((MAX_CRITICAL_CHANCE * ((double)(rank + 1) / ICONS.length)) * trueCCPercent);
		
		rankedEnemiesThreshold = (rank + 1) * 10;
		
		currentHealth = getMaxHealth();
	}
	
	/** heal a character */
	public void heal(int healthRegain) {
		if (healthRegain + currentHealth > getMaxHealth()) {
			healthRegain -= healthRegain + currentHealth - getMaxHealth();
		}
		currentHealth += healthRegain;
	}
	
	/** gets the amount of damage a character does. If a character’s critical chance lands less than the critical chance then the damage is doubled. */
	public int attackDamage() {
		boolean critical = (getCriticalChance() > Math.random() * 101);
		return getAttack() * (critical ? 0 : 2);
	}
	
	/** takes health away from a character. */
	public void takeDamage(int damage) {
		currentHealth -= damage;
		if (currentHealth < 0)
			currentHealth = 0;
	}
	
	/** return the character in string form */
	public String toString() {
		String characterData = String.format(
				"%c %s|\n" + 
				"LV: %d / %d | Xp: %d / %d | ED: %d / %d|\n" + 
				"HP: %d / %d | At: %d | Ct: %d / 100 |", 
				getIcon(), name,
				level, MAX_LEVEL, currentExperience, leveledExperienceThreshold, defeatedEnemies, rankedEnemiesThreshold,
				currentHealth, getMaxHealth(), getAttack(), getCriticalChance());
		return characterData;
	}
	
	/** returns a break down of a character */
	public String toExpansiveString() {
		String characterData = String.format(
				"%c %s|\n" + 
				"LV: %d / %d\n" + 
				"Xp: %d / %d\n" + 
				"ED: %d / %d\n" + 
				"HP %d : %d / %d + %d\n" + 
				"At %d : %d = %d + %d\n" + 
				"Ct %d : %d = %d + %d / 100", 
				getIcon(), name,
				level, MAX_LEVEL, currentExperience, leveledExperienceThreshold, defeatedEnemies, rankedEnemiesThreshold,
				healthPercent, currentHealth, leveledMaxHealth, rankMaxHealth, 
				attackPercent, getAttack(), leveledAttack, rankAttack, 
				criticalPercent, getCriticalChance(), leveledCriticalChance, rankCriticalChance);
		return characterData;
	}
	
	/** returns a shorten string of information 
	 * icon name | current hp / max hp | At | ct.
	 * */
	public String toConsiseString() {
		String characterData = String.format(
				"%c %s| HP: %d / %d | At: %d | Ct: %d / 100 |", 
				getIcon(), name,
				currentHealth, getMaxHealth(), getAttack(), getCriticalChance());
		return characterData;
	}
	
	/** returns the save data of this character
	 *  rank name level currentExperience deferatedEnemies healthPercent attackPercent criticalPercent.
	 *  */
	public String toSaveString() {
		return String.format("%d %s %d %d %d %d %d %d", rank, name, level, currentExperience, defeatedEnemies, healthPercent, attackPercent, criticalPercent);
	}
}
