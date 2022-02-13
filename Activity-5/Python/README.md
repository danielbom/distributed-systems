# Servidor: Versão 3.0

## Instale as depedências do projeto

    - pip3 install -r ./config/requirements.txt

    ou

    - cd config && pip3 install -r requirements.txt

## Crie o banco de dados

    - python3 create_database.py

## Inicialize o servidor

    - python3 server.py


## Informações adicionais

### Padrão do pacote:
    - mode        (int): Request (1), Response (2)
    - verb        (int): GET (1), POST (2), PUT (3), DELETE (4)
    - format_data (int): AUTO (0), JSON (1), PROTOCOL BUFFER (2)
    - resource    (str) [max len 255]
    - params      (str) [max len 255]
    - data        (bytearray)

### Verbos HTTP:
    - GET: Consultar dados
    - PORT: Adicionar dados
    - PUT: Atualizar dados
    - DELETE: Deletar dados

