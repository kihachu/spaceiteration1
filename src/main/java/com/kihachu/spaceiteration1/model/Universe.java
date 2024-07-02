package com.kihachu.spaceiteration1.model;

import com.kihachu.spaceiteration1.camera.CameraController;
import com.kihachu.spaceiteration1.camera.Controller;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Represents the entire universe in the application.
 * Manages the initialization and setup of all celestial bodies, asteroids, spacecraft, and the camera controller.
 */
public class Universe {
    /**
     * Manages the asteroids in the universe.
     */
    private Asteroids asteroids;

    /**
     * The primary stage for the universe.
     */
    Stage universeStage;

    /**
     * The width of the universe scene.
     */
    public int WIDTH = 1920;

    /**
     * The height of the universe scene.
     */
    public int HEIGHT = 1080;

    /**
     * The scene representing the universe.
     */
    Scene universeScene;

    /**
     * The group containing all elements in the universe.
     */
    Group universeGroup;

    /**
     * The controller for handling camera movements and interactions.
     */
    CameraController cameraController;

    /**
     * The spacecraft in the universe.
     */
    SpaceCraft spaceCraft;

    /**
     * The controller for handling user input and controlling the spacecraft.
     */
    Controller controller;

    /**
     * The user interface for displaying information.
     */
    UI ui;

    /**
     * Constructs a Universe object and initializes the scene, camera, spacecraft, and other components.
     *
     * @param universeStage the primary stage for the universe.
     */
    public Universe(Stage universeStage) {
        this.universeStage = universeStage;
        PerspectiveCamera camera = new PerspectiveCamera(true);
        this.universeGroup = new Group();
        this.universeScene = new Scene(universeGroup, WIDTH, HEIGHT, true);
        this.spaceCraft = new SpaceCraft("SpaceCraft", "/models/HST/hst.3ds", -90, 0, 0);
        this.cameraController = new CameraController(universeStage, camera, universeGroup, universeScene, getUniverse());
        this.universeScene.setCamera(camera);
        this.controller = new Controller(universeScene, spaceCraft);
        this.ui = new UI(getUniverse());

        String[] asteroidModels = {
                "/models/asteroid/asteroid1.3ds",
                "/models/asteroid/asteroid2.3ds",
                "/models/asteroid/asteroid3.3ds",
                "/models/asteroid/asteroid4.3ds",
                "/models/asteroid/asteroid5.3ds",
                "/models/asteroid/asteroid6.3ds",
                "/models/asteroid/asteroid7.3ds",
                "/models/asteroid/asteroid8.3ds"
        };
        this.asteroids = new Asteroids(universeGroup, asteroidModels);

        asteroids.addAsteroids(1000);
        addSkybox();
        addCelestialBodies();
        addSpaceCraft();

    }

    /**
     * Returns the UI instance associated with this universe.
     *
     * @return the UI instance.
     */
    public UI getUi(){
        return ui;
    }

    /**
     * Returns the scene representing the universe.
     *
     * @return the universe scene.
     */
    public Scene getUniverseScene() {
        return this.universeScene;
    }

    /**
     * Returns this Universe instance.
     *
     * @return this Universe instance.
     */
    public Universe getUniverse() {
        return this;
    }

    /**
     * Returns the camera controller for this universe.
     *
     * @return the camera controller.
     */
    public CameraController getCameraController() {
        return this.cameraController;
    }

    /**
     * Returns the primary stage for this universe.
     *
     * @return the primary stage.
     */
    public Stage getUniverseStage() {
        return this.universeStage;
    }

    /**
     * Returns the group containing all elements in the universe.
     *
     * @return the universe group.
     */
    public Group getUniverseGroup() {
        return this.universeGroup;
    }

    /**
     * Returns the spacecraft in this universe.
     *
     * @return the spacecraft.
     */
    public SpaceCraft getSpaceCraft() { return this.spaceCraft; }

    /**
     * Adds a skybox to the universe group.
     */
    private void addSkybox() {
        Skybox skybox = new Skybox();
        universeGroup.getChildren().add(skybox);
    }

    /**
     * Adds celestial bodies to the universe group.
     */
    private void addCelestialBodies() {
        CelestialBody earth = CelestialBody.createCelestialBody("Earth", 10000, Color.BLUE, 0, 0, 200000, "/textures/earth/earth.jpg");
        universeGroup.getChildren().addAll(earth);
    }

    /**
     * Adds the spacecraft to the universe and sets up the camera and controls.
     */
    private void addSpaceCraft() {
        cameraController.setSpaceCraft(spaceCraft);
        controller.setSpaceCraft(spaceCraft);
    }


}
