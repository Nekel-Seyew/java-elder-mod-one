from PythonBeans import Updateable
from PythonBeans import GuiElement
from Utilities import Vector2
from Utilities import Image2D
from LogicObjs import Door
from java.awt import Color
from java.lang import Math
class UI(Updateable,GuiElement):
	def __init__(self, maingame):
		self.game=maingame
		self.health = 0
	def update(self):
		self.health = self.game.getPlayer().getAttrib("health")
	def draw(self,batch):
		batch.fillRect(Vector2(0,self.game.GAME_HEIGHT*0.8),self.game.GAME_WIDTH,int(self.game.GAME_HEIGHT*0.2),Color(0x00FF00),200)
		batch.fillRect(Vector2(5,self.game.GAME_HEIGHT*0.8 + 5),int(self.game.GAME_WIDTH)-10,int(self.game.GAME_HEIGHT*0.2)-10,Color(0xFFFFFF),201)
		#batch.drawString
class EndGame(Updateable,GuiElement):
	def __init__(self, maingame,keyboard):
		self.game = maingame
		self.addedMusic = False
		self.sprite = Image2D("HelloWorldMod/sprites/galaxy/galaxy2.png")
		self.sprite2 = Image2D("HelloWorldMod/sprites/galaxy/stars.jpg")
		self.tdoor = Door(["HelloWorldMod/sprites/door/door1.png","HelloWorldMod/sprites/door/door2.png","HelloWorldMod/sprites/door/door3.png","HelloWorldMod/sprites/door/door4.png","HelloWorldMod/sprites/door/door5.png","HelloWorldMod/sprites/door/door6.png","HelloWorldMod/sprites/door/door7.png","HelloWorldMod/sprites/door/door8.png","HelloWorldMod/sprites/door/door9.png"],125,0x000000,maingame,keyboard)
	def update(self):
		if self.game.getPlayer().getPos().distanceSquare(Vector2(8,23.5)) <= (0.5*0.5):
			if not self.addedMusic:
				self.game.addSound("HelloWorldMod/music/Enochian_Magic.mp3")
				self.addedMusic=True
			self.tdoor.setWalkthrough(True)
	def getDoor(self):
		return self.tdoor
	def draw(self,batch):
		scalex = self.game.GAME_WIDTH/self.sprite.getWidth() *1.2
		scaley = self.game.GAME_HEIGHT/self.sprite.getHeight() *1.2
		scalex2 = self.game.GAME_WIDTH/self.sprite2.getWidth() *1.5
		scaley2 = self.game.GAME_HEIGHT/self.sprite2.getHeight() *1.5
		tims = 1;
		if self.game.getPlayer().getDir().getX() < 0:
			tims = 1;
		else:
			tims = -1;
		plusx = self.game.getPlayer().getDir().getTheta(Vector2(0,1))*(self.game.GAME_WIDTH/Math.PI) * tims
		batch.Draw(self.sprite2,Vector2(self.game.GAME_WIDTH-self.game.GAME_WIDTH/2 - plusx,self.game.GAME_HEIGHT/2),0.0,float(scalex2),float(scaley2),90)
		batch.Draw(self.sprite,Vector2(self.game.GAME_WIDTH/2 + plusx,self.game.GAME_HEIGHT/2),0.0,float(scalex),float(scaley),90)
		batch.Draw(self.sprite2,Vector2(self.game.GAME_WIDTH*1.5 + plusx,self.game.GAME_HEIGHT/2),0.0,float(scalex2),float(scaley2),90)