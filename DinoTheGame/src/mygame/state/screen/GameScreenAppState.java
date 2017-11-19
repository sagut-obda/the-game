/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.screen;

import mygame.helper.AudioManager;
import com.jme3.animation.AnimControl;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.GameUtilities;
import mygame.KeyBindings;
import mygame.models.Floor;
import mygame.models.GameCharacter;
import mygame.models.ObjectUtilites;
import mygame.models.Obstacle;
import mygame.state.SagutAppState;
import mygame.state.gui.HUDGuiState;
import org.lwjgl.input.Keyboard;

/**
 *
 */
public class GameScreenAppState extends SagutAppState {

    private AudioManager audioManager;
    private GameCharacter character;
    private LinkedList<Floor> poolFloor;
    private LinkedList<Obstacle> poolObstacle;
    

    public GameScreenAppState(SimpleApplication sapp) {
        super(sapp, "Main Menu");
        initKeys();
        audioManager = new AudioManager(assetManager, localRootNode);
    }
    
    private GameScreenAppState dis;
    
    /**
     * the method that build up the scenario of games
     * @param stateManager : the state manager
     * @param app :the app
     */
    @Override
    public void init(AppStateManager stateManager, Application app) {
        stateManager.attach(this.bulletappstate);
        audioManager.addMusic("Sounds/finalv1.ogg");
        audioManager.addMusic("Sounds/finalv2.ogg");
        audioManager.addMusic("Sounds/finalv3.ogg");
        audioManager.addMusic("Sounds/ncs2016.ogg");
        audioManager.playMusic(5, true, false);
        dis = this;
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
        character = new GameCharacter(GameUtilities.getInstance().getAssetManager(), "Kizuna Ai", "Models/kizuna/kizuna.j3o");
        character.setInputManager(app.getInputManager());
        
        //------------------------------
        //Set Character Control for Player (character)
        character.createControl();
        character.setCamera(camera);
        character.setLocation(10, 2, -1);
        character.addAndCreateAnimation("kizunaai_mesh");
        character.setAnimation("Walk");
        try {
            character.getControl().setJumpSpeed(30);
            character.getControl().setFallSpeed(50);
            character.getControl().setGravity(70);
        } catch (Exception ex) {
            Logger.getLogger(GameScreenAppState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
    
    /**
     * initialize the keys for player can play it
     */
    private void initKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyBindings.GO_LEFT));
        inputManager.addMapping("Right", new KeyTrigger(KeyBindings.GO_RIGHT));
        inputManager.addMapping("Jump", new KeyTrigger(KeyBindings.JUMP));
        inputManager.addMapping("Pause", new KeyTrigger(KeyBindings.GO_PAUSE));
        inputManager.addMapping("Restart", new KeyTrigger(KeyBindings.GO_RESTART));
        inputManager.addListener(actionListener, "Left", "Right", "Jump", "Pause", "Restart");

    }
    
    /**
     * method for add decorate such as water , the sun , and stone
     */
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
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Left") && !gameOverDebouncer && dis.isEnabled()) {
                character.isLeft(isPressed);
            } else if (name.equals("Right") && !gameOverDebouncer && dis.isEnabled()) {
                character.isRight(isPressed);
            } else if (name.equals("Jump") && !gameOverDebouncer && dis.isEnabled()) {
                character.jump();
            } else if (name.equals("Pause") && isPressed && !gameOverDebouncer) {
                dis.setEnabled(!dis.isEnabled());
            } else if (name.equals("Restart") && isPressed && !gameOverDebouncer && !dis.isEnabled()){
                reset();
            }
        }
    };
    
    private final ActionListener gameRestart = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Restart") && gameOverDebouncer) {
                reset();
            }
        }

    };
    
    protected boolean gameOverDebouncer = false;
    protected double timePassed = 0;
    protected float incraseSpeedBy = 0.2f;
    protected double incraseSpeedOn = 10;
    protected float speed = 1f;
    protected float speedMax = 8f;
    protected boolean speedHasUpdatedDebouncer = false;
    
    /**
     * main loop for this method
     * @param tpf : time per frame the constant value that needed for fair play
     */
    @Override
    public void update(float tpf) {
        timePassed += tpf;
        if(timePassed >= incraseSpeedOn && !speedHasUpdatedDebouncer && !gameOverDebouncer && speed <= speedMax) {
            timePassed = 0;
            speed += speed * incraseSpeedBy;
            speedHasUpdatedDebouncer = true;
            System.out.println("Speed Incrased by " + speed);
        } else {
            speedHasUpdatedDebouncer = false;
        }
        
        character.move();
        Iterator<Obstacle> obit = poolObstacle.iterator();
        CollisionResults res = new CollisionResults();
        while (obit.hasNext()) {
            Obstacle o = obit.next();
            o.move(tpf * speed);
            if (o.getX() < -20) {
                localRootNode.detachChild(o.getSpatial());
                o.regenerate();
                localRootNode.attachChild(o.getSpatial());
            }
            int x = character.collideWith(o.getWorldBound(), res);
            if (x>30&&!gameOverDebouncer) {
                audioManager.pauseMusic();
                gameOverDebouncer = true;
                inputManager.removeListener(actionListener);
                inputManager.reset();
                inputManager.addMapping("Restart", new KeyTrigger(Keyboard.KEY_SPACE));
                inputManager.addListener(gameRestart, "Restart");
                HUDGuiState.getCurrentInstance().triggerShowGameOverScreen();
                //reset();
            }else if(x!=0){
              //  o.printInfo();
                System.out.println(x);
            }

        }
        Iterator<Floor> it = poolFloor.iterator();
        while (it.hasNext()) {
            Floor f = it.next();
            f.move(tpf * speed);
        }
    }
    
    @Override
    protected void onEnable() {
        HUDGuiState.getCurrentInstance().setEnabled(true);
        localRootNode.getChild("kizunaai_mesh").getControl(AnimControl.class).setEnabled(true);
    }
    
    @Override
    protected void onDisable() {
        HUDGuiState.getCurrentInstance().setEnabled(false);
        localRootNode.getChild("kizunaai_mesh").getControl(AnimControl.class).setEnabled(false);
    }
    
    /**
     * method for restart the game
     */
    public void reset(){
        // reset method only reset the obstacle position and score
        audioManager.stopMusic();
        audioManager.playMusic(5, true, false);
        Iterator<Obstacle> it = poolObstacle.iterator();
        int i = 0;
        while(it.hasNext()){
            Obstacle o = it.next();
            o.setX(150+50*i);
            i++;
        }
        gameOverDebouncer = false ;
        inputManager.removeListener(gameRestart);
        inputManager.reset();
        initKeys();
        HUDGuiState.getCurrentInstance().triggerResetScore();
        speed = 1.0f;
        timePassed = 0;
        this.setEnabled(true);
    }
}
