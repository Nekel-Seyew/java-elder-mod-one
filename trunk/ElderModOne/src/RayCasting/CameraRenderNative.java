/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RayCasting;

import Game.Level;
import Game.Player;
import PythonBeans.Lighting;

/**
 *
 * @author KyleSweeney
 */
public class CameraRenderNative {
    
    public native static void render(int start, int end, int[] dest, double[] depthBuffer,
            double playerPosX, double playerPosY, double playerDirX,double playerDirY,
            double playerFOV,Level level, int w, int h);
    public native static int getColor(double posX, double posY, int color, 
            int lightType,int lightColor,float lightIntensity,double lightPosX,double lightPosY,float lightRange,
            double lightDirX,double lightDirY,float lightAngle);
}
