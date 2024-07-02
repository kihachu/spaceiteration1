package com.kihachu.spaceiteration1.model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

import java.util.Random;

/**
 * A class representing stars around the camera.
 * It allows creating a group of stars with random positions and adding them to a 3D scene.
 */
public class Stars {
    private int numberOfStars;
    private Group group;

    /**
     * Constructs a Stars object with a specified number of stars and a group.
     *
     * @param numberOfStars the number of stars to create.
     * @param group the group to which the stars will be added.
     */
    public Stars(int numberOfStars, Group group) {
        this.numberOfStars = numberOfStars;
        this.group = group;
        addStars(numberOfStars, group);
    }

    /**
     * Adds a specified number of stars with random positions to the provided group.
     *
     * @param numberOfStars the number of stars to create.
     * @param group the group to which the stars will be added.
     */
    private void addStars(int numberOfStars, Group group) {
        Random random = new Random();
        for (int i = 0; i < numberOfStars; i++) {
            Sphere star = new Sphere(2); // Star size
            star.setMaterial(new PhongMaterial(Color.WHITE)); // Цвет звезды

            // Random star location
            double x = (random.nextDouble() - 0.5) * 2000 * 2;
            double y = (random.nextDouble() - 0.5) * 2000 * 2;
            double z = (random.nextDouble() - 0.5) * 2000 * 2;
            star.setTranslateX(x);
            star.setTranslateY(y);
            star.setTranslateZ(z);

            group.getChildren().add(star);
        }
    }
    /**
     * Adds a specified number of stars with random positions around the camera to the group.
     *
     * @param cameraX the X coordinate of the camera.
     * @param cameraY the Y coordinate of the camera.
     * @param cameraZ the Z coordinate of the camera.
     * @param numberOfStars the number of stars to create.
     */
    public void addStarsAroundCamera(double cameraX, double cameraY, double cameraZ, int numberOfStars) {
        Random random = new Random();
        for (int i = 0; i < numberOfStars; i++) {
            Sphere star = new Sphere(2); // Размер звезды
            star.setMaterial(new PhongMaterial(Color.WHITE)); // Цвет звезды

            // Randomize the position of the star around the camera
            double x = cameraX + (random.nextDouble() - 0.5) * 2000;
            double y = cameraY + (random.nextDouble() - 0.5) * 2000;
            double z = cameraZ + (random.nextDouble() - 0.5) * 2000;
            star.setTranslateX(x);
            star.setTranslateY(y);
            star.setTranslateZ(z);

            group.getChildren().add(star);
        }
    }
}
