package com.kihachu.spaceiteration1.model;

import com.kihachu.spaceiteration1.camera.CameraController;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
/**
 * UI class responsible for displaying and updating the user interface elements in the universe.
 */
public class UI {
    private Text text;
    private Universe universe;

    /**
     * Constructs a UI object and initializes the text element with collected resources information.
     *
     * @param universe the universe instance containing the spacecraft and camera controller.
     */
    public UI (Universe universe){
        this.text = new Text("Collected resources: " + universe.getSpaceCraft().getCollectedResources() + "kg");
        text.setFont(new Font(40));
        this.universe = universe;
        text.setFill(Color.WHITE);
        text.setLayoutX(300);
        text.setLayoutY(300);
        universe.getCameraController().spaceCraftGroup.getChildren().add(text);
    }

    /**
     * Updates the text element on the screen with the current amount of collected resources.
     */
    public void updateText(){
        this.text.setText("Collected resources: " + this.universe.getSpaceCraft().getCollectedResources() + "kg");
    }
}
