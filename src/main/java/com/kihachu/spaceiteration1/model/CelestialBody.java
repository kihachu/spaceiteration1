package com.kihachu.spaceiteration1.model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * Represents a celestial body in the universe, such as a planet or a star.
 * Provides functionality for setting and getting its position, name, and color.
 */
public class CelestialBody extends Sphere {
    private String name;
    private double radius;
    private Color color;
    private String texturePath;

    /**
     * Constructs a CelestialBody object with the specified name, radius, color, and texture path.
     *
     * @param name the name of the celestial body.
     * @param radius the radius of the celestial body.
     * @param color the color of the celestial body.
     * @param texturePath the path to the texture image file.
     */
    public CelestialBody(String name, double radius, Color color, String texturePath) {
        super(radius);
        this.name = name;
        this.radius = radius;
        this.color = color;
        this.texturePath = texturePath ;

        // Set the material for the sphere
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(TexturedSphere.class.getResource(texturePath).toExternalForm()));
        this.setMaterial(material);
    }

    /**
     * Returns the name of the celestial body.
     *
     * @return the name of the celestial body.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the color of the celestial body.
     *
     * @return the color of the celestial body.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the position of the celestial body.
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
     * Returns the position of the celestial body as an array of doubles.
     *
     * @return the position of the celestial body.
     */
    public double[] getPosition() {
        return new double[]{this.getTranslateX(), this.getTranslateY(), this.getTranslateZ()};
    }

    /**
     * Creates a new CelestialBody object with the specified properties and position.
     *
     * @param name the name of the celestial body.
     * @param radius the radius of the celestial body.
     * @param color the color of the celestial body.
     * @param x the X coordinate of the celestial body's position.
     * @param y the Y coordinate of the celestial body's position.
     * @param z the Z coordinate of the celestial body's position.
     * @param texturePath the path to the texture image file.
     * @return the newly created CelestialBody object.
     */
    public static CelestialBody createCelestialBody(String name, double radius, Color color, double x, double y, double z, String texturePath) {
        CelestialBody body = new CelestialBody(name, radius, color, texturePath);
        body.setPosition(x, y, z);
        return body;
    }
}
