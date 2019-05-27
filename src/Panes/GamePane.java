package Panes;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Agent.SnakeBrain;
import Agent.SnakeDQN;
import NeuralNetwork.NeuralNetwork;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.concurrent.Task;

/**
 * @author Danny, Paul, Yara
 * @version 1.0
 * @created 17-Feb-2019 5:39:59 PM
 */

public class GamePane extends Pane {

	public String iterationString = new String();
	public int iterationNum = 0;

	public String scoreString = new String();
	public int score = 0;

	GridPane gridpane = new GridPane();
	boolean onlyOneDirection = true;
	Rectangle recs[][] = new Rectangle[10][10];
	Scene scene;

    Pane displayPane;
    int iteration = 0;


	SnakeDQN dqn = new SnakeDQN(0.001, 0.995, 10, 10);

	SnakeBrain brainySnek = new SnakeBrain();
	Duration time;


	//The scale of the gridpane size to the gamepane size.
	double scale = 0.9;

	//The scaler for the borders.
	double borderScale = 0.2;

	//The scaler for the gaps.
	double gapScale = 0.05;

	//Color of the Snake
	 Color colorOfSnake = Color.PURPLE;

	// First Timeline for DQN
	 Timeline timeline = new Timeline();

	// Second Timeline for Static AI
	 Timeline timeline2 = new Timeline();
	 
	 KeyFrame keyframe = new KeyFrame(Duration.millis(70));
	 
	 int speed = 20;
	 
	static ArrayList<String> dat = new ArrayList<String>();



	boolean dq = false;
	boolean stat = false;

	public void openExcelSpreadsheet()
	{
		try {
		    Desktop.getDesktop().open(new File("snakeData.csv"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}	

	public void finalize() throws Throwable {
		super.finalize();
	}

	public void setNN(File nnFile) throws Exception
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nnFile));
		SnakeDQN nn = (SnakeDQN)ois.readObject();
		dqn = nn;
	}

	public void setSnek(double newLearningRate, double newDiscountFactor, double epsilonDecay, double hitWall, double ateApple, double idle, double hitSelf)
	{
		dqn = new SnakeDQN(newLearningRate, newDiscountFactor, recs.length, recs[0].length, idle, ateApple, hitWall, hitSelf);

		System.out.println(idle + " " + ateApple + " " + hitWall);

		dqn.setEpsilonDecay(epsilonDecay);
	}

	public void saveDQN(String filePath)
	{
		SnakeDQN.saveSnakeDQN(dqn, filePath);
	}

	public void appendConsoleDisplay(String s)
	{
		((DisplayPane) displayPane).appendConsole(s);
	}

	public void setConsole(String s)
	{
		((DisplayPane) displayPane).Console.setText(s);
	}

	public void trainSnek(int episodes) throws Exception
	{
		dat = new ArrayList<String>();
		File RP = new File("snakeData.csv");
 		BufferedWriter writer = new BufferedWriter(new FileWriter(RP));
		dat.add("Round,Average Score,Max Score");
		
		((DisplayPane) displayPane).appendConsole("\nround\tavgScore\tmaxScore\n");
		for(int round = 0; round < episodes; round++)
		{
			double averageScore = 0;
			int maxScore = 0;
			double averageEpsilon = 0;
			int totalSteps = 0;
			for(int gameIndex = 0; gameIndex < 1000; gameIndex++)
			{
				dqn.reset();

				while(!dqn.isDone())
				{
					totalSteps++;
					averageEpsilon += dqn.getEpsilon();
					dqn.step();
				}

				averageScore += dqn.getScore();

				if(dqn.getScore() > maxScore)
				{
					maxScore = dqn.getScore();
				}
			}

			averageScore /= 1000.0;
			averageEpsilon /= (double)totalSteps;
			//System.out.println(round + "," + averageScore + "," + maxScore + "," + averageEpsilon);
			dat.add(round + "," + averageScore + "," + maxScore + "," + averageEpsilon+"\n");

			
			//for(int i =0; i < dat.size(); i++)
			//{
				try {
					//System.out.println("writing" + (dat.size()));
						writer = new BufferedWriter(new FileWriter(RP, true));
						writer.append(dat.get(dat.size()-1));
						writer.flush();
						writer.close();
					}
				catch(IOException f)
					{
						System.out.println("something didn't work");
					}
			//}

			((DisplayPane) displayPane).appendConsole(round + "\t\t" + averageScore + "\t\t" + maxScore);
		}
		NeuralNetwork.saveNetwork(dqn.getNetwork(), "tempSnake.nn");
	}

