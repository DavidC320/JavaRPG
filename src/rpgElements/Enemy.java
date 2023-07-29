package rpgElements;
/*
 * David Cruz
 * 7/22/2023
 * */

/** the Enemy subclass of CharacterManager to create teams of enemies */
public class Enemy extends CharacterManager{
	
	public Enemy() {
		
	}
	
	/** Generate a team to fight */
	public void generateTeam(int level) {
		clearCollection();
		for (int num = 0; num < 3; num++) {
			addCharacter(generateCharacter(level));
		}
		int[] party = getParty();
		party[0] = 1;
		party[1] = 2;
		party[2] = 3;
		party[3] = 4;
	}
	
	/** Generate boss team to fight */
	public void generateBossTeam(int level) {
		clearCollection();
		addCharacter(generateCharacter(level + 1));
		for (int num = 0; num < 3; num++) {
			addCharacter(generateCharacter(level));
		}
		int[] party = getParty();
		party[0] = 1;
		party[1] = 2;
		party[2] = 3;
		party[3] = 4;
	}
	
	/** Generate boss team to fight */
	public void generateFinalBossTeam(int level) {
		clearCollection();
		addCharacter(generateCharacter(CharacterBase.MAX_LEVEL + 10));
		for (int num = 0; num < 4; num++) {
			addCharacter(generateCharacter(level));
		}
		int[] party = getParty();
		party[0] = 1;
		party[1] = 2;
		party[2] = 3;
		party[3] = 4;
	}

}
