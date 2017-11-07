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
package dinos.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author G. Christianto
 */
public abstract class SagutAppState extends BaseAppState {
    protected SimpleApplication sapp;
    protected final Node localRootNode;
    protected AssetManager assetManager;
    
    public SagutAppState (SimpleApplication sapp, String rootNodeName){
        this.sapp = sapp;
        this.localRootNode = new Node(rootNodeName);
    }
    
    @Override
    protected void initialize(Application app) {
        this.sapp.getRootNode().attachChild(localRootNode);
        this.assetManager = sapp.getAssetManager();
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
