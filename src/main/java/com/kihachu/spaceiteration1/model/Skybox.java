package com.kihachu.spaceiteration1.model;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

/**
 * Represents a skybox in the 3D scene, which is used to create a background environment.
 */
public class Skybox extends Group {
    private static final double SIZE = 1000000; // Размер skybox

    /**
     * Constructs a Skybox object and initializes its six faces with appropriate textures.
     */
    public Skybox() {
        // Create six faces with different textures
        Box front = createFace("/textures/skybox/front.png", 0, 0, -SIZE / 2);
        Box back = createFace("/textures/skybox/back.png", 0, 0, SIZE / 2);
        Box left = createFace("/textures/skybox/left.png", -SIZE / 2, 0, 0);
        Box right = createFace("/textures/skybox/right.png", SIZE / 2, 0, 0);
        Box top = createFace("/textures/skybox/top.png", 0, -SIZE / 2, 0);
        Box bottom = createFace("/textures/skybox/bottom.png", 0, SIZE / 2, 0);

        back.setRotationAxis(Rotate.Y_AXIS);
        back.setRotate(180);

        left.setRotationAxis(Rotate.Y_AXIS);
        left.setRotate(90);

        right.setRotationAxis(Rotate.Y_AXIS);
        right.setRotate(-90);

        top.setRotationAxis(Rotate.X_AXIS);
        top.setRotate(90);

        bottom.setRotationAxis(Rotate.X_AXIS);
        bottom.setRotate(-90);

        getChildren().addAll(front, back, left, right, top, bottom);
    }

    /**
     * Creates a face of the skybox with the specified texture and position.
     *
     * @param texturePath the path to the texture image file.
     * @param x the X coordinate of the face's position.
     * @param y the Y coordinate of the face's position.
     * @param z the Z coordinate of the face's position.
     * @return the created face with the applied texture.
     */
    private Box createFace(String texturePath, double x, double y, double z) {
        Box face = new Box(SIZE, SIZE, 1);
        face.setTranslateX(x);
        face.setTranslateY(y);
        face.setTranslateZ(z);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(getClass().getResource(texturePath).toExternalForm()));
        face.setMaterial(material);
        return face;
    }
}
