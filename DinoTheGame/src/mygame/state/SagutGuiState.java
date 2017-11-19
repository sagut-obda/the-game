/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *  This class represents the GUI as an AppState. Made because
 *  the GUI State requires Nifty, NiftyDisplay, and ScreenController.
 *
 */
public abstract class SagutGuiState extends SagutAppState implements ScreenController {
    
    protected Nifty nifty; 
    protected NiftyJmeDisplay niftyDisplay;
    protected ViewPort guiViewPort;
    
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
        guiViewPort = app.getGuiViewPort();
        this.init(this.getStateManager(), app);
        sapp.getGuiViewPort().addProcessor(niftyDisplay);
    }
    
    @Override
    public void cleanup(Application app){
        super.cleanup(app);
        sapp.getGuiViewPort().removeProcessor(niftyDisplay);
    }

    @Override
    public void onDisable() {
        
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
