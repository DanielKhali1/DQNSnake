package Panes;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;

public class MasterPane extends Pane
{
	double width;
	double height;
	
	public MasterPane(double width, double height)
	{
		this.width = width;
		this.height = height;
		
		Pane controlPane = new ControlPane(width, height);
		//Pane helpTabPane = new HelpTabPane(width, height);
		
		getChildren().add(controlPane);
		helpButton();

		//getChildren().add(helpTabPane);
	}
	
	public void helpButton()
	{
		Button help = new Button (" ? ");
		help.setStyle("-fx-background-color: white");
		help.setLayoutX(width*0.85);
		help.setLayoutY(height*.1	);
		getChildren().add(help);
		help.setStyle("-fx-font-size: 20;");
		
		help.setOnAction(ex-> {
		  
		  final String text =  " To move your Snek use your arrow keys\n\nClick 'Upload Net' to upload a .nn file and click 'Save'\n\nTo create a network click 'Create Network\n\n"
			+ "Click on the 'Static AI' button then click on 'Start Game' button down below, If the snake dies the game will automatically restart and the iteration count\n\nwill go up by 1\n\n"
			+ "The Snek gains points by eating the apples, It loses point by hitting the wall.\n\nThe points can be tracked in the display panel\nThe 'DQN' button Sets the agent to run the DQN Algorithm ";
	     
		Alert popup = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
	
		popup.setHeaderText("Need Help?");
		popup.setTitle("Help");
		popup.showAndWait();
	  });
	}

}
