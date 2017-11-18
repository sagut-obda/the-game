/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.FlyCamAppState;
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
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import mygame.KeyBindings;
import mygame.models.Floor;
import mygame.models.GameCharacter;
import mygame.models.Obstacle;
import mygame.state.gui.HUDGuiState;

/**
 *
 * @author hayashi & Ferdian
 */
public class MainMenuState extends SagutAppState {

    private GameCharacter character;
    private LinkedList<Floor> poolFloor;
    private LinkedList<Obstacle> poolObstacle;

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
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        //----------------------------
        //Initiate Control Mode 
        this.bulletappstate.setDebugEnabled(false);
        this.flycamera.setEnabled(false);
        //-----------------------------
        //Load all Model Character

        character = new GameCharacter(assetManager, "Kizuna Ai", "Models/kizuna/kizuna.j3o");
        character.setInputManager(app.getInputManager());
        //------------------------------
        //Set Character Control for Player (character)
        BoundingBox bb = (BoundingBox) character.getWorldBound();
        CapsuleCollisionShape ccs = new CapsuleCollisionShape(bb.getXExtent(), bb.getYExtent());
        characterControl = new CharacterControl(ccs, 1f);
        characterControl.setJumpSpeed(20);
        characterControl.setFallSpeed(25);
        characterControl.setGravity(30);
        character.setControl(characterControl, camera);
        characterControl.setPhysicsLocation(new Vector3f(10, 2, -1));
        //-----------------------------------------------------
        //Attach to the localRoot and add all items to Pooling Structure data
        localRootNode.attachChild(character.getSpatial());
        for (int i = 0; i < 5; i++) {
            Node n = new Node();
            n.attachChild(assetManager.loadModel("Scenes/LandmarkScene.j3o"));
            Spatial s = n.getChild("Lantai");
            Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            m.setTexture("ColorMap", assetManager.loadTexture("Models/asset-Grass.jpg"));
            s.setMaterial(m);
            Floor f = new Floor(s, -20, 0, 0);
            Vector3f vector = s.getLocalScale();
            f.setX(vector.x * i);
            localRootNode.attachChild(s);
            poolFloor.add(f);

        }
        for (int i = 0; i < 2; i++) {
            Spatial s = assetManager.loadModel("Models/tree/tree.j3o");
            BoundingBox bb1 = (BoundingBox) s.getWorldBound();
            bb1.setXExtent(bb1.getXExtent() - 0.5f);
            bb1.setYExtent(bb1.getYExtent() + 50f);
            bb1.setZExtent(bb1.getZExtent() - 0.5f);
            s.setModelBound(bb1);
            localRootNode.attachChild(s);
            Obstacle o = new Obstacle(s, -20, 0, 0);
            o.setAsset(assetManager);
            o.setX(150 + 50 * i);
            o.setZ(-1);
            poolObstacle.add(o);
        }

        Spatial s = ((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("World");
        Spatial y = ((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("Edges");
        Spatial zz = ((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("Stone");
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setTexture("ColorMap", assetManager.loadTexture("Models/watertexture.jpg"));
        s.setMaterial(m);
        Material z = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        z.setTexture("ColorMap", assetManager.loadTexture("Models/assetMatahari.jpg"));
        y.setMaterial(z);
        Material zz1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        zz1.setTexture("ColorMap", assetManager.loadTexture("Models/stoneasset.jpg"));
        zz.setMaterial(zz1);
        Spatial zz2 = zz.clone();
        zz2.setLocalTranslation(0, -0.5f, -7);
        localRootNode.attachChild(s);
        localRootNode.attachChild(y);
        localRootNode.attachChild(zz);
        localRootNode.attachChild(zz2);
        //------------------------------
        //---------- bullet appstate controller in here
        bulletappstate.getPhysicsSpace().add(localRootNode.getChild("World").getControl(RigidBodyControl.class));
        bulletappstate.getPhysicsSpace().add(characterControl);
        //------------------------------------
        //Last for all ~fiuh , set the camera 
        chaseCamera = new ChaseCamera(camera, character.getSpatial(), inputManager);
        chaseCamera.setDefaultHorizontalRotation(3.13f);
        chaseCamera.setDefaultVerticalRotation(0.2f);
        chaseCamera.setRotationSpeed(0);
        //----------------------------------
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-10, 2, -1).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        DirectionalLight sunBlakang = new DirectionalLight();
        sunBlakang.setDirection(new Vector3f(10, 2, -1).normalizeLocal());
        sunBlakang.setColor(ColorRGBA.White);
        rootNode.addLight(sunBlakang);
        // Add HUD
        stateManager.attach(new HUDGuiState(sapp, "HUD"));
    }

    private void initKeys() {
        KeyBindings k = new KeyBindings();
        inputManager.addMapping("Left", new KeyTrigger(k.GO_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(k.GO_RIGHT));
        inputManager.addMapping("Jump", new KeyTrigger(k.JUMP));
        inputManager.addListener(actionListener, "Left", "Right", "Jump");

    }
    private ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {

            if (name.equals("Left") && !gameOverDebouncer) {
                character.isLeft(isPressed);
            } else if (name.equals("Right") && !gameOverDebouncer) {
                character.isRight(isPressed);
            } else if (name.equals("Jump") && !gameOverDebouncer) {
                character.jump();
            }
        }

    };
    protected boolean gameOverDebouncer = false;

    @Override
    public void update(float tpf) {
        character.move();
        Iterator<Obstacle> obit = poolObstacle.iterator();
        CollisionResults res = new CollisionResults();
        while (obit.hasNext()) {
            Obstacle o = obit.next();
            o.move(tpf);
            if (o.getX() < -20) {
                localRootNode.detachChild(o.getSpatial());
                o.regenerate();
                localRootNode.attachChild(o.getSpatial());
            }
            int x = character.collideWith(o.getWorldBound(), res);
            if (x != 0 && !gameOverDebouncer) {
                gameOverDebouncer = true;
                HUDGuiState.getCurrentInstance().triggerShowGameOverScreen();
            }

        }
        Iterator<Floor> it = poolFloor.iterator();
        while (it.hasNext()) {
            Floor f = it.next();
            f.move(tpf);
        }

    }
}
