package FeedingFrenzy;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Options extends Pane {
	private TextField tfPSize = new TextField();
	private TextField tfESize = new TextField();
	private TextField tfSpeed = new TextField();
	private TextField tfWidth = new TextField();
	private TextField tfHeight = new TextField();
	
	public Options() {
		GridPane options = new GridPane();
		options.setHgap(5);
		options.setVgap(5);
		options.add(new Label("Player Size:"), 0, 0);
		options.add(tfPSize, 1, 0);
		options.add(new Label("Enemy Size:"), 0, 1);
		options.add(tfESize, 1, 1);
		options.add(new Label("Initial Speed:"), 0, 2);
		options.add(tfSpeed, 1, 2);
		options.add(new Label("Window Width:"), 0, 3);
		options.add(tfWidth, 1, 3);
		options.add(new Label("Window Height:"), 0, 4);
		options.add(tfHeight, 1, 4);
	}
}
