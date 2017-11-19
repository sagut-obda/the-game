/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.gui;

import mygame.state.SagutGuiState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import javafx.concurrent.Task;
import mygame.Main;
import mygame.models.PlainHighScore;
import mygame.state.MainMenuState;

/**
 *
 * @author Hayashi
 */
public class HUDGuiState extends SagutGuiState {

    protected static HUDGuiState hud;
    protected TextRenderer lblScore;
    protected TextRenderer lblScoreHigh;
    protected TextRenderer lblValueScore;
    protected TextRenderer lblValueHigh;
    protected long scoreTotal;
    //protected Task<Void> tskScoreUpdater;
    //protected Thread thdScore;
    protected boolean stopRequested;
    protected PlainHighScore hs;

    public static HUDGuiState getCurrentInstance() {
        return hud;
    }

    public HUDGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
        hud = this;
        stopRequested = false;
        scoreTotal = 0;
        hs = PlainHighScore.load();
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        nifty.fromXml("Interface/hud-gui.xml", "scrHUD", this);
        Screen scrHUD = nifty.getScreen("scrHUD");
        lblScore = scrHUD.findElementById("lblScore")
                .getRenderer(TextRenderer.class);
        lblScoreHigh = scrHUD.findElementById("lblScoreHigh")
                .getRenderer(TextRenderer.class);
        Screen scrGameOver = nifty.getScreen("scrGameOver");
        lblValueHigh = scrGameOver.findElementById("lblValueHigh")
                .getRenderer(TextRenderer.class);
        lblValueScore = scrGameOver.findElementById("lblValueScore")
                .getRenderer(TextRenderer.class);
        lblScoreHigh.setText(String.valueOf(hs.getLngHighestScore()));
    }

    protected int pointPerTicks = 1;

    public void updateScore() {
        updateScore(scoreTotal + pointPerTicks);
    }

    public void updateScore(long score) {
        scoreTotal = score;
        if (lblScore != null) {
            lblScore.setText(String.valueOf(scoreTotal));
        }
    }

    // Debouncer Rate = 0.04s for an update score to be fired.
    protected double scoreDebouncerRate = 0.5f;
    private double debouncerTemp = 0;
    protected boolean isOver = false;

    /**
     * Sets the score update ticks. This will add the point to the score in a
     * second rate.
     *
     * Default: 0.5s.
     *
     * @param second how long to wait before the score will ticks to update.
     */
    public void setScoreTicks(double second) {
        this.scoreDebouncerRate = second;
    }

    /**
     * Gets the score update ticks.
     *
     * @return how long will the score wait until it added a new point.
     */
    public double getScoreTicks() {
        return this.scoreDebouncerRate;
    }

    @Override
    public void update(float tpf) {
        if (!isOver) {
            debouncerTemp += tpf;
            if (debouncerTemp >= scoreDebouncerRate) {
                debouncerTemp = 0;
                updateScore();
            }
        }
    }

    /**
     * @return the pointPerTicks
     */
    public int getPointPerTicks() {
        return pointPerTicks;
    }

    /**
     * @param pointPerTicks the pointPerTicks to set
     */
    public void setPointPerTicks(int pointPerTicks) {
        this.pointPerTicks = pointPerTicks;
    }

    public void triggerShowGameOverScreen() {
        isOver = true;
        hs.setLngHighestScore(scoreTotal);
        hs.save();
        lblValueHigh.setText(String.valueOf(hs.getLngHighestScore()));
        lblValueScore.setText(String.valueOf(scoreTotal));
        nifty.gotoScreen("scrGameOver");
    }
    
    public void triggerResetScore() {
        updateScore(0);
        nifty.gotoScreen("scrHUD");
        isOver = false;
    }
}
