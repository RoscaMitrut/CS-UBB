from numpy import exp,mean,random

def function(x):
    return exp(-x**2)

def monte_carlo(a, b, num_samples=5000):
    random_samples = random.uniform(a, b, num_samples) # sample de elemente din [a,b]
    function_values = function(random_samples) # evaluare functie pt [a,b]
    integral_estimate = mean(function_values) * (b - a) #estimare
    
    return integral_estimate

result = monte_carlo(-1, 3)

print(f"Valoare estimata: {result}")