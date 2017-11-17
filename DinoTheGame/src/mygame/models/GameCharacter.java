/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

/**
 *
 * @author Ferdian
 */
public class GameCharacter extends PlainObject {

    protected Material material;
    private InputManager inputManager;
    private CharacterControl thisControl;
    private Camera camera;
    private boolean toLeft = false, toRight = false;
    private AnimChannel channel;
    private AnimControl control;

    public GameCharacter(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length, InputManager inputManager) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length);
        this.material = material;
        this.inputManager = inputManager;
    }

    public GameCharacter(AssetManager assetManager, String name, String path) {
        super(assetManager, name, path);
        control = ((Node) this.spatial).getChild("kizunaai_mesh").getControl(AnimControl.class);
        channel = control.createChannel();
        channel.setAnim("Walk");
    }

    public void changeColor(ColorRGBA c) {
        material.setColor("Color", c);
        this.spatial.setMaterial(material);
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    public void move() {

//        if (!(this.z >= -5.1 && this.z <= -1)) {
//            thisControl.setWalkDirection(Vector3f.ZERO);
//        }
        Vector3f walkDirection = new Vector3f(0, 0, 0);
        Vector3f camLeft = camera.getLeft().clone();
        camLeft.y = 0;
        camLeft.normalizeLocal();
        if (!toLeft && !toRight) {
            thisControl.setWalkDirection(Vector3f.ZERO);
        } else if (toLeft) {         
            if (this.z >= -5.1) {
                walkDirection.addLocal(camLeft).multLocal(0.2f);
                thisControl.setWalkDirection(walkDirection);
            } else {
                thisControl.setWalkDirection(Vector3f.ZERO);
            }
        } else if (toRight) {       
            if (this.z <= -1) {
                walkDirection.addLocal(camLeft.negate()).multLocal(0.2f);
                thisControl.setWalkDirection(walkDirection);
            } else {
                thisControl.setWalkDirection(Vector3f.ZERO);
            }
        }
        setPosition(thisControl.getPhysicsLocation());

    }

    public void isRight(boolean isRight) {
        toRight = isRight;
    }

    public void isLeft(boolean isLeft) {
        toLeft = isLeft;
    }

    public void setControl(CharacterControl thisControl, Camera camera) {
        this.thisControl = thisControl;
        this.camera = camera;
    }

    public void jump() {
        this.thisControl.jump();
    }

    public void setPosition(Vector3f vector) {
        setZ(vector.z);
        setY(vector.y);
        setX(vector.x);
    }

}
