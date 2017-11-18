/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.screen;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import mygame.state.SagutAppState;
import mygame.state.gui.MainMenuGuiState;

/**
 *
 * @author Hayashi
 */
public final class MainMenuScreenState extends SagutAppState {
    
    private MainMenuGuiState guiMainMenu;
    
    public MainMenuScreenState(SimpleApplication sapp) {
        super(sapp, "MainMenuScreen");
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        guiMainMenu = new MainMenuGuiState(sapp);
        stateManager.attach(guiMainMenu);
        inputManager.setCursorVisible(true);
    }
    
    @Override
    protected void onDisable(){
        guiMainMenu.setEnabled(false);
    }
    
}