	public GamePane(double width, double height)
	{
	    //---------------------------- Set Up ------------------------------- //

		//Create Display Pane
		displayPane = new DisplayPane(width, height);
		getChildren().add(displayPane);


		//dealing with sizing
		setPrefSize(width * 0.75, height * 0.75);

		//The top left corner of this pane is at (width * 0.25, 0)
		setLayoutX(width * 0.25);
		setStyle("-fx-background-color: '#6d6d6d';");

		//Sets up Grid Pane
		setUpGridPane();

	    // generate random Objective Item



	    //---------------------------- GAME LOOPS ---------------------------------------------------------------------- //

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(KeyFrame1Content(Duration.millis(70)));
		

		//---------------------------- GAME IMPLEMENTATION 2 ------------------------------- //
		timeline2.setCycleCount(Timeline.INDEFINITE);
		timeline2.getKeyFrames().add(KeyFrame2Content(Duration.millis(70)));
		
		time = keyframe.getTime();

	getChildren().addAll(gridpane);

	}

	//Color of the Snake
	public void colorOfSnake(double red, double green, double blue)
	{
		colorOfSnake = Color.color(red, green, blue);
	}
	
	public KeyFrame KeyFrame2Content(Duration x)
	{
		KeyFrame keyframe2 = new KeyFrame(x, action ->
		{
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Boolean Value that Determines whether you can go back on top of yourself
			onlyOneDirection = true;

			//clears the display grid
			ClearGrid();
			
			brainySnek.UpdateGrid();

			//makes decision
			brainySnek.MakeDecision();

			//executes action
			brainySnek.ExecuteAction();

			//makes a grid for brainySnake
			UpdateGrid2();
			
			if(brainySnek.isDead())
			{
				iteration++;
				brainySnek.reset();
			}
			
			//adds to score if snake eats objective item
		    ((DisplayPane) displayPane).setScore(brainySnek.getScore()+"");

		    //adds to highscore if the int score is greater than int highscore.
		    ((DisplayPane) displayPane).setHighScore(brainySnek.getScore());

		    ((DisplayPane) displayPane).setIteration(iteration+"");
		    
		    //---------------------------- AI Integration ------------------------------- //


		});
		
		return keyframe2;
	}
	
	
	public KeyFrame KeyFrame1Content(Duration x)
	{
		KeyFrame keyframe = new KeyFrame(x, action ->
		{
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Boolean Value that Determines whether you can go back on top of yourself
			onlyOneDirection = true;

		//---------------------------- GAME IMPLEMENTATION ------------------------------- //
			ClearGrid();

			dqn.step();
			dqn.UpdateGrid();

			UpdateGrid();

			if(dqn.isDead())
			{
				iteration++;
				dqn.reset();
			}

			//adds to score if snake eats objective item
		    ((DisplayPane) displayPane).setScore(dqn.getScore()+"");

		    //adds to highscore if the int score is greater than int highscore.
		    ((DisplayPane) displayPane).setHighScore(dqn.getScore());


		    ((DisplayPane) displayPane).setIteration(iteration+"");


		});
		
		return keyframe;
	}
	
	
	public void slower()
	{
		Duration slower = new Duration(10);
		
		time = time.add(slower);
		
		KeyFrame tempKeyFrame = KeyFrame1Content(time);
		//KeyFrame tempKeyFrame2 = KeyFrame2Content(time);
		
		timeline.getKeyFrames().add(tempKeyFrame);
		timeline.stop();
		timeline.getKeyFrames().remove(0);
		timeline.play();
		
		
		/*
		timeline2.getKeyFrames().add(tempKeyFrame2);
		timeline2.stop();
		timeline2.getKeyFrames().remove(0);
		timeline2.play();
		*/
	}
	
