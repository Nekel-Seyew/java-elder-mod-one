/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import PythonBeans.Drawable;
import Utilities.Image2D;
import Utilities.Vector2;
import java.util.ArrayList;

/**
 *
 * @author KyleSweeney
 */
public class AnimatedSprite implements Drawable{
    
    ArrayList<Image2D> sprites;
    int index;
    long timeBetween;
    long time;
    
    Vector2 location;
    double verticalMove;
    Vector2 scale;
    int invisColor;
    boolean shouldShade;
    
    public AnimatedSprite(String[] images, Vector2 position, int invisibleColor, boolean shade, long transition){
        this.location=position;
        verticalMove = 0;
        scale = new Vector2(1,1);
        this.invisColor = invisibleColor;
        this.shouldShade = shade;
        
        sprites = new ArrayList<Image2D>();
        for(String s : images){
            sprites.add(new Image2D(s));
        }
        index =0;
        time = System.currentTimeMillis();
        this.timeBetween=transition;
    }
    
    public void setScale (double scaleX, double scaleY){
        scale = new Vector2(scaleX, scaleY);
    }
    public void setVerticalMove(double move){
        this.verticalMove=move;
    }

    @Override
    public Vector2 getPosition() {
        return location;
    }

    @Override
    public Image2D getSprite() {
        long timeNow = System.currentTimeMillis();
        if(timeNow - time > this.timeBetween){
            index = index+1 >= sprites.size() ? 0 : index+1;
            time = timeNow;
        }
        return sprites.get(index);
    }

    @Override
    public Vector2 getScale() {
        return scale;
    }

    @Override
    public double getVerticalMove() {
        return verticalMove;
    }

    @Override
    public int invisibleColor() {
        return invisColor;
    }

    @Override
    public boolean doShade() {
        return this.shouldShade;
    }
    
}
