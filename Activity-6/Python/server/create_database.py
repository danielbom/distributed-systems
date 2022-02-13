'''

    Script de criação do banco de dados.

    @autors daniel e mara

'''

from config import *
from core import Database
from fakedb import op


cursor = Database(PATHDB).connection.cursor()
op.create_tables(cursor)
op.insert_fake_data(cursor)
Database(PATHDB).connection.commit()