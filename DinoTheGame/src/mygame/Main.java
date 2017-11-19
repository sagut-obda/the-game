package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import mygame.state.SagutAppState;
import mygame.state.screen.GameScreenAppState;
import mygame.state.screen.MainMenuScreenState;

/**
 * This is the Main Class of our Game.
 *
 */
public class Main extends SimpleApplication {

    /**
     * Sets the quick development mode. This will activate the super duper fast
     * auto start program. Just try it.
     */
    public static boolean quickDevelopmentMode = true;

    /**
     * Main program starting point.
     *
     * @param args
     */
    public static void main(String[] args) {
        Main app = new Main();
        app.setDisplayFps(false);
        app.setDisplayStatView(false);
        if (quickDevelopmentMode) {
            configForQuickDevelopmentMode(app);
        }
        app.start();

    }

    /**
     * This will recondition the file for development. So we don't have to waste
     * clicks.
     *
     * @param app app instance.
     */
    public static void configForQuickDevelopmentMode(SimpleApplication app) {
        AppSettings apset = new AppSettings(true);
        apset.setTitle("Run Ai");
        app.setShowSettings(false);
        app.setSettings(apset);
    }

    private AppState activeScreen;

    /**
     * Switch state to a new desired state.
     *
     * @param nextState nextState that'll be attached
     */
    private void switchState(SagutAppState nextState) {
        if (activeScreen != null) {
            stateManager.detach(activeScreen);
        }
        activeScreen = nextState;
        stateManager.attach(nextState);
    }

    /**
     * Initializer for our game.
     */
    @Override
    public void simpleInitApp() {
        GameUtilities.Initialize(this.getAssetManager());
        this.triggerMainMenu();
    }

    /**
     * Simple updater for this game scope.
     *
     * @param tpf
     */
    @Override
    public void simpleUpdate(float tpf) {

    }

    /**
     * Simple RenderManager Manager to manage the Render.
     *
     * @param rm
     */
    @Override
    public void simpleRender(RenderManager rm) {

    }

    /**
     * Trigger to start the game. will reset the score and remove last state.
     */
    public void triggerStartGame() {
        switchState(new GameScreenAppState(this));
    }

    /**
     * Trigger the displayment to main menu. Will reset the last state.
     */
    public void triggerMainMenu() {
        switchState(new MainMenuScreenState(this));
    }
}
