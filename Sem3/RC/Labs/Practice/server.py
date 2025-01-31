import socket
import threading
import random

def handle_client(client_socket):
    n = client_socket.recv(1024).decode('utf-8')
    n = int(n)
    mat = [[0 for _ in range(int(n))] for _ in range(int(n))]
    for i in range(n):
        mat[i][i]=1 

    response = f'Jocul a inceput'

    client_socket.send(response.encode('utf-8'))
    client_socket.close()

host = '127.0.0.1'
port = 8888

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((host, port))
server.listen(5)

print(f"[*] Listening on {host}:{port}")

while True:
    client, addr = server.accept()
    print(f"[*] Accepted connection from {addr[0]}:{addr[1]}")
    client_handler = threading.Thread(target=handle_client, args=(client,))
    client_handler.start()