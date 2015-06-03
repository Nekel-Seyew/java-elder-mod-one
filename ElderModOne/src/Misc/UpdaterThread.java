/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Misc;

import Game.Level;
import Game.Player;
import PythonBeans.AnimatedCell;
import PythonBeans.Updateable;
import Utilities.Image2D;
import Utilities.KeyBoard;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author Nekel
 */
public class UpdaterThread extends RecursiveAction{
    
    private boolean update;
    
    private Player player;
    private ArrayList<Updateable> updateObjects;
    private Level level;
    private KeyBoard keyboard;

    public UpdaterThread(Player player, ArrayList<Updateable> updateObjects, Level level, KeyBoard keyboard) {
        this.update = false;
        this.player = player;
        this.updateObjects = updateObjects;
        this.level = level;
        this.keyboard = keyboard;
    }
    
    public void setLevel(Level l ){
        this.level=l;
    }
    
    public void beginUpdate(){
        this.reinitialize();
        this.update = true;
    }

    @Override
    protected void compute() {
        //this.reinitialize();
        for (int i = 0; i < updateObjects.size(); i++) {
            updateObjects.get(i).update();
        }
        for (Image2D wallsprite : this.level.getUpdateableWallsFloorCeil()) {
            if (wallsprite instanceof AnimatedCell) {
                ((AnimatedCell) wallsprite).update();
            }
        }


        if (keyboard.isKeyDown(KeyBoard.a)) {
            player.left();
        }
        if (keyboard.isKeyDown(KeyBoard.d)) {
            player.right();
        }
        if (keyboard.isKeyDown(KeyBoard.s) || keyboard.isKeyDown(KeyEvent.VK_DOWN)) {
            player.backwards();
        }
        if (keyboard.isKeyDown(KeyBoard.w) || keyboard.isKeyDown(KeyEvent.VK_UP)) {
            player.forward();
        }
        if (keyboard.isKeyDown(KeyEvent.VK_LEFT)) {
            player.rotateLeft();
        }
        if (keyboard.isKeyDown(KeyEvent.VK_RIGHT)) {
            player.rotateRight();
        }

        //do moving and turning and whatever else the player needs updating
        player.update(level);
    }
}
