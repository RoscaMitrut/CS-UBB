import socket
#Un client trimite unui server un sir de caractere
#si un caracter. Serverul va returna clientului
#toate pozitiile pe care caracterul primit se
#regaseste in sir.

#TCP_IP = "127.0.0.1"
TCP_IP = "172.30.111.53"
TCP_PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))

MESSAGE = input("Dati un sir de caractere: ")
s.send(MESSAGE.encode())
print("Am trimis la server: ",MESSAGE)

CHARACTER = input("Dati un caracter: ")
s.send(CHARACTER.encode())
print("Am trimis la server: ",CHARACTER)

data = s.recv(100)
s.close()
print("Am primit de la server: ", data.decode())