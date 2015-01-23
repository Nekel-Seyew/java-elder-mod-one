from Game import Enemy
from Game import Sprite
from PythonBeans import Lighting
from PythonBeans import Drawable
from Utilities import Vector2

class GlowEnemy(Enemy):
	def __init__(self, pos, speed, spritepath, color, range):
		Enemy.__init__(self,pos,None,speed)
		self.light = Lighting(pos,color,range,Vector2(1,1),0,Lighting.LightingType.ambient,0.25)
		self.draw = Sprite(spritepath,pos,0xFFFFFF,False)
		self.draw.setScale(0.5,0.5);
		#self.draw.setVerticalMove(31)
	def getLight(self):
		return self.light
	def getDrawable(self):
		return self.draw
	
	def getPosition(self):
		return self.draw.getPosition()
	def getSprite(self):
		return self.draw.getSprite()
	def getScale(self):
		return self.draw.getScale()
	def getVerticalMove(self):
		return self.draw.getVerticalMove()
	def getInvisibleColor(self):
		return self.draw.getInvisibleColor(self)
	def doShade(self):
		return self.draw.doShade()