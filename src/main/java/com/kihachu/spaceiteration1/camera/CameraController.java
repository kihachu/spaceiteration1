package com.kihachu.spaceiteration1.camera;

import com.kihachu.spaceiteration1.model.Asteroids;
import com.kihachu.spaceiteration1.model.SpaceCraft;
import com.kihachu.spaceiteration1.model.Universe;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
/**
 * Controller class for handling camera movements and interactions in a 3D space environment.
 */
public class CameraController {
    private final Stage primaryStage;
    private final Camera camera;
    private final Group universeGroup;
    private final Scene scene;
    private SpaceCraft spaceCraft;
    private double anchorX, anchorY;
    private double anchorAngleX = 0;
    private double anchorAngleY = 0;
    private RaycastingController raycastingController;
    private final DoubleProperty angleX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleY = new SimpleDoubleProperty(0);
    private AnimationTimer animationTimer;
    public Rotate xRotate;
    public Rotate yRotate;
    public Group spaceCraftGroup;
    private Asteroids asteroids;
    private Universe universe;

    /**
     * Constructs a CameraController object with the specified stage, camera, group, scene, and universe.
     *
     * @param primaryStage the primary stage of the application.
     * @param camera the camera used for the 3D view.
     * @param group the group representing the universe.
     * @param scene the scene containing the group.
     * @param universe the universe model containing all 3D objects.
     */
    public CameraController(Stage primaryStage, Camera camera, Group group, Scene scene, Universe universe) {
        this.primaryStage = primaryStage;
        this.camera = camera;
        this.universeGroup = group;
        this.scene = scene;
        this.universe = universe;
        camera.getTransforms().addAll(new Translate(0, 0, -2000));
        this.spaceCraftGroup = new Group();
        spaceCraftGroup.getChildren().add(camera);
        universeGroup.getChildren().add(spaceCraftGroup);

        camera.setFarClip(1000000);
        camera.setNearClip(1);
        initMouseControl(spaceCraftGroup, scene, primaryStage);
        startAnimationTimer();
    }

    private void initMouseControl(Group group, Scene scene, Stage stage) {
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(angleX);
        yRotate.angleProperty().bind(angleY);

        scene.setOnMousePressed(mouseEvent -> {
            anchorX = mouseEvent.getSceneX();
            anchorY = mouseEvent.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();

        });

        scene.setOnMouseDragged(mouseEvent -> {
            angleX.set(clamp(anchorAngleX - (anchorY - mouseEvent.getSceneY()), -90, 90));
            angleY.set(clamp(anchorAngleY + (anchorX - mouseEvent.getSceneX()), -180, 180));
        });


        scene.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            double mouseDelta = scrollEvent.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() - mouseDelta);
        });

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleMouseClick);
    }

    private void updateCameraPosition() {
        spaceCraftGroup.setTranslateX(spaceCraft.getTranslateXX());
        spaceCraftGroup.setTranslateY(spaceCraft.getTranslateYY());
        spaceCraftGroup.setTranslateZ(spaceCraft.getTranslateZZ());
    }

    private void handleMouseClick(MouseEvent event) {
        if (raycastingController != null) {
            raycastingController.handleMouseClick(event);
        }
    }

    private void startAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (spaceCraft != null) {
                    spaceCraft.update();
                    updateCameraPosition();
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Sets the spacecraft and adds it to the universe group and to the spacecraft group.
     *
     * @param spaceCraft the spacecraft to be controlled and displayed.
     */
    public void setSpaceCraft(SpaceCraft spaceCraft) {
        this.spaceCraft = spaceCraft;
        spaceCraftGroup.getChildren().add(spaceCraft);
        universeGroup.getChildren().add(spaceCraft);
        raycastingController = new RaycastingController(universeGroup, spaceCraftGroup, this.spaceCraft, universe.getUi());
    }

    /**
     * Clamps a value between a minimum and maximum range.
     *
     * @param value the value to be clamped.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return the clamped value.
     */
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
