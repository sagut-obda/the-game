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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.Iterator;
import java.util.LinkedList;
import javafx.scene.control.Label;
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

    public MainMenuState(SimpleApplication sapp) {
        super(sapp, "Main Menu");
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
        character = new GameCharacter(10, 10, 0, 0, 0, 0, "PlayerTest", 1, 1, assetManager, null, mainMaterial, 2, 0);

        //------------------------------
        //Set Character Control for Player (character)
        BoundingBox bb = (BoundingBox) character.getSpatial().getWorldBound();
        CapsuleCollisionShape ccs = new CapsuleCollisionShape(bb.getXExtent(), bb.getYExtent());
        CharacterControl playerControl = new CharacterControl(ccs, 1f);
        character.changeColor(ColorRGBA.Magenta);
        character.getSpatial().addControl(playerControl);
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
        for (int i = 0; i < 2; i++) {
            Node n = new Node();
            n.attachChild(assetManager.loadModel("Scenes/LandmarkScene.j3o"));
            Spatial s = n.getChild("Obstacle");
            localRootNode.attachChild(s);
            Obstacle o = new Obstacle(s, -20, 0, 0);
            o.setX(50+50*i);
            poolObstacle.add(o);
        }
        //------------------------------

        //---------- bullet appstate controller in here
        bulletappstate.getPhysicsSpace().add(localRootNode.getChild("Lantai").getControl(RigidBodyControl.class));
        bulletappstate.getPhysicsSpace().add(playerControl);
        //------------------------------------
        //Last for all ~fiuh , set the camera 
        chaseCamera = new ChaseCamera(camera, character.getSpatial(), inputManager);
        chaseCamera.setDefaultHorizontalRotation(-3.2f);
        chaseCamera.setDefaultVerticalRotation(0.3f);
        //----------------------------------

    }

    @Override
    public void update(float tpf) {

        Vector3f v2 = character.getLocalTranslation();
        Iterator<Obstacle> obit = poolObstacle.iterator();
        CollisionResults res = new CollisionResults();
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
            if (f.getX() < -110) {
                f.setX(f.getX() + 200);
            }
        }
    }
}
