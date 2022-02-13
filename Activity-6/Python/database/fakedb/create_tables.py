'''

    Script inicial para a criação do banco de dados falso.

    @autors daniel e mara

'''
import csv
import sqlite3
from sqlite3 import Error
from name_fakes import name_fakes
from random import randint, choice, random

sqlite3.enable_callback_tracebacks(True)

conn = sqlite3.connect(":memory:")

c = conn.cursor()

c.execute('''
CREATE TABLE IF NOT EXISTS curso (
    codigo integer,
    nome text,
    PRIMARY KEY (codigo)
)''')
c.execute('''
CREATE TABLE IF NOT EXISTS disciplina (
    codigo text,
    nome text,
    professor text,
    cod_curso integer,
    PRIMARY KEY (codigo),
    FOREIGN KEY (cod_curso)
        REFERENCES Curso (codigo)
            ON DELETE CASCADE
)''')
c.execute('''
CREATE TABLE IF NOT EXISTS aluno (
    ra integer,
    nome text,
    periodo integer,
    cod_curso integer,
    PRIMARY KEY (ra),
    FOREIGN KEY (cod_curso)
        REFERENCES Curso (codigo)
            ON DELETE CASCADE
)''')
c.execute('''
CREATE TABLE IF NOT EXISTS matricula (
    ra integer,
    cod_disciplina text,
    ano integer,
    semestre integer,
    nota real,
    faltas integer,
    PRIMARY KEY (ra, cod_disciplina, ano, semestre),
    FOREIGN KEY (ra)
        REFERENCES Aluno (ra)
            ON DELETE CASCADE,
    FOREIGN KEY (cod_disciplina)
        REFERENCES Disciplina (codigo)
            ON DELETE CASCADE
)''')

cod_disciplinas = []
cursos_ids = {}
cursos = []
disciplinas = []
with open("cursos.csv", "r") as file:
    csv_file = csv.reader(file, delimiter=";")
    id_curso = 1
    for i, row in enumerate(csv_file):
        curso, _periodo, disciplina = row

        if curso not in cursos_ids:
            cursos.append((id_curso, curso))
            id_curso += 1
            cursos_ids[curso] = id_curso

        cod_disciplina, nome_disciplina = disciplina.split(" - ", 1)
        disciplinas.append(
            (cod_disciplina, nome_disciplina, f"prof{i}", cursos_ids[curso])
        )
        cod_disciplinas.append(cod_disciplina)
        

c.executemany("INSERT INTO Curso VALUES (?, ?)", cursos)

c.executemany("INSERT OR IGNORE INTO Disciplina VALUES (?, ?, ?, ?)", disciplinas)

c.executemany("INSERT INTO Aluno VALUES (?, ?, ?, ?)",
    [(
        name,
        randint(1, 8), # periodo
        randint(1, 7)  # cod_curso
    ) for name in name_fakes * 10]
)

c.executemany("INSERT OR IGNORE INTO Matricula VALUES (?, ?, ?, ?, ?, ?)",
    [(
        randint(1, len(name_fakes)), # ra
        choice(cod_disciplinas),     # cod_disciplina
        randint(0, 2) + 2017,        # ano
        randint(1, 8),               # semestre
        randint(0, 100) / 10,        # nota
        randint(0, 20)               # faltas
    ) for i in range(100)]
)

conn.commit()
conn.close()
