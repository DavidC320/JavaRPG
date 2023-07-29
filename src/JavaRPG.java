import rpgElements.*;

import java.util.HashMap;

import jFX.*;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class JavaRPG extends Application{
	private Player player = new Player();
	private Explore explore = new Explore(player);
	
	@Override
	/** sets up the game */
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Java RPG");
		SceneController sceneController = new SceneController(primaryStage);
		
		// add panes to the SceneController
		HashMap<String, Pane> panes = sceneController.getPanesHashMap();
		
		panes.put("title", new TitlePane(sceneController));
		panes.put("main menu", new MainMenuPane(sceneController, player));
		panes.put("collection", new CollectionManagerPane(sceneController, player));
		panes.put("load game", new LoadGamePane(sceneController, player));
		panes.put("save game", new SaveGamePane(sceneController, player));
		panes.put("set explore", new SetExploreLevelPane(sceneController, player, explore));
		panes.put("first character", new CreateFirstCharacterPane(sceneController, player));
		panes.put("floor", new ExploreFloorPane(sceneController, player, explore));
		panes.put("new character", new GetNewCharacterPane(sceneController, player, explore));
		panes.put("fight", new CombatPane(sceneController, player, explore));
		panes.put("fight results", new CombatResultsPane(sceneController, player, explore));
		panes.put("explore results", new ExploreResultsPane(sceneController, player, explore));
		panes.put("party warning", new NeedPartyMembersPane(sceneController));
		panes.put("save warning", new SaveWarningPane(sceneController));
		
		// set the inital pane
		sceneController.setPane("title");
	}
	
	/** starts the game */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
