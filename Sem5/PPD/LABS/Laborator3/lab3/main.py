import random

def generate_file(filename, n):
    """Generates a file with n numbers in the specified format."""
    with open(filename, 'w') as file:
        # Write the number of digits on the first line
        file.write(f"{n}\n")
        # Generate n random numbers and write each on a new line
        for _ in range(n):
            file.write(f"{random.randint(0, 9)}\n")

generate_file("Numar1.txt", 1000)
generate_file("Numar2.txt", 1000)
