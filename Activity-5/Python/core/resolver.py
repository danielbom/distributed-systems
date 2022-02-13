from core.utils import *
from core.packet import Packet
from core.schemas import *

class Resolver(object):
    def __init__(self):
        self.resolver = {
            1: self.get, 2: self.post, 3: self.put, 4: self.delete
        }

    def get(self, packet: Packet):
        print("LOG: Resolvendo requisição GET")

        p = parse_params(packet.params)
        print("LOG: Parametros " + str(p))

        try:
            table = packet.resource
            header = schemas[table]["header"]
            select = p.get('select', ",".join(header))
            query = p.get('query', 'true')
        except:
            tables = packet.resource
            select = p.get('select', '*')
            query = p.get('query', 'true')
        
        query = f"SELECT {select} FROM {table} WHERE {query}"
        data = [select.split(",")] + Database().execute(query).fetchall()
        if packet.format_data == 1:
            return json_bytearray(data)
        elif packet.format_data == 2:
            return protocol_buffer_bytearray(data, table + "s")
        else:
            raise Exception("Representação externa de dados inválida.")

    def post(self, packet: Packet):
        print("LOG: Resolvendo requisição de POST")

        p = parse_params(packet.params)
        print("LOG: Parametros " + str(p))

        table = packet.resource
        print("LOG: Tabela:", table)
        header = schemas[table]["header"]
        select = p.get('select', ",".join(header))
        print("LOG: Cabeçalhos:", select)
        
        if packet.format_data == 1:
            try:
                print("LOG: ", packet.data.decode("UTF8"))
                values = bytearray_json(packet.data)
                values = map(lambda x: f'"{x}"' if isinstance(x, str) else x, values)
                values = ",".join(map(str, values))
                print("LOG: Data:", values)
            except:
                raise Exception("Erro durante o parse do JSON.")
        elif packet.format_data == 2:
            values = bytearray_protocol_buffer(packet.data)
        else:
            raise Exception("Formato de dados inexistênte.")


        query = f"INSERT INTO {table}({select}) VALUES({values})"
        Database().execute(query)
        return json_bytearray("OK")
        
    def put(self, packet: Packet):
        print("LOG: Resolvendo requisição de PUT")

        p = parse_params(packet.params)
        print("LOG: Parametros " + str(p))

        table = packet.resource
        print("LOG: Tabela:", table)

        values = p.get('values', "!")
        print("LOG: Valores:", values)

        query = p.get('query', 'false')
        print("LOG: Cabeçalhos:", query)

        query = f"UPDATE {table} SET {values} WHERE {query}"
        Database().execute(query)
        return json_bytearray("OK")

    def delete(self, packet: Packet):
        print("LOG: Resolvendo requisição de DELETE")

        p = parse_params(packet.params)
        print("LOG: Parametros " + str(p))

        table = packet.resource
        print("LOG: Tabela:", table)

        query = p.get('query', 'false')
        print("LOG: Cabeçalhos:", query)

        query = f"DELETE FROM {table} WHERE {query}"
        Database().execute(query)
        return json_bytearray("OK")

    def resolve(self, packet: Packet):
        verb = self.resolver.get(packet.verb)
        return verb(packet) if verb else None
    