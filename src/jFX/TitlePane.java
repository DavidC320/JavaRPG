package jFX;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

/*
 * David cruz 
 * 7/22/2023
 * */

public class TitlePane extends GridPane{
	public TitlePane(SceneController sceneController) {
		setPadding(new Insets(11, 11, 11, 11));
		setAlignment(Pos.TOP_CENTER);
		add(new Label("Java RPG\nVersion P1.1\nBy David Cruz"), 0, 0);
		
		Button playBtn = new Button("Play game");
		add(playBtn, 0, 1);
		
		Button loadBtn = new Button("load game");
		add(loadBtn, 0, 2);
		
		playBtn.setOnAction((e) ->{
			sceneController.setPane("first character");
		});
		
		loadBtn.setOnAction((e) -> sceneController.setPane("load game"));
	}
}