	public void faster()
	{
		Duration faster = new Duration(5);
		
		time = time.subtract(faster);
		
		KeyFrame tempKeyFrame = KeyFrame1Content(time);
		//KeyFrame tempKeyFrame2 = KeyFrame2Content(time);
		
		timeline.getKeyFrames().add(tempKeyFrame);
		timeline.stop();
		timeline.getKeyFrames().remove(0);
		timeline.play();
		
		
		/*
		timeline2.getKeyFrames().add(tempKeyFrame2);
		timeline2.stop();
		timeline2.getKeyFrames().remove(0);
		timeline2.play();
		*/
	}


	public void UpdateGrid()
	{
		for(int i = 0; i < recs.length; i++)
		{
			for( int j = 0; j < recs[0].length; j++)
			{
				if(dqn.Grid[i][j] == .5)
					recs[i][j].setFill(Color.RED);
				if(dqn.Grid[i][j] == 1)
					recs[i][j].setFill(colorOfSnake);
			}
		}
	}

	public void UpdateGrid2()
	{
		for(int i = 0; i < recs.length; i++)
		{
			for( int j = 0; j < recs[0].length; j++)
			{
				if(brainySnek.Grid[i][j] == .5)
					recs[i][j].setFill(Color.RED);
				if(brainySnek.Grid[i][j] == 1)
					recs[i][j].setFill(colorOfSnake);
			}
		}
	}
	public void setUpGridPane()
	{
		//The size of the gaps.
				double gapSize;

				//The thickness of the borders.
				double topBorder, rightBorder, bottomBorder, leftBorder;

				//The scaler for the borders
				double borderScale = 0.2;

				//The sclae of the gridpane size to the gamepane size.
				double scale = 0.9;

				//The thickness of the borders.
				double gapScale = 0.05;

				//The side length of the boxes.
				double boxSide;

				//The height and width of the gridpane
				double gHeight, gWidth;

				//The scale will change the height, and in return, will also change the width.
				double numerator = getPrefHeight() * scale;
				double denominator = 2*borderScale + recs.length + (recs.length-1)*gapScale;
				boxSide = numerator / denominator;

				topBorder = rightBorder = bottomBorder = leftBorder = boxSide*borderScale;
				gapSize = boxSide*gapScale;

				//Set the borders.
				gridpane.setPadding(new Insets(topBorder, rightBorder, bottomBorder, leftBorder));

				int row=0;
				for(row=0; row < recs.length; row++) {

					int col=0;
					for(col=0; col < recs[row].length; col++) {

						Rectangle rec = new Rectangle();
						rec.setHeight(boxSide);
						rec.setWidth(boxSide);
						rec.setFill(Color.WHITE);

						recs[row][col] = rec;

						gridpane.add(recs[row][col], col, row);
					}
				}

			    gridpane.setHgap(gapSize);
			    gridpane.setVgap(gapSize);

			    //Test to see if gridpane is out of proportions.
			    gWidth = 2*(borderScale*boxSide) + recs[0].length*boxSide +
			    		(recs[0].length - 1)*gapScale*boxSide;

			    gHeight = 2*(borderScale*boxSide) + recs.length*boxSide +
			    		(recs.length - 1)*gapScale*boxSide;

			    double xPos = (getPrefWidth()-gWidth)/2;
			    double yPos = (getPrefHeight()-gHeight)/2;

			    gridpane.relocate(xPos, yPos);
	}


	public void ClearGrid()
	{
		for(int i = 0; i < recs.length; i++)
		{
			for(int j = 0; j < recs[i].length; j++)
			{
				recs[i][j].setFill(Color.WHITE);
			}
		}
	}

	public void ChangeGridSize(int width, int height)
	{
		recs = new Rectangle[40][40];
		setUpGridPane();
	}




	//---------------------------- Getters and Setters for timeline (DQN) ------------------------------- //

	public Color getColor(Color colorOfSnake) {

		return colorOfSnake;
	}

	public void Play()
	{
		timeline.play();
	}

	public void Stop()
	{
		timeline.stop();
	}

	public void reset()
	{
		dqn.reset();
	}



	//---------------------------- Getters and Setters for timeline2 (Static AI) ------------------------------- //
	public void Play2() {
		timeline2.play();

	}

	public void Stop2() {
		timeline2.stop();
	}

	public void reset2() {
		brainySnek.reset();
	}


}//end GamePane
