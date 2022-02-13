'''

    Script inicial de inicialização do servidor.

    Valores de configuração se encontram no pacote 'config'.
    Classes principais de manipulação de requisições se encontram no pacote 'core'.

    @autors daniel e mara

'''

from config import *
from core import *
import socket, threading, time

mutex = threading.Semaphore()

def handlerErrorJSON(socket, client, error):
    print()
    print("ERROR: " + str(error))
    print("Cause: " + str(error.__cause__))
    print("Context: " + str(error.__context__))
    error_message = str(error) + "\n" + str(error.__context__)
    response = Packet(2, 5, 1, 1, "", "", json_bytearray(error_message))
    print("LOG: Resposta de error:", response)
    socket.send(response.getBytes())
    print()

def resolver(socket, client):
    print('LOG: Conectado:', client)
    resolver = Resolver()
    while True:
        data = socket.recv(PACKET_SIZE)
        if len(data) <= 0: break

        request = Packet.fromBytes(data)
        print("LOG: Pacote: " + str(request))
        print("LOG: Data:", data)

        try:
            mutex.acquire()
            data = resolver.resolve(request)
            mutex.release()
            time.sleep(0.1)

            response = Packet(2, request.verb, 1, request.format_data, "", "", data)
            data = response.getBytes()
            socket.send(data)
            print("LOG: Resposta: ", response)
            print("LOG: Data: ", data)
        except Exception as error:
            handlerErrorJSON(socket, client, error) 
    socket.close()
    print('LOG: Close:', client)
    print()

tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp.bind((HOST, PORT))
tcp.listen(1)


while True:
    connection, client = tcp.accept()
    t = threading.Thread(target = resolver, args=(connection, client))
    t.start()