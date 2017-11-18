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
import mygame.GameUtilities;

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
        if (isLog) {
            this.spatial = GameUtilities.getInstance().getAssetManager().loadModel("Models/tree/tree.j3o");
            BoundingBox bb = (BoundingBox) this.spatial.getWorldBound();
            bb.setXExtent(0.000001f);
            bb.setYExtent(40);
            bb.setZExtent(0.000001f);
            this.spatial.setModelBound(bb);
        } else {
            this.spatial = GameUtilities.getInstance().getAssetManager().loadModel("Models/log/log.j3o");
            nextPosition = 1;
        }
        this.setZ((float) (-2.1 * nextPosition - 1));
        this.setX(200);
    }

    public void setValueOfHitBox(float x, float y, float z) {
        BoundingBox bb1 = (BoundingBox) getWorldBound();
        bb1.setXExtent(x);
        bb1.setYExtent(y);
        bb1.setZExtent(z);
        this.spatial.setModelBound(bb1);
    }
    public void printInfo(){
        System.out.println(this.spatial.getWorldBound().toString());
    }

}
