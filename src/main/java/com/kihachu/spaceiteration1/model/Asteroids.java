package com.kihachu.spaceiteration1.model;

import com.interactivemesh.jfx.importer.tds.TdsModelImporter;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.Random;

/**
 * Represents a collection of asteroids in the universe.
 * Provides functionality for adding and removing asteroids.
 */
public class Asteroids {
    private Group group;
    private String[] asteroidModels;
    private Random random;

    /**
     * Constructs an Asteroids object with the specified group and asteroid models.
     *
     * @param group the group to which the asteroids will be added.
     * @param asteroidModels an array of file paths to the asteroid models.
     */
    public Asteroids(Group group, String[] asteroidModels) {
        this.group = group;
        this.asteroidModels = asteroidModels;
        this.random = new Random();
    }

    /**
     * Adds a specified number of asteroids to the group with random positions and rotations.
     *
     * @param numberOfAsteroids the number of asteroids to add.
     */
    public void addAsteroids(int numberOfAsteroids) {
        double spacing = 100000; // Радиус, в котором генерируются астероиды
        for (int i = 0; i < numberOfAsteroids; i++) {
            String modelPath = asteroidModels[random.nextInt(asteroidModels.length)];
            Asteroid asteroid = new Asteroid("Asteroid" + i, modelPath, 10 + random.nextInt(491));
            double x = (random.nextDouble() - 0.5) * spacing * 2;
            double y = (random.nextDouble() - 0.5) * spacing * 2;
            double z = (random.nextDouble() - 0.5) * spacing * 2;
            asteroid.setPosition(x, y, z);
            asteroid.setScale(30); // Увеличиваем размер астероида
            addRotation(asteroid, random);
            group.getChildren().add(asteroid);
        }
    }

    /**
     * Adds rotation to a specified asteroid with random speed.
     *
     * @param asteroid the asteroid to add rotation to.
     * @param random the random number generator for determining the rotation speed.
     */
    private void addRotation(Asteroid asteroid, Random random) {
        double randomSpeed = 1 + (5 - 1) * random.nextDouble(); // Random rotation speed in the range of 1 to 5 degrees per second
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(360 / randomSpeed), asteroid);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAxis(new javafx.geometry.Point3D(1, 1, 1)); // Rotation around axis (1, 1, 1)
        rotateTransition.play();
    }

    /**
     * Removes a specified asteroid from the group.
     *
     * @param asteroid the asteroid to remove.
     */
    public void removeAsteroid(Asteroid asteroid) {
        group.getChildren().remove(asteroid);
    }

    /**
     * Represents a single asteroid in the universe.
     * Provides functionality for setting its position, scale, and mining resources.
     */
    public static class Asteroid extends Group {
        private String name;
        private Translate translate;
        private int resources;

        /**
         * Constructs an Asteroid object with the specified name, model path, and initial resources.
         *
         * @param name the name of the asteroid.
         * @param modelPath the path to the 3D model of the asteroid.
         * @param resources the initial amount of resources in the asteroid.
         */
        public Asteroid(String name, String modelPath, int resources) {
            this.name = name;
            this.resources = resources;
            TdsModelImporter importer = new TdsModelImporter();
            this.translate = new Translate();
            URL modelUrl = getClass().getResource(modelPath);

            if (modelUrl != null) {
                importer.read(modelUrl);
                Node[] meshViews = importer.getImport();
                this.getChildren().addAll(meshViews);
            } else {
                System.err.println("Model not found at path: " + modelPath);
            }
        }

        /**
         * Returns the name of the asteroid.
         *
         * @return the name of the asteroid.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the amount of resources remaining in the asteroid.
         *
         * @return the amount of resources remaining in the asteroid.
         */
        public int getResources() {
            return resources;
        }

        /**
         * Sets the position of the asteroid.
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
         * Sets the scale of the asteroid.
         *
         * @param scale the scale factor.
         */
        public void setScale(double scale) {
            this.setScaleX(scale);
            this.setScaleY(scale);
            this.setScaleZ(scale);
        }

        /**
         * Mines a specified amount of resources from the asteroid and adds them to the spacecraft's collected resources.
         * If the asteroid's resources are depleted, it is removed from the group.
         *
         * @param amount the amount of resources to mine.
         * @param spaceCraft the spacecraft to add the mined resources to.
         * @return the amount of resources mined.
         */
        public int mineResources(int amount, SpaceCraft spaceCraft) {
            if (resources > 0) {
                int mined = Math.min(resources, amount);
                resources -= mined;
                spaceCraft.collectedResources += mined;
                if (resources == 0) {
                    // Remove asteroid if resources are depleted
                    ((Group) this.getParent()).getChildren().remove(this);
                }
                return mined;
            }
            return 0;
        }
    }
}
