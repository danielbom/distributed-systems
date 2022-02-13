# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: models.proto

import sys
_b=sys.version_info[0]<3 and (lambda x:x) or (lambda x:x.encode('latin1'))
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='models.proto',
  package='',
  syntax='proto3',
  serialized_options=None,
  serialized_pb=_b('\n\x0cmodels.proto\"%\n\x05\x63urso\x12\x0e\n\x06\x63odigo\x18\x01 \x01(\x05\x12\x0c\n\x04nome\x18\x02 \x01(\t\"P\n\ndisciplina\x12\x0e\n\x06\x63odigo\x18\x01 \x01(\t\x12\x0c\n\x04nome\x18\x02 \x01(\t\x12\x11\n\tprofessor\x18\x03 \x01(\t\x12\x11\n\tcod_curso\x18\x04 \x01(\x05\"E\n\x05\x61luno\x12\n\n\x02ra\x18\x01 \x01(\x05\x12\x0c\n\x04nome\x18\x02 \x01(\t\x12\x0f\n\x07periodo\x18\x03 \x01(\x05\x12\x11\n\tcod_curso\x18\x04 \x01(\x05\"l\n\tmatricula\x12\n\n\x02ra\x18\x01 \x01(\x05\x12\x16\n\x0e\x63od_disciplina\x18\x02 \x01(\t\x12\x0b\n\x03\x61no\x18\x03 \x01(\x05\x12\x10\n\x08semestre\x18\x04 \x01(\x05\x12\x0c\n\x04nota\x18\x05 \x01(\x02\x12\x0e\n\x06\x66\x61ltas\x18\x06 \x01(\x05\"\xa0\x01\n\x07streams\x12\x13\n\x0b\x64\x65scription\x18\x01 \x01(\t\x12\x0e\n\x06header\x18\x02 \x03(\t\x12\x16\n\x06\x61lunos\x18\x03 \x03(\x0b\x32\x06.aluno\x12\x16\n\x06\x63ursos\x18\x04 \x03(\x0b\x32\x06.curso\x12\x1e\n\nmatriculas\x18\x05 \x03(\x0b\x32\n.matricula\x12 \n\x0b\x64isciplinas\x18\x06 \x03(\x0b\x32\x0b.disciplina\"?\n\x0cinputService\x12\x10\n\x08resource\x18\x01 \x01(\t\x12\x0e\n\x06select\x18\x02 \x01(\t\x12\r\n\x05query\x18\x03 \x01(\t2+\n\x07Service\x12 \n\x03Get\x12\r.inputService\x1a\x08.streams\"\x00\x62\x06proto3')
)




_CURSO = _descriptor.Descriptor(
  name='curso',
  full_name='curso',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='codigo', full_name='curso.codigo', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='curso.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=16,
  serialized_end=53,
)


_DISCIPLINA = _descriptor.Descriptor(
  name='disciplina',
  full_name='disciplina',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='codigo', full_name='disciplina.codigo', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='disciplina.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='professor', full_name='disciplina.professor', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_curso', full_name='disciplina.cod_curso', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=55,
  serialized_end=135,
)


_ALUNO = _descriptor.Descriptor(
  name='aluno',
  full_name='aluno',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='ra', full_name='aluno.ra', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nome', full_name='aluno.nome', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='periodo', full_name='aluno.periodo', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_curso', full_name='aluno.cod_curso', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=137,
  serialized_end=206,
)


_MATRICULA = _descriptor.Descriptor(
  name='matricula',
  full_name='matricula',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='ra', full_name='matricula.ra', index=0,
      number=1, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cod_disciplina', full_name='matricula.cod_disciplina', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='ano', full_name='matricula.ano', index=2,
      number=3, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='semestre', full_name='matricula.semestre', index=3,
      number=4, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='nota', full_name='matricula.nota', index=4,
      number=5, type=2, cpp_type=6, label=1,
      has_default_value=False, default_value=float(0),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='faltas', full_name='matricula.faltas', index=5,
      number=6, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=208,
  serialized_end=316,
)


_STREAMS = _descriptor.Descriptor(
  name='streams',
  full_name='streams',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='description', full_name='streams.description', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='header', full_name='streams.header', index=1,
      number=2, type=9, cpp_type=9, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='alunos', full_name='streams.alunos', index=2,
      number=3, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='cursos', full_name='streams.cursos', index=3,
      number=4, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='matriculas', full_name='streams.matriculas', index=4,
      number=5, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='disciplinas', full_name='streams.disciplinas', index=5,
      number=6, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=319,
  serialized_end=479,
)


_INPUTSERVICE = _descriptor.Descriptor(
  name='inputService',
  full_name='inputService',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='resource', full_name='inputService.resource', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='select', full_name='inputService.select', index=1,
      number=2, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='query', full_name='inputService.query', index=2,
      number=3, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=_b("").decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=481,
  serialized_end=544,
)

_STREAMS.fields_by_name['alunos'].message_type = _ALUNO
_STREAMS.fields_by_name['cursos'].message_type = _CURSO
_STREAMS.fields_by_name['matriculas'].message_type = _MATRICULA
_STREAMS.fields_by_name['disciplinas'].message_type = _DISCIPLINA
DESCRIPTOR.message_types_by_name['curso'] = _CURSO
DESCRIPTOR.message_types_by_name['disciplina'] = _DISCIPLINA
DESCRIPTOR.message_types_by_name['aluno'] = _ALUNO
DESCRIPTOR.message_types_by_name['matricula'] = _MATRICULA
DESCRIPTOR.message_types_by_name['streams'] = _STREAMS
DESCRIPTOR.message_types_by_name['inputService'] = _INPUTSERVICE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

curso = _reflection.GeneratedProtocolMessageType('curso', (_message.Message,), {
  'DESCRIPTOR' : _CURSO,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:curso)
  })
_sym_db.RegisterMessage(curso)

disciplina = _reflection.GeneratedProtocolMessageType('disciplina', (_message.Message,), {
  'DESCRIPTOR' : _DISCIPLINA,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:disciplina)
  })
_sym_db.RegisterMessage(disciplina)

aluno = _reflection.GeneratedProtocolMessageType('aluno', (_message.Message,), {
  'DESCRIPTOR' : _ALUNO,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:aluno)
  })
_sym_db.RegisterMessage(aluno)

matricula = _reflection.GeneratedProtocolMessageType('matricula', (_message.Message,), {
  'DESCRIPTOR' : _MATRICULA,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:matricula)
  })
_sym_db.RegisterMessage(matricula)

streams = _reflection.GeneratedProtocolMessageType('streams', (_message.Message,), {
  'DESCRIPTOR' : _STREAMS,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:streams)
  })
_sym_db.RegisterMessage(streams)

inputService = _reflection.GeneratedProtocolMessageType('inputService', (_message.Message,), {
  'DESCRIPTOR' : _INPUTSERVICE,
  '__module__' : 'models_pb2'
  # @@protoc_insertion_point(class_scope:inputService)
  })
_sym_db.RegisterMessage(inputService)



_SERVICE = _descriptor.ServiceDescriptor(
  name='Service',
  full_name='Service',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  serialized_start=546,
  serialized_end=589,
  methods=[
  _descriptor.MethodDescriptor(
    name='Get',
    full_name='Service.Get',
    index=0,
    containing_service=None,
    input_type=_INPUTSERVICE,
    output_type=_STREAMS,
    serialized_options=None,
  ),
])
_sym_db.RegisterServiceDescriptor(_SERVICE)

DESCRIPTOR.services_by_name['Service'] = _SERVICE

# @@protoc_insertion_point(module_scope)