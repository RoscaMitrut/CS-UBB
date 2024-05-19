from scipy.stats import uniform, rv_discrete, expon
import numpy as np
import matplotlib.pyplot as plt
from collections import Counter

def check_condition(p, k, u):
    sum1 = 0
    for i in range(k):
        sum1 += p[i]
    sum2 = sum1 + p[k]
    return sum1 < u <= sum2

def generate_random_numbers(x, p, n):
    u = uniform.rvs(size=n)
    random_numbers = []
    for i in range(n):
        for k in range(len(p)):
            if check_condition(p, k, u[i]):
                random_numbers.append(x[k])
    return random_numbers

def generate_random_numbers_exp(n, alpha):
    uniform_numbers = uniform.rvs(size=n)
    exp_numbers = np.log(1 - uniform_numbers) / (-alpha)
    return exp_numbers


def pct_unu(n):
    grupe_sanguine = ['0', 'A', 'B', 'AB']
    probabilitati = [0.46, 0.40, 0.10, 0.04]

    data = generate_random_numbers(grupe_sanguine, probabilitati, n)

    category_counts = Counter(data)

    x_positions = np.arange(len(grupe_sanguine))

    plt.bar(x_positions, [category_counts[category] / n for category in grupe_sanguine], width=0.4, color='red',
            alpha=0.5, label='Frecvente relative')

    plt.bar(x_positions + 0.4, probabilitati, width=0.4, color='blue', alpha=0.5, label='Probabilitati teoretice')

    plt.xticks(x_positions + 0.2, grupe_sanguine)
    plt.legend()
    plt.show()

def pct_doi(n):
    alpha = 1 / 12.0

    data = generate_random_numbers_exp(n, alpha)
    plt.hist(data, bins=12, density=True, range=(0, 60), color='blue')

    x = range(0, 60)
    plt.plot(x, expon.pdf(x, loc=0, scale=1/alpha), 'r-')
    plt.xticks(range(0, 60, 5))
    plt.show()

    simulated_p = np.sum(data >= 5) / n
    print("Estimare simulată a P(E): ", simulated_p)

    theoretical_p = 1 - expon.cdf(5, scale=1/alpha)
    print("Estimare teoretică a P(E): ", theoretical_p)

pct_doi(200)
pct_unu(50)