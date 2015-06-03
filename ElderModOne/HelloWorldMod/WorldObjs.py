from PythonBeans import Updateable
from PythonBeans import Drawable
from PythonBeans import GuiElement
from Game import Sprite
from Utilities import Vector2
from java.lang import Math
class Weapon(Updateable, GuiElement):
	def __init__(self, img, spwn_loc, inviscolor, dmg, maingame):
		self.tsprite = Sprite(img, spwn_loc, inviscolor, False)
		self.dmg = dmg
		self.pos = spwn_loc
		self.drawgui = False
		self.game=maingame
	def update(self):
		if self.game.getPlayer().getPos().distanceSquare(self.pos) <= (0.5* 0.5):
			self.game.getLevel().removeSprite(self.tsprite)
			self.game.getPlayer().getAttrib("weapons").add(self)
			self.drawgui = True
	def getPosition(self):
		return self.pos
	def getSprite(self):
		return self.tsprite
	def draw(self, batch):
		if self.drawgui:
			batch.Draw(self.tsprite.getSprite(),Vector2(32,32),120)

class Sword(Weapon):
	def __init__(self, spwn_loc, maingame):
		Weapon.__init__(self,"HelloWorldMod/sprites/sword.png",spwn_loc,0x000000, 10, maingame)
	def draw(self,batch):
		if self.drawgui:
			batch.Draw(self.tsprite.getSprite(),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.7),-Math.PI/4,5.5,120)