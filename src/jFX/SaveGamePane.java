package jFX;
import rpgElements.Player;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/*
 * David cruz 
 * 7/22/2023
 * */

public class SaveGamePane extends JRPGFXPane implements Refresh{
	private TextField saveGameName = new TextField();
	private Label results = new Label("");
	private Player player;
	
	public SaveGamePane(SceneController sceneController, Player player) {
		super(sceneController, "Save Game", e-> sceneController.setPane("main menu"));
		this.player = player;
		
		// main body
		VBox saveDataControls = new VBox();
		Button saveGame = new Button("Save Game");
		saveDataControls.getChildren().addAll(new Label("Enter your save name"), saveGameName, results, saveGame);
		setCenter(saveDataControls);
		
		saveGame.setOnAction((e) -> {
			String fileName = saveGameName.getText().trim();
			if (fileName.length() == 0) {
				results.setText("Enter a name to save");
			}
			player.setFileName(fileName);
			player.saveGame();
			results.setText("Game saved.");
		});
		
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		saveGameName.setText(player.getFileName());
		results.setText("");
		
	}

}
