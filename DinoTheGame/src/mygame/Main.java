package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import mygame.state.MainMenuState;
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

    @Override
    public void simpleInitApp() {
        // This will load the main menu first, as a loader.
        stateManager.attach(new MainMenuScreenState(this));
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
}
