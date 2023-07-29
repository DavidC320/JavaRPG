package jFX;
import rpgElements.Player;
import rpgElements.Explore;
import rpgElements.CharacterBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
/*
 * David cruz 
 * 7/22/2023
 * */

public class ExploreResultsPane extends BorderPane implements Refresh{
	private Label resultTitleText = new Label();
	private HBox partyBox = new HBox(15);
	private Player player;
	private Explore explore;
	
	public ExploreResultsPane(SceneController sceneController, Player player, Explore explore) {
		this.setStyle("-fx-background-color: #c5c7a3");
		this.player = player;
		this.explore = explore;
		
		setTop(resultTitleText);
		
		HBox controlPanel = new HBox(15);
		controlPanel.setAlignment(Pos.CENTER);
		setCenter(controlPanel);
		
		Button tryAgainBtn = new Button("explore agian");
		Button backToExplore = new Button("make a new expedition.");
		Button toCollection = new Button("Go to your collection\nand set a new team");
		
		controlPanel.getChildren().setAll(tryAgainBtn, backToExplore, toCollection);
		
		tryAgainBtn.setOnAction((e) -> {
			explore.resetFloors();
			sceneController.setPane("floor");
		});
		backToExplore.setOnAction((e) -> sceneController.setPane("set explore"));
		
		toCollection.setOnAction(e -> sceneController.setPane("collection"));
		
		// display your party
		VBox partyPanel = new VBox(15);
		partyPanel.setAlignment(Pos.CENTER);
		partyBox.setAlignment(Pos.CENTER);
		setBottom(partyPanel);
		
		partyPanel.getChildren().addAll(new Label("Your Party"), partyBox);
	}

	@Override
	public void refresh() {
		// add party members to party box and give then xp and enemy defeats
		explore.ditributeXPAndEnemies(player.getPartyAsList());


		// get type of result
		System.out.print(explore.getCurrentFloorDifficualty() + " " + explore.getMaxDifficulty());
		if (explore.getCurrentFloorDifficualty() == CharacterBase.MAX_LEVEL)
			resultTitleText.setText("You completed the hardest Difficulty!\nThank you for playing.");
		
		else if (explore.getCurrentFloorDifficualty() == explore.getMaxDifficulty()) {
			explore.increaseMaxDifficulty();
			resultTitleText.setText("You completed this exploration!\nYou also unlocked a new tier of difficulty");
		}
		
		else if (explore.getCurrentFloor() == Explore.MAX_FLOORS)
			resultTitleText.setText("You completed this exploration!");
		
		else if (!player.hasLivingPartyMembers()) {
			resultTitleText.setText("Your party has fainted...\nThere fine by the way.");
			}
		else
			resultTitleText.setText("A tacticle retreat!\nMight have lost some stuff but it is better safe than sorry.");
		

		player.fullHealParty();
		
		partyBox.getChildren().clear();
		for (CharacterBase character: player.getPartyAsList()) {
			
			String name = character.getName();
			int max_length = (name.length() < 6) ? name.length() : 6;
			Label characterLabel = new Label(String.format("%c\n%s\nLV %d: %d / %d\nRK %d: %d / %d", 
					character.getIcon(), name.substring(0, max_length), 
					character.getLevel(), character.getExperience(), character.getExperienceThreshold(),
					character.getRank(), character.getDefeatedEnemies(), character.getRankThreshold()));
			partyBox.getChildren().add(characterLabel);
		}
		
	}

}
