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
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import javafx.concurrent.Task;

/**
 *
 * @author Hayashi
 */
public class HUDGuiState extends SagutAppState implements ScreenController {

    private Nifty nifty;
    protected static HUDGuiState hud;
    protected NiftyJmeDisplay niftyDisplay;
    protected TextRenderer lblScore;
    protected long scoreTotal;
    protected Task<Void> tskScoreUpdater;
    protected Thread thdScore;
    protected boolean stopRequested;

    public static HUDGuiState getCurrentInstance() {
        return hud;
    }

    public HUDGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
        hud = this;
        stopRequested = false;
        scoreTotal = 0;
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                sapp.getAssetManager(), sapp.getInputManager(), sapp.getAudioRenderer(), sapp.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/guiHUD.xml", "HUDGameScreen", this);
        sapp.getGuiViewPort().addProcessor(niftyDisplay);

        lblScore = nifty.getScreen("HUDGameScreen").findElementById("lblScore").getRenderer(TextRenderer.class);
    }

    public void updateScore() {
        updateScore((scoreTotal + 4));
    }

    public void updateScore(long score) {
        scoreTotal = score;
        if (lblScore != null) {
            lblScore.setText(String.valueOf(scoreTotal));
        }
    }

    // Debouncer Rate = 0.04s for an update score to be fired.
    protected double scoreDebouncerRate = 0.04;
    private double debouncerTemp = 0;

    @Override
    public void update(float tpf) {
        debouncerTemp += tpf;
        if (debouncerTemp >= scoreDebouncerRate) {
            debouncerTemp = 0;
            updateScore();
        }
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
