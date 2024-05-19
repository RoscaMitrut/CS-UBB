import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import norm

nr_sim = 5000 #numar simulari
X = np.random.normal(165, 10, nr_sim) # valori generate
plt.hist(X, bins=16, density=True, range=(130,210), alpha=0.7, color='blue') # X

minim = np.min(X) #valoare minima X
maxim = np.max(X) #valoare maxima X
Y = np.linspace(minim, maxim, 1000) # valorile dintre min si max, distribuite uniform
plt.plot(Y,norm.pdf(Y,165,10),'r') # graficul functiei de densitate

#valori_interval = np.sum((X >= 160) & (X <= 170))
#probabilitate_interval = valori_interval / nr_sim
#print(f"Probabilitatea ca X sa fie intre 160 si 170: {probabilitate_interval}")
print(f"Media: {np.mean(X)}")
print(f"Deviatia standard: {np.std(X)}")
plt.show()