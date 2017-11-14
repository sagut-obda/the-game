/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.ChaseCamera;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;
import mygame.KeyBindings;
import mygame.models.Floor;
import mygame.models.GameCharacter;
import mygame.models.Obstacle;

/**
 *
 * @author hayashi & Ferdian
 */
public class MainMenuState extends SagutAppState {

    private GameCharacter character;
    private LinkedList<Floor> poolFloor;
    private LinkedList<Obstacle> poolObstacle;
    private final Vector3f walkDirection = Vector3f.ZERO;

    public MainMenuState(SimpleApplication sapp) {
        super(sapp, "Main Menu");
        initKeys();
    }

    @Override
    public void init(AppStateManager stateManager, Application app) {
        stateManager.attach(this.bulletappstate);
        //Construct the memory pooling technique
        poolFloor = new LinkedList<>();
        poolObstacle = new LinkedList<>();
        //----------------------------
        //Initiate Control Mode 
        this.bulletappstate.setDebugEnabled(true);
        this.flycamera.setEnabled(false);
        //-----------------------------
        //Load all Model Character

        Material mainMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        character = new GameCharacter(10, 1, -2, 0, 0, 1, "PlayerTest", 1, 1, assetManager, null, mainMaterial, 2, 0, app.getInputManager());

        //------------------------------
        //Set Character Control for Player (character)
        BoundingBox bb = (BoundingBox) character.getSpatial().getWorldBound();
        CapsuleCollisionShape ccs = new CapsuleCollisionShape(bb.getXExtent(), bb.getYExtent());
        characterControl = new CharacterControl(ccs, 1f);
        characterControl.setJumpSpeed(20);
        characterControl.setFallSpeed(25);
        characterControl.setGravity(30);
        character.changeColor(ColorRGBA.Magenta);
        character.getSpatial().addControl(characterControl);
        character.setControl(characterControl, camera);

        //-----------------------------------------------------
        //Attach to the localRoot and add all items to Pooling Structure data
        localRootNode.attachChild(character.getSpatial());
        for (int i = 0; i < 2; i++) {
            Node n = new Node();
            n.attachChild(assetManager.loadModel("Scenes/LandmarkScene.j3o"));
            Spatial s = n.getChild("Lantai");
            Floor f = new Floor(s, -20, 0, 0);
            Vector3f vector = s.getLocalScale();
            f.setX(vector.x * i);
            localRootNode.attachChild(s);
            poolFloor.add(f);

        }
        Node n = new Node();
        n.attachChild(assetManager.loadModel("Scenes/LandmarkScene.j3o"));
        Spatial s = n.getChild("Obstacle");
        localRootNode.attachChild(s);
        poolObstacle.add(new Obstacle(localRootNode.getChild("Obstacle"), -20, 0, 0));
        //------------------------------

        //---------- bullet appstate controller in here
        bulletappstate.getPhysicsSpace().add(localRootNode.getChild("Lantai").getControl(RigidBodyControl.class));
        bulletappstate.getPhysicsSpace().add(characterControl);
        //------------------------------------
        //Last for all ~fiuh , set the camera 
        chaseCamera = new ChaseCamera(camera, character.getSpatial(), inputManager);
        chaseCamera.setDefaultHorizontalRotation(-3.2f);
        chaseCamera.setDefaultVerticalRotation(0.3f);
        //----------------------------------

    }

    private void initKeys() {
        KeyBindings k = new KeyBindings();
        inputManager.addMapping("Left", new KeyTrigger(k.GO_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(k.GO_RIGHT));
        inputManager.addMapping("Jump", new KeyTrigger(k.JUMP));
        inputManager.addListener(actionListener, "Left", "Right", "Jump");
    }
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Left")) {
                left = isPressed;
                character.isLeft(isPressed);
            } else if (name.equals("Right")) {
                right = isPressed;
                character.isRight(isPressed);
            } else if (name.equals("Jump")) {
                character.jump();
            }
        }

    };
    private boolean left, right;
    private int state = -1;
    @Override
    public void update(float tpf) {
        character.move();
        Iterator<Obstacle> obit = poolObstacle.iterator();
        CollisionResults res = new CollisionResults();
        //character.move(tpf);
        while (obit.hasNext()) {
            Obstacle o = obit.next();
            o.move(tpf);
            if (o.getX() < -20) {
                o.regenerate();
            }
            if (character.collideWith(o.getWorldBound(), res) != 0) {
                     System.out.println("Collide");
            }

        }
        Iterator<Floor> it = poolFloor.iterator();
        while (it.hasNext()) {
            Floor f = it.next();
            f.move(tpf);
        }
    }
}
