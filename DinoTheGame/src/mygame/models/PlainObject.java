/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

/**
 *
 * @author Ferdian
 */
public class PlainObject extends AbstractControl {
    protected float x , y , z;
    protected float vy,vx,vz ;
    protected String nameObject ;
    protected float height , width ,length;
    

    public PlainObject(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width,AssetManager assetManager ,Texture texture , Material material,int type,float length) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vy = vy;
        this.vx = vx;
        this.vz = vz;
        this.nameObject = nameObject;
        this.height = height;
        this.width = width;
        this.length = length ;
        switch(type){
            case 1 :
                Box b = new Box(length/2, height/2, width/2);
                this.spatial = new Geometry(nameObject, b);
                break;
            case 2:
                Sphere s = new Sphere(100, 100, width);
                this.spatial = new Geometry(nameObject, s);
                break;
            case 3:
                Quad q = new Quad(length, width);
                this.spatial = new Geometry(nameObject, q);
                break;
            
        }
        this.spatial.setLocalTranslation(x, y, z);
        this.spatial.setMaterial(material);
        
    }
    public PlainObject(float x , float y, float z,float height,float width,float length,AssetManager assetManager,String name,String path){
       this.x = x ;
       this.y = y;
       this.z = z ;
       this.height = height;
       this.length = length;
       this.width = width ;
       this.nameObject = name ;
       this.spatial = assetManager.loadModel(path);
       
    }

    @Override
    protected void controlUpdate(float tpf) {
          this.x += vx*tpf;
          this.y += vy*tpf;
          this.z += vz*tpf;
          this.spatial.setLocalTranslation(x, y, z);
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
