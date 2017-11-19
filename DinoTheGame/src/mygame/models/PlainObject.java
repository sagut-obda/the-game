/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
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

    protected float x, y, z;
    protected float vy, vx, vz;
    protected String nameObject;
    protected float height, width, length;
    protected Material material;
    protected Texture texture;

    public PlainObject(float x, float y, float z, float vy, float vx, float vz, String nameObject, float height, float width, AssetManager assetManager, Texture texture, Material material, int type, float length) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vy = vy;
        this.vx = vx;
        this.vz = vz;
        this.nameObject = nameObject;
        this.height = height;
        this.width = width;
        this.length = length;
        switch (type) {
            case 1:
                Box b = new Box(length / 2, height / 2, width / 2);
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

    public PlainObject(float x, float y, float z, float height, float width, float length, AssetManager assetManager, String name, String path) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.height = height;
        this.length = length;
        this.width = width;
        this.nameObject = name;
        this.spatial = assetManager.loadModel(path);
        this.spatial.setLocalTranslation(x, y, z);

    }

    public PlainObject(AssetManager assetManager, String name, String path) {
        this.spatial = assetManager.loadModel(path);
        this.nameObject = name;
        this.x = spatial.getLocalTranslation().x;
        this.y = spatial.getLocalTranslation().y;
        this.z = spatial.getLocalTranslation().z;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
    }

    public PlainObject(AssetManager assetManager, String name, String path, float vx, float vy, float vz) {
        this.spatial = assetManager.loadModel(path);
        this.nameObject = name;
        this.x = spatial.getLocalTranslation().x;
        this.y = spatial.getLocalTranslation().y;
        this.z = spatial.getLocalTranslation().z;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
    }

    public PlainObject(Spatial spatial, float vx, float vy, float vz) {
        this.spatial = spatial;
        this.x = spatial.getLocalTranslation().x;
        this.y = spatial.getLocalTranslation().y;
        this.z = spatial.getLocalTranslation().z;
        this.vy = vy;
        this.vx = vx;
        this.vz = vz;
    }

    public PlainObject(Spatial spatial, Texture texture) {
        this.spatial = spatial;
        this.texture = texture;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.x += vx * tpf;
        this.y += vy * tpf;
        this.z += vz * tpf;
        this.spatial.setLocalTranslation(x, y, z);

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Vector3f getLocalTranslation() {
        return this.spatial.getLocalTranslation();
    }

    public int collideWith(Collidable other, CollisionResults result) {
        return this.spatial.collideWith(other, result);
    }

    public BoundingVolume getWorldBound() {
        return this.spatial.getWorldBound();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    private void resetPosition(float x, float y, float z) {
        this.spatial.setLocalTranslation(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setX(float x) {
        resetPosition(x, y, z);
    }

    public void setY(float y) {
        resetPosition(x, y, z);
    }

    public void setZ(float z) {
        resetPosition(x, y, z);
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public void setVz(float vz) {
        this.vz = vz;
    }

}
