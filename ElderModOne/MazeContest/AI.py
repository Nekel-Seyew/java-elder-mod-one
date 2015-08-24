from Game import Enemy
from Utilities import Vector2
from java.lang import Math

class Drenji(Enemy):
	def __init__(self,pos, game):
		super(Drenji,self).__init__(pos,Vector2(),1)
		self.dead = False
		self.goToLoc = None
		self.game = game
	def update(self):
		super(Drenji,self).update()
		if (self.goToLoc is None) or self.isMoveTo() is False:
			self.goToLoc = Vector2(Math.rand()*29,Math.rand()*29)