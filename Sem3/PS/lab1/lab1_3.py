from random import sample
#from math import perm, comb
from itertools import permutations, combinations

def aranjamente(cuvant,nr,numar_total = None,aleator = None):
	litere = list(cuvant)
	aranj = permutations(litere,nr)

	if numar_total==True:
		nr_total = 0;
		for i in aranj:
			nr_total += 1
		print(nr_total)
		return
	
	if aleator==True:
		if nr<len(cuvant):         
			print(sample(litere,2))
			return
		return
	
	for i in aranj:
		print(i)
	
def combinari(cuvant,nr,numar_total = None,aleator = None):
		litere = list(cuvant)
		comb = combinations(litere,nr)	

		if numar_total==True:
			nr_total = 0
			for i in comb:
				nr_total+=1
			print(nr_total)
			return
		
		if aleator==True:
			print(sample(list(comb),1)[0])
			return
		
		for i in comb:
			print(i)

aranjamente("word",2)
aranjamente('word',2,numar_total=True)
aranjamente('word',2,aleator=True)
print("-----")
combinari('word',2)
combinari('word',2,numar_total=True)
combinari('word',2,aleator=True)