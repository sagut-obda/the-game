/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import mygame.GameUtilities;

/**
 *
 * @author Ferdian
 */
public class GameCharacter extends PlainObjectAnimated {
    
    private InputManager inputManager;
    private CharacterControl thisControl;
    private Camera camera;
    private boolean toLeft = false, toRight = false;
    
    public GameCharacter(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length, InputManager inputManager) {
        super(x, y, z, vy, vx, vz, nameObject, height, width, assetManager, texture, material, type, length,inputManager);
        this.material = material;
        this.inputManager = inputManager;
    }
    
    public GameCharacter(AssetManager assetManager, String name, String path) {
        super(assetManager, name, path);
    }
    
    public void changeColor(ColorRGBA c) {
        material.setColor("Color", c);
        this.spatial.setMaterial(material);
    }
    
    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
    
    public void move() {
        Vector3f walkDirection = new Vector3f(0, 0, 0);
        Vector3f camLeft = camera.getLeft().clone();
        camLeft.y = 0;
        camLeft.normalizeLocal();
        if (!toLeft && !toRight) {
            thisControl.setWalkDirection(Vector3f.ZERO);
        } else if (toLeft) {
            if (this.z >= GameUtilities.getInstance().getEdgesLeft()) {
                walkDirection.addLocal(camLeft).multLocal(0.2f);
                thisControl.setWalkDirection(walkDirection);
            } else {
                thisControl.setWalkDirection(Vector3f.ZERO);
            }
        } else if (toRight) {
            if (this.z <= GameUtilities.getInstance().getEdgesRight()) {
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
    
    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    public void createControl() {
        CharacterControl characterControl;
        BoundingBox bb = (BoundingBox) this.getWorldBound();
        CapsuleCollisionShape ccs = new CapsuleCollisionShape(bb.getXExtent(), bb.getYExtent());
        characterControl = new CharacterControl(ccs, 1f);
        characterControl.setJumpSpeed(20);
        characterControl.setFallSpeed(25);
        characterControl.setGravity(30);
        this.thisControl = characterControl;
        this.spatial.addControl(thisControl);
    }
    
    public void setLocation(float x, float y, float z) {
        if (this.thisControl == null) {
            this.setX(x);
            this.setY(y);
            this.setZ(z);
        } else {
            thisControl.setPhysicsLocation(new Vector3f(x, y, z));
        }
    }
    
    public CharacterControl getControl() throws Exception {
        if (this.thisControl == null) {
            throw new Exception("Need Control");
        }
        return this.thisControl;
    }
    
    public void jump() {
        this.thisControl.jump();
    }
    
    public void setPosition(Vector3f vector) {
        setZ(vector.z);
        setY(vector.y);
        setX(vector.x);
    }

    @Override
    public void addAndCreateAnimation() {
        super.addAndCreateAnimation(); 
    }

    @Override
    public void setAnimation(String nameAnimation) {
        super.setAnimation(nameAnimation); 
    }

    @Override
    public void addAndCreateAnimation(String nameChild) {
        super.addAndCreateAnimation(nameChild); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
