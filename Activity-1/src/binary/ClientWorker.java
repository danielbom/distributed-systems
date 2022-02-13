package binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Classe responsável por encapsular as operações de comunicação
 * com o servidor pelo cliente.
 * 
 * @author daniel
 */
public class ClientWorker implements Runnable {

	// mode: REQUEST (1) | RESPONSE (2)
	// operation: ADDFILE (1) | DELETE (2) | GETFILELIST (3) | GETFILE (4)
	private Wrapper wrapper = new Wrapper();
	private PacketResponse response;
	private PacketRequest request;
	private PacketData data = new PacketData(1024);

	private Socket socket;
	private String sharedpath;

	private boolean running = true;
	private String lastCommand = "";

	private OutputStream outputStream;
	private InputStream inputStream;

	public ClientWorker(Socket socket, String sharedpath) throws IOException {
		this.sharedpath = sharedpath;
		this.socket = socket;
		
		this.request = this.wrapper.packetRequest();
		this.response = this.wrapper.packetResponse();

		if (socket != null) {
			this.outputStream = socket.getOutputStream();
			this.inputStream = socket.getInputStream();
		}
	}
	
	public void help() {
		System.out.println("- Comandos:");
		System.out.println("\t(add) ADDFILE [filename]: Transfere o arquivo para o servidor.");
		System.out.println("\t(del) DELETE [filename]: Exclui o arquivo do servidor.");
		System.out.println("\t (ls) GETFILELIST: Mostra os arquivos existentes no servidor.");
		System.out.println("\t(get) GETFILE [filename]: Obtem o arquivo do servidor.");
		System.out.println("\t  (?) HELP : Lista todos os comandos");
	}
	
	@Override
	public void run() {
        Scanner reader = new Scanner(System.in);
        String message = "";
		while (this.running) {
			System.out.print("|> ");
			message = reader.nextLine();
			if (message == null) break;
			message = message.trim();
			if (message.equals("")) continue;
			if (message.equalsIgnoreCase("EXIT")) break;
			if (message.equals("!"))
				message = lastCommand;
			if (message.matches("\\?|HELP")) {
				help();
				continue;
			}
			lastCommand = message;
			Utils.log("Enviado: " + message);
			this.createPacket(message);
			this.resolve();
		}
		reader.close();
		try {
			socket.close();
		} catch (IOException e) {
			// Ignore ...
		}
	}

	public String alias(String command) {
		switch(command) {
		case "ls":
			return "GETFILELIST";
		case "rm":
			return "DELETE";
		case "add":
			return "ADDFILE";
		case "get":
			return "GETFILE";
		}
		return command;
	}
	
	public void createPacket(String data) {
		String[] parse = data.split("[ ]+");
		String filename = data.replace(parse[0], "").trim();
		parse[0] = alias(parse[0]);
		
		Utils.log(Arrays.toString(parse));
		switch (BinaryProtocol.getOperation(parse[0])) {
		case 1: // ADDFILE
			this.wrapper.createPacketAdd(filename);
			break;
		case 2: // DELETE
			this.wrapper.createPacketDel(filename);
			break;
		case 3: // GETFILELIST
			this.wrapper.createPacketList();
			break;
		case 4: // GETFILE
			this.wrapper.createPacketGet(filename);
			break;
		default:
			Utils.log("Esta operação não existe.");			
		}
		Utils.log("Request: " + request);
		Utils.log("Response: " + response);
	}
	
	private void resolve() {
		switch (request.operation()) {
		case 1: // ADDFILE
			this.upload();
			break;
		case 2: // DELETE
			this.waitResponse(buffer -> {
				response.fromBytes(buffer);
				System.out.println(response.result() == 1 ? "\tOK" : "\tFAIL");
				Utils.log("Resposta: " + response);
				return null;
			});
			break;
		case 3: // GETFILELIST
			this.waitResponse(buffer -> {
				request.fromBytes(buffer);
				Utils.log("Resposta: " + request);
				request.filenames().forEach(name -> {
					System.out.println("\t" + name);
				});
				return null;
			});
			break;
		case 4: // GETFILE
			this.download();
			break;
		}
		request.flush();
		response.flush();
	}
	
