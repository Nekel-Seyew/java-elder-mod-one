/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RayCasting;

import Advance.AMath;
import Game.Level;
import Game.Player;
import Hardware_Accelerated.AccelGame;
import PythonBeans.Drawable;
import PythonBeans.Lighting;
import Utilities.ImageCollection;
import Utilities.Vector2;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author KyleSweeney
 */
public class Camera {
    //Vector2 plane = new Vector2(0, 0.66);
    private Vector2 resolution = new Vector2();
    private CameraRender r;
    private ForkJoinPool pool;
    //double[] zBuffer;
    
    //BufferedImage sceneOld;
    private int[] buffer;
    
    //share resources, and don't do more work than you need to do.
    private HashMap<Integer, Integer> colorHashMap;
    private Drawable[] sortHelper;

    public Camera(Vector2 resolution) {
        //this.nullVector = new Vector2(0, 0);
        //this.plane = new Vector2(0, 0.66);
        this.resolution = resolution;
        colorHashMap = new HashMap<Integer,Integer>(64,0.2f);
        r = new CameraRender(0,(int)resolution.getX(),null,null,null,resolution,(int)resolution.getX()/4);
        pool = new ForkJoinPool();
        //sceneOld = new BufferedImage((int)resolution.getX(), (int)resolution.getY(), BufferedImage.TYPE_INT_ARGB);
        buffer = new int[(int)resolution.getX() * (int)resolution.getY()];
        sortHelper = new Drawable[10];
    }

    /*
    This is good, so far. Placing everything into a single buffer speeds things up a bit.
    However, we still have the problem of cache misses: either when checking for a wall segment,
    obtaining the pixel value in the images, or even placing things into the 1D buffer.
    Really, the 1D buffer is a 2D buffer we are pretending is 1D.
    
    New strategy, the levels, when loaded, are going to Transpose themselves.
    */
    public void render(Level level, Player player, ImageCollection batch) {
        BufferedImage scene = new BufferedImage((int)resolution.getX(), (int)resolution.getY(), BufferedImage.TYPE_INT_ARGB);
        int[] buffer = this.buffer;
        //generate the plane vector
        
        for(int i=0; i<buffer.length; i++){
            buffer[i] = 0x00000000;
        }
        
        double[] depthBuffer = new double[(int)resolution.getX()];
        
        level.ensureSortedLights();

        //because reasons, this dosen't go higher than 7.
        r.update(0, (int) resolution.getX(), buffer,depthBuffer, player, level, resolution, (int) resolution.getX() /7);
        pool.invoke(r);
        
        int h = (int)resolution.getY();

        //may or may not work
        //sprite draw time
        //sprites sorted on ZBuffer
        ArrayList<Drawable> sprites = level.getSprites();
        
        Vector2 plane = player.getDir().clone();
        plane.rotate(-Math.PI / 2);
        double lengthOfFov = player.getDir().length() * Math.tan(player.getFov() / 2);
        plane.scalarMultiply(lengthOfFov / plane.length());
        
        double playerDirX = player.getDir().getX();
        double playerDirY = player.getDir().getY();
        
        double invDet = 1.0/(plane.getX()*playerDirY - plane.getY()*playerDirX);
        
//        ArrayList<InnerSprite> msprites = new ArrayList<InnerSprite>(sprites.size());
//        for(Drawable d : sprites){
//            msprites.add(new InnerSprite(d,player.getPos()));
//        }
//        Collections.sort(msprites);
        if(this.sortHelper.length < sprites.size()){
            this.sortHelper = new Drawable[(sprites.size())];
        }
        qSortSprites(sprites,this.sortHelper,player.getPos(),0,sprites.size()-1);
//        System.out.println("Starting to draw sprites");
//        System.out.println("msprites size: "+msprites.size());
        for(Drawable sprite : sprites){
            double spriteX = sprite.getPosition().getX() - player.getPos().getX();
            double spriteY = sprite.getPosition().getY() - player.getPos().getY();
            
            double transformX = invDet * (playerDirY * spriteX - playerDirX * spriteY);
            double transformY = invDet * (-plane.getY() * spriteX + plane.getX()*spriteY);
            
            int spriteScreenX = (int)(resolution.getX()/2 * (1 + transformX/transformY));
            
            int vMoveScreen = (int)(sprite.getVerticalMove()/transformY);
            int spriteHeight = (int)(Math.abs((int)(h/transformY)) / (1/sprite.getScale().getY()));
            
            int drawStartY = (h>>1) - (spriteHeight>>1) + vMoveScreen;
            if(drawStartY < 0) drawStartY =0;
            int drawEndY = (h>>1) + (spriteHeight>>1) + vMoveScreen;
            if(drawEndY >= h) drawEndY=h-1;
            
            int spriteWidth = (int)(Math.abs((int)(h / transformY)) / (1/sprite.getScale().getX()));
            int drawStartX = spriteScreenX - (spriteWidth>>1);
            if(spriteWidth < 0 ) drawStartX =0;
            int drawEndX = spriteScreenX + (spriteWidth>>1);
            if(drawEndX >= (int)resolution.getX()) drawEndX = (int)resolution.getX() -1;
//            System.out.println("DrawStartX: "+drawStartX);
//            ArrayList<Lighting> lights = level.getLightsAtLocation(sprite.location);
            
            ArrayList<Lighting> lights = new ArrayList<Lighting>();//empty list
            if(sprite.doShade()){
                lights = level.getLightsAtLocation(sprite.getPosition());
            }
            for(int stripe = drawStartX; stripe < drawEndX; stripe++){
                int texX = (int)(256 * (stripe - (spriteScreenX - (spriteWidth>>1))) * sprite.getSprite().getWidth()/spriteWidth) /256;
                if((transformY > 0 && stripe > 0) && (stripe < resolution.getX() && transformY < depthBuffer[stripe] )){
                    for(int y = drawStartY; y < drawEndY; y++){
                        int d = (y-(int)sprite.getVerticalMove()) * 256 - h*128 + spriteHeight*128; //assume sprites are 64x64
                        int texY = ((d * sprite.getSprite().getHeight())/spriteHeight)/256;
                        int color = sprite.getSprite().getColor(Math.abs(texX), Math.abs(texY));
                        if((color & 0xFFFFFF) != sprite.invisibleColor()){ //needs to be bitwise AND together because invisColor is only RGB and not ARGB
                            for(Lighting light : lights){
                                color = getColor(sprite.getPosition().getX(),sprite.getPosition().getY(),color,light);
                            }
                            buffer[y * (int) resolution.getX() + stripe] = color;
                        }
                    }
                }
            }
            
        }
        
        
//        batch.Draw(scene, new Vector2(AccelGame.frame.getWidth() / 2, AccelGame.frame.getHeight() / 2), 100);
        scene.setRGB(0, 0, (int)resolution.getX(), (int)resolution.getY(), buffer, 0, (int)resolution.getX());//faster, by like 2ms. Thats enough to validate it.
        batch.Draw(scene, new Vector2(AccelGame.frame.getWidth()>>1, AccelGame.frame.getHeight()>>1), 
                (float)(AccelGame.frame.getWidth()/resolution.getX()), 
                (float)(AccelGame.frame.getHeight()/resolution.getY()), 
                100);
    }
    
