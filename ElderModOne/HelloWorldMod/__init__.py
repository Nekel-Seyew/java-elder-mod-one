from Game import Level
from PythonBeans import Lighting
from Utilities import Vector2
from Utilities import KeyBoard
from Game import Player
from Game import TextBox

from java.util import ArrayList

from Game import Pathfinding
from Game import Sprite

from Sound import SoundPlayer

#from Misc import LightEnemy

from eldermodone import MainGame
from LogicObjs import KeyboardCont
from WorldObjs import Sword
from Gui import UI

print "\nHello, world!"
level = Level()
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
#level.putWallSprite(2,"HelloWorldMod/sprites/redbrick.png")
level.putWallSprite(2,["HelloWorldMod/sprites/redbrick.png","HelloWorldMod/sprites/purplestone.png","HelloWorldMod/sprites/mossy.png"],150)
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
#print light.toString()
level.addLighting(light)

#light2 = Lighting(Vector2(20.5,11.5),0xFFFFFF,3,player.getDir().clone(),player.getFov()/4,Lighting.LightingType.directional,0.5)
#level.addLighting(light2)

path = Pathfinding.getPath(Vector2(1,1),Vector2(21,12),level)
#for x in path:
    #print x


level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(20.5,11.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(18.5,4.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(10,4.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(10,12.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,6.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,20.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(3.5,14.5),0x000000,False))
level.addSprite(Sprite("HelloWorldMod/sprites/greenlight.png",Vector2(14.5,20.5),0x000000,False))


level.addLighting(Lighting(Vector2(18.5,4.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(10,4.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(10,12.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,6.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,20.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(3.5,14.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))
level.addLighting(Lighting(Vector2(14.5,20.5),0xFFFFFF,2,Vector2(),0.0,Lighting.LightingType.ambient,0.5))

level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(21.5,1.5),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(15.5,1.5),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(16,1.8),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(16.2,1.2),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(3.5,2.5),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(9.5,15.5),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(10,15.1),0x000000,True))
level.addSprite(Sprite("HelloWorldMod/sprites/barrel.png",Vector2(10.5,15.8),0x000000,True))

sword = Sword(Vector2(19,11.5),maingame)
#level.addSprite(Sprite("HelloWorldMod/sprites/sword.png",Vector2(19,11.5),0xFFFFFF,False))
level.addSprite(sword.getSprite())
maingame.addUpdateable(sword)
maingame.addGuiElement(sword)

kcontrol = KeyboardCont(keyboard,maingame)
maingame.addUpdateable(kcontrol)

#textbox = TextBox(Vector2(400,200),Vector2(750,100),0xFFFFFF,"Welcome to HelloWorldDemo Mod!",0,0,0x000000,500,13,5,0x00FF00,1000);
#maingame.addUpdateable(textbox)
#maingame.addGuiElement(textbox)

ui = UI(maingame)
maingame.addUpdateable(ui)
maingame.addGuiElement(ui)

maingame.giveLevel(level)
player.setPos(Vector2(22,11.5))

#musak = SoundPlayer("HelloWorldMod/music/Enochian_Magic.mp3")
#musak.start()
maingame.addSound("HelloWorldMod/music/Enochian_Magic.mp3")

player.setAttrib("weapons",ArrayList())
player.setAttrib("health",100)

#lightEnemy = LightEnemy(Vector2(1,1),Vector2(0,1),2,2,0xFFFFFF,3)
#lightEnemy.moveTo(player.getPos().clone(),level)
#maingame.addUpdateable(lightEnemy)
#level.addLighting(lightEnemy.getLight())