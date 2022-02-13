import sqlite3, json
import protoc.models_pb2 as models_pb2

# link: https://stackoverflow.com/questions/6760685/creating-a-singleton-in-python
class Singleton(type):
    _instances = {}
    def __call__(cls, *args, **kwargs):
        if cls not in cls._instances:
            cls._instances[cls] = super(Singleton, cls).__call__(*args, **kwargs)
        return cls._instances[cls]

class Database(object, metaclass=Singleton):
    def __init__(self, conn):
        sqlite3.enable_callback_tracebacks(True)
        self.connection = sqlite3.connect(conn)
        self.cursor = self.connection.cursor()
    
    def execute(self, query):
        try:
            print("LOG: Query:", query)
            self.cursor.execute(query)
        except:
            raise Exception(f"Erro ao executar '{query}'")
        return self.cursor

def parse_params(params):
    try:
        if params == '':
            return {}
        attrs = {}
        for attr in params.split("&"):
            key, value = attr.split(">")
            attrs[key] = value
        return attrs
    except:
        raise Exception("Erro durante o parse dos parâmetros")


def json_bytearray(obj: list):
    return bytearray(map(ord, json.dumps(obj)))

def bytearray_json(bytearrayJson: bytearray):
    return json.loads(bytearrayJson.decode("UTF8"))

def parse_protocol_buffer(list_objs: list, resource=None):
    stream = models_pb2.streams()
    header = list_objs.pop(0)
    stream.header.extend(header)
    for list_attrs in list_objs:
        model = getattr(stream, resource).add()
        for i, head in enumerate(header):
            setattr(model, head, list_attrs[i])
    return stream

def protocol_buffer_bytearray(list_objs: list, resource=None):
    if resource == None:
        return bytearray(0)
    return parse_protocol_buffer(list_objs, resource).SerializeToString()

def bytearray_protocol_buffer(obj: bytearray, resource=None):
    stream =  models_pb2.streams()
    stream.ParseFromString(obj)
    return stream