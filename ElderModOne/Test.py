import math

poop=0
for y in range(1,1000):
    for x in range(1,100000):
        poop += math.sin(x)
print(poop)