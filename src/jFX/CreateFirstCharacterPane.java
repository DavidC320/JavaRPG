 package jFX;
import rpgElements.Player;
import rpgElements.CharacterBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/*
 * David cruz 
 * 7/22/2023
 * */

public class CreateFirstCharacterPane extends JRPGFXPane implements Refresh{
	private TextField characterName = new TextField(); 
	private Button enterCharacter = new Button("Create");
	private Label results = new Label("");
	
	// show and continue
	private VBox continuePanel = new VBox(15);
	private Label characterInfo = new Label("");
	private Label continueText = new Label("This is your character. Not powerfull but a start.\nGo out and find more friends.");
	private Button continueBtn = new Button("Continue");
	
	public CreateFirstCharacterPane(SceneController sceneController, Player player) {
		super(sceneController, "Create a character.");
		
		VBox createCharacterPane = new VBox(15);
		createCharacterPane.setAlignment(Pos.CENTER);
		setCenter(createCharacterPane);
		
		continuePanel.setAlignment(Pos.CENTER);
		createCharacterPane.getChildren().setAll(new Label("Enter the name of your character."), characterName, enterCharacter,
				continuePanel);
		
		enterCharacter.setOnAction((e) -> {
			String name = characterName.getText().trim();
			if (name.length() <= 0) {
				results.setText("Enter a name.");
				return;
			}
			enterCharacter.setDisable(true);
			characterName.setEditable(false);
			
			CharacterBase firstCharacter = new CharacterBase(name);
			player.setFileName(name);
			player.addCharacter(firstCharacter);
			
			characterInfo.setText(firstCharacter.toString());
			
			continuePanel.getChildren().setAll(characterInfo, continueText, continueBtn);
		});
		
		continueBtn.setOnAction((e) -> sceneController.setPane("main menu"));
		
	}

	@Override
	public void refresh() {
		continuePanel.getChildren().clear();
		enterCharacter.setDisable(false);
		characterName.setEditable(true);
		characterName.setText("");
		
	}

}
