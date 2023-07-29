package jFX;
import rpgElements.Player;
import rpgElements.Explore;
import rpgElements.CharacterBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
/*
 * David cruz 
 * 7/22/2023
 * */

public class SetExploreLevelPane extends JRPGFXPane implements Refresh{
	private Label currentSelectionTxt = new Label("");
	private int currentDifficulty = 1;
	private HBox partyBox = new HBox(15);
	private Player player;
	private Explore explore;
	
	private final int DIFFICULTY_STEP = 10;
	
	public SetExploreLevelPane(SceneController sceneController, Player player, Explore explore) {
		super(sceneController, "set Your expedition", (e) -> sceneController.setPane("main menu"));
		this.player = player;
		this.explore = explore;
		
		VBox explorePanel = new VBox(15);
		explorePanel.setAlignment(Pos.CENTER);
		HBox controlPanel = new HBox(15);
		controlPanel.setAlignment(Pos.CENTER);
		Button startExplore = new Button("Explore");
		explorePanel.getChildren().addAll(new Label("Set your difficulty."), controlPanel, startExplore);
		setCenter(explorePanel);
		
		// spin box for difficulty
		Button easierBtn = new Button("<");
		Button harderBtn = new Button(">");
		controlPanel.getChildren().addAll(easierBtn, currentSelectionTxt, harderBtn);
		
		// button actions
		easierBtn.setOnAction((e) -> {
			currentDifficulty -= DIFFICULTY_STEP;
			if (currentDifficulty < 1)
				currentDifficulty = 1;
			currentSelectionTxt.setText(String.format("%d - %d", currentDifficulty, currentDifficulty + 9));
		});
		harderBtn.setOnAction((e) -> {
			currentDifficulty += DIFFICULTY_STEP;
			if (currentDifficulty > CharacterBase.MAX_LEVEL)
				currentDifficulty = CharacterBase.MAX_LEVEL - 9;
			
			else if (currentDifficulty > explore.getMaxDifficulty())
				currentDifficulty = explore.getMaxDifficulty() - 9;
			
			currentSelectionTxt.setText(String.format("%d - %d", currentDifficulty, currentDifficulty + 9));
		});
		startExplore.setOnAction((e) -> {
			player.fullHealParty();
			explore.setExploreDifficulty(currentDifficulty - 1);
			explore.resetFloors();
			sceneController.setPane("floor");
			});
		
		// display your party
		VBox partyPanel = new VBox(15);
		partyPanel.getChildren().addAll(new Label("Your Party"), partyBox);
		partyPanel.setAlignment(Pos.CENTER);
		partyBox.setAlignment(Pos.CENTER);
		setBottom(partyPanel);
		
	}

	@Override
	public void refresh() {
		currentDifficulty = explore.getMaxDifficulty() - 9;
		currentSelectionTxt.setText(String.format("%d - %d", currentDifficulty, currentDifficulty + 9));
		
		// add party members to party box
		partyBox.getChildren().clear();
		for (CharacterBase character: player.getPartyAsList()) {
			String name = character.getName();
			int max_length = (name.length() < 6) ? name.length() : 6;
			Label characterLabel = new Label(String.format("%c\n%s", character.getIcon(), name.substring(0, max_length)));
			partyBox.getChildren().add(characterLabel);
		}
		
	}

}
