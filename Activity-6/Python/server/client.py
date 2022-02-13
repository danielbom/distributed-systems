'''

    Script de um cliente de teste do servidor.

    Testes rápidos sobre as requisições podem ser feitas por aqui.
    
    [Deprecated]

    @autors daniel e mara

'''
from config import *
from core.packet import Packet
from core.utils import *

import socket, json

def send(msg):
    tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp.connect((HOST, PORT))
    print("LOG: message:", msg)
    print("LOG: data:", msg.data)
    print()
    tcp.send(msg.getBytes())
    for _ in range(1_000_000):
        data = tcp.recv(PACKET_SIZE)
        if len(data) > 0:
            print("LOG: data recv: ", data)
            print()
            response = Packet.fromBytes(data)
            print("LOG: Resposta: ", response)
            print("LOG: Data: ", json.loads(response.data.decode("UTF8")))
            break
    tcp.close()


resources = ["aluno", "curso", "matricula", "disciplina"]
data = json_bytearray([1, "ciência"])
def test_curso():
    print("---------------- DELETE -----------------")
    msg = Packet(1, 4, 1, 1, resources[1], "query>codigo=1", data)
    send(msg)

    print("---------------- SELECT -----------------")
    msg = Packet(1, 1, 1, 1, resources[1], "select>nome", data)
    send(msg)

    print("---------------- INSERT -----------------")
    msg = Packet(1, 2, 1, 1, resources[1], "", data)
    send(msg)

    print("---------------- SELECT -----------------")
    msg = Packet(1, 1, 1, 1, resources[1], "", data)
    send(msg)

    print("---------------- UPDATE -----------------")
    data = json_bytearray([1, "ciência  computação"])
    msg = Packet(1, 3, 1, 1, resources[1], 'query>codigo=1&values>nome="ciencia da computacao"', data)
    send(msg)

    print("---------------- SELECT -----------------")
    msg = Packet(1, 1, 1, 1, resources[1], "", data)
    send(msg)

print("---------------- SELECT -----------------")
msg = Packet(1, 1, 1, 1, resources[3], 'query>disciplina.cod_curso = 1', data)
send(msg)