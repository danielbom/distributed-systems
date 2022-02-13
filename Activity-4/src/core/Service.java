package core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.protobuf.InvalidProtocolBufferException;

import models.AlunoView;
import models.Curso;
import models.Disciplina;
import protoc.Models.streams;

/**
 * Classe responsável em servir as operações necessários para
 * acesso aos dados do servidor e retornar um resultado claro
 * para ser usado no front-end.
 * 
 * @author daniel e mara
 */
public class Service {
	
	private ProtocolController controller;
	private String host;
	private int port;

	public Service(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	private void initOperation() throws UnknownHostException, IOException {
		Utils.log("Inicializando requisição.");
		controller = new ProtocolController(new Socket(host, port));
	}
	
	private void finishOperation() {
		Utils.log("Finalizando requisição");
		controller.close();
	}
	
	private Packet doOperation(String resource, String params, int format) {
		Packet packet = null;
		try {
			this.initOperation();
			packet = this.controller.get(resource, params, format);
		} catch (UnknownHostException e1) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			this.finishOperation();
		}
		return packet;
	}
	
	public ServiceResult<Curso> getAllCursos() {
		Packet response = doOperation("curso", "", 2);
		
		ServiceResult<Curso> result = new ServiceResult<Curso>();
		ArrayList<String> header = result.getHeader();
		ArrayList<Curso> cursos = result.getValues();
		try {
			streams streamCursos = streams.parseFrom(response.data());
			streamCursos.getHeaderList().forEach(head -> {
				header.add((String) head); 
			});
			
			streamCursos.getCursosList().forEach(curso -> {
				cursos.add(new Curso(curso.getCodigo(), curso.getNome()));
			});
		} catch (InvalidProtocolBufferException e) {
			Utils.log("Erro durante parse do Protobuffer");
		}
		return result;
	}
	
	public ServiceResult<Disciplina> getDisciplinaByCodCurso(int cod_curso) {
		Packet response = doOperation("disciplina", "query>cod_curso=" + cod_curso, 2);
		
		ServiceResult<Disciplina> result = new ServiceResult<Disciplina>();
		ArrayList<String> header = result.getHeader();
		ArrayList<Disciplina> disciplinas = result.getValues();
		
		try {
			streams streamDisciplinas = streams.parseFrom(response.data());
			streamDisciplinas.getHeaderList().forEach(head -> {
				header.add((String) head); 
			});
			
			streamDisciplinas.getDisciplinasList().forEach(disciplina -> {
				disciplinas.add(new Disciplina(
					disciplina.getCodigo(),
					disciplina.getNome(),
					disciplina.getProfessor(),
					disciplina.getCodCurso()
				));
			});
		} catch (InvalidProtocolBufferException e) {
			Utils.log("Erro durante parse do Protobuffer");
		}
		
		return result;
	}
	
	private JSONArray opGetRequest(String resource, String params, int format) {
		Packet response = doOperation(resource, params, format);
		JSONArray jsonValues = null;
		
		byte[] data = response.data();
		try {
			Utils.log(Utils.decode(data));
			jsonValues = new JSONArray(Utils.decode(data));
		} catch (JSONException | UnsupportedEncodingException e) {
			return null;
		}
		return jsonValues;
	}
	
	public ServiceResult<AlunoView> getAlunos(String cod_disciplina) {
		String resource = "matricula as m,aluno as a";
		String select = "select>a.ra,nome,periodo,cod_curso,cod_disciplina,semestre,nota,faltas,ano";
		String query = "query>m.ra=a.ra and cod_disciplina=\"" + cod_disciplina + "\"";
		String params = select + "&" + query;
		JSONArray jsonValues = opGetRequest(resource, params, 1);
		if (jsonValues == null) return null;

		ServiceResult<AlunoView> result = new ServiceResult<AlunoView>();
		ArrayList<String> header = result.getHeader();
		ArrayList<AlunoView> cursos = result.getValues();

		JSONArray jsonHeader = (JSONArray) jsonValues.remove(0);
		jsonHeader.toList().forEach(head -> header.add((String) head));
		if (jsonValues.isEmpty()) return null;

		jsonValues.toList().forEach(value -> {
			ArrayList<Object> array = (ArrayList<Object>) value;
			int ra = (int) array.get(0);
			String nome = (String) array.get(1);
			int periodo = (int) array.get(2);
			int cod_curso = (int) array.get(3);
			int semestre = (int) array.get(5);
			double nota = (double) array.get(6);
			int faltas = (int) array.get(7);
			int ano = (int) array.get(8);
			cursos.add(new AlunoView(ra, nome, periodo, cod_curso, cod_disciplina, semestre, (float) nota, faltas, ano));
		});
		
		return result;
	}
	
	public void postNota(int ra, String cod_disciplina, int ano, int semestre, float nota, int faltas) {
		String dataJSON = "[" + ra + ",\"" + cod_disciplina + "\"," + ano + ","
				+ semestre + "," + nota + "," + faltas + "]";
		
		try {
			this.initOperation();
			this.controller.post("matricula", dataJSON);
		} catch (UnknownHostException e1) {
		} catch (IOException e) {
		} finally {
			this.finishOperation();
		}
	}
	
	public void putNota(int ra, String cod_disciplina, int ano, int semestre, float nota, int faltas) {
		String query = "query>ra=" + ra + " and cod_disciplina=\"" +
				cod_disciplina + "\" and ano=" + ano + " and semestre=" + semestre;
		String values = "values>nota=" + nota + ",faltas=" + faltas;
		String params = query + "&" + values;
		
		try {
			this.initOperation();
			this.controller.put("matricula", params);
		} catch (UnknownHostException e1) {
		} catch (IOException e) {
		} finally {
			this.finishOperation();
		}
	}
	
	public void deleteNota(int ra, String cod_disciplina, int ano, int semestre) {
		Packet response = null;
		JSONArray jsonValues = null;
		String params = "query>ra=" + ra + " and cod_disciplina=\"" + cod_disciplina + "\""
				+ " and ano=" + ano + " and semestre=" + semestre;
		
		try {
			this.initOperation();
			response = this.controller.delete("matricula", params);
		} catch (UnknownHostException e1) {
		} catch (IOException e) {
		} finally {
			this.finishOperation();
		}
		
		byte[] data = response.data();
		try {
			jsonValues = new JSONArray(Utils.decode(data));
		} catch (JSONException | UnsupportedEncodingException e) {
		}
	}
	
}
