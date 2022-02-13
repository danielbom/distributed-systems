
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
