/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PythonBeans;

import Utilities.Image2D;
import java.util.ArrayList;

/**
 *
 * @author KyleSweeney
 */
public class AnimatedCell extends Image2D{
    ArrayList<Image2D> frames;
    long timeDiff;
    long lastTime;
    int i=0;
    
    public AnimatedCell(String[] frames, long timeDiff){
        this.frames = new ArrayList<Image2D>(frames.length);
        for(String s : frames){
            this.frames.add(new Image2D(s));
        }
        this.timeDiff=timeDiff;
        lastTime = System.currentTimeMillis();
    }
    
    @Override
    public int getColor(int x, int y){
        long timeNow = System.currentTimeMillis();
        if(timeNow - lastTime > timeDiff){
            i = i+1 >= frames.size() ? 0 : i+1;
            lastTime = timeNow;
        }
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
    
}
