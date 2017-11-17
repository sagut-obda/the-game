/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import java.util.Random;

/**
 *
 * @author Ferdian
 */
public class Obstacle extends PlainObject{
    
    public Obstacle(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        super(assetManager, name, path, vx, vy, vz);
    }
    public Obstacle(Spatial spatial ,float vx,float vy,float vz){   
        super(spatial,vx,vy,vz);
    }
    public void move(float tpf){
        controlUpdate(tpf);
    }
    public void regenerate(){
      Random rand = new Random();
      int nextPosition = rand.nextInt(3);
      this.setZ((float)(-2.1*nextPosition-1));
      this.setX(100);
    }
    
}
