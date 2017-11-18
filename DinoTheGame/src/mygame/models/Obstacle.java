/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Ferdian
 */
public class Obstacle extends PlainObject {

    public Obstacle(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        super(assetManager, name, path, vx, vy, vz);
    }

    public Obstacle(Spatial spatial, float vx, float vy, float vz) {
        super(spatial, vx, vy, vz);
    }

    public void move(float tpf) {
        controlUpdate(tpf);
    }

    public void regenerate() {
        Random rand = new Random();
        int nextPosition = rand.nextInt(3);
        boolean isLog = rand.nextBoolean();
        BoundingBox bb = (BoundingBox) this.spatial.getWorldBound();
        if (isLog) {
            this.spatial = assetManager.loadModel("Models/tree/tree.j3o");
            bb.setXExtent(0.44597244f);
            bb.setYExtent(31.001266f);
            bb.setZExtent(0.44597292f);
            this.spatial.setModelBound(bb);
        } else {
            this.spatial = assetManager.loadModel("Models/log/log.j3o");
            nextPosition = 1;
        }
        this.setZ((float) (-2.1 * nextPosition - 1));
        this.setX(100);
    }

    private AssetManager assetManager;

    public void setAsset(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setSpatial(Spatial s) {
        this.spatial = s;
    }

}
