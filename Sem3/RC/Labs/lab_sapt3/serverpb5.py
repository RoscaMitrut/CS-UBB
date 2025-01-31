#Un client trimite unui server un numar.
#Serverul va returna clientului sirul divizorilor
#acestui numar.
import socket
import math

def divisorGenerator(n):
    large_divisors = []
    for i in range(1, int(math.sqrt(n) + 1)):
        if n % i == 0:
            yield i
            if i*i != n:
                large_divisors.append(n / i)
    for divisor in reversed(large_divisors):
        yield divisor


TCP_IP = "172.30.114.72"
TCP_PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((TCP_IP, TCP_PORT))
s.listen(1)

while 1:
	conn, addr = s.accept()
	print ('Connection address:', addr)
	
	data = conn.recv(100)
	print ("Am primit de la client", data)
	if not data: break


	divizori_lista = list(divisorGenerator(int(data.decode())))
	
	divizori_lista2 = []

	for i in divizori_lista:
		divizori_lista2.append(str(int(i)))

	divizori = ','.join(divizori_lista2)

	print ("Am trimis la client", divizori)
	conn.send(str(divizori).encode()) 

conn.close()