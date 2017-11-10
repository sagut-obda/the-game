package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import mygame.state.MainMenuState;


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
        stateManager.attach(new MainMenuState(this));
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
