'''

    Script responsável por oferecer as funções de criação das tabelas e adição
    dos dados utilizados na aplicação.

    @autors daniel e mara

'''
import os, csv


def create_tables(cursor):
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Curso (
        codigo integer,
        nome text,
        PRIMARY KEY (codigo)
    )''')
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Disciplina (
        codigo text,
        nome text,
        professor text,
        cod_curso integer,
        PRIMARY KEY (codigo),
        FOREIGN KEY (cod_curso)
            REFERENCES Curso (codigo)
                ON DELETE CASCADE
    )''')
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Aluno (
        ra integer,
        nome text ,
        periodo integer,
        cod_curso integer,
        PRIMARY KEY (ra),
        FOREIGN KEY (cod_curso)
            REFERENCES Curso (codigo)
                ON DELETE CASCADE
    )''')
    cursor.execute('''
    CREATE TABLE IF NOT EXISTS Matricula (
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

def insert_fake_data(cursor):
    cod_disciplinas = []
    cursos_ids = {}
    cursos = []
    disciplinas = []
    with open("fakedb/cursos.csv", "r") as file:
        csv_file = csv.reader(file, delimiter=";")
        id_curso = 1
        for i, row in enumerate(csv_file):
            curso, _periodo, disciplina = row

            if curso not in cursos_ids:
                cursos.append((id_curso, curso))
                cursos_ids[curso] = id_curso
                id_curso += 1

            cod_disciplina, nome_disciplina = disciplina.split(" - ", 1)
            disciplinas.append(
                (cod_disciplina, nome_disciplina, f"prof{i}", cursos_ids[curso])
            )
            cod_disciplinas.append(cod_disciplina)

    with open("fakedb/alunos.csv", "r") as file:
        csv_file = list(csv.reader(file, delimiter=";"))
        alunos = map(tuple, csv_file[1:])

    with open("fakedb/matriculas.csv", "r") as file:
        csv_file = list(csv.reader(file, delimiter=";"))
        matriculas = map(tuple, csv_file[1:])

    cursor.executemany("INSERT INTO Curso VALUES (?, ?)", cursos)

    cursor.executemany("INSERT OR IGNORE INTO Disciplina VALUES (?, ?, ?, ?)", disciplinas)

    cursor.executemany("INSERT INTO Aluno VALUES (?, ?, ?, ?)", alunos)

    cursor.executemany("INSERT OR IGNORE INTO Matricula VALUES (?, ?, ?, ?, ?, ?)", matriculas)
