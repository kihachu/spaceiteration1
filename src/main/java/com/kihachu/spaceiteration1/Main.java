package com.kihachu.spaceiteration1;

import com.kihachu.spaceiteration1.model.Universe;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Universe universe = new Universe(primaryStage);
        primaryStage.setTitle("Space Iteration 1");
        primaryStage.setScene(universe.getUniverseScene());
        primaryStage.show();
    }
}
