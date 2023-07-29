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

public class ExploreFloorPane extends JRPGFXPane implements Refresh{
	private Label currentFloorText = new Label("");
	private HBox partyBox = new HBox(15);
	private Player player;
	private Explore explore;
	private SceneController sceneController;
	
	public ExploreFloorPane(SceneController sceneController, Player player, Explore explore) {
		super(sceneController, "Explore Floor", "End Expedition");
		this.sceneController = sceneController;
		this.player = player;
		this.explore = explore;
		
		// show current floor and controls
		VBox floorPanel = new VBox(15);
		floorPanel.setAlignment(Pos.CENTER);
		Button exploreButton = new Button("Explore floor");
		setCenter(floorPanel);
		floorPanel.getChildren().setAll(currentFloorText, exploreButton);
		
		// display your party
		VBox partyPanel = new VBox(15);
		partyPanel.getChildren().addAll(new Label("Your Party"), partyBox);
		partyPanel.setAlignment(Pos.CENTER);
		partyBox.setAlignment(Pos.CENTER);
		setBottom(partyPanel);
		
		exploreButton.setOnAction((e) -> updateFloor());
	}
	
	public void updateFloor() {
		explore.incrementFloor();
		if (explore.getCurrentFloor() >= Explore.MAX_FLOORS) {
			sceneController.setPane("explore results");
			return;
		}
		
		int event = explore.randomEvent();
		if (event == 1) {
			sceneController.setPane("new character");
		}
		else if (event == 2) {
			if (explore.getCurrentFloor() == Explore.MAX_FLOORS - 1  && 
					explore.getCurrentFloorDifficualty() == CharacterBase.MAX_LEVEL) {
				explore.generateBossTeam();
			}
			else if (explore.getCurrentFloor() == Explore.MAX_FLOORS - 1) {
				explore.generateBossTeam();
			}
			else
				explore.generateEnemyTeam();
			
			sceneController.setPane("fight");
		}
		String data = String.format("CurrentFloor\n%d / %d\nDifficulty: %d", explore.getCurrentFloor(), Explore.MAX_FLOORS, explore.getCurrentFloorDifficualty());
		currentFloorText.setText(data);
			
	}

	@Override
	public void refresh() {
		String data = String.format("CurrentFloor\n%d / %d\nDifficulty: %d", explore.getCurrentFloor(), Explore.MAX_FLOORS, explore.getCurrentFloorDifficualty());
		currentFloorText.setText(data);
		
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
