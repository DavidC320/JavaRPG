package jFX;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class NeedPartyMembersPane extends BorderPane{
	public NeedPartyMembersPane(SceneController sceneController) {
		this.setStyle("-fx-background-color: #a7a88c");
		
		VBox textInfo = new VBox(10);
		setCenter(textInfo);
		textInfo.setAlignment(Pos.CENTER);
		Button okay = new Button("Okay");
		textInfo.getChildren().addAll(new Label("You can't explore with an empty party."), new Label("click a empty slot at the bottom of the collection page"), 
				new Label("and select a character in the list to assign them to that spot."), new Label("To remove a party member, just double click their slot."), 
				okay);
		
		okay.setOnAction(e -> sceneController.setPane("collection"));
	}

}
