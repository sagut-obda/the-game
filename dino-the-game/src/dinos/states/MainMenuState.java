/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dinos.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import dinos.Main;

/**
 *
 * @author hayashi
 */
public class MainMenuState extends AbstractAppState {

    private SimpleApplication app;
    private Node rootNode;
    private final Node localRootNode = new Node("MainMenu");
    private AssetManager assetManager;

    public MainMenuState(Main aThis) {
        
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = (SimpleApplication) app;
        rootNode = this.app.getRootNode();
        rootNode.attachChild(localRootNode);
        assetManager = this.app.getAssetManager();

        // Test purposes only.
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        localRootNode.attachChild(geom);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        rootNode.detachChild(localRootNode);
    }

    @Override
    public void setEnabled(boolean enabled) {

    }

    @Override
    public void update(float tpf) {
        Spatial kubus = localRootNode.getChild("Box");
        if( kubus != null ){
            kubus.rotate(0.1f * tpf, 0.2f * tpf, -0.3f * tpf);
        }
    }
}
