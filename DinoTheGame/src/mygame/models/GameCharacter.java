/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

/**
 *
 * @author Ferdian
 */
public class GameCharacter extends PlainObject{
    protected Material material ;
    public GameCharacter(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length);
        this.material = material ;
    }
    public void changeColor(ColorRGBA c ){
        material.setColor("Color", c);
        this.spatial.setMaterial(material);
    }
   
    
}
