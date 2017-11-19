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
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import mygame.GameUtilities;
import mygame.KeyBindings;
import mygame.Main;
import mygame.models.GameCharacter;
import mygame.state.SagutAppState;
import mygame.state.gui.MainMenuGuiState;

/**
 *
 * @author Hayashi
 */
public final class MainMenuScreenState extends SagutAppState {
    
    private MainMenuGuiState guiMainMenu;
    private GameCharacter character;
    
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
        
        // Adding kizuna
        character = new GameCharacter(GameUtilities.getInstance().getAssetManager(), "Kizuna Ai", "Models/kizuna/kizuna.j3o");
        character.createControl();
        character.setCamera(camera);
        character.setLocation(0, -3, 0);
        character.addAndCreateAnimation("kizunaai_mesh");
        character.setAnimation("Walk");
        character.getSpatial().setLocalScale(2f);
        localRootNode.attachChild(character.getSpatial());
        
        // Lighting
        AmbientLight al=new AmbientLight();
        al.setColor(new ColorRGBA(0.7f, 0.7f, 0.7f, 0.3f));
        localRootNode.addLight(al);
        sapp.getViewPort().setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 0.8f, 1));
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
    protected void cleanup(Application app){
        inputManager.setCursorVisible(false);
        inputManager.removeListener(actionListener);
        this.stateManager.detach(guiMainMenu);
        sapp.getViewPort().setBackgroundColor(ColorRGBA.Black);
        super.cleanup(app);
    }
    
}
