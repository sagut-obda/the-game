/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.gui;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import mygame.Main;
import mygame.state.SagutGuiState;

/**
 *
 * @author Hayashi
 */
public class MainMenuGuiState extends SagutGuiState {
    protected static MainMenuGuiState hud;
    
    public MainMenuGuiState(SimpleApplication sapp){
        super(sapp, "MainMenuGUI");
    }
    
    public static MainMenuGuiState getCurrentInstance() {
        return hud;
    }

    public MainMenuGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
         hud = this;
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        nifty.fromXml("Interface/main-menu-gui.xml", "scrMainMenu", this);
    }
    
    public void btnExit_Click() {
        sapp.stop();
    }
    
    public void btnStart_Click() {
        ((Main)sapp).triggerStartGame();
    }
}
