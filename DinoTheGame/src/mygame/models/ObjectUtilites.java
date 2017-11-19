/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import mygame.GameUtilities;

/**
 *
 * @author Ferdian
 */
public final class ObjectUtilites extends PlainObject {

    public ObjectUtilites(Spatial spatial, Texture texture) {
        super(spatial, texture);
        material = new Material(GameUtilities.getInstance().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        initialize();
    }

    public void initialize() {
        this.material.setTexture("ColorMap", texture);
        this.spatial.setMaterial(material);
    }

}
