/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import PythonBeans.Drawable;
import Utilities.Image2D;
import Utilities.Vector2;

/**
 *
 * @author KyleSweeney
 */
public class Sprite implements Drawable{
    
    Image2D theSprite;
    Vector2 location;
    double verticalMove;
    Vector2 scale;
    int invisColor;
    boolean shouldShade;

    public Sprite(String image, Vector2 position, int invisibleColor, boolean shade) {
        theSprite = new Image2D(image);
        this.location = position;
        verticalMove = 0;
        scale = new Vector2(1,1);
        this.invisColor = invisibleColor;
        this.shouldShade = shade;
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
        return theSprite;
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
        return this.invisColor;
    }

    @Override
    public boolean doShade() {
        return shouldShade;
    }
    
    

}
