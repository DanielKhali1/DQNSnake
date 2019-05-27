package Panes;

import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

/**
 * @author Danny
 * @version 1.0
 * @created 17-Feb-2019 5:39:58 PM
 */
public class DisplayPane extends Pane {

	Text Iteration = new Text();
	Text score = new Text();
	Text highscore = new Text();
	public TextArea Console = new TextArea();
	
	int highscoreINT = 0;
	
	public void finalize() throws Throwable {
		super.finalize();
	}

	public void setIteration(String Iter)
	{
		Iteration.setText(Iter);
	}

	public void setScore(String scr)
	{
		score.setText(scr);
	}
	
	public void appendConsole(String text)
	{
		Console.setText(Console.getText()+"\n"+text);
	}
	
	public void setHighScore(int scr) {
    	if(highscoreINT < scr)
    	{
    		highscoreINT++;
    		String highscoreString = Integer.toString(highscoreINT);
    		highscore.setText(highscoreString);
    	}
	}
	
	

	public DisplayPane(double width, double height)
	{

		setPrefSize(width * 0.75, height * 0.25);

		//The top left corner of this pane is at (width * 0.25, height * 0.75)
		setLayoutX(0);
		//setLayoutX(width * 0.25);
		setLayoutY(height * 0.75);
		setStyle("-fx-background-color: '#4f4f4f';");

		Pane content = new Pane();
		content.setPrefSize(getPrefWidth()*0.98, getPrefHeight()*0.90);
		content.setLayoutX(getPrefWidth()*0.009);
		content.setLayoutY(getPrefHeight()*0.05);
		content.setStyle("-fx-background-color: '#a5a5a5'");
		getChildren().add(content);

		Text Title = new Text("Display Panel");
		Title.setStyle("-fx-font-size: 20;");
		Title.setLayoutX(content.getPrefWidth()*0.01);
		Title.setLayoutY(content.getPrefHeight()*0.15);

		Pane iterationPane = new Pane();
		iterationPane.setPrefSize(getPrefWidth()*0.15, getPrefHeight()*0.6);
		iterationPane.setLayoutX(getPrefWidth()*0.01);
		iterationPane.setLayoutY(getPrefHeight()*0.2);
		iterationPane.setStyle("-fx-background-color: '#a5a5a5'");

		Text iterationTitle = new Text("Iteration");
		iterationTitle.setStyle("-fx-font-size: 20;");
		iterationTitle.setLayoutX(iterationPane.getPrefWidth()*0.01);
		iterationTitle.setLayoutY(iterationPane.getPrefHeight()*0.15);

		Iteration.setLayoutX(iterationPane.getPrefWidth()*0.25);
		Iteration.setLayoutY(iterationPane.getPrefHeight()*0.6);
		Iteration.setFont(Font.font(35));

		iterationPane.getChildren().addAll(iterationTitle, Iteration);


			Pane scorePane = new Pane();
			scorePane.setPrefSize(getPrefWidth()*0.15, getPrefHeight()*0.6);
			scorePane.setLayoutX(getPrefWidth()*0.15);
			scorePane.setLayoutY(getPrefHeight()*0.2);
			scorePane.setStyle("-fx-background-color: '#a5a5a5'");

			Text scoreTitle = new Text("Score");
			scoreTitle.setStyle("-fx-font-size: 20;");
			scoreTitle.setLayoutX(scorePane.getPrefWidth()*0.01);
			scoreTitle.setLayoutY(scorePane.getPrefHeight()*0.15);

			score.setLayoutX(scorePane.getPrefWidth()*0.25);
			score.setLayoutY(scorePane.getPrefHeight()*0.6);
			score.setFont(Font.font(35));

			scorePane.getChildren().addAll(scoreTitle, score);
			
				Pane highscorePane = new Pane();
				highscorePane.setPrefSize(getPrefWidth()*0.15, getPrefHeight()*0.6);
				highscorePane.setLayoutX(getPrefWidth()*0.25);
				highscorePane.setLayoutY(getPrefHeight()*0.2);
				highscorePane.setStyle("-fx-background-color: '#a5a5a5'");
				
				Text highscoreTitle = new Text("HighScore");
				highscoreTitle.setStyle("-fx-font-size: 20;");
				highscoreTitle.setLayoutX(highscorePane.getPrefWidth()*0.01);
				highscoreTitle.setLayoutY(highscorePane.getPrefHeight()*0.15);

				highscore.setLayoutX(highscorePane.getPrefWidth()*0.25);
				highscore.setLayoutY(highscorePane.getPrefHeight()*0.6);
				highscore.setFont(Font.font(35));

					highscorePane.getChildren().addAll(highscoreTitle, highscore);
					
					
		Console = new TextArea();
		Console.setLayoutX(getPrefWidth()*0.45);
		Console.setLayoutY(getPrefHeight()*0.05);
		Console.setPrefHeight(getPrefHeight()*.75);
		//Console.setDisable(true);
		Console.setStyle("-fx-opacity: 1; -fx-text-fill: 'black';");
		Console.setText("No Loaded Network");

		content.getChildren().addAll(Title, iterationPane, scorePane, highscorePane, Console);






	}
}//end DisplayPane
