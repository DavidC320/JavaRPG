package jFX;
import rpgElements.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
/*
 * David cruz 
 * 7/22/2023
 * based off of Show HBox and VBox
 * */

public class MainMenuPane extends JRPGFXPane{
	public MainMenuPane(SceneController sceneController, Player player) {
		super(sceneController, "Main menu", "Quit Game", e -> sceneController.setPane("save warning"));
		
		VBox menu = new VBox(15);
		menu.setAlignment(Pos.CENTER);
		setCenter(menu);
		
		// buttons
		Button manageCollectionBtn = new Button("Collection");
		Button saveGameBtn = new Button("Save Game");
		Button exploreBtn = new Button("Explore");
		
		menu.getChildren().addAll(new Label("What would you like to do?") ,
				manageCollectionBtn, exploreBtn, saveGameBtn);
		
		// button actions
		manageCollectionBtn.setOnAction((e) -> sceneController.setPane("collection"));
		saveGameBtn.setOnAction((e) -> sceneController.setPane("save game"));
		exploreBtn.setOnAction((e) -> {
			if (player.hasPartyMembers())
				sceneController.setPane("set explore");
			else
				sceneController.setPane("party warning");
			});
	}
}
