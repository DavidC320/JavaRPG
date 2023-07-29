package jFX;
import rpgElements.Player;
import rpgElements.Explore;
import rpgElements.CharacterBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
/*
 * 7/23/2023
 * David Cruz
 * */

public class GetNewCharacterPane extends BorderPane implements Refresh{
	private CharacterBase newCharacter;
	private Label characterName = new Label();
	private Label characterInfo = new Label();
	private Explore explore;

	public GetNewCharacterPane(SceneController sceneController, Player player, Explore explore) {
		this.explore = explore;
		this.setStyle("-fx-background-color: #c5c7a3");
		
		VBox controlPanel = new VBox(15);
		controlPanel.setAlignment(Pos.CENTER);
		HBox actionPanel = new HBox(15);
		actionPanel.setAlignment(Pos.CENTER);
		
		setCenter(controlPanel);
		
		controlPanel.getChildren().setAll(new Label("You found a new Character"), characterName, characterInfo, 
				actionPanel);
		
		Button leave = new Button("Leave");
		Button take = new Button("take");
		
		actionPanel.getChildren().setAll(leave, take);
		
		leave.setOnAction((e) -> sceneController.setPane("floor"));
		take.setOnAction((e) -> {
			player.addCharacter(newCharacter);
			sceneController.setPane("floor");
		});
		
		
	}
	
	@Override
	public void refresh() {
		newCharacter = explore.generateCharacter();
		characterName.setText(newCharacter.getIcon() + " " + newCharacter.getName());
		characterInfo.setText(newCharacter.toString());
	}
	

}
