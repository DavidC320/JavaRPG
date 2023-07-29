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
		this.player = player;
		this.explore = explore;
		
		setTop(resultTitleText);
		
		HBox controlPanel = new HBox(15);
		controlPanel.setAlignment(Pos.CENTER);
		setCenter(controlPanel);
		
		Button tryAgainBtn = new Button("try again");
		Button backToExplore = new Button("back to set explore");
		
		controlPanel.getChildren().setAll(tryAgainBtn, backToExplore);
		
		tryAgainBtn.setOnAction((e) -> {
			explore.resetFloors();
			sceneController.setPane("floor");
		});
		backToExplore.setOnAction((e) -> sceneController.setPane("set explore"));
		
		// display your party
		VBox partyPanel = new VBox(15);
		partyPanel.setAlignment(Pos.CENTER);
		partyBox.setAlignment(Pos.CENTER);
		setBottom(partyPanel);
		
		partyPanel.getChildren().addAll(new Label("Your Party"), partyBox);
	}

	@Override
	public void refresh() {
		// get type of result
		player.fullHealParty();
		
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
		
		partyBox.getChildren().clear();
		for (CharacterBase character: player.getPartyAsList()) {
			explore.setDamageDealt(0);
			explore.setDefeatedEnemeis(0);
			
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
