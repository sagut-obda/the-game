/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

/**
 *
 * @author Ferdian
 */
public  class PlainObjectAnimated extends PlainObject {

    protected AnimChannel channel;
    protected AnimControl control;

    public PlainObjectAnimated(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        super(assetManager, name, path, vx, vy, vz);
    }

    public PlainObjectAnimated(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length, InputManager inputManager) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length);
        this.material = material;

    }
     public PlainObjectAnimated(AssetManager assetManager, String name, String path) {
        super(assetManager, name, path);
    }
     /**
      * method to add animation use this method if the animation in the child node of scene in scene composer
      * 
      * @param nameChild 
      */
    public void addAndCreateAnimation(String nameChild) {
        control = ((Node) this.spatial).getChild(nameChild).getControl(AnimControl.class);
        channel = control.createChannel();
    }
    /**
     * method to add animation use this 
     * if the animations are in the same level with scene
     */
    public void addAndCreateAnimation() {
        control = ((Node) this.spatial).getControl(AnimControl.class);
        channel = control.createChannel();
    }
    /**
     * set the animation
     * it can be work if you call addAndCreateAnimation first
     * @param nameAnimation name the animation
     */
    public void setAnimation(String nameAnimation) {
        if (control == null || channel == null) {
            return;
        }
        channel.setAnim(nameAnimation);
    }

}
