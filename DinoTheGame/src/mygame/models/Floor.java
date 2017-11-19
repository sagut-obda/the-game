/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

/**
 *
 * @author Ferdian
 */
public class Floor extends PlainObject {

    /**
     * constructor for debugging
     *
     * @param x position of x
     * @param y position of y
     * @param z position of z
     * @param vy acceleration y
     * @param vx acceleration x
     * @param vz acceleration z
     * @param nameObject the name that gonna inputed for node
     * @param height the height
     * @param width the width
     * @param assetManager assetManager used by texture and material
     * @param texture add the texture
     * @param material add the material
     * @param type type for geometries
     * @param length length
     */
    public Floor(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length);
    }

    /**
     * constructor for complex object that imported from anywhere
     *
     * @param assetManager assetmanager for pathing
     * @param name name that gonna inputed to node
     * @param path the path in your directionary
     */

    public Floor(AssetManager assetManager, String name, String path) {
        super(assetManager, name, path);
    }

    /**
     * constructor for complex object that imported from anywhere and have
     * acceleration
     *
     * @param assetManager assetmanager for pathing
     * @param name name that gonna inputed to node
     * @param path the path in your directionary
     * @param vx acceleration x
     * @param vy acceleration y
     * @param vz acceleration z
     */
    public Floor(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        super(assetManager, name, path, vx, vy, vz);
    }

    /**
     * method used for adding some texture ( like images or something ) and
     * material (effect ) this method directly set the spatial with the material
     * and texture
     *
     * @param material the material that gonna be added later on
     * @param texture the texture that gonna be added later on
     */
    public void setMaterialTexture(Material material, Texture texture) {
        this.material = material;
        this.texture = texture;
        this.material.setTexture("ColorMap", texture);
        this.spatial.setMaterial(this.material);
    }

    /**
     * constructor to added the spatial to this class for encapsulation matter
     *
     * @param s the spatial
     * @param vx acceleration x
     * @param vy acceleration y
     * @param vz acceleration z
     */
    public Floor(Spatial s, float vx, float vy, float vz) {
        super(s, vx, vy, vz);
    }

    /**
     * move method moving by tpf
     *
     * @param tpf time per frame
     */
    public void move(float tpf) {
        controlUpdate(tpf);
        if (this.getX() < -100) {
            this.setX(this.getX() + 250);
        }
    }

}
