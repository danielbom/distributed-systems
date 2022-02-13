
# Copie e cole no terminal

protoc=$(pwd)/protoc/bin/protoc

PY_DIR=$(pwd)/pythonCode
JAVA_DIR=$(pwd)/javaCode

FILENAME=models.proto

$protoc -I=$(pwd) --python_out=$PY_DIR $(pwd)/$FILENAME
$protoc -I=$(pwd) --java_out=$JAVA_DIR $(pwd)/$FILENAME

