from core.utils import *
from models_pb2_grpc import ServiceServicer

class Service(ServiceServicer):
    def Get(self, request, context):
        table = request.resource
        select = request.select
        query = request.query
        query = f"SELECT {select} FROM {table} WHERE {query}"
        data = [select.split(",")] + Database().execute(query).fetchall()
        return parse_protocol_buffer(data, table + "s")
