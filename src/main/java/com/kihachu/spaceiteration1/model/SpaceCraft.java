package com.kihachu.spaceiteration1.model;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import com.kihachu.spaceiteration1.camera.CameraController;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.net.URL;

/**
 * Represents a spacecraft in the universe.
 * Provides functionality for moving, rotating, and mining resources from asteroids.
 */
public class SpaceCraft extends Group {
    private String name;
    private Translate translate;
    private Rotate rotateX;
    private Rotate rotateY;
    private Rotate rotateZ;

    private double velocityZ = 0;

    private double rotationVelocityX = 0;
    private double rotationVelocityY = 0;
    private double rotationVelocityZ = 0;

    private double acceleration = 1;
    private double rotationAcceleration = 0.01;

    private Rotate initialRotateX;
    private Rotate initialRotateY;
    private Rotate initialRotateZ;
    private Cylinder laser;
    /**
     * The amount of resources collected by the spacecraft.
     */
    public int collectedResources;

    private AnimationTimer miningTimer;


    /**
     * Constructs a SpaceCraft object with the specified name, model path, and initial rotations.
     *
     * @param name the name of the spacecraft.
     * @param modelPath the path to the 3D model of the spacecraft.
     * @param initialRotationX the initial rotation angle around the X axis.
     * @param initialRotationY the initial rotation angle around the Y axis.
     * @param initialRotationZ the initial rotation angle around the Z axis.
     */
    public SpaceCraft(String name, String modelPath, double initialRotationX, double initialRotationY, double initialRotationZ) {
        this.name = name;
        TdsModelImporter importer = new TdsModelImporter();
        URL modelUrl = getClass().getResource(modelPath);

        if (modelUrl != null) {
            importer.read(modelUrl);
            Node[] meshViews = importer.getImport();
            this.getChildren().addAll(meshViews);
        } else {
            System.err.println("Model not found at path: " + modelPath);
        }

        this.translate = new Translate();
        this.rotateX = new Rotate(initialRotationX, Rotate.X_AXIS);
        this.rotateY = new Rotate(initialRotationY, Rotate.Y_AXIS);
        this.rotateZ = new Rotate(initialRotationZ, Rotate.Z_AXIS);

        this.initialRotateX = new Rotate(initialRotationX, Rotate.X_AXIS);
        this.initialRotateY = new Rotate(initialRotationY, Rotate.Y_AXIS);
        this.initialRotateZ = new Rotate(initialRotationZ, Rotate.Z_AXIS);

        this.getTransforms().addAll(translate, rotateX, rotateY, rotateZ, initialRotateX, initialRotateY, initialRotateZ);

//        setPosition(0, 0, 0);
        laser = new Cylinder(50, 1000); // Create the cylinder as a laser
        laser.setMaterial(new PhongMaterial(Color.RED));
        this.getChildren().add(laser);
        laser.setVisible(false);
        this.collectedResources = 0;
    }

    /**
     * Sets the position of the spacecraft.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @param z the Z coordinate.
     */
    public void setPosition(double x, double y, double z) {
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }
    /**
     * Returns the spacecraft itself.
     *
     * @return the spacecraft instance.
     */
    public SpaceCraft getSpaceCraft(){
        return this;
    }

    /**
     * Returns the amount of collected resources.
     *
     * @return the amount of collected resources.
     */
    public int getCollectedResources(){
        return this.collectedResources;
    }

    /**
     * Sets the rotation angles of the spacecraft.
     *
     * @param x the rotation angle around the X axis.
     * @param y the rotation angle around the Y axis.
     * @param z the rotation angle around the Z axis.
     */
    public void setRotation(double x, double y, double z) {
        this.rotateX.setAngle(x);
        this.rotateY.setAngle(y);
        this.rotateZ.setAngle(z);
    }

    /**
     * Returns the rotation angle around the X axis.
     *
     * @return the rotation angle around the X axis.
     */
    public double getRotateX() {
        return rotateX.getAngle();
    }

    /**
     * Returns the rotation angle around the Y axis.
     *
     * @return the rotation angle around the Y axis.
     */
    public double getRotateY() {
        return rotateY.getAngle();
    }

    /**
     * Returns the rotation angle around the Z axis.
     *
     * @return the rotation angle around the Z axis.
     */
    public double getRotateZ() {
        return rotateZ.getAngle();
    }

    /**
     * Returns the X coordinate of the spacecraft's translation.
     *
     * @return the X coordinate of the translation.
     */
    public double getTranslateXX(){
        return translate.getX();
    }

    /**
     * Returns the Y coordinate of the spacecraft's translation.
     *
     * @return the Y coordinate of the translation.
     */
    public double getTranslateYY(){
        return translate.getY();
    }

    /**
     * Returns the Z coordinate of the spacecraft's translation.
     *
     * @return the Z coordinate of the translation.
     */
    public double getTranslateZZ(){
        return translate.getZ();
    }

    /**
     * Accelerates the spacecraft forward.
     */
    public void accelerateForward() {
        this.velocityZ -= acceleration;
    }

