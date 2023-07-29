package RPG;
/*
 * Character class
 * David Cruz
 * 6/18/2023
 * 
 * This class is the collectible character class the user will use.
 * 
 * Basis statistics are used to save a character
 * icon
 * name
 * defeated enemies
 * level
 * current experience
 * base max health
 * base attack
 * base critical chance
 * 
 * other statistics
 * current health
 * 
 * leveled statistics are used to uncompress a character
 * leveled max health
 * leveled attack
 * leveled critical chance
 * leveled experience threshold
 * leveled enemies threshold
 */

// understanding classes from Keep on Coding
// https://www.youtube.com/watch?v=IUqKuGNasdM



public class Character {
	// you can private vars and make setters. source https://www.w3schools.com/java/java_encapsulation.asp
	
	// saved values
	private int icon = 0;
	
	public String name;
	public int level;
	public int currentExperience;
	public int defeatedEnemies;
	
	private int healthPercent;
	private int attackPercent;
	private int criticalPercent;
	
	// Calculated on creation
	private int baseMaxHealth;
	private int baseAttack;
	private int baseCriticalChance;
	
	public int currentHealth;
	
	private int leveledMaxHealth;
	private int leveledAttack;
	private int leveledCriticalChance;
	
	public int leveledExperienceThreshold;
	public int rankedEnemiesThreshold;
	
	// constants
	// these wher moved for convenience
	final int MAX_LEVEL = 50;
	final char [] ICONS = {'☺', '☻', '♥', '♦', '♣', '♠'};
	
	public Character(){ // Setting defaults
		icon = 0;
		name = "CHAR_TST";
		level = 1;
		currentExperience = 0;
		defeatedEnemies = 0;
		healthPercent = 1;
		attackPercent = 1;
		criticalPercent = 1;
	}
	
	
	public void set_character(
			int icon, String name, int level, int currentXP, int defeatedEnemies, 
			int percentMax, int percentAtt, int percentCrit) {
		
		this.icon = icon;
		this.name = name;
		this.level = level;
		currentExperience = currentXP;
		this.defeatedEnemies = defeatedEnemies;
		
		healthPercent = percentMax;
		attackPercent = percentAtt;
		criticalPercent = percentCrit;
		
		calculate_base_icon_stats();
	}
	
	
	public void generate_character() {
		level = (int) (Math.random() * MAX_LEVEL +1);
		
		
		// Random rank
		icon = (int) (Math.random() * ICONS.length);
		
		setRandomStatPercent();
		calculate_base_icon_stats();
	}
	
	
	public void generate_character(int createdLevel) {
		level = createdLevel;
		
		// Random rank
		icon = (int) (Math.random() * ICONS.length);
		
		setRandomStatPercent();
		calculate_base_icon_stats();
	}
	
	
	public void generate_character(int createdLevel, int rank) {
		level = createdLevel;
		
		// Random rank
		icon = rank;
		
		setRandomStatPercent();
		calculate_base_icon_stats();
	}
	
	
	private void setRandomStatPercent() {
		// generating staticistic percent
		// Get the percent that each base statistic will gain
		healthPercent = (int) (Math.random() * 11);
		attackPercent = (int) (Math.random() * 11);
		criticalPercent = (int) (Math.random() * 11);
		//System.out.printf("percents: h%s, a%s, c%s\n", healthPercent, attackPercent, criticalPercent);
	}
	
	
	private void calculate_base_icon_stats() {
		level_character();
		icon_calculator_ranking();
		currentHealth = getMaxHealth();
	}
	
	
	public int getMaxHealth() {
		return (baseMaxHealth + leveledMaxHealth);
	}
	
	
	public int getAttack() {
		return (baseAttack + leveledAttack);
	}
	
	
	public int getCriticalChance() {
		return (baseCriticalChance + leveledCriticalChance);
	}
	
	public char getIcon() {
		return ICONS[icon];
	}
	
	
	public void update_defeated_enemies(int defeated) {
		// calculate defeated enemies
		defeatedEnemies += defeated;
		
		// if defeated is greater than threshold. upgrade icon
		// from https://www.w3schools.com/java/java_arrays.asp
		if (defeatedEnemies >= rankedEnemiesThreshold && icon < ICONS.length- 1) {
			defeatedEnemies -= rankedEnemiesThreshold;
			icon_calculator_ranking();
			icon++;
		}
	}
	
	
	public void update_experience(int experience) {
		currentExperience += experience;
		if (currentExperience >= leveledExperienceThreshold && level < MAX_LEVEL) {
			currentExperience -= leveledExperienceThreshold;
			level_character();
			level++;
		}
	}
	
	
	private void icon_calculator_ranking() {
		// getting values
		//☺☻♥♣♦♠
		double trueHPPercent = (double) healthPercent / 10;
		double trueATPercent = (double) attackPercent / 10;
		double trueCCPercent = (double) criticalPercent / 10;
		
		// Giving points to statistics
		//System.out.printf("%s | %s\n", (10 * icon), trueHPPercent);
		//System.out.printf("%s | %s\n", (2 * icon), trueATPercent);
		//System.out.printf("%s | %s\n", (5 * icon), trueCCPercent);
		
		baseMaxHealth = (int) ((10 * icon) * trueHPPercent);
		baseAttack = (int) ((2 * icon) * trueATPercent);
		baseCriticalChance = (int) ((5 * icon) * trueCCPercent);
		
		rankedEnemiesThreshold = (int) (icon + 1) * 10;
		currentHealth = getMaxHealth();
	}
	
	
	private void level_character() {
		// set variables
		final int MAX_HEALTH = 100;
		final int MAX_ATTACK = 20;
		final int MAX_CRITICAL_CHANCE = 50;
		double percent = (double) level / MAX_LEVEL;
		//System.out.print("lvp: " + percent + "\n");
		
		// get new leveled statistics
		leveledMaxHealth = (int) (MAX_HEALTH * percent);
		leveledAttack = (int) ( MAX_ATTACK * percent );
		leveledCriticalChance = (int) (MAX_CRITICAL_CHANCE * percent);
		
		leveledExperienceThreshold = (int) (level * 5);
		currentHealth = getMaxHealth();
	}
	
	
	public void take_damage(int damage) {
		currentHealth -= damage;
		
		// health corrections
		if (currentHealth < 0)
			currentHealth = 0;
		else if (currentHealth > getMaxHealth())
			currentHealth = getMaxHealth();
	}
	
	
	public int attack() {
		int attack = getAttack();
		double criticalChance = (Math.random() * 101);
		
		// returns the damage
		if (criticalChance <= getCriticalChance())
			return (attack * 2);
		else
			return attack;
	}
	
	public void printStats() {
		System.out.printf(
				"%s%s: %s\n"
				+ "Info---------------------\n"
				+ "LV: %s/%s\n"
				+ "XP: %s/%s\n"
				+ "RK: %s/%s\n"
				+ "Stats-------------------\n"
				+ "%s0 HP: %s/%s\n"
				+ "%s0 AT: %s\n"
				+ "%s0 CH: %s\n\n",
				icon, getIcon(), name,
				level, MAX_LEVEL,
				currentExperience, leveledExperienceThreshold,
				defeatedEnemies, rankedEnemiesThreshold,
				healthPercent, currentHealth, getMaxHealth(),
				attackPercent, getAttack(),
				criticalPercent, getCriticalChance());
	}
}
