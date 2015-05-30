/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Nekel
 */
public class SoundFile {
    private static long IDs = 0;
    
    private String soundFileLocation;
    private long id;
    private Player player;
    private boolean pause;
    
    public SoundFile(String file){
        try {
            this.soundFileLocation = file;
            this.id = IDs++;
            this.pause = false;
            this.player = new Player(new FileInputStream(file));
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SoundFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void playFrame(){
        try {
            player.play(1);
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSoundFileLocation() {
        return soundFileLocation;
    }

    public long getId() {
        return id;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    public boolean isDone(){
        return player.isComplete();
    }
    public void close(){
        player.close();
    }
    
}
