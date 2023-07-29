package jFX;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/*
 * David cruz 
 * 7/22/2023
 * */

public class TitlePane extends BorderPane{
	public TitlePane(SceneController sceneController) {
		this.setStyle("-fx-background-color: #a7a88c");
		
		VBox titlePane = new VBox(0);
		titlePane.setAlignment(Pos.CENTER);
		setTop(titlePane);
		
		Text titleText = new Text("StrongHold Silver:");
		titleText.setFill(Color.DARKBLUE);
		titleText.setFont(new Font(20));
		Text titleSubText = new Text("Java RPG");
		titleSubText.setFill(Color.DARKBLUE);
		titleSubText.setFont(new Font(60));
		Label title = new Label("Version P3.0\nBy David Cruz");
		titlePane.getChildren().addAll(titleText, titleSubText, title);
		
		VBox controls = new VBox(15);
		controls.setAlignment(Pos.CENTER);
		setCenter(controls);
		Button playBtn = new Button("Play game");
		Button loadBtn = new Button("load game");

		controls.getChildren().addAll(playBtn, loadBtn);
		
		playBtn.setOnAction((e) ->{
			sceneController.setPane("first character");
		});
		
		loadBtn.setOnAction((e) -> sceneController.setPane("load game"));
	}
}
