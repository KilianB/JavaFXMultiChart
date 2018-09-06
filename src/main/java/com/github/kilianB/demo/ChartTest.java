package com.github.kilianB.demo;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartTest extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		
	FXMLLoader loader = new FXMLLoader();
		
		//loader.setController(new Controller());
		loader.setLocation(ChartTest.class.getResource("ChartTest.fxml"));
		
		Parent parent = loader.load();
		
		Scene scene = new Scene(parent,500,500);
		 
		scene.getStylesheets().add(ChartTest.class.getResource("chart.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
