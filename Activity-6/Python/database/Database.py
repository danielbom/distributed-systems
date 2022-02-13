import sqlite3
import Pyro4

schemas = {
    "curso": { 
        "header": ["codigo","nome"]
    },
    "disciplina": {
        "header": ["codigo","nome","professor","cod_curso"]
    },
    "aluno": {
        "header": ["ra","nome","periodo","cod_curso"]
    },
    "matricula": {
        "header": ["ra","cod_disciplina","ano","semestre","nota","faltas"]
    },
}

PATHDB="./var/database"

@Pyro4.expose
class Database(object):

    def __init__(self):
        sqlite3.enable_callback_tracebacks(True)
        self.connection = sqlite3.connect(PATHDB)
        self.cursor = self.connection.cursor()

    def execute(self, sql):
        try:
            self.cursor.execute(sql)
            return "OK"
        except Exception as e:
            return e

    def fetch(self):
        try:
            return self.cursor.fetchall()
        except Exception as e:
            return e
