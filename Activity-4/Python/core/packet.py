import bitstring


class Packet(object):
    '''
        Classe representante da estrutura de dados utilizada para
    fazer as operações com o cliente.

    Argumentos e atributos:
        mode        (int): Request (1), Response (2)
        verb        (int): GET (1), POST (2), PUT (3), DELETE (4), ERROR (5)
        finished    (int): False (0), True (1)
        format_data (int): AUTO (0), JSON (1), PROTOCOL BUFFER (2)
        resource    (str) [max len 255]
        params      (str) [max len 255]
        data        (bytearray)
    '''
    def __init__(
        self,
        mode: int,
        verb: int,
        finished: int = 1,
        format_data: int = 1,
        resource: str = "",
        params: str = "",
        data: bytearray = bytearray()
    ):
        self.mode = mode
        self.verb = verb
        self.finished = finished
        self.format_data = format_data
        self.resource = resource
        self.params = params
        self.data = data

    def __str__(self):
        end = f"|{self.resource}|{self.params}|{self.finished}|{self.format_data}|{len(self.data)}"
        try:
            mode = ["REQ", "RES"][self.mode-1]
            verb = ["GET", "POST", "PUT", "DELETE"][self.verb-1]
            return f"{mode}|{verb}" + end
        except:
            return f"{self.mode}|{self.verb}" + end 

    def length(self):
        length_data = len(self.data) if self.data != None else 0 
        return 7 + len(self.resource) + len(self.params) + length_data

    def getBytes(self):
        stream = bitstring.BitStream()
        stream.append(f"int:8={self.mode}")
        stream.append(f"int:8={self.verb}")
        stream.append(f"int:8={self.finished}")
        stream.append(f"int:8={self.format_data}")
        stream.append(f"int:8={len(self.resource)}")
        stream.append(bytearray(map(ord, self.resource)))
        stream.append(f"int:8={len(self.params)}")
        stream.append(bytearray(map(ord, self.params)))
        stream.append(f"int:16={len(self.data)}")
        stream.append(self.data)
        return stream.bytes
    
    def fromBytes(data: bytearray):
        stream = bitstring.BitStream(data)
        mode = stream[:8].uint; del stream[:8]
        verb = stream[:8].uint; del stream[:8]
        finished = stream[:8].uint; del stream[:8]
        format_data = stream[:8].uint; del stream[:8]

        length = stream[:8].uint; del stream[:8]
        print("DEBUG resource: ", length)
        resource = stream[:8*length].bytes.decode("UTF8")
        del stream[:8*length]

        length = stream[:8].uint; del stream[:8]
        print("DEBUG params: ", length)
        params = stream[:8*length].bytes.decode("UTF8")
        del stream[:8*length]

        length = stream[:16].uint; del stream[:16]
        print("DEBUG data: ", length)
        return Packet(mode, verb, finished, format_data, resource, params, stream.bytes)


if __name__ == "__main__":
    p = Packet(1, 2, "alunos")
    p.params = "periodo=1&cod_curso=1"
    p.data = bytearray(map(ord, "Ola mundo!"))

    data = p.getBytes()
    print(p)
    print(data)

    k = Packet.fromBytes(data)
    data = k.getBytes()
    print(k)
    print(data)
    