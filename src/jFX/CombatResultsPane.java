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

public class CombatResultsPane extends BorderPane implements Refresh {
	private Label infoText = new Label();
	private HBox partyBox = new HBox(15);
	private Player player;
	private Explore explore;
	
	public CombatResultsPane(SceneController sceneController, Player player, Explore explore) {
		this.setStyle("-fx-background-color: #c5c7a3");
		this.player = player;
		this.explore = explore;
		
		VBox resultPane = new VBox(15);
		resultPane.setAlignment(Pos.CENTER);
		setCenter(resultPane);
		
		Button exploreBtn = new Button("Explore");
		resultPane.getChildren().setAll(new Label("You won the fight, Here are the result"), infoText, exploreBtn);
		
		
		
		// display your party
		VBox partyPanel = new VBox(15);
		partyPanel.setAlignment(Pos.CENTER);
		partyBox.setAlignment(Pos.CENTER);
		setBottom(partyPanel);
		
		partyPanel.getChildren().addAll(new Label("Your Party"), partyBox);
		
		exploreBtn.setOnAction((e) -> sceneController.setPane("floor"));
	}

	@Override
	public void refresh() {
		player.fullHealParty();
		
		int expericeFound = (int) Math.ceil(explore.getDamageDealt() / 2 / player.getPartyAsList().size());
		int enemiesDefeated = explore.getEnemiesDefeated();
		infoText.setText(String.format("You made it to floor %d\nEach Member gains\nExperience: %d\nDefeated Enemies: %d", explore.getCurrentFloorDifficualty(), expericeFound, enemiesDefeated));
		
		// add party members to party box and give then xp and enemy defeats
		explore.ditributeXPAndEnemies(player.getPartyAsList());
		
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
