package jFX;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/*
 * 7/29/2023
 * David Cruz
 */

/** a default abstract class that incorporates the basic needs of most panes used by the sceneCotnroller */
public abstract class JRPGFXPane extends BorderPane{
	private SceneController sceneController;
	private Button backBtn  = new Button("Back");
	
	/** Create a page with a page Title */
	public JRPGFXPane(SceneController sceneController, String pageTitle) {
		setUpPane(sceneController, pageTitle);
	}
	
	/** Create a page with a page Title and alternate button text*/
	public JRPGFXPane(SceneController sceneController, String pageTitle, String buttonText) {
		setUpPane(sceneController, pageTitle);
		backBtn.setText(buttonText);
	}
	
	/** Create a page with a page Title and an action event*/
	public JRPGFXPane(SceneController sceneController, String pageTitle, EventHandler<ActionEvent> object) {
		setUpPane(sceneController, pageTitle);
		this.setBackButtonOnAction(object);
	}
	
	/** Create a page with a page Title, alternate button text, and an action event*/
	public JRPGFXPane(SceneController sceneController, String pageTitle, String buttonText, EventHandler<ActionEvent> object) {
		setUpPane(sceneController, pageTitle);
		this.setBackButtonOnAction(object);
		backBtn.setText(buttonText);
	}
	
	/** sets up the pane */
	private void setUpPane(SceneController sceneController, String pageTitle) {
		setSceneController(sceneController);
		
		this.setStyle("-fx-background-color: #a7a88c");
		
		// title bar
		HBox titleBar = new HBox(15);
		titleBar.setStyle("-fx-background-color: #bdbf9b");
		setTop(titleBar);
		Label pageName = new Label(pageTitle);
		backBtn.setAlignment(Pos.CENTER_LEFT);
		
		// add to title bar
		titleBar.getChildren().addAll(backBtn, pageName);
		
		// actions
		backBtn.setOnAction((e) -> sceneController.setPane("title"));
	}
	/** gets the scene controller */
	public SceneController getSceneController() {
		return sceneController;
	}
	
	/** sets the scene controller */
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	/** set the back button's action*/
	private void setBackButtonOnAction(EventHandler<ActionEvent> object) {
		this.backBtn.setOnAction(object);
	}

}
