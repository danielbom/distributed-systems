
# Copie e cole no terminal

dir=$(pwd)
protoc=$dir/protoc/bin/protoc

PY_DIR=$dir/pythonCode
JAVA_DIR=$dir/javaCode

FILENAME=models.proto

# Compilando modelos simples
$protoc -I=$dir --python_out=$PY_DIR $dir/$FILENAME
$protoc -I=$dir --java_out=$JAVA_DIR $dir/$FILENAME

# gRPC python
python3 -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. models.proto
# OBS: Precisa instalar as dependências necessárias para conseguir executar

# gRPC java
# Clone o repositório abaixo.
# https://github.com/grpc/grpc-java

# Siga as instruções do ./grpc-java/compile/README.md e dentro do diretório
# ./grpc-java/compile/ execute o código abaixo.
protoc --plugin=protoc-gen-grpc-java=build/exe/java_plugin/protoc-gen-grpc-java \
  --grpc-java_out=. --proto_path=$(pwd) models.proto
# OBS: É necessário ter o protobuf instalado no computador.