    private double notZero(double in) {
        if (Math.abs(0 - in) < 1E-6) {
            return 1E-6;
        }
        return in;
    }
    
    private int[][] transpose(int[][] array){
        int[][] arrayT = new int[array[0].length][array.length];
        for(int i=0; i<arrayT.length; i++){
            for(int j=0; j<arrayT[i].length; j++){
                arrayT[i][j]=array[j][i];
            }
        }
        return arrayT;
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
                if (theta(directionToMe,light.getDirection())> light.getAngle()){
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
     //we can get away with this since when creating the light, we normalize the length of the direction vector
    private double theta(Vector2 lightToPix, Vector2 ligthDir){
        return AMath.acos(lightToPix.dotProduct(ligthDir)/lightToPix.length());
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
    public int getShadeColor(int colorA, int colorB, double distSquare, float length, float intensity, Lighting.LightingType type) {
        int finalColor = 0;

        int alphaA = colorA & 0xFF000000;
        int alphaB = colorB & 0xFF000000;

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

        finalColor |= 0xFF000000 & (int) (alphaA + dist * (alphaB - alphaA));
        finalColor |= 0x00FF0000 & (int) (redA + dist * (redB - redA));
        finalColor |= 0x0000FF00 & (int) (greenA + dist * (greenB - greenA));
        finalColor |= 0x000000FF & (int) (blueA + dist * (blueB - blueA));

        return finalColor;
    }
    
    private void qSortSprites(ArrayList<Drawable> sprites, Drawable[] helper, final Vector2 player, final int start, final int end){
        if(start < end){
            final int pivot = qSortHelper(sprites,helper,player,start,end);
            qSortSprites(sprites,helper,player,start,pivot);
            qSortSprites(sprites,helper,player,pivot+1,end);
        }
    }
    
    private int qSortHelper(ArrayList<Drawable> sprites, Drawable[] helper, Vector2 player, int lo, int hi){
        int pIndex = (int)(Math.random() * (hi-lo)) + lo;
        double pValue = sprites.get(pIndex).getPosition().distanceSquare(player);
        //swap
        Drawable d = sprites.get(pIndex);
        sprites.set(pIndex, sprites.get(hi));
        sprites.set(hi, d);
        int storeIndex = lo;
        for(int i= lo; i < hi; i++){
            if(sprites.get(i).getPosition().distanceSquare(player) > pValue){
                //swap
                d = sprites.get(i);
                sprites.set(i, sprites.get(storeIndex));
                sprites.set(storeIndex, d);
                storeIndex +=1;
            }
        }
        //swap
        d = sprites.get(hi);
        sprites.set(hi, sprites.get(storeIndex));
        sprites.set(storeIndex, d);
        return storeIndex;
    }
    
    private void sortSprites(ArrayList<Drawable> sprites, Drawable[] helper, Vector2 player, int start, int end){
        if(start == end) return;
        if(end - start <=1) return;
        int middle = start + (end - start)/2;
        sortSprites(sprites, helper, player, start, middle);
        sortSprites(sprites, helper, player, middle+1, end);
        
        int left = start, right = middle, k=0;
        while(k < (end-start)){
            if (left < middle && right < end) {
                //feels backwards because in reality, we want it largest first, smallest last.
                if (sprites.get(left).getPosition().distanceSquare(player) >= sprites.get(right).getPosition().distanceSquare(player)) {
                    helper[k + start]= sprites.get(left);
                    left += 1;
                    k += 1;
                } else {
                    helper[(k + start)] = sprites.get(right);
                    right += 1;
                    k += 1;
                }
            } else if (left < middle) {
                helper[(k + start)] = sprites.get(left);
                left += 1;
                k += 1;
            } else {
                helper[(k + start)] = sprites.get(right);
                right += 1;
                k += 1;
            }
        }
        for(k=start; k < end; k++){
            sprites.set(k, helper[k]);
        }
    }

    public Vector2 getResolution() {
        return resolution;
    }

    public HashMap<Integer, Integer> getColorHashMap() {
        return colorHashMap;
    }
    
    
}
