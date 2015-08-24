from Game import Enemy
from Utilities import Vector2
from PythonBeans import Updateable
from java.util import ArrayList

class EnemyControll(Updateable):
	def __init__(self,maingame,keyboard):
		self.list = ArrayList()
		self.game = maingame
		self.keyboard = keyboard
	def update(self):
		for enemy in self.list:
			enemy.update()
class Testing(Updateable):
	def __init__(self,player,maingame,level):
		self.enemy = Enemy(player.getPos(),player.getDir(),1)
		self.maingame = maingame
		self.player = player
		self.locations = [Vector2(1,1),Vector2(14,12),Vector2(1,21),Vector2(22,21),Vector2(15,20),Vector2(22,2),Vector2(1,1),Vector2(8,21),Vector2(1,1),Vector2(22,11.5)]
		self.index = 0
		self.level=level
		maingame.addUpdateable(self.enemy)
	def update(self):
		if self.index+1 == len(self.locations):
			self.maingame.exit()
			return
		if not self.enemy.isMoveTo():
			self.enemy.moveTo(self.locations[self.index],self.level)
			self.index +=1

class BasicEnemy(Enemy):
	def __init__(self, pos, speed, sprite, inviscolor, dmg):
		super(BasicEnemy,self).__init__(pos,Vector2(),speed)
		self.mysprite = Sprite(sprite,pos,inviscolor,True)
		self.dmg = dmg
	def getSprite():
		return self.mysprite
	def getDamage():
		return self.dmg