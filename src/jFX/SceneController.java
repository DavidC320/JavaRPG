package jFX;
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
	
	/** creates a sceneController */
	public SceneController(Stage stage) {
		this.stage = stage;
		
		// set some things up beforehand.
		Scene scene = new Scene(parantPane, 580, 450);
		stage.setScene(scene);
	}
	
	/** returns the hash map of the controller */
	public HashMap<String, Pane> getPanesHashMap(){
		return panes;
	}
	
	/** changes the pane to a pane within the hash map. if the pane implements refresh then the refresh method in the pane is activated. */
	public void setPane(String paneName) {
		try {
			
		Pane pane = panes.get(paneName);
		
		// refreshes the pane if it implements the Refresh Instance.
		// based off of https://www.baeldung.com/java-check-class-implements-interface
		if (pane instanceof Refresh) {
			((Refresh) pane).refresh();
			}
		
		parantPane.setCenter(pane);
		stage.show();
		}
		catch (Exception e) {
			System.out.println(paneName);
			e.printStackTrace();
		}
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
