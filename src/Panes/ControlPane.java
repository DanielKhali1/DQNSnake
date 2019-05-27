package Panes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Agent.SnakeBrain;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author Danny
 * @version 1.0
 * @created 17-Feb-2019 5:39:58 PM
 */

public class ControlPane extends Pane {

	boolean GAChecked = false;

	SnakeBrain brainyBoy = new SnakeBrain();

	boolean whichTimeline = true;

	public void finalize() throws Throwable {
		super.finalize();
	}
	public ControlPane(double width, double height) 
	{

		Pane gamePane = new GamePane(width, height);
		getChildren().add(gamePane);

		setPrefSize(width * 0.25, height);

		//The top left corner of this pane is at (0, 0)
		setLayoutX(0);
		setLayoutY(0);
		setStyle("-fx-background-color: '#4f4f4f';");

		Pane content = new Pane();
		content.setPrefSize(getPrefWidth()*0.95, getPrefHeight()*0.975);
		content.setLayoutX(getPrefWidth()*0.02);
		content.setLayoutY(getPrefHeight()*0.01);
		content.setStyle("-fx-background-color: '#a5a5a5'");

		Text txtAgentPane = new Text("Agent");
		Pane AgentPane = new Pane();
		Pane gridSizePane = new Pane();

		gridSizePane.setStyle("-fx-background-color: '#e0e0e0'");
		gridSizePane.setLayoutX(getPrefWidth()*0.02);
		gridSizePane.setLayoutY(getPrefHeight()*0.6);
		gridSizePane.setPrefHeight(content.getPrefHeight()*.195);
		gridSizePane.setPrefWidth(content.getPrefWidth()*.95);
		content.getChildren().add(gridSizePane);

		Text txtgridSize = new Text("Generate Report");
		txtgridSize.setStyle("-fx-font-size: 18");
		txtgridSize.setLayoutX(gridSizePane.getPrefWidth()*0.02);
		txtgridSize.setLayoutY(gridSizePane.getPrefHeight()*0.15);
		txtgridSize.setDisable(true);
		gridSizePane.getChildren().add(txtgridSize);
		
		Text OpenExcel = new Text("Open CSV");
		OpenExcel.setStyle("-fx-font-size: 15");
		OpenExcel.setLayoutX(gridSizePane.getPrefWidth()*0.02);
		OpenExcel.setLayoutY(gridSizePane.getPrefHeight()*0.40);

		gridSizePane.getChildren().add(OpenExcel);
		
		
		
		
		
		Button openButton = new Button("Open");
		openButton.setLayoutX(gridSizePane.getPrefWidth()*0.02);
		openButton.setLayoutY(gridSizePane.getPrefHeight()*0.50);
		
		gridSizePane.getChildren().add(openButton);
		
		openButton.setOnAction( e->
		{
			((GamePane) gamePane).openExcelSpreadsheet();
		});
		
		Button btCreateNew = new Button("Create Network");
		Button btTrain = new Button("Train");
		Button btUpload = new Button("Upload");


		Pane trainingPane = new Pane();

		trainingPane.setStyle("-fx-background-color: '#e0e0e0'");
		trainingPane.setLayoutX(getPrefWidth()*0.02);
		trainingPane.setLayoutY(getPrefHeight()*0.80);
		trainingPane.setPrefHeight(content.getPrefHeight()*.15);
		trainingPane.setPrefWidth(content.getPrefWidth()*.95);
		content.getChildren().add(trainingPane);

		Button btStop = new Button("STOP");
		Button btStartTraining = new Button("Start Game");
		Button btPlay = new Button("Play");
		Button btPause = new Button("Pause");
		Button btRestart = new Button("Restart");
		
		
		Button btFaster = new Button(">>");
		btFaster.setLayoutX(trainingPane.getPrefWidth()*.6);
		btFaster.setLayoutY(trainingPane.getPrefHeight()*.3);
		btFaster.setDisable(true);
		trainingPane.getChildren().add(btFaster);
		
		btFaster.setOnAction( e ->{
			((GamePane)gamePane).faster();
		});
		
		Button btSlower = new Button("<<");
		btSlower.setLayoutX(trainingPane.getPrefWidth()*.6);
		btSlower.setLayoutY(trainingPane.getPrefHeight()*.6);
		btSlower.setDisable(true);
		trainingPane.getChildren().add(btSlower);
		
		btSlower.setOnAction( e ->{
			((GamePane)gamePane).slower();
		});

		btStartTraining.setDisable(true);

		Text txtTrainingController = new Text("Training Controller");
		txtTrainingController.setStyle("-fx-font-size: 18");
		txtTrainingController.setLayoutX(trainingPane.getPrefWidth()*0.02);
		txtTrainingController.setLayoutY(trainingPane.getPrefHeight()*0.2);
		trainingPane.getChildren().add(txtTrainingController);

		btStop.setLayoutX(trainingPane.getPrefWidth()*.4);
		btStop.setLayoutY(trainingPane.getPrefHeight()*.6);
		trainingPane.getChildren().add(btStop);
		btStop.setDisable(true);

		btStop.setOnAction(e->
		{
			if(whichTimeline) {
				((GamePane) gamePane).ClearGrid();
				((GamePane) gamePane).Stop();
				btPlay.setDisable(true);
				btStartTraining.setDisable(false);
			} else {
				((GamePane) gamePane).Stop2();
				btPlay.setDisable(false);
				btStartTraining.setDisable(true);
			}

			btPause.setDisable(true);
			btRestart.setDisable(true);
			btStop.setDisable(true);
			
			btFaster.setDisable(true);
			btSlower.setDisable(true);
		});

		btStartTraining.setLayoutX(trainingPane.getPrefWidth()*.02);
		btStartTraining.setLayoutY(trainingPane.getPrefHeight()*.6);
		trainingPane.getChildren().add(btStartTraining);

		btStartTraining.setOnAction(e->
		{
			((GamePane) gamePane).reset();
			((GamePane) gamePane).Play();

			btStartTraining.setDisable(true);
			btPause.setDisable(false);
			btRestart.setDisable(false);
			btStop.setDisable(false);
			
			btFaster.setDisable(false);
			btSlower.setDisable(false);

		});

		btPlay.setLayoutX(trainingPane.getPrefWidth()*.02);
		btPlay.setLayoutY(trainingPane.getPrefHeight()*.3);
		trainingPane.getChildren().add(btPlay);
		btPlay.setDisable(true);

		btPlay.setOnAction(e->
		{
			if(whichTimeline) {
				((GamePane) gamePane).Play();
				btStop.setDisable(false);
			}else {
				((GamePane) gamePane).Play2();
				btStop.setDisable(true);
			}

			btPlay.setDisable(true);
			btPause.setDisable(false);
			btRestart.setDisable(false);
			
			btFaster.setDisable(false);
			btSlower.setDisable(false);
		});

		btPause.setLayoutX(trainingPane.getPrefWidth()*.2);
		btPause.setLayoutY(trainingPane.getPrefHeight()*.3);
		trainingPane.getChildren().add(btPause);
		btPause.setDisable(true);

		btPause.setOnAction(e->
		{
			if(whichTimeline)
			{
				((GamePane) gamePane).Stop();
				btStop.setDisable(false);
			}else{
				((GamePane) gamePane).Stop2();
				btStop.setDisable(true);
				 }

			btPause.setDisable(true);
			btPlay.setDisable(false);
			btRestart.setDisable(false);
			btFaster.setDisable(true);
			btSlower.setDisable(true);
		});

		btRestart.setLayoutX(trainingPane.getPrefWidth()*.4);
		btRestart.setLayoutY(trainingPane.getPrefHeight()*.3);
		trainingPane.getChildren().add(btRestart);
		btRestart.setDisable(true);

		btRestart.setOnAction(e->
		{
			btPlay.setDisable(true);
			btPause.setDisable(false);


			if(whichTimeline)
			{
				((GamePane) gamePane).reset();
				((GamePane) gamePane).Play();
				btStop.setDisable(false);
			}else{
				((GamePane) gamePane).reset2();
				((GamePane) gamePane).Play2();
				btStop.setDisable(true);
				 }

		});


		AgentPane.setStyle("-fx-background-color: '#e0e0e0'");
		AgentPane.setLayoutX(getPrefWidth()*0.02);
		AgentPane.setLayoutY(getPrefHeight()*0.10);
		AgentPane.setPrefHeight(content.getPrefHeight()*.50);
		AgentPane.setPrefWidth(content.getPrefWidth()*.95);
		content.getChildren().add(AgentPane);

		Text txtLearningRate = new Text("Learning Rate");
		TextField tfLearningRate = new TextField("0.001");
		Text txtDiscountFactor = new Text("Discount Factor");
		TextField tfDiscountFactor = new TextField("0.995");

		/////////////////////////////////////////////////////

		Text txtEpsilonDecay = new Text("Epsilon Decay");
		TextField tfEpsilonDecay = new TextField("0.99999");

		Text txtRewards = new Text("Rewards:");

		Text txtwallDeath = new Text("Wall Death");
		TextField tfwallDeath = new TextField("-1");

		Text txtSelfDestruct = new Text("Hits Self");
		TextField tfSelfDestruct = new TextField("-1");


		Text txtEatsApple = new Text("Eats Apple");
		TextField tfEatsApple = new TextField("3");

		Text txtIdle = new Text("Idle");
		TextField tfIdle = new TextField("0");




		txtLearningRate.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtLearningRate.setLayoutY(AgentPane.getPrefHeight()*.20);
		txtLearningRate.setStyle("-fx-font-size: 12;");
		tfLearningRate.setLayoutX(AgentPane.getPrefWidth()*.02);
		tfLearningRate.setLayoutY(AgentPane.getPrefHeight()* .21);

		txtDiscountFactor.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtDiscountFactor.setLayoutY(AgentPane.getPrefHeight()*.30);
		txtDiscountFactor.setStyle("-fx-font-size: 12;");
		tfDiscountFactor.setLayoutX(AgentPane.getPrefWidth()*.02);
		tfDiscountFactor.setLayoutY(AgentPane.getPrefHeight()*.31);

		txtEpsilonDecay.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtEpsilonDecay.setLayoutY(AgentPane.getPrefHeight()*.40);
		txtEpsilonDecay.setStyle("-fx-font-size: 12;");
		tfEpsilonDecay.setLayoutX(AgentPane.getPrefWidth()*.02);
		tfEpsilonDecay.setLayoutY(AgentPane.getPrefHeight()* .41);

		txtRewards.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtRewards.setLayoutY(AgentPane.getPrefHeight()*.52);
		txtRewards.setStyle("-fx-font-size: 15;");

		txtwallDeath.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtwallDeath.setLayoutY(AgentPane.getPrefHeight()*.57);
		txtwallDeath.setStyle("-fx-font-size: 12;");
		tfwallDeath.setLayoutX(AgentPane.getPrefWidth()*.02);
		tfwallDeath.setLayoutY(AgentPane.getPrefHeight()*.58);
		tfwallDeath.setPrefWidth(60);

		txtSelfDestruct.setLayoutX(AgentPane.getPrefWidth()*.50);
		txtSelfDestruct.setLayoutY(AgentPane.getPrefHeight()*.57);
		txtSelfDestruct.setStyle("-fx-font-size: 12;");
		tfSelfDestruct.setLayoutX(AgentPane.getPrefWidth()*.50);
		tfSelfDestruct.setLayoutY(AgentPane.getPrefHeight()*.58);
		tfSelfDestruct.setPrefWidth(60);

		txtEatsApple.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtEatsApple.setLayoutY(AgentPane.getPrefHeight()*.67);
		txtEatsApple.setStyle("-fx-font-size: 12;");
		tfEatsApple.setLayoutX(AgentPane.getPrefWidth()*.02);
		tfEatsApple.setLayoutY(AgentPane.getPrefHeight()*.68);
		tfEatsApple.setPrefWidth(60);

		txtIdle.setLayoutX(AgentPane.getPrefWidth()*.50);
		txtIdle.setLayoutY(AgentPane.getPrefHeight()*.67);
		txtIdle.setStyle("-fx-font-size: 12;");
		tfIdle.setLayoutX(AgentPane.getPrefWidth()*.50);
		tfIdle.setLayoutY(AgentPane.getPrefHeight()*.68);
		tfIdle.setPrefWidth(60);

		Button btDQN = new Button("DQN");
		btDQN.setLayoutX(AgentPane.getPrefWidth()*.02);
		btDQN.setLayoutY(AgentPane.getPrefHeight()*.1);
		btDQN.setStyle("-fx-background-color: '#301934'; -fx-text-fill: 'white';");
		AgentPane.getChildren().add(btDQN);

		AgentPane.getChildren().addAll(txtLearningRate, tfLearningRate,txtDiscountFactor, tfDiscountFactor,
				txtEpsilonDecay, tfEpsilonDecay,txtRewards
				,txtwallDeath,tfwallDeath,txtSelfDestruct,tfSelfDestruct,
				txtEatsApple,tfEatsApple,
				txtIdle, tfIdle);



		Button btStatic = new Button("Static AI");
		btStatic.setLayoutX(AgentPane.getPrefWidth()*.55);
		btStatic.setLayoutY(AgentPane.getPrefHeight()*.1);
		AgentPane.getChildren().add(btStatic);
		btStatic.setStyle("-fx-background-color: 'white'; -fx-text-fill: '#006400';");

		btUpload.setOnAction(e ->
		{

	        FileChooser  fileChooser = new FileChooser();
	        Stage tempStage = new Stage();
	       // tempStage.show();
	        File neuralNetworkFile = fileChooser.showOpenDialog(tempStage);

	        try {
				((GamePane)gamePane).setNN(neuralNetworkFile);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        btTrain.setDisable(false);
	        btStartTraining.setDisable(false);
		});

		Button btSave = new Button("Save");
		btSave.setLayoutX(AgentPane.getPrefWidth()*.7);
		btSave.setLayoutY(AgentPane.getPrefHeight()*.77);
		AgentPane.getChildren().add(btSave);

		btSave.setOnAction(e ->{
			FileChooser  fileChooser = new FileChooser();
	        Stage tempStage = new Stage();
	        fileChooser.setInitialFileName("myDQN.nn");
	       // tempStage.show();
	        File dqnFile = fileChooser.showSaveDialog(tempStage);

	        try {
				((GamePane) gamePane).saveDQN(dqnFile.getAbsolutePath());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		txtAgentPane = new Text("Agent Controller");
		txtAgentPane.setLayoutX(AgentPane.getPrefWidth()*.02);
		txtAgentPane.setLayoutY(AgentPane.getPrefHeight()*.05);
		txtAgentPane.setStyle("-fx-font-size: 18");
		AgentPane.getChildren().add(txtAgentPane);

		btTrain.setLayoutX(AgentPane.getPrefWidth()*.02);
		btTrain.setLayoutY(AgentPane.getPrefHeight()*.90);
		AgentPane.getChildren().add(btTrain);

		Text txtEpisodes = new Text("Episodes");
		TextField tfEpisodes = new TextField("100");

		txtEpisodes.setLayoutX(AgentPane.getPrefWidth()*.30);
		txtEpisodes.setLayoutY(AgentPane.getPrefHeight()*.90);
		txtEpisodes.setStyle("-fx-font-size: 12;");
		tfEpisodes.setLayoutX(AgentPane.getPrefWidth()*.30);
		tfEpisodes.setLayoutY(AgentPane.getPrefHeight()* .91);

		btTrain.setDisable(true);
		tfEpisodes.setDisable(true);

		btTrain.setOnAction(e ->{

			Stage stage = new Stage();
			Pane pane = new Pane();
			Scene scene = new Scene(pane, 400, 400);
			stage.setScene(scene);
			Image gif;
			try {
				gif = new Image(new FileInputStream("SnakeTraining.gif"));


			ImageView giffyboi = new ImageView(gif);
			giffyboi.setLayoutX(70);
			giffyboi.setLayoutY(50);
			Text txtTrainingSnake = new Text("Training Snake...");
			txtTrainingSnake.setLayoutX(160);
			txtTrainingSnake.setLayoutY(360);
			pane.getChildren().add(txtTrainingSnake);

			pane.getChildren().add(giffyboi);
			} catch (FileNotFoundException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}

			new Thread(new Runnable() {

			    @Override
			    public void run() {


			        Platform.runLater(new Runnable() {

			            @Override
			            public void run()
			            {
			    			stage.show();
			            }
			        });
			    }
			}).start();

			new Thread(new Runnable() {

			    @Override
			    public void run() {
						try {
							((GamePane) gamePane).trainSnek(Integer.parseInt(tfEpisodes.getText()));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}


						Platform.runLater(new Runnable() {

			            @Override
			            public void run()
			            {
			    			stage.close();
			            }
			        });
			    }
			}).start();





		});

		AgentPane.getChildren().addAll(txtEpisodes, tfEpisodes);


		btCreateNew.setLayoutX(AgentPane.getPrefWidth()*.02);
		btCreateNew.setLayoutY(AgentPane.getPrefHeight()*.77);
		AgentPane.getChildren().add(btCreateNew);


		btUpload.setText("Upload Net");
		btUpload.setLayoutX(AgentPane.getPrefWidth()*.4);
		btUpload.setLayoutY(AgentPane.getPrefHeight()*.77);
		AgentPane.getChildren().add(btUpload);

		btCreateNew.setOnAction(e ->{
			//	public SnakeDQN(int[] topology, double learningRate, double discountFactor, int width, int height, double hitWall, double ateApple, double idle)

			((GamePane) gamePane).setSnek(Double.parseDouble(tfLearningRate.getText()), Double.parseDouble(tfDiscountFactor.getText()), Double.parseDouble(tfEpsilonDecay.getText()), Double.parseDouble(tfIdle.getText()), Double.parseDouble(tfEatsApple.getText()), Double.parseDouble(tfwallDeath.getText()), Double.parseDouble(tfSelfDestruct.getText()));

			btTrain.setDisable(false);
			tfEpisodes.setDisable(false);
			btStartTraining.setDisable(false);

			((GamePane)gamePane).setConsole("Created new network");

		});

		getChildren().add(content);
		Text Title = new Text("Control Panel");

		Pane TitlePane = new Pane();
		TitlePane.setStyle("-fx-background-color: '#e0e0e0'");
		TitlePane.setLayoutX(getPrefWidth()*0.02);
		TitlePane.setLayoutY(getPrefHeight()*0.02);
		TitlePane.setPrefHeight(content.getPrefHeight()*.073);
		TitlePane.setPrefWidth(content.getPrefWidth()*.95);
		TitlePane.getChildren().add(Title);
		content.getChildren().add(TitlePane);

		Title.setStyle("-fx-font-size: 20;"); // NEEDS TO BE CHANGED BASED ON SIZE
		Title.setLayoutX(content.getPrefWidth()*0.04);
		Title.setLayoutY(content.getPrefHeight()*0.04);
		//Title.setLayoutX(value);
		//Title.setLayoutY();

		btStatic.setOnAction(ex->{

			whichTimeline = false;

			btDQN.setStyle("-fx-background-color: 'white'; -fx-text-fill: '#301934';");
			btStatic.setStyle("-fx-background-color: '#006400'; -fx-text-fill: 'white';");


			((GamePane) gamePane).colorOfSnake(0.992, 0.666, 0.090);

			((GamePane) gamePane).iteration = 0;

			tfDiscountFactor.setDisable(true);
			tfEpsilonDecay.setDisable(true);
			tfwallDeath.setDisable(true);
			tfLearningRate.setDisable(true);
			tfSelfDestruct.setDisable(true);
			tfIdle.setDisable(true);
			tfEatsApple.setDisable(true);

			btUpload.setDisable(true);
			btCreateNew.setDisable(true);

			btStartTraining.setDisable(true);

			btTrain.setDisable(true);

			btStop.setDisable(true);

			btRestart.setDisable(true);

			btPlay.setDisable(false);

			((GamePane) gamePane).Stop();


		});

		btDQN.setOnAction(ex->{

			((GamePane) gamePane).iteration = 0;

			whichTimeline = true;

			btDQN.setStyle("-fx-background-color: '#301934'; -fx-text-fill: 'white';");
			btStatic.setStyle("-fx-background-color: 'white'; -fx-text-fill: '#006400';");

			((GamePane) gamePane).colorOfSnake(0.552, 0.113, 0.584);

			tfDiscountFactor.setDisable(false);
			tfEpsilonDecay.setDisable(false);
			tfwallDeath.setDisable(false);
			tfLearningRate.setDisable(false);
			tfSelfDestruct.setDisable(false);
			tfIdle.setDisable(false);
			tfEatsApple.setDisable(false);

			btUpload.setDisable(false);
			btCreateNew.setDisable(false);

			btStop.setDisable(true);
			btPlay.setDisable(true);
			btRestart.setDisable(true);
			btPause.setDisable(true);

			btStartTraining.setDisable(false);



			((GamePane) gamePane).Stop2();
			((GamePane) gamePane).reset();
			((GamePane) gamePane).Play();
			((GamePane) gamePane).Stop();


		});


	}

}//end ControlPane
