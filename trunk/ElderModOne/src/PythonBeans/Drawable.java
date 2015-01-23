/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PythonBeans;

import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Vector2;
import java.awt.Graphics2D;

/**
 *
 * @author KyleSweeney
 */
public interface Drawable{
    public Vector2 getPosition();
    public Image2D getSprite();
    public Vector2 getScale();
    public double getVerticalMove();
    public int invisibleColor();
    public boolean doShade();
}
