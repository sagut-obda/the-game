package mygame;

import com.jme3.asset.AssetManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ferdian
 */
public class GameUtilities {

    private static GameUtilities instance;
    private AssetManager assetManager;
    private float edgesLeft;
    private float edgesRight;
    private int manyFloor;
    private int manyObstacle;

    private GameUtilities(AssetManager assetManager) {
        this.assetManager = assetManager;
        edgesLeft = -5.1f;
        edgesRight = -1f;
        manyFloor = 5;
        manyObstacle = 2;
    }

    public float getEdgesLeft() {
        return edgesLeft;
    }

    public float getEdgesRight() {
        return edgesRight;
    }

    public int getManyFloor() {
        return manyFloor;
    }

    public int getManyObstacle() {
        return manyObstacle;
    }

    public static void Initialize(AssetManager assetManager) {
        if (instance != null) {
            return;
        }
        instance = new GameUtilities(assetManager);

    }

    public static GameUtilities getInstance() {
        return instance;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
