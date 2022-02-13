'''

    Script basíco de teste sobre os modelos criados pelo "Protocol Buffers".

    @autors daniel e mara

'''

import models_pb2

def protocol_buffer_bytearray(list_objs: list, resource=None):
    if resource == None:
        return bytearray(0)
    stream = models_pb2.streams()
    header = list_objs.pop(0)
    stream.header.extend(header)
    for list_attrs in list_objs:
        model = getattr(stream, resource).add()
        for i, head in enumerate(header):
            setattr(model, head, list_attrs[i])
    return stream.SerializeToString()

def bytearray_protocol_buffer(obj: bytearray, resource=None):
    stream =  models_pb2.streams()
    stream.ParseFromString(obj)
    return stream

cursos = [
    ["codigo", "nome"],
    [1, "bcc"],
    [2, "eletronica"]
]

data = protocol_buffer_bytearray(cursos, "cursos")

# print(len(data))

result = bytearray_protocol_buffer(data)
print(result)

