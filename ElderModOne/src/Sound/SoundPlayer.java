/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author Nekel
 */
public class SoundPlayer extends Thread{
    private String soundfile;
    javazoom.jl.player.Player soundplayer;
    
    public SoundPlayer(String soundfile){
        try {
            this.soundfile = soundfile;
            soundplayer = new javazoom.jl.player.Player(new FileInputStream(soundfile));
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run(){
        play();
    }
    
    public void play(){
        try {
            soundplayer.play();
        } catch (JavaLayerException ex) {
            Logger.getLogger(SoundPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
