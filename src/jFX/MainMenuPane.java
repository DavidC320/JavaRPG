package jFX;
import rpgElements.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
/*
 * David cruz 
 * 7/22/2023
 * based off of Show HBox and VBox
 * */

public class MainMenuPane extends BorderPane{
	public MainMenuPane(SceneController sceneController, Player player) {
		VBox menu = new VBox(15);
		menu.setAlignment(Pos.TOP_CENTER);
		setCenter(menu);
		
		// buttons
		Button backBtn = new Button("back");
		Button manageCollectionBtn = new Button("Collection");
		Button saveGameBtn = new Button("Save Game");
		Button exploreBtn = new Button("Explore");
		
		// nodes to add
		Node[] nodes = {new Label("Main Menu"), manageCollectionBtn,
				saveGameBtn, exploreBtn, backBtn};
		
		// add to menu
		for (Node node: nodes) {
			VBox.setMargin(node, new Insets(0, 0, 0, 15));
			menu.getChildren().add(node);
		}
		
		// button actions
		backBtn.setOnAction((e) -> sceneController.setPane("title"));
		manageCollectionBtn.setOnAction((e) -> sceneController.setPane("collection"));
		saveGameBtn.setOnAction((e) -> sceneController.setPane("save game"));
		exploreBtn.setOnAction((e) -> {
			if (player.hasPartyMembers())
				sceneController.setPane("set explore");
			else
				sceneController.setPane("collection");
			});
	}
}
