from PythonBeans import Updateable
from PythonBeans import GuiElement
from Utilities import Image2D
from Utilities import Mouse
from Utilities import KeyBoard
from Utilities import Vector2
from java.awt import Color

class UIControll(Updateable,GuiElement):
	def __init__(self,mouse,keyboard,game):
		self.game = game
		self.mouse = mouse
		self.keyboard = keyboard
		self.orb01 = False
		self.orb12 = False
		self.layer = 0
		#self.box = TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"",0,0,0x000000,067,13,5,0x00FF00,500)
		self.haveOrb01 = False
		self.haveOrb12 = False
		self.orb01Sprite = Image2D("MazeContest/sprites/orb01Big.png")
		self.orb12Sprite = Image2D("MazeContest/sprites/orb12Big.png")
		self.isEnd = False
	def update(self):
		if self.keyboard.isKeyDown('q'):
			self.orb01 = True
			self.orb12 = False
		if self.keyboard.isKeyDown('e'):
			self.orb01 = False
			self.orb12 = True
		if self.mouse.isPressed(Mouse.LEFT_BUTTON):
			if self.orb01:
				self.layer = 0
			if self.orb12:
				self.layer = 1
		if self.mouse.isPressed(Mouse.RIGHT_BUTTON) or self.mouse.isPressed(Mouse.OTHER_BUTTON):
			if self.orb01:
				self.layer = 1
			if self.orb12:
				self.layer = 2
	def getLayer(self):
		return self.layer
	def draw(self,batch):
		if self.orb01 and self.haveOrb01:
			batch.Draw(self.orb01Sprite,Vector2(self.game.GAME_WIDTH*0.2,self.game.GAME_HEIGHT*0.8),0.0,0.8,0.8,500)
		if self.orb12 and self.haveOrb12:
			batch.Draw(self.orb12Sprite,Vector2(self.game.GAME_WIDTH*0.2,self.game.GAME_HEIGHT*0.8),0.0,0.8,0.8,500)
		if self.isEnd:
			batch.fillRect(Vector2(0,0),self.game.GAME_WIDTH,self.game.GAME_HEIGHT,Color(0x000000),2000)
	def giveOrb01(self):
		self.haveOrb01 = True
		self.orb01 = True
	def giveOrb12(self):
		self.haveOrb12 = True
		self.orb12 = True
	def haveOrb12Q(self):
		return self.haveOrb12
	def setEnd(self,val):
		self.isEnd = val
		