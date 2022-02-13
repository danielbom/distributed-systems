import models_pb2

aluno = models_pb2.aluno()
aluno.ra = 10
aluno.nome = "Daniel"
aluno.periodo = 5
aluno.cod_curso = 1

print("--- aluno ---")
print(aluno.SerializeToString())
print()

stream = models_pb2.streams()

stream.description = "Stream test"
aluno = stream.alunos.add()
aluno.ra = 10
aluno.nome = "Daniel"
aluno.periodo = 6
aluno.cod_curso = 1

aluno = stream.alunos.add()
aluno.ra = 15
aluno.nome = "Luiz"
aluno.periodo = 5
aluno.cod_curso = 1
print("--- stream ---")
print(stream)