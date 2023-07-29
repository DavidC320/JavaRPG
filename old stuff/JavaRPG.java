package RPG;

public class JavaRPG {
	public static void main(String[] args) {
		debugging();
	}
	
	public static void debugging(){
		java.util.Scanner input = new java.util.Scanner(System.in);
		
		System.out.println(
				"This is the debug menu\nCommands\n0 - testLeveling\n1 - testIcons");
		System.out.print("Enter a command: ");
		
		String command = input.next();
		//System.out.print(command);
		System.out.println("");
		
		if (command.equals("0"))
			testLeveling();
		else if (command.equals("1"))
			testIcons();
		else
			System.out.print("Not a command try again.");
		
		input.close();
		
	}
	
	public static void testLeveling() {
		Character test1 = new Character();
		Character test3 = new Character();
		Character test2 = new Character();
		
		test1.generate_character(0, 0);
		test2.generate_character(25, 0);
		test3.generate_character(50, 0);
		
		test1.printStats();
		test2.printStats();
		test3.printStats();
	}
	
	public static void testIcons() {
		Character test1 = new Character();
		Character test3 = new Character();
		Character test2 = new Character();
		
		test1.generate_character(0, 0);
		test2.generate_character(0, 2);
		test3.generate_character(0, 5);
		
		test1.printStats();
		test2.printStats();
		test3.printStats();
	}
}
