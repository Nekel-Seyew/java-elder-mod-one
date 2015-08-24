/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RayCasting;

import Advance.AMath;
import Game.Level;
import Game.Player;
import PythonBeans.Lighting;
import PythonBeans.Lighting.LightingType;
import PythonBeans.TransparentCell;
import Utilities.Image2D;
import Utilities.Vector2;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.RecursiveAction;
import info.yeppp.Core;
import java.util.Arrays;

/**
 * A worker class which actually draws a portion of the screen's walls,
 * ceiling, and floor.
 * <br></br>
 * IMPORTANT NOTE: YOU MUST CALL UPDATE BEFORE THE POOL INVOKES THIS CLASS.
 * @author KyleSweeney
 */
public class CameraRenderYeppp extends RecursiveAction {

    int start, end;
    int[] dest;
    transient Player player;
    transient Level level;
    Vector2 resolution;
    long threshold;
    double[] depthBuffer;
    
    //ALL CORE CRAP
    double[] xdarray, ydarray, sumdarray;
    int[] xiarray, yiarray, sumiarray;
    float[] yarray;
    float[] working, cInv256, vLineHight, vH,vLineHalf, vTexWidth;
    double[] alpha ,red, green, blue;
    double[] calpha ,cred, cgreen, cblue;
    
    CameraRenderYeppp A, B;
    
    HashMap<Integer, Integer> colorHashMap;

    public CameraRenderYeppp(int start, int end, int[] dest, Player player, 
            Level level, Vector2 resolution, long threshold) {
        super();
        this.start = start;
        this.end = end;
        this.dest = dest;
        this.player = player;
        this.level = level;
        this.resolution = resolution;
        this.threshold = threshold;
        if (!(end - start < threshold)) {
            final int split = (end + start) / 2;
            A = new CameraRenderYeppp(start, split, dest, player, level, resolution, threshold);
            B = new CameraRenderYeppp(split, end, dest, player, level, resolution, threshold);
        }
        //0.75f : Average fps: 25.10534901107864
        this.colorHashMap = new HashMap<Integer, Integer>(64,0.2f);
        //coresetup
        xdarray = new double[10];
        ydarray = new double[10];
        sumdarray = new double[10];
        xiarray = new int[10];
        yiarray = new int[10];
        sumiarray = new int[10];
        yarray = new float[(int)resolution.getX()];
        working = new float[(int)resolution.getX()];
        cInv256 = new float[(int)resolution.getX()];
        vLineHight= new float[(int)resolution.getX()];
        vH= new float[(int)resolution.getX()];
        vTexWidth= new float[(int)resolution.getX()];
        vLineHalf= new float[(int)resolution.getX()];
        alpha =new double[(int)resolution.getX()];
        red=new double[(int)resolution.getX()];
        green=new double[(int)resolution.getX()];
        blue=new double[(int)resolution.getX()];
        calpha =new double[(int)resolution.getX()];
        cred=new double[(int)resolution.getX()];
        cgreen=new double[(int)resolution.getX()];
        cblue=new double[(int)resolution.getX()];
        for(int i=0; i<yarray.length; i++){
            yarray[i]=i;
            working[i]=0;
            cInv256[i]=256f;
        }
    }
    
    public void update(int start, int end, int[] dest, double[] depthBuffer, Player player, Level level, Vector2 resolution, long threshold){
        this.reinitialize();
        this.start = start;
        this.end = end;
        this.dest = dest;
        this.player = player;
        this.level = level;
        this.resolution = resolution;
        this.threshold = threshold;
        this.depthBuffer=depthBuffer;
        if (A != null && B != null) {
            final int split = (end + start) / 2;
            A.update(start, split, dest,depthBuffer, player, level, resolution, threshold);
            B.update(split, end, dest,depthBuffer, player, level, resolution, threshold);
        }
    }
    
//    public native void render(int start, int end, int[] dest, double[] depthBuffer,
//            double playerPosX, double playerPosY, double playerDirX,double playerDirY,
//            Level level, int h, int y);

