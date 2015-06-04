/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Advance.Matrix;
import PythonBeans.AnimatedCell;
import PythonBeans.Drawable;
import PythonBeans.Lighting;
import PythonBeans.TransparentCell;
import Utilities.Image2D;
import Utilities.Vector2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author KyleSweeney
 */
public class Level {
    
    //Levels will have 3 components to it: floor map, walls map, and cieling map.
    //These will describe basic geometry of the room
    //There will then be a seperate, linear array of objects
    
    private HashMap<Integer, Image2D> wallsprites;
    private HashMap<Integer, Image2D> floorsprites;
    private HashMap<Integer, Image2D> ceilingsprites;
    public int[][] walls;
    private int[][] floor;
    private int[][] ceil;
    
    private ArrayList<Lighting> lights;
    private ArrayList<Drawable> sprites;
    
    private boolean haveTransparent;
    
    public Level(){
        wallsprites = new HashMap<Integer, Image2D>(20,0.2f);
        floorsprites = new HashMap<Integer, Image2D>(20,0.2f);
        ceilingsprites = new HashMap<Integer, Image2D>(20,0.2f);
        lights = new ArrayList<Lighting>();
        sprites = new ArrayList<Drawable>();
        haveTransparent = false;
    }
    
    public void putWallSprite(int i, String sprite){
        wallsprites.put(i, new Image2D(sprite));
    }
    public void putWallSprite(int i, String[] frames, long timediff){
        wallsprites.put(i, new AnimatedCell(frames,timediff));
    }
    public void putWallSprite(int i, TransparentCell tc){
        wallsprites.put(i, tc);
        this.haveTransparent=true;
    }
    public void putFloorSprite(int i, String sprite){
        floorsprites.put(i, new Image2D(sprite));
    }
    public void putFloorSprite(int i, String[] frames, long timediff){
        floorsprites.put(i, new AnimatedCell(frames,timediff));
    }
    public void putCeilingSprite(int i, String sprite){
        ceilingsprites.put(i, new Image2D(sprite));
    }
    public void putCeilingSprite(int i, String[] frames, long timediff){
        ceilingsprites.put(i, new AnimatedCell(frames,timediff));
    }
    public boolean isWall(int x, int y){
        return wallsprites.containsKey(walls[y][x]);
    }
    public boolean isTransparentWall(int x, int y){
        return (wallsprites.get(walls[y][x]) instanceof TransparentCell);
    }
    public Image2D getWallSprite(int mapX, int mapY) {
        return wallsprites.get(walls[mapY][mapX]);
    }
    public Image2D getFloorSprite(int mapX, int mapY){
        return floorsprites.get(getFloor(mapX,mapY));
    }
    public Image2D getCeilSprite(int mapX, int mapY){
        return ceilingsprites.get(getCeil(mapX,mapY));
    }

    public void setWalls(int[][] walls) {
        this.walls = transpose(walls);
    }

    public void setFloor(int[][] floor) {
        this.floor = transpose(floor);
    }

    public void setCeil(int[][] ceil) {
        this.ceil = transpose(ceil);
    }
    
    public void addLighting(Lighting light){
        this.lights.add(light);
    }
    public ArrayList<Lighting> getLighting(){
        return this.lights;
    }
    public void addSprite(Drawable sprite){
        this.sprites.add(sprite);
    }
    public ArrayList<Drawable> getSprites(){
        return this.sprites;
    }
    public void removeSprite(Drawable sprite){
        this.sprites.remove(sprite);
    }
    
    public void ensureSortedLights(){
        Collections.sort(lights);
    }
    
    public boolean doesHaveTransparent(){
        return this.haveTransparent;
    }
    
    public ArrayList<Lighting> getLightsAtLocation(Vector2 pos){
        ArrayList<Lighting> lights = new ArrayList<Lighting>(this.lights.size());
        for(Lighting l : this.lights){
            if(l.getType() == Lighting.LightingType.universal){
                lights.add(l);
            }else{
                if(pos.distanceSquare(l.getPos()) < l.getRange()*l.getRange()){
                    lights.add(l);
                }
            }
        }
        return lights;
    }
    
    public ArrayList<Image2D> getUpdateableWallsFloorCeil(){
        ArrayList<Image2D> updateable = new ArrayList<Image2D>();
        updateable.addAll(this.ceilingsprites.values());
        updateable.addAll(this.floorsprites.values());
        updateable.addAll(this.wallsprites.values());
        return updateable;
    }
    
    public String printStatus(){
        String s ="{Num Wall Sprites: "+wallsprites.size()+", Num Ceil Sprites: "
                +this.ceilingsprites.size()+", Num Floor Sprites: "+this.floorsprites.size()
                +", Num sprites: "+this.sprites.size()+"}";
        return s;
    }
    
    //the next two functions *should* be inlined by the JVM, so minimal impact
    //necessary so that if we don't want a ceiling or floor for whatever reason, that's okay
    //if floor is null, return 0, else, return the value
    private int getFloor(int mapX, int mapY){
        return floor != null ? floor[mapY][mapX] : 0;
    }
    //if the ceiling is null, return 0, else return the value
    private int getCeil(int mapX, int mapY){
        return ceil != null ? ceil[mapY][mapX] : 0;
    }
    
    
    //We transpose because otherwise we would have a crap ton of cache misses.
    private int[][] transpose(int[][] array){
        int[][] arrayT = new int[array[0].length][array.length];
        for(int i=0; i<arrayT.length; i++){
            for(int j=0; j<arrayT[i].length; j++){
                arrayT[i][j]=array[j][i];
            }
        }
        return arrayT;
    }
}
