/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.helper;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Irvan Hardyanto
 */
public class AudioManager{
    
    private Node localRootNode;
    private AudioNode currentMusic;
    private AssetManager assetManager;
    private List<AudioNode> BGMBank = new ArrayList<AudioNode>();
    
    public AudioManager(AssetManager assetManager,Node localRootNode){
        this.assetManager = assetManager;
        this.localRootNode = localRootNode;
    }
    
    public void addBackgroundMusic(String file){
        BGMBank.add(new AudioNode(assetManager,file, AudioData.DataType.Stream));
    }
    
    public void playMusic(int volume,boolean isLooping,boolean isPositional){
        Random musicRotator = new Random();
        int currentMusicIdx = musicRotator.nextInt(BGMBank.size());
        currentMusic = BGMBank.get(currentMusicIdx);
        currentMusic.setLooping(isLooping);
        currentMusic.setPositional(isPositional);
        currentMusic.setVolume(volume);
        localRootNode.attachChild(currentMusic);
        currentMusic.play();
    }
    
    public void pauseMusic(){
       currentMusic.pause();
    }

}