    private void render() {
        Vector2 plane = player.getDir().clone();
        plane.rotate(-Math.PI / 2);
        double lengthOfFov = player.getDir().length() * Math.tan(player.getFov() / 2);
        plane.scalarMultiply(lengthOfFov / plane.length());
        
        int h = (int)resolution.getY();
        int w = (int)resolution.getX();
        
        //Method calls are expensive, so we are going to do this here.
        double PlayerPosX = player.getPos().getX();
        double PlayerPosY = player.getPos().getY();
        
        double rayPosX = PlayerPosX;
        double rayPosY = PlayerPosY;
        
        

        for (int x = start; x < end; x++) {
            //calculate ray position and direction 
            double cameraX = 2 * (x/((double)w)) - 1; //x-coordinate in camera space
            double rayDirX = player.getDir().getX() + plane.getX() * cameraX;
            double rayDirY = player.getDir().getY() + plane.getY() * cameraX;

            //which box of the map we're in  
            int mapX = (int) (rayPosX);
            int mapY = (int) (rayPosY);

            //length of ray from current position to next x or y-side
            double sideDistX;
            double sideDistY;

            //length of ray from one x or y-side to next x or y-side
            double rayDirX2 = rayDirX * rayDirX;
            double rayDirY2 = rayDirY * rayDirY;
            double deltaDistX = Math.sqrt(1 + (rayDirY2) / (rayDirX2));
            double deltaDistY = Math.sqrt(1 + (rayDirX2) / (rayDirY2));
            
            double perpWallDist;

            //what direction to step in x or y-direction (either +1 or -1)
            int stepX;
            int stepY;

            int hit = 0; //was there a wall hit?
            int side = 0; //was a NS or a EW wall hit?

            //calculate step and initial sideDist
            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (rayPosX - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
            }
            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (rayPosY - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
            }
            
            //perform DDA
            while (hit == 0) {
                //jump to next map square, OR in x-direction, OR in y-direction
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                //Check if ray has hit a wall
                if (level.isWall(mapX, mapY)) {
                    if(level.getWallSprite(mapX, mapY) instanceof TransparentCell){
                        continue;
                    }
                    hit = 1;
                }
            }

            //Calculate distance of perpendicular ray (oblique distance will give fisheye effect!)
            if (side == 0) {
                perpWallDist = Math.abs((mapX - rayPosX + ((1 - stepX)>>1)) / (rayDirX));
            } else {
                perpWallDist = Math.abs((mapY - rayPosY + ((1 - stepY)>>1)) / (rayDirY));
            }

            //Calculate height of line to draw on screen
            int lineHeight = Math.abs((int) (h / (perpWallDist)));

            //calculate lowest and highest pixel to fill in current stripe
            int drawStart = (int) ((h>>1) - (lineHeight>>1));
//            drawStart += 50;
            if (drawStart < 0) {
                drawStart = 0;
            }
            //trying to look down
            int drawEnd = (int) ((lineHeight>>1) + (h>>1));
//            drawEnd +=50;
            if (drawEnd >= h) {
                drawEnd = h - 1;
            }
            //texturing calculations
            Image2D texture = level.getWallSprite(mapX, mapY);

            //calculate value of wallX
            double wallX; //where exactly the wall was hit
            if (side == 1) {
                wallX = rayPosX + ((mapY - rayPosY + ((1 - stepY)>>1)) / (rayDirY)) * rayDirX;
            } else {
                wallX = rayPosY + ((mapX - rayPosX + ((1 - stepX)>>1)) / (rayDirX)) * rayDirY;
            }
            wallX -= Math.floor((wallX));

            //x coordinate on the texture
            //assume everything is 64x64
            int texX = (int) (wallX * texture.getWidth());
            if (side == 0 && rayDirX > 0) {
                texX = texture.getWidth() - texX - 1;
            }
            if (side == 1 && rayDirY < 0) {
                texX = texture.getWidth() - texX - 1;
            }
            //fairly certain to look up and down, we need to move the drawStart and drawEnd points
            int hAndLine = (lineHeight - h)<<7;
            
            double mapActualX=PlayerPosX + rayDirX*perpWallDist, mapActualY=PlayerPosY + rayDirY*perpWallDist;
            //this reduces the number of lights we need to check for all y points. Still going to, but it's the number that counts
            ArrayList<Lighting> lights = level.getLightsAtLocation(new Vector2(mapActualX,mapActualY));
            
//            for(int i=drawStart; i<(drawEnd)+1; i++){
//                yarray[i]=i;
////                this.vH[i]=-h/2f;
////                this.vLineHalf[i]=lineHeight/2f;
////                this.vLineHight[i]=(1f/((float)lineHeight));
////                this.vTexWidth[i]= texture.getWidth();
//            }
            Arrays.fill(this.vH,drawStart,drawEnd,-h/2f);
            Arrays.fill(this.vLineHalf,drawStart,drawEnd,lineHeight/2f);
            Arrays.fill(this.vLineHight,drawStart,drawEnd,(1f/((float)lineHeight)));
            Arrays.fill(this.vTexWidth,drawStart,drawEnd,texture.getWidth());
            Core.Add_V32fV32f_V32f(yarray, drawStart, this.vH, drawStart, working, drawStart, (drawEnd-drawStart));
            Core.Add_V32fV32f_V32f(working, drawStart, this.vLineHalf, drawStart, working, drawStart, (drawEnd-drawStart));
            Core.Multiply_V32fV32f_V32f(working, drawStart, this.vTexWidth, drawStart, working, drawStart, (drawEnd-drawStart));
            Core.Multiply_V32fV32f_V32f(working, drawStart, this.vLineHight, drawStart, working, drawStart, (drawEnd-drawStart));
            
            //int colorx = 0xFFFFFFFF;
            int[][] textureRaw = texture.getColumnMajorColorMap();
            //int[] workingk = new int[(drawEnd-drawStart)+1];
            for(int i= drawStart; i<drawEnd+1; i++){
                alpha[i-drawStart] = 0xFF000000 & textureRaw[(int)working[i]][texX];
                red[i-drawStart] = 0x00FF0000 & textureRaw[(int)working[i]][texX];
                green[i-drawStart] = 0x0000FF00 & textureRaw[(int)working[i]][texX];
                blue[i-drawStart] = 0x000000FF & textureRaw[(int)working[i]][texX];
            }
            //forColor
            System.arraycopy(alpha, 0, calpha, 0, alpha.length);
            System.arraycopy(red, 0, cred, 0, red.length);
            System.arraycopy(green, 0, cgreen, 0, green.length);
            System.arraycopy(blue, 0, cblue, 0, blue.length);
            Vector2 lightpos = new Vector2(mapActualX, mapActualY);
            //general algorithm: base + percent*(new-base)
            for(Lighting light : lights){
                if(light.getTypeInt() == Lighting.UNIVERSAL){
                    //colorx = getShadeColor(colorx, light.getColor(), 0, 0, light.getIntensity(), light.getType());
                    double[] percent = new double[(drawEnd-drawStart)+1];
                    Arrays.fill(percent, light.getIntensity());
                    Core.Subtract_V64fV64f_V64f(light.getAlphaComp(drawEnd-drawStart), 0, calpha, 0, calpha, 0, (drawEnd-drawStart));
                    Core.Subtract_V64fV64f_V64f(light.getRedComp(drawEnd-drawStart), 0, cred, 0, cred, 0, (drawEnd-drawStart));
                    Core.Subtract_V64fV64f_V64f(light.getGreenComp(drawEnd-drawStart), 0, cgreen, 0, cgreen, 0, (drawEnd-drawStart));
                    Core.Subtract_V64fV64f_V64f(light.getBlueComp(drawEnd-drawStart), 0, cblue, 0, cblue, 0, (drawEnd-drawStart));
                    Core.Multiply_V64fV64f_V64f(percent, 0, calpha, 0, calpha, 0, drawEnd-drawStart);
                    Core.Multiply_V64fV64f_V64f(percent, 0, cred, 0, cred, 0, drawEnd-drawStart);
                    Core.Multiply_V64fV64f_V64f(percent, 0, cgreen, 0, cgreen, 0, drawEnd-drawStart);
                    Core.Multiply_V64fV64f_V64f(percent, 0, cblue, 0, cblue, 0, drawEnd-drawStart);
                    Core.Add_V64fV64f_V64f(alpha, 0, calpha, 0, calpha, 0, drawEnd-drawStart);
                    Core.Add_V64fV64f_V64f(red, 0, cred, 0, cred, 0, drawEnd-drawStart);
                    Core.Add_V64fV64f_V64f(green, 0, cgreen, 0, cgreen, 0, drawEnd-drawStart);
                    Core.Add_V64fV64f_V64f(blue, 0, cblue, 0, cblue, 0, drawEnd-drawStart);
                }else{
                    Vector2 directionToMe = new Vector2(mapActualX - light.getPos().getX(), mapActualY - light.getPos().getY());
                    if (light.getPos().distanceSquare(lightpos) <= light.getRange() * light.getRange()) {
                        if (light.getTypeInt() == Lighting.DIRECTIONAL && theta(directionToMe, light.getDirection()) <= light.getAngle()) {
                            double putdist = (1.0 - (light.getPos().distanceSquare(lightpos) / (light.getRange() * light.getRange()))) * light.getIntensity();
                            double[] percent = new double[(drawEnd - drawStart) + 1];
                            Arrays.fill(percent, putdist);
                            Core.Subtract_V64fV64f_V64f(light.getAlphaComp(drawEnd - drawStart), 0, calpha, 0, calpha, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getRedComp(drawEnd - drawStart), 0, cred, 0, cred, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getGreenComp(drawEnd - drawStart), 0, cgreen, 0, cgreen, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getBlueComp(drawEnd - drawStart), 0, cblue, 0, cblue, 0, (drawEnd - drawStart));
                            Core.Multiply_V64fV64f_V64f(percent, 0, calpha, 0, calpha, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cred, 0, cred, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cgreen, 0, cgreen, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cblue, 0, cblue, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(alpha, 0, calpha, 0, calpha, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(red, 0, cred, 0, cred, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(green, 0, cgreen, 0, cgreen, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(blue, 0, cblue, 0, cblue, 0, drawEnd - drawStart);
                        } else if (light.getTypeInt() == Lighting.AMBIENT) {
                            double putdist = (1.0 - (light.getPos().distanceSquare(lightpos) / (light.getRange() * light.getRange()))) * light.getIntensity();
                            double[] percent = new double[(drawEnd - drawStart) + 1];
                            Arrays.fill(percent, putdist);
                            Core.Subtract_V64fV64f_V64f(light.getAlphaComp(drawEnd - drawStart), 0, calpha, 0, calpha, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getRedComp(drawEnd - drawStart), 0, cred, 0, cred, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getGreenComp(drawEnd - drawStart), 0, cgreen, 0, cgreen, 0, (drawEnd - drawStart));
                            Core.Subtract_V64fV64f_V64f(light.getBlueComp(drawEnd - drawStart), 0, cblue, 0, cblue, 0, (drawEnd - drawStart));
                            Core.Multiply_V64fV64f_V64f(percent, 0, calpha, 0, calpha, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cred, 0, cred, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cgreen, 0, cgreen, 0, drawEnd - drawStart);
                            Core.Multiply_V64fV64f_V64f(percent, 0, cblue, 0, cblue, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(alpha, 0, calpha, 0, calpha, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(red, 0, cred, 0, cred, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(green, 0, cgreen, 0, cgreen, 0, drawEnd - drawStart);
                            Core.Add_V64fV64f_V64f(blue, 0, cblue, 0, cblue, 0, drawEnd - drawStart);
                        }
                    }
                }
                System.arraycopy(calpha, 0, alpha, 0, alpha.length);
                System.arraycopy(cred, 0, red, 0, red.length);
                System.arraycopy(cgreen, 0, green, 0, green.length);
                System.arraycopy(cblue, 0,blue, 0, blue.length);
            }
            for (int y = drawStart; y < drawEnd; y++) {
                dest[y * w + x] = (0xFF000000&((int) calpha[y - drawStart]))
                        | (0x00FF0000&((int) cred[y - drawStart]))
                        | (0x0000FF00&((int) cgreen[y - drawStart]))
                        | (0x000000FF&((int) cblue[y - drawStart]));
                if(y == drawEnd-1){
                    dest[(y+1) * w + x] = (0xFF000000&((int) calpha[y - drawStart-1]))
                        | (0x00FF0000&((int) cred[y - drawStart-1]))
                        | (0x0000FF00&((int) cgreen[y - drawStart-1]))
                        | (0x000000FF&((int) cblue[y - drawStart-1]));
                }
                
            }
            
            depthBuffer[x]=perpWallDist;

            //ceiling and floor time
            double floorXWall, floorYWall;
            if (side == 0 && rayDirX > 0) {
                floorXWall = mapX;
                floorYWall = mapY + wallX;
            } else if (side == 0 && rayDirX < 0) {
                floorXWall = mapX + 1.0;
                floorYWall = mapY + wallX;
            } else if (side == 1 && rayDirY > 0) {
                floorXWall = mapX + wallX;
                floorYWall = mapY;
            } else {
                floorXWall = mapX + wallX;
                floorYWall = mapY + 1.0;
            }

            double distWall, distPlayer, currentDist;
            distWall = perpWallDist;
            distPlayer = 0.0;
            if (drawEnd < 0.0) {
                drawEnd = h;
            }

            //to yepp-ify this: 1)build up array of all the pixels to be colored.2)Color them
            double[] xpos = new double[h];
            double[] ypos = new double[h];
            //now draw floor and ceiling
            for (int y = drawEnd + 1; y < h; y++) {
                currentDist = h / (2.0 * y - h);
                double weight = (currentDist - distPlayer) / (distWall - distPlayer);
                double minusWeight1 = 1.0 - weight;
                double currentFloorX = weight * floorXWall + (minusWeight1) * PlayerPosX;
                double currentFloorY = weight * floorYWall + (minusWeight1) * PlayerPosY;
                
                //yepp
                xpos[y]=currentFloorX;
                xpos[h-y]=currentFloorX;
                ypos[y]=currentFloorY;
                ypos[h-y]=currentFloorY;

                Image2D floor = level.getFloorSprite((int) currentFloorX, (int) currentFloorY);
                Image2D ceil = level.getCeilSprite((int) currentFloorX, (int) currentFloorY);

                int floorTexX = 0, floorTexY = 0;
                if (floor != null) {
                    floorTexX = (int) (currentFloorX * floor.getWidth()) % floor.getWidth();
                    floorTexY = (int) (currentFloorY * floor.getHeight()) % floor.getHeight();
                } else if ((floor == null) && ceil != null) {//2 operations which could prevent 6 opertions
                    floorTexX = (int) (currentFloorX * ceil.getWidth()) % ceil.getWidth();
                    floorTexY = (int) (currentFloorY * ceil.getHeight()) % ceil.getHeight();
                }
                int floorPix = 0;
                if (floor != null) {
                    floorPix = floor.getColor(Math.abs(floorTexX), Math.abs(floorTexY));
                    //shading!
                    alpha[y] = 0xFF000000 & floorPix;
                    red[y] = 0x00FF0000 & floorPix;
                    green[y] = 0x0000FF00 & floorPix;
                    blue[y] = 0x000000FF & floorPix;
                    for (Lighting light : level.getLighting()) {
                        floorPix = getColor(currentFloorX, currentFloorY, floorPix, light);
                    }
//                    int ny=y +50;
//                    if(ny >= h){
//                        //continue;
//                    }else{
//                    dest[ny * w + x] = floorPix;
//                    }
                    dest[y * w + x] = floorPix;
                }
                if (ceil != null) {
//                    int ny=y +50;
//                    if(ny >= h){
//                        continue;
//                    }
                    int ceilPix = ceil.getColor(Math.abs(floorTexX), Math.abs(floorTexY));
                    //shading!
                    alpha[h-y] = 0xFF000000 & ceilPix;
                    red[h-y] = 0x00FF0000 & ceilPix;
                    green[h-y] = 0x0000FF00 & ceilPix;
                    blue[h-y] = 0x000000FF & ceilPix;
                    for (Lighting light : level.getLighting()) {
                        
                        ceilPix = getColor(currentFloorX,currentFloorY,ceilPix,light);
                    }
                    dest[(h - y) * w + x] = ceilPix;
                    if (y == h - 1) {
                        dest[(h - (y + 1)) * w + x] = ceilPix;
                    }
                }
                System.arraycopy(alpha, 0, calpha, 0, alpha.length);
                System.arraycopy(red, 0, cred, 0, red.length);
                System.arraycopy(green, 0, cgreen, 0, green.length);
                System.arraycopy(blue, 0, cblue, 0, blue.length);
                for (Lighting light : level.getLighting()) {
                    if (light.getTypeInt() == Lighting.UNIVERSAL) {
                        //colorx = getShadeColor(colorx, light.getColor(), 0, 0, light.getIntensity(), light.getType());
                        double[] percent = new double[(drawEnd - drawStart) + 1];
                        Arrays.fill(percent, light.getIntensity());
                        Core.Subtract_V64fV64f_V64f(light.getAlphaComp(drawEnd - drawStart), 0, calpha, 0, calpha, 0, calpha.length);
                        Core.Subtract_V64fV64f_V64f(light.getRedComp(drawEnd - drawStart), 0, cred, 0, cred, 0, cred.length);
                        Core.Subtract_V64fV64f_V64f(light.getGreenComp(drawEnd - drawStart), 0, cgreen, 0, cgreen, 0, cgreen.length);
                        Core.Subtract_V64fV64f_V64f(light.getBlueComp(drawEnd - drawStart), 0, cblue, 0, cblue, 0, cblue.length);
                        Core.Multiply_V64fV64f_V64f(percent, 0, calpha, 0, calpha, 0, calpha.length);
                        Core.Multiply_V64fV64f_V64f(percent, 0, cred, 0, cred, 0, cred.length);
                        Core.Multiply_V64fV64f_V64f(percent, 0, cgreen, 0, cgreen, 0, cgreen.length);
                        Core.Multiply_V64fV64f_V64f(percent, 0, cblue, 0, cblue, 0, cblue.length);
                        Core.Add_V64fV64f_V64f(alpha, 0, calpha, 0, calpha, 0, calpha.length);
                        Core.Add_V64fV64f_V64f(red, 0, cred, 0, cred, 0, cred.length);
                        Core.Add_V64fV64f_V64f(green, 0, cgreen, 0, cgreen, 0, cgreen.length);
                        Core.Add_V64fV64f_V64f(blue, 0, cblue, 0, cblue, 0, cblue.length);
                    }else{
                        
                    }

                    System.arraycopy(calpha, 0, alpha, 0, alpha.length);
                    System.arraycopy(cred, 0, red, 0, red.length);
                    System.arraycopy(cgreen, 0, green, 0, green.length);
                    System.arraycopy(cblue, 0, blue, 0, blue.length);
                }
            }

            //End of Ceiling and Floor
            
            //sprites time
            
        }//end for loop
        
        //now time for transparent sprites
        if (level.doesHaveTransparent()) {
            for (int x = start; x < end; x++) {
                //calculate ray position and direction 
                double cameraX = 2 * (x / ((double) w)) - 1; //x-coordinate in camera space
                double rayDirX = player.getDir().getX() + plane.getX() * cameraX;
                double rayDirY = player.getDir().getY() + plane.getY() * cameraX;

                //which box of the map we're in  
                int mapX = (int) (rayPosX);
                int mapY = (int) (rayPosY);

                //length of ray from current position to next x or y-side
                double sideDistX;
                double sideDistY;

                //length of ray from one x or y-side to next x or y-side
                double rayDirX2 = rayDirX * rayDirX;
                double rayDirY2 = rayDirY * rayDirY;
                double deltaDistX = Math.sqrt(1 + (rayDirY2) / (rayDirX2));
                double deltaDistY = Math.sqrt(1 + (rayDirX2) / (rayDirY2));

                double perpWallDist;

                //what direction to step in x or y-direction (either +1 or -1)
                int stepX;
                int stepY;

                int hit = 0; //was there a wall hit?
                int side = 0; //was a NS or a EW wall hit?

                //calculate step and initial sideDist
                if (rayDirX < 0) {
                    stepX = -1;
                    sideDistX = (rayPosX - mapX) * deltaDistX;
                } else {
                    stepX = 1;
                    sideDistX = (mapX + 1.0 - rayPosX) * deltaDistX;
                }
                if (rayDirY < 0) {
                    stepY = -1;
                    sideDistY = (rayPosY - mapY) * deltaDistY;
                } else {
                    stepY = 1;
                    sideDistY = (mapY + 1.0 - rayPosY) * deltaDistY;
                }
                //perform DDA
                while (hit == 0) {
                    //jump to next map square, OR in x-direction, OR in y-direction
                    if (sideDistX < sideDistY) {
                        sideDistX += deltaDistX;
                        mapX += stepX;
                        side = 0;
                    } else {
                        sideDistY += deltaDistY;
                        mapY += stepY;
                        side = 1;
                    }
                    //Check if ray has hit a wall
                    if (level.isWall(mapX, mapY)) {
                        if (level.getWallSprite(mapX, mapY) instanceof TransparentCell) {
                            hit = 1;
                            break;
                        }
                        hit = 2;
                    }
                }
                if (hit == 2) {
                    continue;
                }

                //Calculate distance of perpendicular ray (oblique distance will give fisheye effect!)
                if (side == 0) {
                    perpWallDist = Math.abs((mapX - rayPosX + ((1 - stepX) >> 1)) / (rayDirX));
                } else {
                    perpWallDist = Math.abs((mapY - rayPosY + ((1 - stepY) >> 1)) / (rayDirY));
                }

                //Calculate height of line to draw on screen
                int lineHeight = Math.abs((int) (h / (perpWallDist)));

                //calculate lowest and highest pixel to fill in current stripe
                int drawStart = (int) ((h >> 1) - (lineHeight >> 1));
//            drawStart += 50;
                if (drawStart < 0) {
                    drawStart = 0;
                }
                //trying to look down
                int drawEnd = (int) ((lineHeight >> 1) + (h >> 1));
//            drawEnd +=50;
                if (drawEnd >= h) {
                    drawEnd = h - 1;
                }
                //texturing calculations
                Image2D texture = level.getWallSprite(mapX, mapY);
                int invisibleColor = ((TransparentCell) texture).getInvisColor();

                //calculate value of wallX
                double wallX; //where exactly the wall was hit
                if (side == 1) {
                    wallX = rayPosX + ((mapY - rayPosY + ((1 - stepY) >> 1)) / (rayDirY)) * rayDirX;
                } else {
                    wallX = rayPosY + ((mapX - rayPosX + ((1 - stepX) >> 1)) / (rayDirX)) * rayDirY;
                }
                wallX -= Math.floor((wallX));

                //x coordinate on the texture
                //assume everything is 64x64
                int texX = (int) (wallX * texture.getWidth());
                if (side == 0 && rayDirX > 0) {
                    texX = texture.getWidth() - texX - 1;
                }
                if (side == 1 && rayDirY < 0) {
                    texX = texture.getWidth() - texX - 1;
                }
                //fairly certain to look up and down, we need to move the drawStart and drawEnd points
                int hAndLine = (lineHeight << 7) - (h << 7);

                double mapActualX = PlayerPosX + rayDirX * perpWallDist, mapActualY = PlayerPosY + rayDirY * perpWallDist;
                //this reduces the number of lights we need to check for all y points. Still going to, but it's the number that counts
                ArrayList<Lighting> lights = level.getLightsAtLocation(new Vector2(mapActualX, mapActualY));
                for (int y = drawStart; y < drawEnd; y++) {
                    int d = ((y << 8) + hAndLine); //256 and 128 factors to avoid floats
                    int texY = ((d * texture.getWidth()) / lineHeight) >> 8;
                    int color = 0;
                    try {
                        //shading walls
                        color = texture.getColor(texX, texY);
                        if ((color & 0xFFFFFF) != invisibleColor) {
                            for (Lighting light : lights) {
                                color = getColor(mapActualX, mapActualY, color, light);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                int ny = y +50;
//                if(ny >= h){
//                    continue;
//                }
                    if ((color & 0xFFFFFF) != invisibleColor) {
                        dest[y * w + x] = color;
                        if (y == drawEnd - 1) {//otherwise, there would be an annoying white line along the bottom of the walls....
                            dest[(y + 1) * w + x] = color;
                        }
                        //to eliminate some sprites being drawn over the door
                        depthBuffer[x] = perpWallDist;
                    }
                }
            }
        }
    }

    @Override
    protected void compute() {
        if (end - start < threshold) {
            render();
        } else {
            invokeAll(A,B);
        }
    }
    
    public int getColor(double posX, double posY, int color, Lighting light) {
        int ret=color;
        if (light.getType() == Lighting.LightingType.universal) {
            int hash = colorHashCode(color, light.getColor(), 0, 0, light.getIntensity());
            if (colorHashMap.containsKey(hash)) {
                return colorHashMap.get(hash);
            }
            //very expensive, worse case
            ret= getShadeColor(ret, light.getColor(), 0, 0, light.getIntensity(), light.getType());
            colorHashMap.put(hash, ret);
            return ret;
        } else {
            //need to make sure it doesn't go through walls still....
            Vector2 lightpos = light.getPos();
            float lightRange = light.getRange();
            double distance = new Vector2(posX, posY).distanceSquare(lightpos);
            if (distance > (lightRange * lightRange)) {
                return ret;
            }
            if (light.getType() == Lighting.LightingType.directional) {
                Vector2 directionToMe = new Vector2(posX - lightpos.getX(), posY - lightpos.getY());
                //double angle = acos(directionToMe.dotProduct(light.getDirection())/(light.getDirection().length() * directionToMe.length()));
//                if (directionToMe.getThetaFast(light.getDirection())> light.getAngle()){
//                    return ret;
//                }
                if(theta(directionToMe,light.getDirection())>light.getAngle()){
                    return ret;
                }
            }
            
            int hash = colorHashCode(color, light.getColor(), (long)(distance*100), lightRange, light.getIntensity());
            if (colorHashMap.containsKey(hash)) {
                return colorHashMap.get(hash);
            }
            //very expensive, worse case
            ret= getShadeColor(ret, light.getColor(), distance, light.getRange(), light.getIntensity(), light.getType());
            colorHashMap.put(hash, ret);
            return ret;
        }
    }

    //20 operations
    public int colorHashCode(int colorA, int colorB, long distSquare, float length, float intensity) {
        int hash = 7;
        hash = 59 * hash + colorA;
        hash = 59 * hash + colorB;
        hash = 59 * hash + (int) (distSquare ^ (distSquare >>> 32));
        hash = 59 * hash + (int) (Float.floatToIntBits(length));
//        hash = 59 * hash + (int)(100*length);
        hash = 59 * hash + Float.floatToIntBits(intensity);
//        hash = 59 * hash + (int)(100*intensity);
        return hash;
    }
    
    //57 operations
    public int getShadeColor(int colorA, int colorB, double distSquare, float length, float intensity, LightingType type) {
        int finalColor = 0;

        int alphaA = (colorA & 0xFF000000);
        int alphaB = (colorB & 0xFF000000);

        int redA = colorA & 0x00FF0000;
        int redB = colorB & 0x00FF0000;

        int greenA = colorA & 0x0000FF00;
        int greenB = colorB & 0x0000FF00;

        int blueA = colorA & 0x000000FF;
        int blueB = colorB & 0x000000FF;
        
        double dist = 0;
        if (type == Lighting.LightingType.universal) {
            dist = intensity;
        } else {
            
            //dist = Math.sqrt(distSquare);

            dist = 1.0 - distSquare / (length*length);
            dist *= intensity;
            
        }

        finalColor |= 0xFF000000 & (int) (alphaA + dist * (alphaB - alphaA*1.0));
        finalColor |= 0x00FF0000 & (int) (redA + dist * (redB - redA));
        finalColor |= 0x0000FF00 & (int) (greenA + dist * (greenB - greenA));
        finalColor |= 0x000000FF & (int) (blueA + dist * (blueB - blueA));

        return finalColor;
    }
    
    public float percentGet(int colorpart, int color, double distSquare, float length, float intensity, LightingType type){
        int alphaA = (color & colorpart);
        double dist = 0;
        if (type == Lighting.LightingType.universal) {
            dist = intensity;
        } else {
            
            //dist = Math.sqrt(distSquare);

            dist = 1.0 - distSquare / (length*length);
            dist *= intensity;
            
        }
        return (float)(dist * alphaA);
    }
//    private double acos( double x) {
//        return (-0.69813170079773212 * x * x - 0.87266462599716477) * x + 1.5707963267948966;
//    }
    //we can get away with this since when creating the light, we normalize the length of the direction vector
    private double theta(Vector2 lightToPix, Vector2 ligthDir){
        return AMath.acos(lightToPix.dotProduct(ligthDir)/lightToPix.length());
    }
}
