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

    public Floor(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length);
    }

    public Floor(AssetManager assetManager, String name, String path) {
        super(assetManager, name, path);
    }

    public Floor(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        super(assetManager, name, path, vx, vy, vz);
    }

    public Floor(Spatial s, float vx, float vy, float vz) {
        super(s, vx, vy, vz);
    }

    public void move(float tpf) {
        controlUpdate(tpf);
        if (this.getX() < -100) {
            this.setX(this.getX() + 250);
        }
    }

}
