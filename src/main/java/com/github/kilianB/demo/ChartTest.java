package com.github.kilianB.demo;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartTest extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {

		// exit application on window close
		Platform.setImplicitExit(true);

		Parent parent = FXMLLoader.load(ChartTest.class.getResource("ChartTest.fxml"));

		Scene scene = new Scene(parent, 800, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
