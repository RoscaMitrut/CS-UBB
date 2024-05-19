from random import randint
from numpy.random import randint
from math import factorial

def aranj(n,k):
    return int(factorial(n)/factorial(n-k))

def generare_zile_nastere():
    lista_persoane = []
    for i in range(23):
        temp = randint(1,365)
        lista_persoane.append(temp)
    return lista_persoane

def duplicate_lista(secventa):
  seen = set()
  seen_add = seen.add
  seen_twice = set( x for x in secventa if x in seen or seen_add(x) )
  return len(list( seen_twice ))>0

#1A
##
numar_repetari = 1000
##
contor = 0
for i in range(numar_repetari):
    lista = []
    lista = generare_zile_nastere()
    if(duplicate_lista(lista)==True):
        contor+=1
print("Estimare probabilitate: ",contor/numar_repetari)

#1B
aranj = aranj(365,23)
prob = 1 - (aranj/365**23)
print("Probabilitate: " , prob)