package com.kihachu.spaceiteration1.camera;

import com.kihachu.spaceiteration1.model.SpaceCraft;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.Scene;

/**
 * Controller class responsible for handling user input and controlling the spacecraft.
 */
public class Controller {
    private final Scene scene;
    private SpaceCraft spaceCraft;

    /**
     * Constructs a Controller object with the specified scene and spacecraft.
     *
     * @param scene the scene to which the controller is attached.
     * @param spaceCraft the spacecraft to be controlled.
     */
    public Controller(Scene scene, SpaceCraft spaceCraft) {
        this.scene = scene;
        initKeyHandler();
    }

    /**
     * Initializes key handlers for controlling the spacecraft.
     */
    private void initKeyHandler() {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
        scene.addEventHandler(KeyEvent.KEY_RELEASED, this::handleKeyRelease);
    }

    /**
     * Handles key press events for controlling the spacecraft.
     *
     * @param keyEvent the key event triggered by a key press.
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        if (spaceCraft != null) {
            switch (keyEvent.getCode()) {
                case UP:
                    spaceCraft.accelerateForward();
                    break;
                case DOWN:
                    spaceCraft.accelerateBackward();
                    break;
                case A:
                    spaceCraft.rotateYawLeft();
                    break;
                case D:
                    spaceCraft.rotateYawRight();
                    break;
                case W:
                    spaceCraft.rotatePitchUp();
                    break;
                case S:
                    spaceCraft.rotatePitchDown();
                    break;
                case Q:
                    spaceCraft.rotateRollLeft();
                    break;
                case E:
                    spaceCraft.rotateRollRight();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles key release events.
     *
     * @param keyEvent the key event triggered by a key release.
     */
    private void handleKeyRelease(KeyEvent keyEvent) {
        // No action needed on key release for continuous acceleration
    }

    /**
     * Sets the spacecraft to be controlled by this controller.
     *
     * @param spaceCraft the spacecraft to be controlled.
     */
    public void setSpaceCraft(SpaceCraft spaceCraft) {
        this.spaceCraft = spaceCraft;
    }

}
