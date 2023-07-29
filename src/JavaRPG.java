import rpgElements.*;
import jFX.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaRPG extends Application{
	private Player player = new Player();
	private Explore explore = new Explore(player);
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Java RPG");
		SceneController sceneController = new SceneController(primaryStage, player, explore);
		
		// set the inital pane
		sceneController.setPane("title");
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
