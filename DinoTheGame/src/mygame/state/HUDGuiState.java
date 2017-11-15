/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Hayashi
 */
public class HUDGuiState extends SagutAppState implements ScreenController {

    private Nifty nifty;
    protected static HUDGuiState hud;
    protected NiftyJmeDisplay niftyDisplay;

    public static HUDGuiState getCurrentInstance() {
        return hud;
    }

    public HUDGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
        hud = this;
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                sapp.getAssetManager(), sapp.getInputManager(), sapp.getAudioRenderer(), sapp.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/guiHUD.xml", "HUDGameScreen", this);
        sapp.getGuiViewPort().addProcessor(niftyDisplay);
    }

    @Override
    protected void cleanup(Application app) {
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
