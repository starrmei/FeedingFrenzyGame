package FeedingFrenzy;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.util.Optional;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class GameProject extends Application {
	static GamePane3 gameplay = new GamePane3(); 
	static BorderPane pane = new BorderPane();
	static HBox scorePane = new HBox(15);
	
	static GridPane options = new GridPane();
	static Button btnSet = new Button("Confirm");
	static Label lTitle = new Label("Settings");
	static Label lPSize = new Label("Player Size:");
	static Label lESize = new Label("Enemy Size:");
	static Label lSpeed = new Label("Inital Speed:");
	static Label lWidth = new Label("Window Width:");
	static Label lHeight = new Label("Window Height:");
	//Text Fields
	static TextField tfPSize = new TextField("10");
	static TextField tfESize = new TextField("30");
	static TextField tfWidth = new TextField("600");
	static TextField tfHeight = new TextField("400");
	static Slider slSpeed = new Slider();
	
	static Alert alStart = new Alert(AlertType.CONFIRMATION);
	
	public void start(Stage primaryStage) {
		//Show start message
		getStart();
		
	    Optional<ButtonType> result = alStart.showAndWait();
	    //Close
	    if (result.get() == null) {
	    	System.exit(0);
	    }
	    
	    //Start hit
	    else if (result.get() == ButtonType.OK) {
	    	getGame();
	    	
	    	//Scene
	    	Scene scene = new Scene(pane, (gameplay.getWidth() - 1), (gameplay.getHeight() - 15));
		    primaryStage.setTitle("Feeding Frenzy"); 
		    primaryStage.setScene(scene);
		    primaryStage.show();

		    gameplay.requestFocus();
	    }
	    
	    //Options hit
	    else if(result.get() == ButtonType.CANCEL) {
	    	getOptions();
	    	
	    	btnSet.setOnAction(new EventHandler<ActionEvent>() {
		        @Override
		        public void handle(ActionEvent e) {
		        	boolean OK = true;
		        	try {
						gameplay.setPlayerRadius(Integer.parseInt(tfPSize.getText()));
						gameplay.setEnemyRadius(Integer.parseInt(tfESize.getText()));
						gameplay.setSpeed((int) slSpeed.getValue()); 
						gameplay.setWidth(Integer.parseInt(tfWidth.getText()));
						gameplay.setHeight(Integer.parseInt(tfHeight.getText()));  
						
						getGame();
						
					    gameplay.requestFocus();
		        	}
		        	catch (Exception i) {
			         	OK = false;
			        }
			        if (!OK) {
			         	getError();
			        }
		        } 
		    });
	    	
	    	//Scene
	    	Scene scene = new Scene(pane, (gameplay.getWidth()), (gameplay.getHeight()));
		    primaryStage.setTitle("Feeding Frenzy"); 
		    primaryStage.setScene(scene);
		    primaryStage.show();
		
		    //Request focus
		    options.requestFocus();   
	    }
	}
	
	public static void getStart() {
		//Start game alert
		alStart.setTitle("Feeding Frenzy");
	  	alStart.setHeaderText("Directions");
	    alStart.setContentText("-Use the arrow keys to move your circle, \n-Eat as many pellets as possible and avoid the big red enemy, \n-Watch out, the more pellets you eat, the faster he gets! \n\n-Pause with a mouse click and resume by entering any movement \n-Restart your game any time by pressing the space bar");
	    Button btnStart = (Button) alStart.getDialogPane().lookupButton(ButtonType.OK);
	    btnStart.setText("Start");
	    Button btnOptions = (Button) alStart.getDialogPane().lookupButton(ButtonType.CANCEL);
	    btnOptions.setText("Options");	
	}
	
	public static void getGame() {
		//Pane
		scorePane.setMaxHeight(15);
		scorePane.setMinWidth(gameplay.getWidth());
		scorePane.setMaxWidth(Integer.parseInt(tfWidth.getText()));
		pane.setTop(scorePane);
	    scorePane.setStyle("-fx-border-color: black");
	    pane.setBottom(gameplay);
	    gameplay.setStyle("-fx-border-color: black");
	    scorePane.getChildren().add(gameplay.getStats());
	    gameplay.addPellet();
	    
		//Play/Pause
		gameplay.setOnMouseClicked(x -> gameplay.pause());
		   
		//Movement
		gameplay.setOnKeyPressed(x -> {
			if (x.getCode() == KeyCode.RIGHT) {
				gameplay.play();
		  		gameplay.moveRight();
		    }
		    else if (x.getCode() == KeyCode.LEFT) {
		    	gameplay.play();
		    	gameplay.moveLeft();
		    } 
		    else if (x.getCode() == KeyCode.UP) {
		    	gameplay.play();
		    	gameplay.moveUp();
		    }
		    else if (x.getCode() == KeyCode.DOWN) {
		    	gameplay.play();
		    	gameplay.moveDown();
		    } 
		    else if (x.getCode() == KeyCode.SPACE) {
			  	gameplay.restart();
			}
		});
	}

	public static void getOptions() {
		//Labels
    	lTitle.setFont(new Font("Arial", 20));
		lPSize.setFont(new Font("Arial", 15));
		lESize.setFont(new Font("Arial", 15));
		lSpeed.setFont(new Font("Arial", 15));
		lWidth.setFont(new Font("Arial", 15));
		lHeight.setFont(new Font("Arial", 15));

		//Slider
	    slSpeed.setMin(1);
	    slSpeed.setMax(5);
	    slSpeed.setValue(1);
	    slSpeed.setShowTickLabels(true);
	    slSpeed.setShowTickMarks(true);
	    slSpeed.setSnapToTicks(true);
	    slSpeed.setMajorTickUnit(2);
	    slSpeed.setMinorTickCount(1);
	    slSpeed.setBlockIncrement(1);
		
		options.setHgap(5);
		options.setVgap(5);
		
		options.add(lTitle, 0, 0);
		options.add(lPSize, 0, 2);
		options.add(tfPSize, 1, 2);
		options.add(lESize, 0, 3);
		options.add(tfESize, 1, 3);
		options.add(lSpeed, 0, 4);
	    options.add(slSpeed, 1, 4);
		//options.add(tfSpeed, 1, 4);
		options.add(lWidth, 0, 5);
		options.add(tfWidth, 1, 5);
		options.add(lHeight, 0, 6);
		options.add(tfHeight, 1, 6);
		options.add(btnSet, 1, 7);
		
		pane.setBottom(options);
		options.setAlignment(Pos.CENTER);
	}

	public static void getError() {
	    //Alert
	    Alert alError = new Alert(AlertType.ERROR);
		alError.setTitle("Feeding Frenzy");
		alError.setHeaderText("ERROR!");
		alError.setContentText("Please enter a valid value and try again");
		alError.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
