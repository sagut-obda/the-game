package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.state.MainMenuState;
import mygame.state.SagutAppState;
import mygame.state.screen.MainMenuScreenState;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static boolean quickDevelopmentMode = true;

    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        if (quickDevelopmentMode) {
            configForQuickDevelopmentMode(app);
        }
        app.start();
    }

    public static void configForQuickDevelopmentMode(SimpleApplication app) {
        AppSettings apset = new AppSettings(true);
        apset.setTitle("Run Ai");
        app.setShowSettings(false);
        app.setSettings(apset);
    }

    private AppState activeScreen;

    private void switchState(SagutAppState nextState) {
        if (activeScreen != null) {
            activeScreen.setEnabled(false);
            stateManager.detach(activeScreen);
        }
        activeScreen = nextState;
        stateManager.attach(nextState);
    }

    @Override
    public void simpleInitApp() {
        this.triggerMainMenu();
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }

    public void triggerStartGame() {
        switchState(new MainMenuState(this));
    }

    public void triggerMainMenu() {
        switchState(new MainMenuScreenState(this));
    }

    public void triggerEndingScreen() {

    }
}
