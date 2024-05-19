from random import sample
from matplotlib.pyplot import hist, show
from scipy.stats import bernoulli, geom, hypergeom


def ex1(p=0.5, pasi=20, n=1000):
    # a
    nod = 0
    pozitiiNod = []

    for i in range(pasi):
        pozitiiNod.append(nod)
        nod += (bernoulli.rvs(p) * 2 - 1)

    print("a) Poz: ", pozitiiNod)

    # b
    data = []

    for i in range(n):
        nod = 0
        for j in range(pasi):
            nod += (bernoulli.rvs(p) * 2 - 1)

        data.append(nod)


    left = min(data)
    right = max(data)

    bin_edges = [k + 0.5 for k in range(left - 1, right + 1)]
    hist(data, bin_edges, density=True, rwidth=0.9, color='red', edgecolor='black')
    show()


def ex1c(p=0.5, pasi=200, nCerc=50, n=1000):
    start = 0

    data = []

    for i in range(n):
        nod = start
        for j in range(pasi):
            nod += (bernoulli.rvs(p) * 2 - 1)

            if nod < 0:
                nod = nCerc - 1
            if nod == nCerc:
                nod = 0

        data.append(nod)

    left = min(data)
    right = max(data)

    bin_edges = [k + 0.5 for k in range(left - 1, right + 1)]
    hist(data, bin_edges, density=True, rwidth=0.9, color='red', edgecolor='black')
    show()


def ex2(pasi=200):
    lista = []
    for i in range(pasi):
        total = 0
        castigat = False

        while not castigat:
            bileAlese = sample(range(1, 50), 6)

            bileCastigatoare = sample(range(1, 50), 6)

            corecte = 0

            for bila in bileAlese:
                if bila in bileCastigatoare:
                    corecte += 1

            if corecte >= 3:
                castigat = True

            total += 1

        lista.append(total - 1)

    print("\nBilete cumparate pana la 'castig': ", lista)

    areLoc = 0
    for nr in lista:
        if nr >= 10:
            areLoc += 1

    print("\nProbabilitate simulata pentru evenimentul dat este: ", areLoc / pasi * 100, '%')

    p = sum(hypergeom.pmf(k, 49, 6, 6) for k in range(3, 7))

    probTeoretica = 1 - geom.cdf(9, p)

    print("\nProb teoretica ptr eveniment este: ", probTeoretica * 100, '%')




ex1()
ex1c()
ex2()