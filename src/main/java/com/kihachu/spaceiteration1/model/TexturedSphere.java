package com.kihachu.spaceiteration1.model;

import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * Utility class for creating textured spheres.
 */
public class TexturedSphere {

    /**
     * Creates a sphere with a specified radius and applies a texture to it.
     *
     * @param radius the radius of the sphere.
     * @param texturePath the path to the texture image file.
     * @return the created sphere with the applied texture.
     */
    public static Sphere createTexturedSphere(double radius, String texturePath) {
        Sphere sphere = new Sphere(radius);
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new Image(TexturedSphere.class.getResource(texturePath).toExternalForm()));
        sphere.setMaterial(material);
        return sphere;
    }
}
