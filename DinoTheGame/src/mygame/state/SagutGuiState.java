/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Hayashi
 */
public abstract class SagutGuiState extends SagutAppState implements ScreenController {
    
    protected Nifty nifty; 
    protected NiftyJmeDisplay niftyDisplay;
    
    public SagutGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
    }
    
    @Override
    protected void initialize(Application app) {
        bulletappstate = new BulletAppState();
        rootNode.attachChild(localRootNode);
        niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                sapp.getAssetManager(), sapp.getInputManager(), sapp.getAudioRenderer(), sapp.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
        this.init(this.getStateManager(), app);
        sapp.getGuiViewPort().addProcessor(niftyDisplay);
    }

    @Override
    public void onDisable() {
        sapp.getGuiViewPort().removeProcessor(niftyDisplay);
    }
    
    @Override
    public void bind(Nifty nifty, Screen screen) {

    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
    
}
