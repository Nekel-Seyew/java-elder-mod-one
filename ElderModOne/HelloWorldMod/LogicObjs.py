from PythonBeans import Updateable
from java.awt.event import KeyEvent
from Utilities import Vector2
class KeyboardCont(Updateable):
	def __init__(self,keyboard,maingame):
		self.escapeOn = False
		self.escape = False
		self.keyboard=keyboard
		self.maingame=maingame
	
	def update(self):
		if not self.keyboard.isKeyDown(KeyEvent.VK_ESCAPE):
			self.escapeOn = False
		if self.keyboard.isKeyDown(KeyEvent.VK_ESCAPE) and not self.escapeOn:
			self.maingame.captureMouse(self.escape)
			self.escape = not self.escape
		if self.keyboard.isKeyDown('p'):
			self.maingame.addSound("HelloWorldMod/music/A-Tone.mp3")
		#if self.maingame.getPlayer().getPos().distanceSquare(Vector2(20.5,14)) <= (0.5*0.5) :
			#self.maingame.getPlayer().setPos(Vector2(20.5,7))