/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.gui;

import mygame.state.SagutGuiState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.elements.render.TextRenderer;
import javafx.concurrent.Task;

/**
 *
 * @author Hayashi
 */
public class HUDGuiState extends SagutGuiState {

    protected static HUDGuiState hud;
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
        nifty.fromXml("Interface/scrHUD.xml", "HUDGameScreen", this);
        lblScore = nifty.getScreen("HUDGameScreen")
                .findElementById("lblScore")
                .getRenderer(TextRenderer.class);
    }
    
    protected int pointPerTicks = 4;
    
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
    protected double scoreDebouncerRate = 0.04;
    private double debouncerTemp = 0;

    /**
     * Sets the score update ticks. This will add the point to the score in a
     * second rate.
     *
     * Default: 0.04s.
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
    public double getScoreTicks(){
        return this.scoreDebouncerRate;
    }
    
       
    @Override
    public void update(float tpf) {
        debouncerTemp += tpf;
        if (debouncerTemp >= scoreDebouncerRate) {
            debouncerTemp = 0;
            updateScore();
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
}