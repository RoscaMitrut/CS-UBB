import numpy as np
import matplotlib.pyplot as plt
from random import random
import math

def generare_numere(n):
    lista = []
    for i in range(n):
        lista.append((random(),random()))
    return lista

def interior_cerc(lista,lungime_latura):
    mijloc = (lungime_latura/2,lungime_latura/2)
    contor = 0
    for element in lista:
        if(math.dist(element,mijloc) < lungime_latura/2):
            contor+=1
    return(contor/len(lista))

def colturi(lista,lungime_latura):
    mijloc = (lungime_latura/2,lungime_latura/2)
    contor = 0
#    for element in lista:
#        if(..):
#            contor+=1
    return(contor/len(lista))


N=2000
#generare patrat
x = [0,0,1,1]
y = [0,1,0,1]
plt.plot(x,y,'o')
lista_numere = generare_numere(N)
for elem in lista_numere:
    plt.plot(elem[0],elem[1], '.')

print(interior_cerc(lista_numere,1))



plt.show()