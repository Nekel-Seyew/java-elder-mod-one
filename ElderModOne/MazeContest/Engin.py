from Game import Enemy
from Game import Sprite
from Utilities import Vector2

class Engin(Enemy):
	def __init__(self,pos):
		super(Engin,self).__init__(pos,Vector2(0,1),0)
		self.mysprite = Sprite("MazeContest/sprites/Engin.png",pos,0x000000,True)
		self.pos = pos
	