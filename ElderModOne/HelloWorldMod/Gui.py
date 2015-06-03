from PythonBeans import Updateable
from PythonBeans import GuiElement
from Utilities import Vector2
from java.awt import Color
class UI(Updateable,GuiElement):
	def __init__(self, maingame):
		self.game=maingame
		self.health = 0
	def update(self):
		self.health = self.game.getPlayer().getAttrib("health")
	def draw(self,batch):
		batch.fillRect(Vector2(0,self.game.GAME_HEIGHT*0.8),self.game.GAME_WIDTH,int(self.game.GAME_HEIGHT*0.2),Color(0x00FF00),200)
		batch.fillRect(Vector2(5,self.game.GAME_HEIGHT*0.8 + 5),int(self.game.GAME_WIDTH)-10,int(self.game.GAME_HEIGHT*0.2)-10,Color(0xFFFFFF),201)