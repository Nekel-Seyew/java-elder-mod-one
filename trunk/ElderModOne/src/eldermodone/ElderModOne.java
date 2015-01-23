/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eldermodone;

import Hardware_Accelerated.AccelGame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.script.ScriptException;

/**
 *
 * @author KyleSweeney
 */
public class ElderModOne {
    
    public static File[] getModPackages(){
        ArrayList<File> mods = new ArrayList<File>();
        File currentDir = new File(System.getProperty("user.dir"));
        File[] files = currentDir.listFiles();
        for(File f : files){
            File[] children = f.listFiles();
            if(children != null){
                for(File p : children){
                    if(p.getName().contains("__init__.py")){
                        mods.add(f);
                        System.out.println("Module name: "+f.getName());
                        break;
                    }
                }
            }
        }
        return mods.toArray(new File[mods.size()]);
    }
    
    public static String[] getModListFile(){
        String[] list = null;
        ArrayList<String> data = new ArrayList<String>();
        File modListFile = new File("ModList.list");
        try{
            Scanner reader = new Scanner(modListFile);
            while(reader.hasNext()){
                String s = reader.nextLine();
                data.add(s);
            }
        }catch(FileNotFoundException fnfe){
            return null;
        }
        list = new String[data.size()];
        list = data.toArray(list);
        return list;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ScriptException, FileNotFoundException {
        
        MainGame game = new MainGame();
        AccelGame ag = new AccelGame(game, "ElderModOne");
        AccelGame.frame.setVisible(false);
        Starter gameStarter = new Starter(ag,game);
        gameStarter.setVisible(true);
        //ag.run();
        /*things to be included in python environment:
         1) getModDirectory() function to return base directory of the mod
         2) the player object
         3) 
         */
//        PythonInterpreter interp = new PythonInterpreter();
//        
//        //sys.path.append(new PyString(System.getProperty("user.dir")+"\\HelloWorldMod"));
//        interp.exec("import sys");
//        interp.exec("print sys");
//        interp.exec("print sys.path");
//        
//        interp.set("a", new PyInteger(42));
//        interp.exec("print a");
//        interp.exec("x = 2+2");
//         
//        PyObject x = interp.get("x");
//        interp.set("maingame", maingame);
//        
//        System.out.println("X: "+ x);
////        Reader r = new FileReader("Building.py");
//        Reader r2 = new FileReader("HelloWorldMod/__init__.py");
////        interp.execfile("HelloWorldMod");
//        PyCode module = interp.compile(r2);
//        interp.exec(module);
//        Level l = interp.get("level", Level.class);
//        
//        maingame.giveLevel(l);
        
        
//        System.out.println("Java vs Python speed test");
//        double poop=0;
//        long start = System.currentTimeMillis();
//        for (int j = 0; j < 1000; j++) {
//            for (int i = 0; i < 100000; i++) {
//                poop += (i*j)/8;
//            }
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("Dividing by 8: "+(end-start));
//        start = System.currentTimeMillis();
//        for (int j = 0; j < 1000; j++) {
//            for (int i = 0; i < 100000; i++) {
//                poop += (i*j) >> 3;
//            }
//        }
//        end = System.currentTimeMillis();
//        System.out.println("Shift over by 3: "+(end-start));
//        System.out.println("123/8: " + (123/8));
//        System.out.println("123>>3: "+ (123>>3));
//        System.out.println(""+(64>>6));
//        System.out.println("Math.atan "+(Math.atan2(0,1)));
//        Vector2 some = new Vector2(-1,0);
//        some.rotate(Math.PI/2);
//        System.out.println("Some: "+some);
//        int intTest = 0xFFFF00FF;
//        System.out.println("intTest: "+intTest);
//        start = System.currentTimeMillis();
//        Reader r = new FileReader("Test.py");
//        PyCode rmod = interp.compile(r);
//        interp.exec(rmod);
//        end = System.currentTimeMillis();
//        System.out.println("Jython sin: "+(end-start));
        
//        PyObject buildingClass = interp.get("Building");
//        buildingClass.__call__();
//        BuildingType b = (BuildingType)buildingClass.__tojava__(BuildingType.class);
//        b.setBuildingAddress("Geronimo ave");
        
        //JythonObjectFactory factory = 
        
    }
}
