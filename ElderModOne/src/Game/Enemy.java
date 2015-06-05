/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import PythonBeans.Updateable;
import Utilities.Vector2;
import java.util.Hashtable;

/**
 * A Helpful base class for Enemy types. It even has a "MoveTo" function
 * @author KyleSweeney
 */
public class Enemy implements Updateable{
    protected Vector2 pos;
    protected Vector2 dir;
    protected float speed;
    
    protected long frameTime;
    
    protected Vector2 target;
    protected Vector2[] path;
    protected int pathIndex;
    protected Level level;
    protected boolean moveto;
    
    protected Hashtable attrib;
    
    /**
     * The Constructor for the Enemy Class.
     * @param pos The position of the enemy
     * @param dir The direction the enemy is facing
     * @param speed the speed at which the enemy moves
     */
    public Enemy(Vector2 pos, Vector2 dir, float speed){
        this.pos=pos;
        this.target = pos;
        this.dir = dir;
        this.pathIndex=0;
        this.speed=speed;
        this.frameTime=System.currentTimeMillis();
        this.attrib = new Hashtable();
    }
    

    @Override
    /**
     * An update method as required by the Updateable interface
     */
    public void update() {
        if(moveto){
            moveToUpdate();
        }
    }
    
    /**
     * Finds and follows the shortest path between this enemy instance and the passed in target.
     * @param target the location to which the enemy is moving to
     * @param l the level in which the enemy is moving.
     */
    public void moveTo(Vector2 target, Level l){
        this.level = l;
        int oldX = (int)(this.target.getX()*10 - 5); //to account for our .5 plus shift....
        int oldY = (int)(this.target.getY()*10 - 5);
        int newX = (int)(target.getX()*10);
        int newY = (int)(target.getY()*10);
        
        if(!((oldX == newX) && (oldY==newY))){
            this.target = target;
            path = Pathfinding.getPath(pos, target, l);
            pathIndex=0;
            for(Vector2 x : path){
                if(x != null){
                    x.setX(x.getX()+0.5);
                    x.setY(x.getY()+0.5);
                }
            }
            this.target.setX(((int)this.target.getX()) +0.5);
            this.target.setY(((int)this.target.getY()) +0.5);
            Vector2 curTar = path[pathIndex+1];
            this.pos.setX(path[0].getX());
            this.pos.setY(path[0].getY());
            this.dir.setX(curTar.getX() - this.pos.getX());
            this.dir.setY(curTar.getY() - this.pos.getY());
            this.dir.scalarMultiply(1/this.dir.length());
        }
        moveto=true;
    }
    
    private void moveToUpdate(){
        long currentTime = System.currentTimeMillis();
        double thisFrameTime = (currentTime - frameTime)/1000.0;
        double moveSpeed = thisFrameTime * this.speed;
        
        Vector2 curTar = path[pathIndex];
        
        int curTarX = (int)(curTar.getX());
        int curTarY = (int)(curTar.getY());
        int posX = (int)(pos.getX());
        int posY = (int)(pos.getY());
        
        if((curTarX == posX) && (curTarY==posY)){
            pathIndex++;
            if(pathIndex >= path.length){//done
                moveto=false;
                return;
            }
            curTar = path[pathIndex];
            this.dir.setX(curTar.getX() - this.pos.getX());
            this.dir.setY(curTar.getY() - this.pos.getY());
            this.dir.scalarMultiply(1/this.dir.length());
        }
        this.pos.dX(moveSpeed*this.dir.getX());
        this.pos.dY(moveSpeed*this.dir.getY());
        
        //prevent overshots
        if(this.dir.getX() > 0){
            double x = (this.pos.getX() > path[pathIndex].getX()) ? path[pathIndex].getX() : this.pos.getX();
            this.pos.setX(x);
        }else if(this.dir.getX() < 0){
            double x = (this.pos.getX() < path[pathIndex].getX()) ? path[pathIndex].getX() : this.pos.getX();
            this.pos.setX(x);
        }
        
        if(this.dir.getY() > 0){
            double y = (this.pos.getY() > path[pathIndex].getY()) ? path[pathIndex].getY() : this.pos.getY();
            this.pos.setY(y);
        }else if(this.dir.getY() < 0){
            double y = (this.pos.getY() < path[pathIndex].getY()) ? path[pathIndex].getY() : this.pos.getY();
            this.pos.setY(y);
        }
        frameTime = currentTime;
//        System.out.println("Light Enemy Pos: "+this.pos);
    }

    /**
     * A pointer to the position of this enemy
     * @return 
     */
    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /**
     * Returns the vector in which this enemy is pointing.
     * @return 
     */
    public Vector2 getDir() {
        return dir;
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }

    /**
     * The speed at which the enemy moves, in terms of blocks per second
     * @return 
     */
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
    
    public boolean isMoveTo(){
        return this.moveto;
    }
    
    /**
     * Obtain the attribute 
     * @param s
     * @return 
     */
    public Object getAttrib(String s){
        return this.attrib.get(s.toLowerCase());
    }
    public void setAttrib(String s, Object att){
        this.attrib.put(s.toLowerCase(), att);
    }
}
