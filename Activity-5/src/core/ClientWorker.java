package core;

import java.io.IOException;
import java.net.Socket;

import models.Curso;

/**
 * Classe utilizada para fazer um teste básico da comunicação
 * do cliente Java com o servidor Python.
 * 
 * [Deprecated]
 * 
 * @author daniel e mara
 */
public class ClientWorker implements Runnable {

	private ProtocolController controller;
	
	private Service service;
	private String host;
	private int port;
	

	public ClientWorker(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public ClientWorker(Socket socket) throws IOException {
		this.controller = new ProtocolController(socket);
	}
	
	public void manualTest() {
		while (true) {
			int v = 0;
			String resource = "";
			String params = "";
			String data = "";
			do {
				System.out.println("Operações: GET (1), POST (2), PUT (3), DELETE (4)");
				System.out.println("Informe a operação: ");
				v = Utils.getInt();
			} while (v <= 0 || v >= 4);
			do {
				System.out.println("Recursos: curso, disicipla, matricula, aluno");
				System.out.println("Informe o recurso: ");
				resource = Utils.getInput();
			} while (!resource.matches("curso|disciplina|matricula|aluno"));
			
			System.out.println("Informe os parametros: ");
			params = Utils.getAnyInput();
			
			System.out.println("Informe os dados: ");
			data = Utils.getAnyInput();
			
			try {
				this.controller.requestJSON(v, resource, params, data.getBytes());
			} catch (IOException e) {
				Utils.log("Request erro: " + e.getMessage());
			}
		}
		
	}
	
	@Override
	public void run() {
		service = new Service(host, port);
		ServiceResult<Curso> result = service.getAllCursos();
		
		System.out.println("Cabeçalho.");
		result.getHeader().forEach(head -> {
			System.out.println(head);
		});
		System.out.println();
		
		System.out.println("Valores");
		result.getValues().forEach(value -> {
			System.out.println(value);
		});
	}

}
