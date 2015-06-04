/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Nekel
 */
public class SoundBatch extends Thread{
    ArrayList<SoundFile> sounds;
    
    public SoundBatch(){
        super("Sound Batch");
        sounds = new ArrayList<SoundFile>();
    }
    
    public void addSound(SoundFile soundfile){
        //synchronized(this){
            sounds.add(soundfile);
        //}
    }
    
    @Override
    public void run(){
            while(true){
                for(int i=0; i< sounds.size(); i++){
                    SoundFile player = sounds.get(i);
                    if(player.isDone()){
                        player.close();
                        sounds.remove(i);
                        continue;
                    }
                    if(!player.isPause()){
                        player.playFrame();
                    }
                }
            }
    }
    
    public void pauseSound(SoundFile sf){
        if(sounds.contains(sf)){
            sf.setPause(true);
        }
    }
    public void unpauseSound(SoundFile sf){
        if(sounds.contains(sf)){
            sf.setPause(false);
        }
    }
    public void removeSound(SoundFile sf){
        sounds.remove(sf);
    }
    
    public String currentState(){
        String s="";
        int numgoing = 0;
        int numpaused = 0;
        for(SoundFile sf : sounds){
            if(!sf.isPause()) numgoing++;
            if(sf.isPause()) numpaused++;
        }
        s+="{Number of Sounds: "+sounds.size() + "; Number playing: "+numgoing+"; Number paused: "+numpaused+"}";
        return s;
    }
    
}
