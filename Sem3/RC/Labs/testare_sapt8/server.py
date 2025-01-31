import socket
import threading

def is_balanced_number(num):
    str_num = str(num)
    odd_sum = sum(int(digit) for digit in str_num[::2]) 
    even_sum = sum(int(digit) for digit in str_num[1::2]) 
    return odd_sum == even_sum

def echilibrare(input_num):
    num = input_num + 1 
    while True:
        if is_balanced_number(num):
            return num
        num += 1

def handle_client(client_socket):
    switch = False
    while True:
        n = client_socket.recv(1024).decode()
        n = int(n)

        if(n==0):
            switch = True
        
        print(f"Am primit numarul {n}")
        n2 = echilibrare(n)

        response = f"{n2}"
        response.strip()

        client_socket.send(response.encode())

        if(switch):
            client_socket.send("stop".encode())
            print("S-a incheiat o conexiune")
            break
        else:
            client_socket.send("ok".encode())
    
    client_socket.close()

host = '127.0.0.1'
port = 8888

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((host, port))
server.listen(5)

print(f"Listening on {host}:{port}")

while True:
    client, addr = server.accept()
    print(f"Accepted connection from {addr[0]}:{addr[1]}")
    client_handler = threading.Thread(target=handle_client, args=(client,))
    client_handler.start()