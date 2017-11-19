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
public class PlainHighScore implements Serializable {

    private final static long serialVersionUID = 1;

    private long lngHighestScore = 0;

    /**
     * Get the biggest higscore available on the memory.
     *
     * @return the lngHighestScore
     */
    public long getLngHighestScore() {
        return lngHighestScore;
    }

    /**
     * Set the score. This will automaticly set the biggest score available.
     *
     * @param lngHighestScore the lngHighestScore to set
     */
    public void setLngHighestScore(long lngHighestScore) {
        this.lngHighestScore = Math.max(lngHighestScore, this.lngHighestScore);
    }

    /**
     * This will reset the high score memory.
     */
    public void resetLngHighestScore() {
        this.lngHighestScore = 0;
    }

    /**
     * Load the highscore from file.
     *
     * @return PlainHighScore object
     */
    public static PlainHighScore load() {
        try {
            Object obj = Serializator.fromFile(new File("game.obj.asc"));
            return (PlainHighScore) obj;
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
        }
        return new PlainHighScore();
    }

    /**
     * Saves an object to a file
     */
    public void save() {
        try {
            Serializator.toFile(this, new File("game.obj.asc"));
        } catch (IOException ex) {

        }
    }
}
