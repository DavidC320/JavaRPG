package jFX;
import rpgElements.*;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
/*
 * Based off of 
 * JavaFX switch scenes by Bro Code
 * Map and HashMap in Java by Coding With John
 * 
 * 7/22/2023
 * David Cruz
 * 
 * I didn't know I had to do all of the UI now. Oops.
 * */

public class SceneController {
	private HashMap<String, Pane> panes = new HashMap<>();
	private BorderPane parantPane = new BorderPane();
	private Stage stage;
	
	public SceneController(Stage stage, Player player, Explore explore) {
		this.stage = stage;
		
		// set some things up beforehand.
		Scene scene = new Scene(parantPane, 580, 450);
		stage.setScene(scene);
		
		// add panes to map
		panes.put("title", new TitlePane(this));
		panes.put("main menu", new MainMenuPane(this, player));
		panes.put("collection", new CollectionManagerPane(this, player));
		panes.put("load game", new LoadGamePane(this, player));
		panes.put("save game", new SaveGamePane(this, player));
		panes.put("set explore", new SetExploreLevelPane(this, player, explore));
		panes.put("first character", new CreateFirstCharacterPane(this, player));
		panes.put("floor", new ExploreFloorPane(this, player, explore));
		panes.put("new character", new GetNewCharacterPane(this, player, explore));
		panes.put("fight", new CombatPane(this, player, explore));
		panes.put("fight results", new CombatResultsPane(this, player, explore));
		panes.put("explore results", new ExploreResultsPane(this, player, explore));
	}
	
	public void setPane(String paneName) {
		System.out.println(paneName);
		Pane pane = panes.get(paneName);
		
		// refreshes the pane if it implements the Refresh Instance.
		// based off of https://www.baeldung.com/java-check-class-implements-interface
		if (pane instanceof Refresh) {
			System.out.println("refresh activated");
			((Refresh) pane).refresh();
			}
		
		parantPane.setCenter(pane);
		stage.show();
	}
}

/* Notes
 * 7/22/2023 4: 44
 * So I had this big issue where the pane swither wouldn't go back to the title screen so I looked up
 * a solution.
 * I found this by Jewelsea https://stackoverflow.com/questions/16176701/switch-between-panes-in-javafx
 * and I didn't really read all of it but I did read that I could replace a pane wihtin a pane with a new 
 * pane. And it works.
 */
