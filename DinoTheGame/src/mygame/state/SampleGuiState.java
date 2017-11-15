/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.state;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author Hayashi
 */
public class SampleGuiState extends SagutGuiState {
    
    public SampleGuiState(SimpleApplication sapp, String rootNodeName) {
        super(sapp, rootNodeName);
    }

    @Override
    protected void init(AppStateManager stateManager, Application app) {
        nifty.fromXml("Interface/test-gui.xml", "GScreen0", this);
    }
    
}
