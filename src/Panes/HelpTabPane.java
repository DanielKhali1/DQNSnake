package Panes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;




 public class HelpTabPane extends Pane {
	
	public void finalize() throws Throwable{
		super.finalize();
	}
	Scene scene;
	
	public HelpTabPane (double width, double height)
	{

		Button help = new Button (" ? ");
		help.setStyle("-fx-background-color: white");
		help.setLayoutX(getPrefWidth()*0.85);
		help.setLayoutY(getPrefHeight()*.1	);
		getChildren().add(help);
		help.setStyle("-fx-font-size: 20;");
		
	  help.setOnAction(ex-> {
		  
	final String text =  " To move your Snek use your arrow keys/nClick 'Upload Net' to upload a .nn file and click 'Save'/nTo create a network click 'Create Network\n"
			+ "Click on the 'Static AI' button then click on 'Start Game' button down below, If the snake dies the game will automatically restart and the iteration count\nwill go up by 1\n"
			+ "The Snek gains points by eating the apples, It loses point by hitting the wall.\nThe points can be tracked in the display panel\nThe 'DQN' button Sets the agent to run the DQN Algorithm ";
	     
	Alert popup = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
	
	popup.setHeaderText("Need Help?");
	popup.setTitle("Help");
	popup.showAndWait();
	
	
			});  
	  }	
}