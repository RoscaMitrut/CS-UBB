'''
Sa jucam "război"
Clientul ia un Nr între 4 și 9 și trimite la server.
Serverul generează o matrice de n *n
Și apoi marchează n poziții cu 1
Serverul trimite clientului apoi mesaj că jocul a început.
Clientul trimite poziții până când serverul zice stop.
Răspunsul la o poziție poate fi:
Da - daca a lovit o poziție cu 1
Nu - daca nu a lovit o poziție cu 1
Maxim 5 poziții greșite sau până când a lovit pe toate.
Mesaj de final
'''
import socket

TCP_IP = "127.0.0.1"
TCP_PORT = 8888

n = str(input("Dati un numar intre 4 si 9: "))

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))

s.send(n.encode('utf-8'))

data = s.recv(1024)
print(data.decode('utf-8'))



s.close()
