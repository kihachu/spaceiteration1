package com.kihachu.spaceiteration1.camera;

import com.kihachu.spaceiteration1.model.Asteroids.Asteroid;
import com.kihachu.spaceiteration1.model.SpaceCraft;
import com.kihachu.spaceiteration1.model.UI;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 * Controller class for handling raycasting interactions, such as detecting and mining asteroids, within a 3D space environment.
 */
public class RaycastingController {
    private final Group universeGroup;
    private final Group spaceCraftGroup;
    private final SpaceCraft spaceCraft;
    private final Text text;
    private Cylinder laser;
    private final UI ui;
    private AnimationTimer miningTimer;

    /**
     * Constructs a RaycastingController object with the specified universe group, spacecraft group, spacecraft, and UI.
     *
     * @param universeGroup the group representing the universe.
     * @param spaceCraftGroup the group representing the spacecraft.
     * @param spaceCraft the spacecraft instance being controlled.
     * @param ui the user interface instance for updating text information.
     */
    public RaycastingController(Group universeGroup, Group spaceCraftGroup, SpaceCraft spaceCraft, UI ui) {
        this.universeGroup = universeGroup;
        this.spaceCraftGroup = spaceCraftGroup;
        this.spaceCraft = spaceCraft;
        this.ui = ui;
        this.text = new Text();
        initText();
        initLaser();
    }

    /**
     * Initializes the text element used for displaying information.
     */
    private void initText() {
        text.setFont(new Font(40));
        text.setFill(Color.WHITE);
        text.setTranslateX(300);
        text.setTranslateY(200);
        spaceCraftGroup.getChildren().add(text);
    }

    /**
     * Initializes the laser element.
     */
    private void initLaser() {
        laser = new Cylinder(5, 100);
        laser.setMaterial(new PhongMaterial(Color.RED));
        laser.setVisible(false);
        universeGroup.getChildren().add(laser);
    }

    /**
     * Handles mouse click events for detecting and interacting with asteroids.
     *
     * @param event the mouse event triggered by a click.
     */
    public void handleMouseClick(MouseEvent event) {
        Point3D clickPoint = new Point3D(event.getX(), event.getY(), event.getZ());
        System.out.println("Mouse clicked at: " + clickPoint);

        Node pickedNode = event.getPickResult().getIntersectedNode();
        Asteroid asteroid = findAsteroid(pickedNode);

        if (asteroid != null) {
            int resources = asteroid.getResources();
            text.setText(asteroid.getName() + " has " + resources + " resources left.");
            System.out.println(asteroid.getName() + " with " + resources + " resources left.");
            startMining(asteroid);
        } else {
            text.setText("No asteroid clicked.");
            System.out.println("No asteroid clicked. Picked node: " + pickedNode);
        }
    }

    /**
     * Finds the asteroid node in the scene graph.
     *
     * @param node the node to start the search from.
     * @return the asteroid node if found, otherwise null.
     */
    private Asteroid findAsteroid(Node node) {
        while (node != null) {
            if (node instanceof Asteroid) {
                return (Asteroid) node;
            }
            node = node.getParent();
        }
        return null;
    }

    /**
     * Starts mining resources from the specified asteroid.
     *
     * @param asteroid the asteroid to mine resources from.
     */
    private void startMining(Asteroid asteroid) {
        spaceCraft.startMining(asteroid);

        createLaser();
        updateLaser(asteroid);

        if (miningTimer != null) {
            miningTimer.stop();

        }

        miningTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (asteroid.getResources() > 0) {
                    asteroid.mineResources(1, spaceCraft);
                    text.setText(asteroid.getName() + " has " + asteroid.getResources() + " resources left.");
                    updateLaser(asteroid);
                    ui.updateText();
                } else {
                    stop();
                    removeLaser();
                    laser.setVisible(false);
                    universeGroup.getChildren().remove(asteroid);
                    text.setText(asteroid.getName() + " has been depleted and removed.");
                }
            }
        };
        miningTimer.start();
    }

    /**
     * Creates a laser element for the mining animation.
     */
    private void createLaser() {
        laser = new Cylinder(10, 100);
        laser.setMaterial(new PhongMaterial(Color.RED));
        laser.setVisible(true);
        universeGroup.getChildren().add(laser);
    }

    /**
     * Removes the laser element from the scene.
     */
    private void removeLaser() {
        if (laser != null) {
            laser.setVisible(false);
            universeGroup.getChildren().remove(laser);
        }
    }

    /**
     * Updates the laser element's position and orientation based on the target asteroid.
     *
     * @param asteroid the target asteroid.
     */
    //With chatgpt
    private void updateLaser(Asteroid asteroid) {
        Point3D start = new Point3D(
                spaceCraft.getPosition().getX(), // Offset the laser position
                spaceCraft.getPosition().getY() + 300,
                spaceCraft.getPosition().getZ()
        );
        Point3D end = new Point3D(
                asteroid.getTranslateX(),
                asteroid.getTranslateY(),
                asteroid.getTranslateZ()
        );
        Point3D direction = end.subtract(start);
        double distance = direction.magnitude();

        // Update laser height and orientation
        laser.setHeight(distance);
        laser.setTranslateX((start.getX() + end.getX()) / 2);
        laser.setTranslateY((start.getY() + end.getY()) / 2);
        laser.setTranslateZ((start.getZ() + end.getZ()) / 2);

        // Set rotation axis and angle
        Point3D axis = new Point3D(0, 1, 0).crossProduct(direction);
        double angle = Math.toDegrees(Math.acos(new Point3D(0, 1, 0).dotProduct(direction.normalize())));
        laser.getTransforms().setAll(new Rotate(angle, axis));
    }
}
