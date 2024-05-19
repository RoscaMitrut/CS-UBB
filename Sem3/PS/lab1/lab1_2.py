from random import sample
from math import factorial
from itertools import permutations,combinations

permutari = permutations("word")
print("Permutari:")
l = []
for element in permutari:
    print(element[0],element[1],element[2],element[3])
    l.append(element)

print("Numar de permutari:")
print(factorial(len("word")))

print("Permutare random:")
perm_random = sample(l,1)
print(perm_random[0][0],perm_random[0][1],perm_random[0][2],perm_random[0][3])
