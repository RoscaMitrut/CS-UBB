import socket

TCP_IP = "127.0.0.1"
TCP_PORT = 8888

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))

ok = "ok"

while(ok.startswith("ok")):
	n = input("Dati un numar: ")
	s.send(n.encode())

	data = s.recv(1024)
	print(data.decode())
	
	ok = s.recv(1024).decode()	

s.close()