/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PythonBeans;

import Utilities.Image2D;
import java.util.ArrayList;

/**
 *
 * @author Nekel
 */
public class TransparentCell extends AnimatedCell{
    
    private int inviscolor;
    private boolean walkthrough;
    
    public TransparentCell(String frame, long time, int inviscolor, boolean walkthrough){
        super(new String[]{frame},100);
        this.inviscolor = inviscolor;
        this.walkthrough=walkthrough;
    }
    
    public TransparentCell(String[] frames, long timeDiff, int inviscolor, boolean walkthrough){
        super(frames, timeDiff);
        this.inviscolor=inviscolor;
        this.walkthrough=walkthrough;
    }
    
    public void setWalkthrough(boolean b){
        this.walkthrough=b;
    }
    
    public boolean canGoThrough(){
        return walkthrough;
    }
    
    public int getInvisColor(){
        return this.inviscolor;
    }
}
