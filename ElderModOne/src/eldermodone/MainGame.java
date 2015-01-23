/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eldermodone;

import Game.Level;
import Game.Pathfinding;
import Game.PathfindingFib;
import Game.Player;
import Hardware_Accelerated.AGame;
import Hardware_Accelerated.AccelGame;
import PythonBeans.Drawable;
import PythonBeans.GuiElement;
import PythonBeans.Updateable;
import RayCasting.Camera;
import Utilities.Image2D;
import Utilities.ImageCollection;
import Utilities.KeyBoard;
import Utilities.Mouse;
import Utilities.Vector2;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.python.core.PyCode;
import org.python.util.PythonInterpreter;

/**
 *
 * @author KyleSweeney
 */
public class MainGame extends AGame{
    
    
    Player player;
    Camera camera;
    mouseRobot mr;
    
    Level level;
    
    private boolean first=true;
    
    private int fpsCount =0;
    private long time;
    private long firstTime;
    private long ultCount =0;
    
    private PythonInterpreter pyInterp;
    
    ArrayList<Updateable> updateObjects;
    ArrayList<Drawable> drawObjects;
    ArrayList<GuiElement> guiElements;
    
    private boolean recomputeColor=true;
    
    private boolean captureMouse;
    
    
    
    @Override
    public void InitializeAndLoad() {
        captureMouse = true;
        updateObjects = new ArrayList<Updateable>();
        drawObjects = new ArrayList<Drawable>();
        player = new Player(new Vector2(),new Vector2(-1,0),(float)Math.PI/3);
        guiElements = new ArrayList<GuiElement>();
//        player = new Player(new Vector2(22,11.5), new Vector2(-1,0), (float)Math.PI/3);
//        camera = new Camera(new Vector2(640,480));
//        camera = new Camera(new Vector2(800,600));
//        camera = new Camera(new Vector2(320,200));
//        camera = new Camera(new Vector2(1360,768));
//        camera = new Camera(new Vector2(1680,1050));
        
        //load mod
        try {
            pyInterp = new PythonInterpreter();
            pyInterp.set("player", player);
            pyInterp.set("maingame", this);
            pyInterp.set("keyboard",this.keyboard);
            pyInterp.exec("import sys");
        } 
        catch(Exception e){
            e.printStackTrace();
        }finally {
        }
//        this.setBackgroundColor(Color.red);
        
        time = System.currentTimeMillis();
        firstTime = time;
        
        Console console = new Console(pyInterp);
        console.setVisible(true);
        
    }

    @Override
    public void UnloadContent() {
        
    }

    @Override
    public void Update() {
        if(first){
            mr = new mouseRobot();
            AccelGame.gui.addMouseMotionListener(mr);
            first=false;
            
            AccelGame.frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent we){
                    long timeNow = System.currentTimeMillis();
                    double fps = (ultCount)/((timeNow-firstTime*1.0)/1000);
                    System.out.println("Average fps: "+fps);
                }
            });
//            AccelGame.frame.setSize(1360, 768);
//            AccelGame.frame.setSize(1680,1050);
            
            //turns game fullscreen
//            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//            GraphicsDevice gd = ge.getDefaultScreenDevice();
//            gd.setFullScreenWindow(AccelGame.frame);
        }
        
        for(int i=0; i<updateObjects.size(); i++){
            updateObjects.get(i).update();
        }
        
        
        if(keyboard.isKeyDown(KeyBoard.a)){
            player.left();
        }
        if(keyboard.isKeyDown(KeyBoard.d)){
            player.right();
        }
        if(keyboard.isKeyDown(KeyBoard.s)||keyboard.isKeyDown(KeyEvent.VK_DOWN)){
            player.backwards();
        }
        if(keyboard.isKeyDown(KeyBoard.w) || keyboard.isKeyDown(KeyEvent.VK_UP)){
            player.forward();
        }
        if(keyboard.isKeyDown(KeyEvent.VK_LEFT)){
            player.rotateLeft();
        }
        if(keyboard.isKeyDown(KeyEvent.VK_RIGHT)){
            player.rotateRight();
        }
        
        //do moving and turning and whatever else the player needs updating
        player.update(level);
        
        //frame rate counting
        long timeNow = System.currentTimeMillis();
        if(timeNow - time >= 1000){
//            System.out.println("FPS: "+fpsCount);
            fpsCount = 0;
            time = timeNow;
        }else{
            fpsCount +=1;
        }
        ultCount +=1;
    }
    
    @Override
    public void Draw(Graphics2D gd, ImageCollection batch) {
        camera.render(level, player, batch);
        for(GuiElement ge : this.guiElements){
            ge.draw(batch);
        }
    }
    
    public void giveLevel(Level l){
        this.level = l;
    }
    
    public void addUpdateable(Updateable u){
        updateObjects.add(u);
    }
    public void resetUpdateable(){
        updateObjects.clear();
    }
    public void removeUpdateable(Updateable u){
        updateObjects.remove(u);
    }
    public void addDrawable(Drawable d){
        drawObjects.add(d);
    }
    public void removeDrawable(Drawable d){
        drawObjects.remove(d);
    }
    public void resetDrawable(){
        drawObjects.clear();
    }
    
    public PythonInterpreter getInterpreter(){
        return pyInterp;
    }
    
    public void recomputerColor(){
        recomputeColor=true;
    }
    public void captureMouse(boolean onoff){
        this.captureMouse=onoff;
    }
    
    public void addGuiElement(GuiElement img){
        this.guiElements.add(img);
    }
    public void removeGuiElement(GuiElement img){
        this.guiElements.remove(img);
    }
    
    public Level getLevel(){
        return this.level;
    }
    
    public void setCamera(int w, int h, int resolutionX, int resolutionY, boolean fullScreen){
        AccelGame.frame.setSize(resolutionX, resolutionY);
        camera = new Camera(new Vector2(w,h));
        if(fullScreen){
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(AccelGame.frame);
        }
    }
    public void setBackgroundColor(int color){
        this.setBackgroundColor(new Color(color));
    }
    
    public void setCursorImage(String img){
        Toolkit tk=Toolkit.getDefaultToolkit();
            AccelGame.gui.setCursor(tk.createCustomCursor(tk.createImage(img), new Point(AccelGame.gui.getX(),AccelGame.gui.getY()), "img"));
    }
    
    public void runMods(File[] modPackages){
        for (File mod : modPackages) {
            try {
                Reader r2 = null;
                pyInterp.exec("sys.path.append(" + "'"+mod.getName()+"'" + ")");
                r2 = new FileReader(mod.getName()+"/__init__.py");
                PyCode module = pyInterp.compile(r2);
                pyInterp.exec(module);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    private class mouseRobot implements MouseMotionListener{
        
        Robot robot;
        Mouse m = Mouse.getCurrentInstance();
        
        public mouseRobot(){
            super();
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                Logger.getLogger(MainGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (captureMouse) {
                double dx = AccelGame.gui.getWidth() / 2 - e.getX();
                double dy = AccelGame.gui.getHeight() / 2 - e.getY();
//            System.out.println("dx: "+dx);
                if (dx < 0) {
                    player.rotateAmount(dx / (2 * Math.PI));
                }
                if (dx > 0) {
                    player.rotateAmount(dx / (2 * Math.PI));
                }
                robot.mouseMove((int) AccelGame.gui.getLocationOnScreen().getX() + AccelGame.gui.getWidth() / 2,
                        (int) AccelGame.gui.getLocationOnScreen().getY() + AccelGame.gui.getHeight() / 2);
            }
        }
        
    }
}
