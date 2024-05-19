import random
from random import choices, sample, randrange
from math import comb, perm
from matplotlib.pyplot import bar, hist, grid, show, legend
from scipy.stats import binom
from math import comb
##########################################################################################################################################################

def ex1(n):
	print("EX1")
	P_AB = 0
	P_A = 0

	for i in range(n):
		rosu = 5
		albastru= 3
		verde = 2
		aparitii_rosu = 0
		aparitii_albastru = 0
		aparitii_verde = 0
		bile_extrase = 0

		while bile_extrase < 3:
			bila_selectata = random.randint(0, rosu+albastru+verde+1)

			if bila_selectata in range(0,rosu):
				rosu -= 1
				aparitii_rosu += 1

			elif bila_selectata in range(rosu,rosu+albastru):
				albastru -= 1
				aparitii_albastru += 1

			elif bila_selectata in range(rosu+albastru,rosu+albastru+verde):
				if verde > 0:
					verde -= 1
					aparitii_verde += 1
				else:
					bile_extrase -= 1

			bile_extrase += 1

		A = (aparitii_rosu > 1)
		B = (aparitii_rosu == 3) or (aparitii_albastru == 3)

		if (B and A):
			P_AB += 1

		if (A == True):
			P_A += 1

	print("Simulat: P(B|A) = ", P_AB / P_A )

	pA = 1 - comb(10,3) / comb(10,5)
	pAB = comb(10,3) / comb(10,5)

	print("Calculat: P(B|A) = ", pAB / pA )

##########################################################################################################################################################

def ex2():
	data = [randrange(1, 7) for _ in range(500)]

	bin_edges = [k + 0.5 for k in range(0, 7)]

	hist(data, bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black',
		 alpha=0.5, label='frecvente relative')

	distribution = dict([(i, 1/6) for i in range(1, 7)])

	bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black',
		alpha=0.6, label='probabilitati')

	legend(loc='lower left')
	grid()
	show()

##########################################################################################################################################################

def ex3(n):
	print("EX3")
	bile1 = 6
	bile0 = 4
	data = []

	for i in range(n):
		sum = 0

		for j in range(5):
			c = random.randint(1, 10);
			if c <= 6:
				sum += 1
			else:
				sum += 0

		data.append(sum)

	print("Valori X: ", data)

	distribution = dict([(i, 0) for i in range(6)])

	for i in range(6):
		distribution[i] = binom.pmf(i, 5, 6/10)

	bin_edges = [k + 0.5 for k in range(-1, 6)]

	hist(data, bin_edges, density=True, rwidth=0.9, color='green', edgecolor='black',
		 alpha=0.5, label='frecvente relative')

	bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black',
		alpha=0.6, label='probabilitati')

	legend(loc='upper left')

	grid()
	show()

	count = 0

	for i in data:
		if i > 2 and i <= 5:
			count += 1

	print("Estimat P(2 < X <= 5): ", count/n)

	rez = binom.pmf(3, 5, 6/10) + binom.pmf(4, 5, 6/10) + binom.pmf(5, 5, 6/10)

	print("Calculat P(2 < X <= 5): ", rez)

##########################################################################################################################################################

def ex4():
	print("EX4")
	n = 2000

	maxNo = 0
	prefferedNo = 0

	data = [0 for i in range(19)]

	for i in range(1, 19):
		no = 0
		for j in range(n):
			rez = random.randint(1, 6) + random.randint(1, 6) + random.randint(1, 6)
			data[rez] += 1
			if i == rez:
				no += 1

		if no > maxNo:
			maxNo = no
			prefferedNo = i

	print("Best number by simulation: ", prefferedNo, ", with prob of appearing: ", maxNo / n)

	print("Best number calculated: 10 or 11, with prob of appearing: ", 100/800)

	newData = data[3:]

	bin_edges = [k + 0.5 for k in range(2, 19)]

	bar(range(3, 19), [(value / n) / 18 for value in newData], width=0.85, color='green', edgecolor='black', alpha=0.6,
		label='frecvente relative')

	distribution = dict([(3, 1/216), (4, 3/216), (5, 6/216), (6, 10/216), (7, 15/216),
						 (8, 21/216), (9, 25/216), (10, 27/217), (11, 27/217), (12, 25/216),
						 (13, 21/216), (14, 15/216), (15, 10/216), (16, 6/216), (17, 3/216), (18, 1/256)])

	bar(list(distribution.keys()), list(distribution.values()), width=0.85, color='red', edgecolor='black',
		alpha=0.6, label='probabilitati')

	legend(loc='upper left')

	show()

##########################################################################################################################################################

ex1(100000)
ex2()
ex3(1000)
ex4()