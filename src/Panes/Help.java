package Panes;

import java.io.File;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
 
import javafx.stage.Stage;

/**
 * @author DannyIsTheBomb.com
 * @version 1.0
 * @created 17-Feb-2019 5:39:58 PM
 */

public class Help extends Pane {
 

 	public void finalize() throws Throwable {
			super.finalize();
		}

		public Help (double width, double height)
		{

			Pane gamePane = new GamePane(width, height);
			getChildren().add(gamePane);
			
	Scene scene;	

   // gamePane.getTabs().add(tabA);
   
     
    //Create Tab
     Tab tabC = new Tab();
     tabC.setText("Help");
     //Add something in Tab
     VBox tabC_vBox = new VBox();
     Button ButtonLeft = new Button("Help");

   
     tabC_vBox.getChildren().addAll(
             ButtonLeft);
     tabC.setContent(tabC_vBox);
  
   
   

 }
 
}