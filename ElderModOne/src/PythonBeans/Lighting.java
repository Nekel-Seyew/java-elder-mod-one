/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PythonBeans;

import Utilities.Vector2;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * A light object for use in levels to light things up. Additionally, for the
 * time being, rays can and will go through walls. Be careful. The position is the
 * physical location in the level. The color format is stored as
 * such: "aaaaaaaa rrrrrrrr gggggggg bbbbbbbb". Further more, the range is how
 * far it goes. For example, at a range of 10, things 10 units away will have 0%
 * intensity of the light, things 3 units away will have 70% intensity, and
 * things 7 units away will have 30% intensity. There can also be an direction 
 * for directional lights, and along with that is an angle which measures the 
 * angle between the direction vector and the edge of the cone.
 * </p>
 * <p>
 * For types, there are three.</br>
 * universal - this will illuminate every pixel in the level, no matter the
 * range.</br>
 * ambient - this will illuminate a sphere area in the level, but will decrese
 * with range.</br>
 * directional - this is a cone of light that descreases with range.</br>
 * </p>
 *<p>
 * Also note that the types are arranged in decreasing precedence. That is, 
 * a universal light's effect on the world will be calculated first, then an 
 * abient light's effect, then the directional light's effect. For lights of the
 * same type, how the order in which they are added to the level determines 
 * their priority, in a FIFO order.
 * </p>
 * @author KyleSweeney
 */
public class Lighting implements Comparable{
    protected Vector2 pos;
    protected int color;
    protected float range;
    LightingType type;
    protected Vector2 direction;
    protected float angle;
    protected float intensity;
    protected long numger;
    
    protected int typeInt;
    
    protected double[] alphaComp,redComp,greenComp,blueComp;
    
    public static final int UNIVERSAL =0;
    public static final int AMBIENT =1;
    public static final int DIRECTIONAL=2;
    
    /**
     * <p>
     * A light object for use in levels to light things up. The position is the physical location in the level. Note that the rays, unless
     * specified by type to be universal, will not go through walls. The color format is stored as such:
     * "aaaaaaaa rrrrrrrr gggggggg bbbbbbbb". Further more, the range is how far it goes. For example, at a range of 10, things 10 units away will
     * have 0% intensity of the light, things 3 units away will have 70% of intensity, and things 7 units away will have 30% of intensity.
     * </p>
     * <p>
     * For types, there are three.</br>
     * universal - this will illuminate every pixel in the level, no matter the range.</br>
     * ambient - this will illuminate a sphere area in the level, but will decrese with range.</br>
     * directional - this is a cone of light that descreases with range.</br>
     * </p>
     * @param pos the position in the level
     * @param color the int representation of the color, stored in ARGB format
     * @param range the max distance it covers.
     * @param direction direction vector of the possible cone
     * @param angle the angle made with the direction vector and the furthest edge of the cone.
     * @param type the type of light this will be
     * @param intensity the max amount of color to add to each pixel
     */
    public Lighting(Vector2 pos, int color, float range, Vector2 direction, float angle, LightingType type, float intensity){
        this.pos=pos;
        this.color=color;
        this.range=range;
        this.type=type;
        this.direction=direction;
        this.angle=angle;
        this.intensity=intensity;
        
        if(type == LightingType.ambient){
            this.typeInt = AMBIENT;
        }
        if(type == LightingType.directional){
            this.typeInt = DIRECTIONAL;
        }
        if(type == LightingType.universal){
            this.typeInt = UNIVERSAL;
        }
        if(this.direction != null){
            this.direction.scalarMultiply(1/this.direction.length());
        }
        this.alphaComp = new double[10];
        this.redComp=new double[10];
        this.blueComp= new double[10];
        this.greenComp=new double[10];
        Arrays.fill(alphaComp, 0xFF000000&this.color);
        Arrays.fill(alphaComp, 0x00FF0000&this.color);
        Arrays.fill(alphaComp, 0x0000FF00&this.color);
        Arrays.fill(alphaComp, 0x000000FF&this.color);
        
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public LightingType getType() {
        return type;
    }

    public void setType(LightingType type) {
        this.type = type;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    public int getTypeInt(){
        return this.typeInt;
    }
    
    public double[] getAlphaComp(int size){
        if(this.alphaComp.length < size){
            this.alphaComp = new double[size*2];
            Arrays.fill(this.alphaComp, 0xFF000000&this.color);
        }
        return this.alphaComp;
    }
    public double[] getRedComp(int size){
        if(this.redComp.length < size){
            this.redComp = new double[size*2];
            Arrays.fill(this.redComp, 0x00FF0000&this.color);
        }
        return this.redComp;
    }
    public double[] getGreenComp(int size){
        if(this.greenComp.length < size){
            this.greenComp = new double[size*2];
            Arrays.fill(this.greenComp, 0x0000FF00&this.color);
        }
        return this.greenComp;
    }
    public double[] getBlueComp(int size){
        if(this.blueComp.length < size){
            this.blueComp = new double[size*2];
            Arrays.fill(this.blueComp, 0x000000FF&this.color);
        }
        return this.blueComp;
    }
    
    /**
     * A JSON string representation of this object.
     * @return a JSON string
     */
    public String toString(){
        String s = "{Location: {"+pos+"}, Color: "+Integer.toHexString(color)+", Type: ";
        switch(type){
            case universal:
                s = s.concat("Type: 'universal',");
                break;
            case ambient:
                s = s.concat("Type: 'ambient',");
                break;
            case directional:
                s = s.concat("Type: 'directional',");
                break;
        }
        s = s.concat("range: "+range+", direction: {"+direction+"}, angle: "+angle);
        s = s.concat("}");
        return s;
    }

    @Override
    public int compareTo(Object o) {
        Lighting light = (Lighting)o;
        if(this.type == light.type){
            return 0;
        }else if(this.type == LightingType.universal && light.type != LightingType.universal){
            return -1;
        }else if(this.type == LightingType.ambient && light.type == LightingType.universal){
            return 1;
        }else if(this.type == LightingType.ambient && light.type == LightingType.directional){
            return -1;
        }else if(this.type == LightingType.directional && light.type != LightingType.directional){
            return 1;
        }else{
            return 0;
        }
    }
    
    public enum LightingType{
        universal,
        ambient,
        directional;
    };

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.pos);
        hash = 89 * hash + this.color;
        hash = 89 * hash + Float.floatToIntBits(this.range);
        hash = 89 * hash + Objects.hashCode(this.type);
        hash = 89 * hash + Objects.hashCode(this.direction);
        hash = 89 * hash + Float.floatToIntBits(this.angle);
        hash = 89 * hash + Float.floatToIntBits(this.intensity);
        hash = 89 * hash + (int) (this.numger ^ (this.numger >>> 32));
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
        final Lighting other = (Lighting) obj;
        if (!Objects.equals(this.pos, other.pos)) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (Float.floatToIntBits(this.range) != Float.floatToIntBits(other.range)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.direction, other.direction)) {
            return false;
        }
        if (Float.floatToIntBits(this.angle) != Float.floatToIntBits(other.angle)) {
            return false;
        }
        if (Float.floatToIntBits(this.intensity) != Float.floatToIntBits(other.intensity)) {
            return false;
        }
        return true;
    }

   
    
    
}
