import socket

TCP_IP = "127.0.0.1"
#TCP_IP = "172.30.114.72"
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
	
	nr_spatii = data.decode().count(' ')

	print ("Am trimis la client", nr_spatii)
	conn.send(str(nr_spatii).encode()) 

conn.close()