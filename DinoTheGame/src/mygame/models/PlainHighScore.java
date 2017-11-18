/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.models;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.helper.Serializator;

/**
 *
 * @author Hayashi
 */
public class PlainHighScore implements Serializable{
    private final static long serialVersionUID = 1;
    
    private long lngHighestScore = 0;

    /**
     * @return the lngHighestScore
     */
    public long getLngHighestScore() {
        return lngHighestScore;
    }

    /**
     * @param lngHighestScore the lngHighestScore to set
     */
    public void setLngHighestScore(long lngHighestScore) {
        this.lngHighestScore = Math.max(lngHighestScore, this.lngHighestScore);
    }
    
    public void resetLngHighestScore(){
        this.lngHighestScore = 0;
    }
    
    public static PlainHighScore load() {
        try {
            Object obj = Serializator.fromFile(new File("game.obj.asc"));
            return (PlainHighScore) obj;
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return new PlainHighScore();
    }
    
    public void save() {
        try {
            Serializator.toFile(this, new File("game.obj.asc"));
        } catch (IOException ex) {
            
        }
    }
}
