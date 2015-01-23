from Game import AnimatedSprite
from Utilities import Vector2
from PythonBeans import Updateable
import time

class Projectile(Updateable):
	def __init__(self,images, pos, color, transition, speed, dir):
		self.sprite = AnimatedSprite(images, pos, color, False,transition)
		self.pos = pos
		self.dir = dir
		self.speed = speed
		self.lasttime = int(round(time.time()*1000));
	
	def update(self):
		nowtime = int(round(time.time()*1000))
		moveSpeed = ((nowtime - self.lasttime)/1000.0)*self.speed
		self.pos.dX(self.dir.getX()*moveSpeed)
		self.pos.dY(self.dir.getY()*moveSpeed)
		self.lasttime=nowtime
	def getSprite(self):
		return self.sprite