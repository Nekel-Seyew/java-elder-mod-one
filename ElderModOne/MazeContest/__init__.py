from Game import Level
from Game import Player
from Game import TextBox
from Game import DialogueBox
from Game import Sprite
from Game import Enemy
from PythonBeans import TransparentCell
from PythonBeans import Lighting
from PythonBeans import Updateable
from PythonBeans import AnimatedCell
from Utilities import Vector2
from Utilities import KeyBoard
from Utilities import Mouse
from java.util import ArrayList
from java.lang import Math

from Engin import Engin
from GUI import UIControll

class LevelController(Updateable):
	def layerData(self):
		self.layer0data =  [
						[1,1,1,1,1,3,4,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,0,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,0,0,0,1,0,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1],
						[1,1,0,0,2,0,0,0,0,0,0,1,1,1,0,0,1,1,0,0,0,0,1,0,0,0,0,0,1],
						[1,1,1,1,0,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		self.layer1data =  [
						[1,1,1,1,1,3,4,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1],
						[1,1,0,0,2,0,0,0,0,2,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1],
						[1,1,1,1,0,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,0,1,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,1,1,1,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,1,1,0,1,1,1,1,0,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,1,0,1,0,0,0,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,8,1,1,1,1]
					]
		self.layer2data =  [
						[1,1,1,1,1,3,4,5,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,0,0,0,1,0,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,0,1,0,1,1,0,1,0,9],
						[1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,0,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,1,1,1,1,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,0,0,0,1,0,1,0,1,0,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,1,1,1,1,0,1,0,1,0,1,1,0,1,0,1,1,1,0,1],
						[1,1,0,0,0,0,0,0,0,2,0,1,1,1,0,0,1,1,0,0,0,0,1,0,0,0,0,0,1],
						[1,1,1,1,0,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,1,1,0,0,0,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,1,0,1,0,1,0,1,1,1,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,1,0,1,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,0,0,1,1,1,1,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,1,1,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,1,1,0,1,0,0,0,0,1],
						[1,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,1,0,1,1,0,1,1,1,1,0,1],
						[1,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,1,1,0,1,0,0,0,0,1],
						[1,1,1,1,1,1,6,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
	def setUpDatas(self):
		self.floor = [
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		self.level1data = [ #0 is the value of empty
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		self.ceil = self.floor #same data, a 29x29 grid of sameness
		self.level2data =  [
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,1,1,1,0,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,1,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,0,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,1,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,1,1,1,1,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,0,2,2,1,1,0,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,0,1,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		self.level3data =  [
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		self.level4data =  [
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,0,1,1,0,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,2,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,0,1,0,1,1,0,0,0,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,0,0,0,0,2,1,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
						[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
					]
		
	def __init__(self,player, game, keyboard):
		self.setUpDatas()
		self.layerData()
		self.player = player
		self.game = game
		self.level = Level()
		self.keyboard = keyboard
		
		self.level.setFloor(self.floor)
		self.level.setCeil(self.ceil)
		self.level.setWalls(self.level1data)
		self.level.putFloorSprite(1,"MazeContest/sprites/wood.png")
		self.level.putCeilingSprite(1,"MazeContest/sprites/wood.png")
		self.level.putWallSprite(1,"MazeContest/sprites/colorstone.png")
		self.startPos = Vector2(11,14)
		self.startDir = Vector2(1,0)
		self.game.giveLevel(self.level)
		self.game.getPlayer().setPos(self.startPos.clone())
		self.game.getPlayer().setDir(self.startDir.clone())
		self.UI = UIControll(mouse,keyboard,game)
		self.game.addUpdateable(self.UI)
		self.game.addGuiElement(self.UI)
		#time to set up text boxes
		self.textBoxes = ArrayList()
		#First Event data
		self.firstEvent = True
		self.firstEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*I Turn Around*",0,0,0x000000,125,13,5,0x00FF00,1000))
		#second Event Data
		self.secondEvent = False
		self.secondEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*I Turn Around Again*",0,0,0x000000,125,13,5,0x00FF00,1000))
		#third event data
		self.thirdEvent = False
		self.thirdEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*Again, and Again. It's all I can do...*",0,0,0x000000,125,13,5,0x00FF00,1000))
		#fourth event data
		self.fourthEvent = False
		self.fourthEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*...Trapped in this abomination of life called The 'Plane of Dreams'*",0,0,0x000000,125,13,5,0x00FF00,1000))
		self.level.putWallSprite(2,"MazeContest/sprites/bluestone.png")
		#fifth event
		self.fifthEvent = False
		self.fifthEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*Euclidean and non-euclidean geometry, you can throw those out the window.\nAnd then the window will jiggle, evaporate, and give birth to an elephant.*",0,0,0x000000,067,13,5,0x00FF00,1000))
		#sixth event
		self.sixthEvent = False
		self.sixthEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*You see, I'm quite a reasonable Twizarech. One of the most reasonable I knew.\nThe Plane of Dreams is anything but reasonable....\n....everything but resonable....*",0,0,0x000000,015,13,5,0x00FF00,1000))
		#self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*It baffles me how I...*",0,0,0x000000,125,13,5,0x00FF00,1000))
		self.seventhEvent = False
		self.seventEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*...Wait*",0,0,0x000000,125,13,5,0x00FF00,1000))
		self.eigthEvent = False
		self.eigthEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*This is a looping corridor.\n-sigh-*",0,0,0x000000,125,13,5,0x00FF00,1000))
		self.ninthEvent = False
		self.ninthEventActivated = False
		self.textBoxes.add(TextBox(Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,"*Huh?*",0,0,0x000000,125,13,5,0x00FF00,1000))
		self.enginEncounter = False
		self.enginEncounterActivated = False
		self.engin = Engin(Vector2(22,21.5))
		self.firstEnginDiag = DialogueBox("MazeContest/first.diag",Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,0,0,0x000000,10,13,5,0x00FF00,1000,self.keyboard)
		self.secondEnginEncounter = False
		self.secondEnginEncounterActivated = False
		self.secondEnginDiag = DialogueBox("MazeContest/second.diag",Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,0,0,0x000000,10,13,5,0x00FF00,1000,self.keyboard)
		self.thirdEnginEncounter = False
		self.thirdEnginEncounterActivated = False
		self.thirdEnginDiag = DialogueBox("MazeContest/fourth.diag",Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,0,0,0x000000,10,13,5,0x00FF00,1000,self.keyboard)
		#post engin, main puzzle section begins
		self.baseSpeed = self.player.getSpeed()
		self.layers = False
		self.layer0 = False
		self.layer1 = False
		self.layer2 = False
		self.orb01sprite = Sprite("MazeContest/sprites/orb01Small.png",Vector2(25.5,25.5),0x010101,True)
		self.orb12sprite = Sprite("MazeContest/sprites/orb12Small.png",Vector2(5.5,25.5),0x010101,True)
		#switches and lights
		self.light1 = AnimatedCell(["MazeContest/sprites/switches/Red1.png","MazeContest/sprites/switches/Red2.png"],0)
		self.light1.setRunOnce(True)
		self.light1.setStop(True)
		self.light2 = AnimatedCell(["MazeContest/sprites/switches/Yellow1.png","MazeContest/sprites/switches/Yellow2.png"],0)
		self.light2.setRunOnce(True)
		self.light2.setStop(True)
		self.light3 = AnimatedCell(["MazeContest/sprites/switches/Blue1.png","MazeContest/sprites/switches/Blue2.png"],0)
		self.light3.setRunOnce(True)
		self.light3.setStop(True)
		self.switch1 = AnimatedCell(["MazeContest/sprites/lever1.png","MazeContest/sprites/lever2.png"],0)
		self.switch1.setRunOnce(True)
		self.switch1.setStop(True)
		self.switch1On = False
		self.switch2 = AnimatedCell(["MazeContest/sprites/lever1.png","MazeContest/sprites/lever2.png"],0)
		self.switch2.setRunOnce(True)
		self.switch2.setStop(True)
		self.switch2On = False
		self.switch3 = AnimatedCell(["MazeContest/sprites/lever1.png","MazeContest/sprites/lever2.png"],0)
		self.switch3.setRunOnce(True)
		self.switch3.setStop(True)
		self.switch3On = False
		self.level.putWallSprite(6,self.switch1)
		self.level.putWallSprite(8,self.switch2)
		self.level.putWallSprite(9,self.switch3)
		self.level.putWallSprite(3,self.light1)
		self.level.putWallSprite(4,self.light2)
		self.level.putWallSprite(5,self.light3)
		#level shade
		self.layer1Shade = Lighting(Vector2(),0x0000FF,0.0,Vector2(),0.0,Lighting.LightingType.universal,0.2)
		self.layer2Shade = Lighting(Vector2(),0xFF00FF,0.0,Vector2(),0.0,Lighting.LightingType.universal,0.2)
		self.allShade = Lighting(Vector2(),0x000000,0.0,Vector2(),0.0,Lighting.LightingType.universal,0.2)
		self.level.addLighting(self.allShade)
		#finale
		self.finalDiag = DialogueBox("MazeContest/third.diag",Vector2(self.game.GAME_WIDTH/2,self.game.GAME_HEIGHT*0.8),Vector2(self.game.GAME_WIDTH*0.9,self.game.GAME_HEIGHT*0.2),0xFFFFFF,0,0,0x000000,10,13,5,0x00FF00,5000,self.keyboard)
		self.finalActivated = False
	def update(self):
		if self.firstEvent:
			if self.firstEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(0))
				self.game.addUpdateable(self.textBoxes.get(0))
				self.firstEventActivated = True
			if self.player.getDir().getTheta(self.startDir) >= Math.PI/2:
				self.firstEvent = False
				self.game.removeGuiElement(self.textBoxes.get(0))
				self.game.removeUpdateable(self.textBoxes.get(0))
				self.secondEvent = True
		if self.secondEvent:
			if self.secondEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(1))
				self.game.addUpdateable(self.textBoxes.get(1))
				self.secondEventActivated = True
			if self.player.getDir().getTheta(self.startDir) <= 0.1:
				self.secondEvent = False
				self.game.removeGuiElement(self.textBoxes.get(1))
				self.game.removeUpdateable(self.textBoxes.get(1))
				self.thirdEvent = True
		if self.thirdEvent:
			if self.thirdEventActivated is False and self.player.getPos().distanceSquare(self.startPos) <= 4:
				self.game.addGuiElement(self.textBoxes.get(2))
				self.game.addUpdateable(self.textBoxes.get(2))
				self.thirdEventActivated = True
			if self.player.getDir().getTheta(self.startDir) >= Math.PI/2:
				self.thirdEvent = False
				self.game.removeGuiElement(self.textBoxes.get(2))
				self.game.removeUpdateable(self.textBoxes.get(2))
				self.fourthEvent = True
		if self.fourthEvent:
			if self.fourthEventActivated is False:
				self.level.setWalls(self.level2data)
				self.game.addGuiElement(self.textBoxes.get(3))
				self.game.addUpdateable(self.textBoxes.get(3))
				self.fourthEventActivated = True
			if self.player.getPos().distanceSquare(Vector2(22,14)) <= 1:
				self.fourthEvent = False
				self.game.removeGuiElement(self.textBoxes.get(3))
				self.game.removeUpdateable(self.textBoxes.get(3))
				self.fifthEvent = True
		if self.fifthEvent:
			if self.fifthEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(4))
				self.game.addUpdateable(self.textBoxes.get(4))
				self.fifthEventActivated = True
			if self.player.getPos().distanceSquare(Vector2(20,22)) <=1:
				self.fifthEvent = False
				self.game.removeGuiElement(self.textBoxes.get(4))
				self.game.removeUpdateable(self.textBoxes.get(4))
				self.sixthEvent = True
		if self.sixthEvent:
			if self.sixthEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(5))
				self.game.addUpdateable(self.textBoxes.get(5))
				self.sixthEventActivated = True
			if self.player.getPos().distanceSquare(Vector2(13,24))<=1 or self.player.getPos().distanceSquare(Vector2(13,17)) <=1:
				self.sixthEvent=False
				self.game.removeGuiElement(self.textBoxes.get(5))
				self.game.removeUpdateable(self.textBoxes.get(5))
				self.level.setWalls(self.level3data)
				self.seventhEvent = True
		if self.seventhEvent:
			if self.seventEventActivated is False and self.player.getPos().distanceSquare(Vector2(13,21)) <= 1:
				self.game.addGuiElement(self.textBoxes.get(6))
				self.game.addUpdateable(self.textBoxes.get(6))
				self.seventEventActivated = True
			if self.player.getPos().distanceSquare(Vector2(19,22)) <= 1:
				self.game.removeGuiElement(self.textBoxes.get(6))
				self.game.removeUpdateable(self.textBoxes.get(6))
				self.seventhEvent = False
				self.eigthEvent = True
		if self.eigthEvent:
			if self.eigthEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(7))
				self.game.addUpdateable(self.textBoxes.get(7))
				self.eigthEventActivated = True
			if self.player.getPos().distanceSquare(Vector2(16,24)) <=1:
				self.level.setWalls(self.level4data)
				self.game.removeGuiElement(self.textBoxes.get(7))
				self.game.removeUpdateable(self.textBoxes.get(7))
				self.eigthEvent = False
				self.ninthEvent = True
		if self.ninthEvent:
			if self.ninthEventActivated is False:
				self.game.addGuiElement(self.textBoxes.get(8))
				self.game.addUpdateable(self.textBoxes.get(8))
				self.ninthEventActivated = True
			if self.player.getPos().distanceSquare(self.engin.pos) <= 4:
				self.ninthEvent = False
				self.game.removeGuiElement(self.textBoxes.get(8))
				self.game.removeUpdateable(self.textBoxes.get(8))
				self.level.addSprite(self.engin.mysprite)
				self.enginEncounter = True
		if self.enginEncounter:
			if self.enginEncounterActivated is False:
				self.game.removeGuiElement(self.textBoxes.get(8))
				self.game.removeUpdateable(self.textBoxes.get(8))
				self.game.addGuiElement(self.firstEnginDiag)
				self.game.addUpdateable(self.firstEnginDiag)
				self.enginEncounterActivated = True
			if self.firstEnginDiag.isDone():
				self.enginEncounter = False
				self.game.removeGuiElement(self.firstEnginDiag)
				self.game.removeUpdateable(self.firstEnginDiag)
				self.game.player.setPos(Vector2(1.5,1.5))
				self.game.player.setDir(Vector2(Math.sqrt(2)/2,Math.sqrt(2)/2))
				self.level.setWalls(self.layer0data)
				self.engin.pos.dX(1)
				#setup stuff for layer0
				self.layer0 = True
				self.level.addSprite(self.orb01sprite)
				self.layers = True
				self.secondEnginEncounter = True
		if self.layer0:
			if self.secondEnginEncounter:
				if self.secondEnginEncounterActivated is False and self.player.getPos().distanceSquare(self.engin.pos) < 4:
					self.game.addGuiElement(self.secondEnginDiag)
					self.game.addUpdateable(self.secondEnginDiag)
					self.secondEnginEncounterActivated = True
				if self.secondEnginDiag.isDone():
					self.secondEnginEncounter = False
					self.game.removeGuiElement(self.secondEnginDiag)
					self.game.removeUpdateable(self.secondEnginDiag)
					self.level.removeSprite(self.engin.mysprite)
					self.engin.pos.reset(3.5,25.5)
					self.thirdEnginEncounter = True
			if self.player.getPos().distanceSquare(Vector2(25.5,25.5)) <= 0.5:
				self.level.removeSprite(self.orb01sprite)
				self.UI.giveOrb01()
		if self.layer1:
			if self.thirdEnginEncounter:
				if self.thirdEnginEncounterActivated is False and self.player.getPos().distanceSquare(self.engin.pos) < 16:
					self.game.addGuiElement(self.thirdEnginDiag)
					self.game.addUpdateable(self.thirdEnginDiag)
					self.level.addSprite(self.engin.mysprite)
					self.thirdEnginEncounterActivated = True
				if self.thirdEnginDiag.isDone():
					self.game.removeGuiElement(self.thirdEnginDiag)
					self.game.removeUpdateable(self.thirdEnginDiag)
					self.level.removeSprite(self.engin.mysprite)
			if self.player.getPos().distanceSquare(Vector2(5.5,25.5)) <= 0.5:
				self.level.removeSprite(self.orb12sprite)
				self.UI.giveOrb12()
			if self.player.getPos().distanceSquare(Vector2(28,24.5)) <= 0.5 and keyboard.isKeyDown('f'):
				self.switch2.setStop(False)
				self.light2.setStop(False)
				self.switch2On = True
		if self.layer2:
			if self.player.getPos().distanceSquare(Vector2(27.5,6.5)) <=1 and keyboard.isKeyDown('f'):
				self.switch1.setStop(False)
				self.light1.setStop(False)
				self.switch1On = True
			if self.player.getPos().distanceSquare(Vector2(2.5,28)) <= 0.5 and keyboard.isKeyDown('f'):
				self.switch3.setStop(False)
				self.light3.setStop(False)
				self.switch3On = True
		if self.layers:
			layer = self.UI.getLayer()
			if layer is 0:
				if self.layer0 is False:
					self.player.setSpeed(self.baseSpeed)
					self.level.removeLighting(self.layer1Shade)
					self.layer0 = True
					self.level.setWalls(self.layer0data)
					self.layer1 = False
					self.layer2 = False
			if layer is 1:
				if self.layer1 is False:
					self.player.setSpeed(self.baseSpeed*2)
					self.level.removeLighting(self.layer2Shade)
					self.level.addLighting(self.layer1Shade)
					self.layer1 = True
					self.level.setWalls(self.layer1data)
					self.layer0 = False
					self.layer2 = False
					if self.UI.haveOrb12Q() is False:
						self.level.addSprite(self.orb12sprite)
			if layer is 2:
				if self.layer2 is False:
					self.player.setSpeed(self.baseSpeed*3)
					self.level.removeLighting(self.layer1Shade)
					self.level.addLighting(self.layer2Shade)
					self.layer2 = True
					self.level.setWalls(self.layer2data)
					self.layer0 = False
					self.layer1 = False
		if (self.switch1On and self.switch2On) and self.switch3On:
			if self.finalActivated is False:
				self.game.addGuiElement(self.finalDiag)
				self.game.addUpdateable(self.finalDiag)
				self.finalActivated = True
				self.UI.setEnd(True)
			if self.finalDiag.isDone():
				self.game.exit()
			
			
			
			

def main():
	levelcontroler = LevelController(player,maingame,keyboard)
	print "Welcome to 'Mazing', which is an a'mazing' game!"
	maingame.addUpdateable(levelcontroler)



main()