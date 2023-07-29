package jFX;
import rpgElements.Player;
import rpgElements.Explore;
import rpgElements.Explore.NoPartyMembers;
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

public class CombatPane extends BorderPane implements Refresh{
	private VBox playerPartyBox = new VBox(15);
	private VBox enemyPartyBox = new VBox(15);
	private Player player;
	private Explore explore;
	
	public CombatPane(SceneController sceneController, Player player, Explore explore) {
		this.setStyle("-fx-background-color: #7e8069");
		this.player = player;
		this.explore = explore;
		// display player
		setLeft(playerPartyBox);
		playerPartyBox.setStyle("-fx-background-color: #89a888");
		
		// display enemy
		setRight(enemyPartyBox);
		enemyPartyBox.setStyle("-fx-background-color: #a8888a");
		
		// Controls
		HBox controlPanel = new HBox(15);
		controlPanel.setAlignment(Pos.CENTER);
		setCenter(controlPanel);
		
		Button runBtn = new Button("Run!");
		Button fightBtn = new Button("Fight");
		
		controlPanel.getChildren().setAll(runBtn, fightBtn);
		
		runBtn.setOnAction((e) -> sceneController.setPane("explore results"));
		
		fightBtn.setOnAction((e) -> {
			try {
				int result = explore.fight();
				if (result == -1) {
					sceneController.setPane("explore results");
				}
				else if (result == 1) {
					sceneController.setPane("fight results");
				}
				
				updateTeams();
				
				
			} catch (NoPartyMembers e1) {
				// TODO Auto-generated catch block
				sceneController.setPane("floor");
				e1.printStackTrace();
			}
		});
		
	}
	
	public void updateTeams() {
		playerPartyBox.getChildren().clear();
		for (CharacterBase character: player.getPartyAsList()) {
			Label characterLabel = new Label(String.format("%c LV: %d\nHp: %d / %d", character.getIcon(), character.getLevel(), character.getCurrentHealht(), character.getMaxHealth()));
			playerPartyBox.getChildren().add(characterLabel);
		}
		
		enemyPartyBox.getChildren().clear();
		for (CharacterBase character: explore.getEnemyteam()) {
			Label characterLabel = new Label(String.format("%c LV: %d\nHp: %d / %d", character.getIcon(), character.getLevel(), character.getCurrentHealht(), character.getMaxHealth()));
			enemyPartyBox.getChildren().add(characterLabel);
		}
	}

	@Override
	public void refresh() {
		updateTeams();
		
	}

}
