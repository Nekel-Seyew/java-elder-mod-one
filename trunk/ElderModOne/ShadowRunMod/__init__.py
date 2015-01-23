from Game import Level
from PythonBeans import Lighting
from Utilities import Vector2
from Utilities import KeyBoard
from Utilities import Vector2
from Game import Player
from Game import Sprite
from GlowEnemy import GlowEnemy
from Projectile import Projectile

from eldermodone import MainGame
#import HelloWorldMod.KeyboardCont
#from LogicObjs import KeyboardCont

#print "Hello, world!"
level = Level()

stuff =[[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],
		[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0]]

walls =[[0,0,0,0,0,0,1,1,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[1,1,1,1,1,1,1,0,1,1,1,1,1,1,1],
		[1,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
		[1,1,1,1,1,1,1,0,1,1,1,1,1,1,1],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,0,1,0,0,0,0,0,0],
		[0,0,0,0,0,0,1,1,1,0,0,0,0,0,0]]
level.setFloor(stuff)
level.putFloorSprite(1,"ShadowRunMod/sprites/floors/floor1.png")
level.putWallSprite(1,["ShadowRunMod/sprites/walls/wall1.png","ShadowRunMod/sprites/walls/eagle.png"],250)
level.setWalls(walls)

glowenemy = GlowEnemy(Vector2(6.5,7.5),2,"ShadowRunMod/sprites/enemy/spheroid.png",0x54F9FF,2)
level.addLighting(glowenemy.getLight())
maingame.addUpdateable(glowenemy)
level.addSprite(glowenemy.getDrawable())

projectile = Projectile(["ShadowRunMod/sprites/enemy/projectile/basic-1.png","ShadowRunMod/sprites/enemy/projectile/basic-2.png","ShadowRunMod/sprites/enemy/projectile/basic-3.png","ShadowRunMod/sprites/enemy/projectile/basic-4.png"],Vector2(10,7.5),0x000000,250,0.25,Vector2(-1,0))
maingame.addUpdateable(projectile)
level.addSprite(projectile.getSprite())

'''
#note, the vertical axis is your X, and the horizontal is the Y. Because yes.....
stuff = [
  [8,8,8,8,8,8,8,8,8,8,8,4,4,6,4,4,6,4,6,4,4,4,6,4],
  [8,0,0,0,0,0,0,0,0,0,8,4,0,0,0,0,0,0,0,0,0,0,0,4],
  [8,0,3,3,0,0,0,0,0,8,8,4,0,0,0,0,0,0,0,0,0,0,0,6],
  [8,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,6],
  [8,0,3,3,0,0,0,0,0,8,8,4,0,0,0,0,0,0,0,0,0,0,0,4],
  [8,0,0,0,0,0,0,0,0,0,8,4,0,0,0,0,0,6,6,6,0,6,4,6],
  [8,8,8,8,0,8,8,8,8,8,8,4,4,4,4,4,4,6,0,0,0,0,0,6],
  [7,7,7,7,0,7,7,7,7,0,8,0,8,0,8,0,8,4,0,4,0,6,0,6],
  [7,7,0,0,0,0,0,0,7,8,0,8,0,8,0,8,8,6,0,0,0,0,0,6],
  [7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,6,0,0,0,0,0,4],
  [7,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,8,6,0,6,0,6,0,6],
  [7,7,0,0,0,0,0,0,7,8,0,8,0,8,0,8,8,6,4,6,0,6,6,6],
  [7,7,7,7,0,7,7,7,7,8,8,4,0,6,8,4,8,3,3,3,0,3,3,3],
  [2,2,2,2,0,2,2,2,2,4,6,4,0,0,6,0,6,3,0,0,0,0,0,3],
  [2,2,0,0,0,0,0,2,2,4,0,0,0,0,0,0,4,3,0,0,0,0,0,3],
  [2,0,0,0,0,0,0,0,2,4,0,0,0,0,0,0,4,3,0,0,0,0,0,3],
  [1,0,0,0,0,0,0,0,1,4,4,4,4,4,6,0,6,3,3,0,0,0,3,3],
  [2,0,0,0,0,0,0,0,2,2,2,1,2,2,2,6,6,0,0,5,0,5,0,5],
  [2,2,0,0,0,0,0,2,2,2,0,0,0,2,2,0,5,0,5,0,0,0,5,5],
  [2,0,0,0,0,0,0,0,2,0,0,0,0,0,2,5,0,5,0,5,0,5,0,5],
  [1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,5],
  [2,0,0,0,0,0,0,0,2,0,0,0,0,0,2,5,0,5,0,5,0,5,0,5],
  [2,2,0,0,0,0,0,2,2,2,0,0,0,2,2,0,5,0,5,0,0,0,5,5],
  [2,2,2,2,1,2,2,2,2,2,2,1,2,2,2,5,5,5,5,5,5,5,5,5]
]

level.setWalls(stuff)
level.putWallSprite(1,"HelloWorldMod/sprites/eagle.png")
level.putWallSprite(2,"HelloWorldMod/sprites/redbrick.png")
level.putWallSprite(3,"HelloWorldMod/sprites/purplestone.png")
level.putWallSprite(4,"HelloWorldMod/sprites/greystone.png")
level.putWallSprite(5,"HelloWorldMod/sprites/bluestone.png")
level.putWallSprite(6,"HelloWorldMod/sprites/mossy.png")
level.putWallSprite(7,"HelloWorldMod/sprites/wood.png")
level.putWallSprite(8,"HelloWorldMod/sprites/colorstone.png")
level.putFloorSprite(1,"HelloWorldMod/sprites/greyStone.png")
level.putFloorSprite(2,"HelloWorldMod/sprites/bluestone.png")
level.putCeilingSprite(1,"HelloWorldMod/sprites/wood.png")

stuff1 = [
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,1],
  [1,2,1,1,2,2,2,2,2,1,1,1,2,2,2,2,2,2,2,2,2,2,2,1],
  [1,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1],
  [1,2,1,1,2,2,2,2,2,1,1,1,2,2,2,2,2,2,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,1,1,1,2,1,1,1],
  [1,1,1,1,2,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,1],
  [1,1,1,1,2,1,1,1,1,2,1,2,1,2,1,2,1,1,2,1,2,1,2,1],
  [1,1,2,2,2,2,2,2,1,1,2,1,2,1,2,1,1,1,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,1,2,1,2,1],
  [1,1,2,2,2,2,2,2,1,1,2,1,2,1,2,1,1,1,1,1,2,1,1,1],
  [1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,2,1,1,1],
  [1,1,1,1,2,1,1,1,1,1,1,1,2,2,1,2,1,1,2,2,2,2,2,1],
  [1,1,2,2,2,2,2,1,1,1,2,2,2,2,2,2,1,1,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,1,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,2,1,1,1,2,2,2,1,1],
  [1,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,2,2,1,2,1,2,1],
  [1,1,2,2,2,2,2,1,1,1,2,2,2,1,1,2,1,2,1,2,2,2,1,1],
  [1,2,2,2,2,2,2,2,1,2,2,2,2,2,1,1,2,1,2,1,2,1,2,1],
  [1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1],
  [1,2,2,2,2,2,2,2,1,2,2,2,2,2,1,1,2,1,2,1,2,1,2,1],
  [1,1,2,2,2,2,2,1,1,1,2,2,2,1,1,2,1,2,1,2,2,2,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
]
level.setFloor(stuff1)
stuff2 = [
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
  [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
]
level.setCeil(stuff2)

light = Lighting(Vector2(3,3),0x000000,10,Vector2(), 0.0, Lighting.LightingType.universal,0.5)
print light.toString()
level.addLighting(light)

light2 = Lighting(Vector2(20.5,11.5),0xFFFFFF,3,player.getDir().clone(),player.getFov()/4,Lighting.LightingType.directional,0.5)
level.addLighting(light2)

path = Pathfinding.getPath(Vector2(1,1),Vector2(21,12),level)
for x in path:
    print x


level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(20.5,11.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(18.5,4.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(10,4.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(10,12.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,6.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,20.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,14.5),0xFFFFFF,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(14.5,20.5),0xFFFFFF,False))


level.addLighting(Lighting(Vector2(18.5,4.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(10,4.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(10,12.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,6.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,20.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,14.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(14.5,20.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))

level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(21.5,1.5),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(15.5,1.5),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(16,1.8),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(16.2,1.2),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(3.5,2.5),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(9.5,15.5),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(10,15.1),0xFFFFFF,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(10.5,15.8),0xFFFFFF,True))

kcontrol = KeyboardCont(keyboard,maingame)
maingame.addUpdateable(kcontrol)
'''
maingame.giveLevel(level)
player.setPos(Vector2(1,7.5))
player.setDir(Vector2(1,0))
maingame.setBackgroundColor(0x000000)