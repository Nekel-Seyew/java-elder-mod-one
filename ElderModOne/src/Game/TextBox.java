/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import PythonBeans.Drawable;
import PythonBeans.GuiElement;
import PythonBeans.Updateable;
import Utilities.FontStyle;
import Utilities.FontType;
import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.Vector2;
import java.awt.Color;

/**
 *
 * @author Nekel
 */
public class TextBox implements Updateable, GuiElement{

    private String text;
    private Color background;
    private Color textColor;
    private int speed;
    private int fonttype;
    private int fontstyle;
    private Vector2 screenLocation;
    private Vector2 dimentions;
    private int depth;
    private int textsize;
    
    //outlines
    private boolean outline;
    private int outlineThickness;
    private Color outlinecolor;
    
    //utility fields
    private int index;
    private long lastTime;
    private boolean done;
    
    public TextBox(Vector2 centerlocation, Vector2 dimentions, int background,
            String text, int type, int style, int textcolor, 
            int delay, int size, int depth ){
        this.text = new String(text);
        this.background = new Color(background);
        this.textColor = new Color(textcolor);
        this.speed = delay;
        this.fontstyle = style;
        this.fonttype = type;
        this.screenLocation = centerlocation;
        this.dimentions = dimentions;
        this.outline = false;
        this.depth = depth;
        this.textsize = size;
        
        this.index = 0;
        this.done = false;
        this.lastTime = System.currentTimeMillis();
    }
    
    public TextBox(Vector2 centerlocation, Vector2 dimentions, int background,
            String text, int type, int style, int textcolor, 
            int delay, int size, int outlineThickness, int outlinecolor, int depth){
        this.text = new String(text);
        this.background = new Color(background);
        this.textColor = new Color(textcolor);
        this.speed = delay;
        this.fontstyle = style;
        this.fonttype = type;
        this.screenLocation = centerlocation;
        this.dimentions = dimentions;
        this.outline = true;
        this.outlineThickness = outlineThickness;
        this.outlinecolor = new Color(outlinecolor);
        this.depth = depth;
        this.textsize = size;
        
        this.index = 0;
        this.done = false;
        this.lastTime = System.currentTimeMillis();
    }
    
    @Override
    public void update() {
        if(!done){
            if(index >= text.length()){
                done = true;
            }
            
            long now = System.currentTimeMillis();
            if(now - lastTime >= speed){
                index +=1;
                lastTime = now;
            }
        }
    }

    @Override
    public void draw(ImageCollection batch) {
        //batch.
        Vector2 pos = this.screenLocation.clone();
        Vector2 mov = this.dimentions.clone();
        mov.scalarMultiply(0.5);
        pos.subtract(mov);
        if(this.outline){
            Vector2 innerpos = pos.clone();
            innerpos.dX(this.outlineThickness);
            innerpos.dY(this.outlineThickness);
            Vector2 innerdim = this.dimentions.clone();
            innerdim.dX(-(2*this.outlineThickness));
            innerdim.dY(-(2*this.outlineThickness));
            
            batch.fillRect(pos, (int)this.dimentions.getX(), (int)this.dimentions.getY(), this.outlinecolor, depth);
            batch.fillRect(innerpos, (int)innerdim.getX(), (int)innerdim.getY(), background, depth+1);
            innerpos.dY(12);
            innerpos.dX(5);
            batch.DrawString(innerpos, text.substring(0, index), this.textColor, depth+2, fonttype, fontstyle, textsize);
        }else{
            Vector2 innerpos = pos.clone();
            batch.fillRect(pos, (int)mov.getX(), (int)mov.getY(), this.outlinecolor, depth);
            innerpos.dY(6);
            innerpos.dX(5);
            batch.DrawString(innerpos, text.substring(0,index), this.textColor, depth+1, fonttype, fontstyle, textsize);
        }
    }
    
}
