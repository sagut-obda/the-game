/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state.screen;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import mygame.KeyBindings;
import mygame.Main;
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
        
        // Key init
        KeyBindings k = new KeyBindings();
        inputManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Start");
    }
    
    private final ActionListener actionListener = new ActionListener() {
        protected boolean startDebouncer = false;
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("Start") && !startDebouncer) {
                startDebouncer = true;
                ((Main)sapp).triggerStartGame();
            }
        }
    };
    
    @Override
    protected void onDisable(){
        inputManager.setCursorVisible(false);
        inputManager.removeListener(actionListener);
        guiMainMenu.setEnabled(false);
    }
    
}
