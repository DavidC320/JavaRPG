package jFX;
import rpgElements.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
/*
 * David cruz 
 * 7/22/2023
 * */

public class LoadGamePane extends JRPGFXPane implements Refresh{
	private TextField saveName = new TextField();
	private Label result = new Label();

	public LoadGamePane(SceneController sceneController, Player player) {
		super(sceneController, "Load Game.");
		
		VBox controls = new VBox(15);
		controls.setAlignment(Pos.CENTER);
		setCenter(controls);
		Button loadGameBtn = new Button("Load Game");
		
		controls.getChildren().addAll(new Label("Enter the name of your save in the save folder. Do not include .txt"), saveName, result, loadGameBtn);
		
		loadGameBtn.setOnAction((e) ->{
			player.setFileName(saveName.getText());
			if (!player.saveGameExists()) {
				result.setText("This game doesn't exist.");
				return;
				}
			
			player.loadGame();
			sceneController.setPane("main menu");
		});
		
		
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	

}
