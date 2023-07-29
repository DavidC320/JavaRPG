package jFX;
import rpgElements.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
/*
 * David cruz 
 * 7/22/2023
 * */

public class LoadGamePane extends GridPane implements Refresh{
	private TextField saveName = new TextField();
	private Label result = new Label();

	public LoadGamePane(SceneController sceneController, Player player) {
		setPadding(new Insets(11, 11, 11, 11));
		setAlignment(Pos.TOP_CENTER);
		
		Button backBtn = new Button("back to title");
		add(backBtn, 0, 0);
		
		add(new Label("Load game"), 0, 1);
		add(saveName, 0, 2);
		
		add(result, 0, 3);
		
		Button loadGameBtn = new Button("Load Game");
		add(loadGameBtn, 0, 4);
		
		backBtn.setOnAction((e) -> sceneController.setPane("title"));
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
