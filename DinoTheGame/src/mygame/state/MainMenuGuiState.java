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
public class MainMenuGuiState extends SagutGuiState {
    protected static MainMenuGuiState hud;

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
}
