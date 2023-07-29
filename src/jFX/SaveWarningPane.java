package jFX;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class SaveWarningPane extends BorderPane{
	public SaveWarningPane(SceneController sceneController) {
		this.setStyle("-fx-background-color: #a7a88c");
		
		VBox textInfo = new VBox(10);
		setCenter(textInfo);
		textInfo.setAlignment(Pos.CENTER);
		HBox controls = new HBox(15);
		controls.setAlignment(Pos.CENTER);
		Button saveGame = new Button("Save\nGame");
		Button okay = new Button("Quit\nGame");
		controls.getChildren().setAll(saveGame, okay);
		textInfo.getChildren().addAll(new Label("Are you sure you want to quit?"), new Label("Any unsaved progress will be lost."), 
				controls);
		
		saveGame.setOnAction(e -> sceneController.setPane("save game"));
		okay.setOnAction(e -> sceneController.setPane("title"));
	}

}
