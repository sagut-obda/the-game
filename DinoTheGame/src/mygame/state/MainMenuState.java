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
import com.jme3.texture.Texture;
import java.util.Iterator;
import java.util.LinkedList;
import mygame.GameUtilities;
import mygame.KeyBindings;
import mygame.models.Floor;
import mygame.models.GameCharacter;
import mygame.models.ObjectUtilites;
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
        this.bulletappstate.setDebugEnabled(true);
        this.flycamera.setEnabled(false);
        //-----------------------------
        //Load all Model Character

        character = new GameCharacter(GameUtilities.getInstance().getAssetManager(), "Kizuna Ai", "Models/kizuna/kizuna.j3o");
        character.setInputManager(app.getInputManager());
        //------------------------------
        //Set Character Control for Player (character)
        character.createControl();
        character.setCamera(camera);
        character.setLocation(10, 2, -1);
        character.addAndCreateAnimation("kizunaai_mesh");
        character.setAnimation("Walk");
        //-----------------------------------------------------
        //Attach to the localRoot and add all items to Pooling Structure data
        localRootNode.attachChild(character.getSpatial());
        for (int i = 0; i < GameUtilities.getInstance().getManyFloor(); i++) {
            Floor f = new Floor(((Node) assetManager.loadModel("Scenes/LandmarkScene.j3o")).getChild("Lantai"), -20, 0, 0);
            Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            Texture t = assetManager.loadTexture("Models/asset-Grass.jpg");
            f.setMaterialTexture(m, t);
            f.setX(f.getSpatial().getLocalScale().x * i);
            localRootNode.attachChild(f.getSpatial());
            poolFloor.add(f);
        }
        
        for (int i = 0; i < GameUtilities.getInstance().getManyObstacle(); i++) {
            Spatial tree = assetManager.loadModel("Models/tree/tree.j3o").clone();
            Obstacle o = new Obstacle(tree, -20, 0, 0);
            o.setValueOfHitBox(0.00001f, 50, 0.00001f);
            o.setX(150 + 50 * i);
            o.setZ(-1);
            poolObstacle.add(o);
            localRootNode.attachChild(o.getSpatial());
        }
        addDecorate();
        //------------------------------
        //---------- bullet appstate controller in here
        bulletappstate.getPhysicsSpace().add(localRootNode.getChild("World").getControl(RigidBodyControl.class));
        try {
            bulletappstate.getPhysicsSpace().add(character.getControl());
        } catch (Exception ex) {

        }
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

    private void addDecorate() {
        ObjectUtilites iniAir = new ObjectUtilites(((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("World"), assetManager.loadTexture("Models/watertexture.jpg"));
        ObjectUtilites iniBatuKiri = new ObjectUtilites(((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("Stone"), assetManager.loadTexture("Models/stoneasset.jpg"));
        ObjectUtilites iniBatuKanan = new ObjectUtilites(((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("Stone"), assetManager.loadTexture("Models/stoneasset.jpg"));
        iniBatuKanan.getSpatial().setLocalTranslation(0, -0.5f, -7);
        ObjectUtilites iniTrump = new ObjectUtilites(((Node) (assetManager.loadModel("Scenes/World.j3o"))).getChild("Edges"), assetManager.loadTexture("Models/assetMatahari.jpg"));
        localRootNode.attachChild(iniAir.getSpatial());
        localRootNode.attachChild(iniBatuKiri.getSpatial());
        localRootNode.attachChild(iniBatuKanan.getSpatial());
        localRootNode.attachChild(iniTrump.getSpatial());
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
            if (x>30&&!gameOverDebouncer) {
                System.out.println(x);
                gameOverDebouncer = true;
                HUDGuiState.getCurrentInstance().triggerShowGameOverScreen();
            }else if(x!=0){
              //  o.printInfo();
                System.out.println(x);
            }

        }
        Iterator<Floor> it = poolFloor.iterator();
        while (it.hasNext()) {
            Floor f = it.next();
            f.move(tpf);
        }

    }
}