    /**
     * Accelerates the spacecraft backward.
     */
    public void accelerateBackward() {
        this.velocityZ += acceleration;
    }

    /**
     * Rotates the spacecraft to the left around the Y axis (yaw).
     */
    public void rotateYawLeft() {
        this.rotationVelocityY -= rotationAcceleration;
    }

    /**
     * Rotates the spacecraft to the right around the Y axis (yaw).
     */
    public void rotateYawRight() {
        this.rotationVelocityY += rotationAcceleration;
    }

    /**
     * Rotates the spacecraft upwards around the X axis (pitch).
     */
    public void rotatePitchUp() {
        this.rotationVelocityX -= rotationAcceleration;
    }

    /**
     * Rotates the spacecraft downwards around the X axis (pitch).
     */
    public void rotatePitchDown() {
        this.rotationVelocityX += rotationAcceleration;
    }

    /**
     * Rotates the spacecraft to the left around the Z axis (roll).
     */
    public void rotateRollLeft() {
        this.rotationVelocityZ -= rotationAcceleration;
    }

    /**
     * Rotates the spacecraft to the right around the Z axis (roll).
     */
    public void rotateRollRight() {
        this.rotationVelocityZ += rotationAcceleration;
    }

    /**
     * Returns the position of the spacecraft as a Point3D.
     *
     * @return the position of the spacecraft.
     */
    public Point3D getPosition() {
        return new Point3D(translate.getX(), translate.getY(), translate.getZ());
    }

    /**
     * Updates the position and rotation of the spacecraft based on its velocity and rotation velocity.
     */

    //With chatgpt
    public void update() {
        // Create rotation matrices for each axis
        double[][] rotationX = {
                {1, 0, 0},
                {0, Math.cos(Math.toRadians(rotateX.getAngle())), -Math.sin(Math.toRadians(rotateX.getAngle()))},
                {0, Math.sin(Math.toRadians(rotateX.getAngle())), Math.cos(Math.toRadians(rotateX.getAngle()))}
        };

        double[][] rotationY = {
                {Math.cos(Math.toRadians(rotateY.getAngle())), 0, Math.sin(Math.toRadians(rotateY.getAngle()))},
                {0, 1, 0},
                {-Math.sin(Math.toRadians(rotateY.getAngle())), 0, Math.cos(Math.toRadians(rotateY.getAngle()))}
        };

        double[][] rotationZ = {
                {Math.cos(Math.toRadians(rotateZ.getAngle())), -Math.sin(Math.toRadians(rotateZ.getAngle())), 0},
                {Math.sin(Math.toRadians(rotateZ.getAngle())), Math.cos(Math.toRadians(rotateZ.getAngle())), 0},
                {0, 0, 1}
        };

        // Multiply the rotation matrices to get the combined rotation matrix
        double[][] rotationMatrix = multiplyMatrices(multiplyMatrices(rotationZ, rotationY), rotationX);

        // Define the forward direction vector
        double[] forward = {0, 0, -1};

        // Apply the rotation to the forward direction vector
        double[] rotatedForward = multiplyMatrixAndVector(rotationMatrix, forward);

        // Update the position based on the rotated forward vector
        this.translate.setX(this.translate.getX() + rotatedForward[0] * velocityZ);
        this.translate.setY(this.translate.getY() + rotatedForward[1] * velocityZ);
        this.translate.setZ(this.translate.getZ() + rotatedForward[2] * velocityZ);

        // Update rotations
        this.rotateX.setAngle(this.rotateX.getAngle() + rotationVelocityX);
        this.rotateY.setAngle(this.rotateY.getAngle() + rotationVelocityY);
        this.rotateZ.setAngle(this.rotateZ.getAngle() + rotationVelocityZ);

        // Apply damping to gradually reduce the velocity and rotation velocity
        velocityZ *= 0.98;
        rotationVelocityX *= 0.98;
        rotationVelocityY *= 0.98;
        rotationVelocityZ *= 0.98;
    }

    /**
     * Multiplies two 3x3 matrices.
     *
     * @param a the first matrix.
     * @param b the second matrix.
     * @return the resulting matrix after multiplication.
     */
    //With chatgpt
    private double[][] multiplyMatrices(double[][] a, double[][] b) {
        double[][] result = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 3; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    /**
     * Multiplies a 3x3 matrix and a 3-element vector.
     *
     * @param matrix the matrix.
     * @param vector the vector.
     * @return the resulting vector after multiplication.
     */
    //With chatgpt
    private double[] multiplyMatrixAndVector(double[][] matrix, double[] vector) {
        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = 0;
            for (int j = 0; j < 3; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }


    /**
     * Starts mining resources from the specified asteroid.
     *
     * @param asteroid the asteroid to mine resources from.
     */
    public void startMining(Asteroids.Asteroid asteroid) {
        miningTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (asteroid.getResources() > 0) {
                    System.out.println("Mining... Resources left: " + asteroid.getResources());
                } else {
                    stop();
                    System.out.println("Asteroid depleted and removed.");
                }
            }
        };
        miningTimer.start();
    }

}
