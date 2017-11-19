/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.helper;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that manages background music
 * @author Irvan Hardyanto
 */
public class AudioManager {
    private Node localRootNode;
    private AudioNode currentMusic;
    private List<AudioNode> BGMBank = new ArrayList<AudioNode>();
    private AssetManager assetManager;
    
    public AudioManager(AssetManager assetManager,Node localRootNode){
        this.assetManager = assetManager;
        this.localRootNode = localRootNode;
    }
    
    /**
     * Adds an music file 
     * @param file FilePath
     */
    public void addMusic(String file){
       BGMBank.add(new AudioNode(assetManager,file,AudioData.DataType.Stream));
    }
    
    /**
     * choose a random music from BGMBank, and then play it
     * @param volume music volume
     * @param isLooping true : music loops, false, music don't loop
     * @param isPositional 
     */
    public void playMusic(int volume,boolean isLooping,boolean isPositional){
        Random rand = new Random();
        int currentMusicIdx = rand.nextInt(BGMBank.size());
        currentMusic = BGMBank.get(currentMusicIdx);
        currentMusic.setLooping(isLooping);
        currentMusic.setPositional(isPositional);
        localRootNode.attachChild(currentMusic);
        currentMusic.play();
    }
    
    /**
     * Pause a music
     */
    public void pauseMusic(){
        currentMusic.pause();
    }
}
