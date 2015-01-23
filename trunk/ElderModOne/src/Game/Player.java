/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Utilities.Vector2;
import java.util.Hashtable;

/**
 *
 * @author KyleSweeney
 */
public class Player {
    private Vector2 pos;
    private Vector2 dir;
    private float fov;
    private long frameTime;
    private float speed = 3.0f;
    private float turnSpeed = 2.0f;
    
    private boolean doForward;
    private boolean doBackward;
    private boolean doRotateLeft;
    private boolean doRotateRight;
    private boolean doLeft;
    private boolean doRight;
    
    private float rotateValue=0.0f;
    private boolean doRotate=false;
    
    private Hashtable attributes;
    
    public Player(Vector2 pos, Vector2 dir, float fov){
        this.pos = pos;
        this.dir = dir;
        this.fov = fov;
        frameTime = System.currentTimeMillis();
        doForward=false;
        doBackward=false;
        doRotateLeft=false;
        doRotateRight=false;
        doLeft=false;
        doRight=false;
        attributes = new Hashtable();
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getDir() {
        return dir;
    }
    
    public void rotateLeft(){
        doRotateLeft=true;
    }
    public void rotateAmount(double amount){
        doRotate = true;
        rotateValue += 0.016*amount;
        //dir.rotate(0.016*amount);
    }
    public void rotateRight(){
        doRotateRight=true;
    }
    public void dx(double amount){
        this.pos.dX(amount);
    }
    public void dy(double amount){
        this.pos.dY(amount);
    }
    public void left(){
        doLeft=true;
    }
    public void right(){
        doRight=true;
    }
    public void forward(){
        doForward=true;
    }
    public void backwards(){
        doBackward=true;
    }
    
    //do moving and turning and whatever else the player needs updating
    public void update(Level level){
        long currentTime = System.currentTimeMillis();
        double thisFrameTime = (currentTime - frameTime)/1000.0;
        double moveSpeed = thisFrameTime * this.speed;
        double rotSpeed = thisFrameTime * this.turnSpeed;
        
        //found to be a good factor of preventing us from getting stuck in the walls
        double noColliFactor = 3.0;
        
        if(doForward){
            if(!level.isWall((int)(pos.getX() +dir.getX()*(moveSpeed*noColliFactor)), (int)pos.getY())) pos.dX(moveSpeed*dir.getX());
            if(!level.isWall((int)(pos.getX()), (int)(pos.getY() + dir.getY()*(moveSpeed*noColliFactor)))) pos.dY(moveSpeed*dir.getY());
            doForward=false;
        }
        if(doBackward){
            if(!level.isWall((int)(pos.getX() -dir.getX()*(moveSpeed*noColliFactor)), (int)pos.getY())) pos.dX( -(moveSpeed*dir.getX()));
            if(!level.isWall((int)(pos.getX()), (int)(pos.getY() - dir.getY()*(moveSpeed*noColliFactor)))) pos.dY(-(moveSpeed*dir.getY()));
            doBackward=false;
        }
        if(doRotateRight){
            dir.rotate(-rotSpeed);
            doRotateRight=false;
        }
        if(doRotateLeft){
            dir.rotate(rotSpeed);
            doRotateLeft=false;
        }
        if(doRight){
            Vector2 rightDir = dir.clone();
            rightDir.rotate(-Math.PI/2);
            if(!level.isWall((int)(pos.getX() +rightDir.getX()*(moveSpeed*noColliFactor)), (int)pos.getY())) pos.dX(moveSpeed*rightDir.getX());
            if(!level.isWall((int)(pos.getX()), (int)(pos.getY() + rightDir.getY()*(moveSpeed*noColliFactor)))) pos.dY(moveSpeed*rightDir.getY());
            doRight=false;
        }
        if(doLeft){
            Vector2 leftDir = dir.clone();
            leftDir.rotate(Math.PI/2);
            if(!level.isWall((int)(pos.getX() +leftDir.getX()*(moveSpeed*noColliFactor)), (int)pos.getY())) pos.dX(moveSpeed*leftDir.getX());
            if(!level.isWall((int)(pos.getX()), (int)(pos.getY() + leftDir.getY()*(moveSpeed*noColliFactor)))) pos.dY(moveSpeed*leftDir.getY());
            doLeft=false;
        }
        if(doRotate){
            dir.rotate(this.rotateValue);
            rotateValue=0.0f;
            doRotate=false;
        }
//        System.out.println((currentTime - frameTime)+"ms");
        frameTime = currentTime;
    }

    public float getFov() {
        return fov;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public void setDir(Vector2 dir) {
        this.dir = dir;
    }
    
    public Object getAttrib(String attrib){
        return this.attributes.get(attrib.toLowerCase());
    }
    
    public void setAttrib(String attrib, Object value){
        this.attributes.put(attrib.toLowerCase(), value);
    }
    
    
}
