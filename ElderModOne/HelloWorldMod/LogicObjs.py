from PythonBeans import Updateable
from java.awt.event import KeyEvent
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