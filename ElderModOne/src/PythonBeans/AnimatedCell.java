/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PythonBeans;

import Utilities.Image2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author KyleSweeney
 */
public class AnimatedCell extends Image2D{
    ArrayList<Image2D> frames;
    long timeDiff;
    long lastTime;
    int i=0;
    boolean stop;
    boolean runOnce;
    
    public AnimatedCell(String[] frames, long timeDiff){
        this.frames = new ArrayList<Image2D>(frames.length);
        for(String s : frames){
            this.frames.add(new Image2D(s));
        }
        this.timeDiff=timeDiff;
        lastTime = System.currentTimeMillis();
        stop = false;
        runOnce = false;
    }
    
    @Override
    public int getColor(int x, int y){
        return frames.get(i).getColor(x, y);
    }
    
    @Override
    public int getHeight(){
        return frames.get(i).getHeight();
    }
    /**
     * Returns the Width of the original image
     * @return the width of the original image
     */
    @Override
    public int getWidth(){
        return frames.get(i).getWidth();
    }
    
    public void update(){
        if (!stop) {
            long timeNow = System.currentTimeMillis();
            if (timeNow - lastTime > timeDiff) {
                if(runOnce){
                    i = i + 1 >= frames.size() ? i : i + 1;
                    //stop = true;
                    return;
                }
                i = i + 1 >= frames.size() ? 0 : i + 1;
                lastTime = timeNow;
            }
        }
    }
    
    public boolean onLastFrame(){
        return i+1 == frames.size();
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.frames);
        hash = 79 * hash + (int) (this.timeDiff ^ (this.timeDiff >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnimatedCell other = (AnimatedCell) obj;
        if (!Objects.equals(this.frames, other.frames)) {
            return false;
        }
        if (this.timeDiff != other.timeDiff) {
            return false;
        }
        return true;
    }
    
}
