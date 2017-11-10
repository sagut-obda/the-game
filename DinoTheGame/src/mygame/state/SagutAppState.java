/*
 * This class is designed to simplify the programming routine
 * that has similiarities with doing things. So we decided
 * to make a root class for all of them that can be written
 * once and will do all those full of copy-paste things.
 *
 * Refs for this code are
 * https://jmonkeyengine.github.io/wiki/jme3/advanced/application_states.html#baseappstate
 *
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

/**
 *
 * @author G. Christianto
 */
public abstract class SagutAppState extends BaseAppState {
    protected SimpleApplication sapp;
    protected final Node localRootNode;
    protected final Node rootNode ;
    protected final AssetManager assetManager;
    protected FlyByCamera flycamera ;
    protected BulletAppState bulletappstate;
    protected CharacterControl characterControl ;
    protected ChaseCamera chaseCamera;
    protected Camera camera;
    protected InputManager inputManager;
    public SagutAppState (SimpleApplication sapp, String rootNodeName){
        this.sapp = sapp;
        this.localRootNode = new Node(rootNodeName);
        this.rootNode =sapp.getRootNode();
        flycamera = sapp.getFlyByCamera();
        this.assetManager = sapp.getAssetManager();
        camera= sapp.getCamera();
        inputManager = sapp.getInputManager();
    }
    
    @Override
    protected void initialize(Application app) {
        bulletappstate = new BulletAppState();
        rootNode.attachChild(localRootNode);
        this.init(this.getStateManager(), app);
        
    }
    
    protected abstract void init(AppStateManager stateManager, Application app);

    @Override
    protected void cleanup(Application app) {
        this.sapp.getRootNode().detachChild(localRootNode);
    }

    @Override
    protected void onEnable() {
    
    }

    @Override
    protected void onDisable() {
    
    }
}
