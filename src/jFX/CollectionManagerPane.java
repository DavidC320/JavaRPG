package jFX;
import rpgElements.Player;
import rpgElements.CharacterBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
/* 
 * David Cruz
 * 7/22/2023
 * */

public class CollectionManagerPane extends BorderPane implements Refresh{
	private VBox characterColumnVBox = new VBox(15);
	private Label currentPageTxt = new Label("");
	
	//character selected from list
	private Label selectedCharacterTxt = new Label("");
	private Label selectedPartyTxt = new Label("Current selected party slot: None. click one of the party slots\nto start assinging characters");
	private PartyButton[] partyButtonArray = {
			new PartyButton(0, ""), new PartyButton(1, ""), 
			new PartyButton(2, ""), new PartyButton(3, "")};
	
	//Party managing
	private int selectedPartyIndex = -1;
	
	private int currentPage = 0;
	private int characterMaxPage = 0;
	static private final int MAX_DISPLAYED_CHARACTERS = 6;
	private Player player;
	
	public CollectionManagerPane(SceneController sceneController, Player player) {
		this.player = player;
		// clicking characters panel
		BorderPane controlPanel = new BorderPane();
		setLeft(controlPanel);
		
		characterColumnVBox.setAlignment(Pos.TOP_LEFT);
		characterColumnVBox.setPadding(new Insets(5, 0, 5, 0));
		controlPanel.setCenter(characterColumnVBox);
		
		
		// page controls
		BorderPane pageControls = new BorderPane();
		controlPanel.setTop(pageControls);
		
		Button lastPage = new Button("<");
		Button nextPage = new Button(">");
		
		Label titleLb = new Label("Collection Manager");
		BorderPane.setAlignment(titleLb, Pos.CENTER);
		pageControls.setTop(titleLb);
		pageControls.setLeft(lastPage);
		pageControls.setCenter(currentPageTxt);
		pageControls.setRight(nextPage);
		
		lastPage.setOnAction((e) -> updatePageNumber(-1));
		nextPage.setOnAction((e) -> updatePageNumber(1));
		
		// display more data on selected character.
		BorderPane characterPanel = new BorderPane();
		BorderPane.setAlignment(selectedCharacterTxt, Pos.TOP_LEFT);
		characterPanel.setLeft(selectedCharacterTxt);
		setRight(characterPanel);
		
		// party changing
		GridPane partyControls = new GridPane();
		partyControls.add(new Label("Party Manager"), 0, 0);
		partyControls.add(selectedPartyTxt, 0, 1);
		
		
		HBox partyButtons = new HBox(15);
		partyButtons.getChildren().addAll(partyButtonArray);
		partyControls.add(partyButtons, 0, 2);
		Button backBtn = new Button("Back");
		partyControls.add(backBtn, 0, 3);
		controlPanel.setBottom(partyControls);
		
		backBtn.setOnAction((e) -> sceneController.setPane("main menu"));
		
	}

	@Override
	public void refresh() {
		if (player.getCollection().size() <= 0) {
			currentPageTxt.setText("Don't have any characters.");
			return;
		}
		currentPage = 0;
		characterMaxPage = (int)Math.ceil(player.getCollection().size() / MAX_DISPLAYED_CHARACTERS);
		
		selectedPartyIndex = -1;
		updatePageNumberText();
		updateCharacterList();
		updateParty();
		
	}
	
	private void updateCharacterList() {
		characterColumnVBox.getChildren().clear();
		
		// checking if the displaying amount is over the actual number of Characters
		int startingPoint = currentPage * MAX_DISPLAYED_CHARACTERS;
		int displayingCharactersCheck = (currentPage + 1) * MAX_DISPLAYED_CHARACTERS;
		
		int numberDisplayed;
		if (displayingCharactersCheck > player.getCollection().size())
			numberDisplayed = player.getCollection().size() - startingPoint;
		else
			numberDisplayed = MAX_DISPLAYED_CHARACTERS;
		
		// System.out.printf("\n%d, %d, %d\n", displayingCharactersCheck, numberDisplayed, startingPoint);
		
		for (int num = 0; num < numberDisplayed; num++) {
			int index = num + startingPoint;
			// System.out.println(num);
			
			String characterConcise = player.getCollection().get(index).toConsiseString();
			characterColumnVBox.getChildren().add(new CharacterButton(index, characterConcise));
		}
		
	}
	
	private void updatePageNumberText() {
		currentPageTxt.setText(String.format("%d / %d", currentPage, characterMaxPage));
	}
	
	private void updatePageNumber(int offset) {
		currentPage += offset * 1;
		if (currentPage > characterMaxPage)
			currentPage = 0;
		else if (currentPage < 0)
			currentPage = characterMaxPage;
		updatePageNumberText();
		updateCharacterList();
	}
	
	private void updateParty() {
		int[] party = player.getParty();
		//System.out.println(party.toString());
		
		for (int num = 0; num < 4; num++) {
			//System.out.print(party[num]+ " ");
			if (party[num] >= 0) {
				PartyButton button = partyButtonArray[num];
				CharacterBase character = player.getCharacterFromCollection(party[num]);
				// 7 5 4 for max amount of characters in a string. 
				// from https://stackoverflow.com/questions/8369708/limiting-the-number-of-characters-in-a-string-and-chopping-off-the-rest
				String name = character.getName();
				int max_length = (name.length() < 6) ? name.length() : 6;
				
				button.setText(String.format("%c\n%s", character.getIcon(), name.substring(0, max_length)));
			}
			else {
				PartyButton button = partyButtonArray[num];
				button.setText(" Empty \n ");
			}
		}
		//System.out.println();
	}
	
	public class CharacterButton extends Button{
		
		public CharacterButton(int index, String text) {
			this.setText(text);
			
			this.setOnAction((e) -> {
				String charactertxt = player.getCollection().get(index).toString();
				selectedCharacterTxt.setText(charactertxt);
				
				// changing party members
				if (selectedPartyIndex >= 0) {
					// System.out.println("update party " + index);
					player.changePartyIndex(selectedPartyIndex, index);
					updateParty();
				}
			});
		}
	}
	
	public class PartyButton extends Button{
		public PartyButton(int partyIndex, String text) {
			this.setText(text);
			
			this.setOnAction((e) -> {
				// checks if the button click is the same. this empties the slot
				if (partyIndex == selectedPartyIndex) {
					player.changePartyIndex(partyIndex, -1);
					selectedPartyIndex = -1;
					selectedPartyTxt.setText("Current selected party slot: None. click one of the party slots\nto start assinging characters");
					updateParty();
				}
				// checks if the user clicks a different button. sets the new selected party index to the new button
				else {
					selectedPartyIndex = partyIndex;
					selectedPartyTxt.setText("Current selected party slot: " + partyIndex + ". select a party member to assign to this slot.\nClick the slot again to clear it.");
				}
				
			});
		}
	}
}