	private void upload() {
		try {
			String filename = sharedpath + request.getFirst();
			Utils.log("Upload " + filename);
			File file = new File(filename);
			
			// Verificando se o arquivo existe
			if (!file.exists()) {
				System.out.println(BinaryProtocol.getError("404"));
				data.mode(2).operation(4).finished(0).filename(filename);
				this.send("".getBytes(), 0);
				return;
			}
			
			// Enviando mensagem de operação
			this.sendMessage();
			
			// Construindo o pacote de envio de dados
			InputStream fileStream = new FileInputStream(file);
			data.mode(2).operation(4).finished(1).filename(filename);
			byte[] dataBuffer = data.data();
			
			int length;
			byte[] inputBuffer = new byte[1024];
			// Enviando o arquivo e recebendo a confirmação
			for (int i = 0; (length = fileStream.read(dataBuffer, 0, dataBuffer.length)) > 0; i++) {
				Utils.log("(" + i + ") Numero de bytes do arquivo recebido [" + length + "]");
				this.send(dataBuffer, length);
				this.receive(inputBuffer);
				data.next();
			}

			// Finalizando a transmissão
			data.finished(0);
			this.send("".getBytes(), 0);
			fileStream.close();
			
			System.out.println("\tOK");
		} catch (IOException e) {
			System.out.println("\tFAIL");
			Utils.log("Erro durante o upload. " + e.getMessage());
		}
	}

	private void download() {
		OutputStream fileStream = null;
		byte[] buffer = new byte[1300];
		try {
			this.sendMessage();
			
			String filename = sharedpath + request.getFirst();
			Utils.log("Download " + filename);
			File file = new File(filename);
			
			// Início da transmissão
			Utils.log("Recebendo 0");
			int length = this.inputStream.read(buffer);
			Utils.logBoolean(length == -1, BinaryProtocol
					.getError("receive"));

			// Fazendo o parse da resposta
			data.fromBytes(buffer);
			Utils.logBoolean(data.finished() == 0, BinaryProtocol
					.getError("404") + " (" + filename + ")");
			Utils.log("Recebido " + data);

			fileStream = new FileOutputStream(file);
			
			for (int i = 1; data.finished() != 0; i++) {
				// Escrevendo no arquivo
				fileStream.write(data.data(), 0, data.dataLength());

				// Enviando uma resposta de confirmação
				this.wrapper.createResponseGet(data.finished());
				Utils.log("Resposta " + response);
				outputStream.write(response.getBytes());
				
				// Recebendo no arquivo
				Utils.log("Recebendo " + i);
				length = this.inputStream.read(buffer);
				Utils.logBoolean(length == -1, BinaryProtocol
						.getError("receive"));
				
				// Fazendo o parse da resposta
				data.fromBytes(buffer);
				Utils.log("Recebido " + data);
			}
			
			System.out.println("\tOK");
		} catch (IOException e) {
			System.out.println("\tFAIL");
		} finally {
			try {
				if (fileStream != null) fileStream.close();
			} catch (IOException e1) { 
				// Ignore ...
			}
		}
	}
	
	private void send(byte[] dataBuffer, int length) throws IOException {
		Utils.log("Enviando");
		data.data(dataBuffer, length);
		outputStream.write(data.getBytes(), 0, data.length());
		Utils.log("Enviado " + data);
	}
	
	private void waitResponse(Function<byte[], ?> function) {
		try {
			this.sendMessage();
			
			// Esperando uma resposta
			byte[] buffer = new byte[1300];
			int length = this.inputStream.read(buffer);
			Utils.log("Tamanho da resposta: " + length);
			if (length == -1) {
				Utils.log("Erro ao receber a resposta!");
				return;
			}
			function.apply(buffer);
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}
	}
	
	private void sendMessage() throws IOException {
		this.outputStream.write(request.getBytes());
	}
	
	private void receive(byte[] inputBuffer) throws IOException {
		Utils.log("Recebendo");
		inputStream.read(inputBuffer);
		response.fromBytes(inputBuffer);
		Utils.log("Recebido " + response);
	}
	
}
