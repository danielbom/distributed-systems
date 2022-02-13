package binary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Classe responsável por manter a comunicação com um cliente e
 * resolver suas requisições.
 * 
 * @author daniel
 */
public class ServerWorker implements Runnable {

	// mode: REQUEST (1) | RESPONSE (2)
	// operation: ADDFILE (1) | DELETE (2) | GETFILELIST (3) | GETFILE (4)
	private final Wrapper wrapper = new Wrapper();
	private PacketResponse response;
	private PacketRequest request;
	private PacketData data = new PacketData(1024);

	private String sharedpath;

	private OutputStream outputStream;
	private InputStream inputStream;
	
	public ServerWorker(Socket socket, String sharedpath) throws IOException {
		this.sharedpath = sharedpath;
		
		this.response = this.wrapper.packetResponse();
		this.request = this.wrapper.packetRequest();
		
		if (socket != null) {
			String host = socket.getInetAddress().getHostAddress();
			int port = socket.getPort();

			Utils.log("Cliente conectado. [" + host + ":" + port + "]");

			this.outputStream = socket.getOutputStream();
			this.inputStream = socket.getInputStream();
		}
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		try {
			while (true) {
				System.out.println();
				Utils.log("Aguardando mensagens.");
				int length = inputStream.read(buffer);
				if (length == -1) break;
				this.rebuildPacket(buffer);
				this.resolve();
			}
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		Utils.log("Cliente desconectado.");
	}
	
	public void rebuildPacket(byte[] buffer) {
		request.fromBytes(buffer);
		if (request.length() <= 0) return;
		
		Utils.log("Recebido: " + request.toString());
		String name = this.sharedpath + request.getFirst();

		int result;
		switch(request.operation()) {
		case 1: // ADDFILE
			result = Utils.createFile(name) ? 1 : 2;
			this.wrapper.createResponseAdd(result);
			Utils.log("Enviado: " + response.toString());
			break;
		case 2: // DELETE
			result = Utils.deleteFile(name) ? 1 : 2;
			this.wrapper.createResponseDel(result);
			Utils.log("Enviado: " + response.toString());
			break;
		case 3: // GETFILELIST
			this.wrapper.createResponseList(this.sharedpath);
			Utils.log("Enviado: " + request.toString());
			break;
		default:
			Utils.log("Esta operação não existe!");
			break;
		}
	}
	
	private void resolve() throws IOException {
		System.out.println();
		Utils.log("Resolve");
		Utils.log("Resposta " + response);
		Utils.log("Requisição " + request);

		switch(request.operation()) {
		case 1:
			this.upload();
			break;
		case 2:
			this.outputStream.write(response.getBytes());
			break;
		case 3:
			this.outputStream.write(request.getBytes());
			break;
		case 4:
			this.download();
			break;
		default:
			Utils.log("Operação não encontrada (" + response.operation() + ")");
		}
		
		request.flush();
		response.flush();
	}

	private void upload() {
		OutputStream fileStream = null;
		byte[] buffer = new byte[1300];
		try {
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
	
	private void download() {
		try {
			String filename = sharedpath + request.getFirst();
			Utils.log("Download " + filename);
			File file = new File(filename);

			// Verificando se o arquivo existe
			if (!file.exists()) {
				System.out.println("Arquivo não existe!");
				data.mode(2).operation(4).finished(0).filename(filename);
				this.send("".getBytes(), 0);
				return;
			}

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
		} catch (IOException e) {
			Utils.log("Erro durante o download. " + e.getMessage());
		}
	}
	
	private void receive(byte[] inputBuffer) throws IOException {
		Utils.log("Recebendo");
		inputStream.read(inputBuffer);
		response.fromBytes(inputBuffer);
		Utils.log("Recebido " + response);
	}
	
	private void send(byte[] dataBuffer, int length) throws IOException {
		Utils.log("Enviando");
		data.data(dataBuffer, length);
		outputStream.write(data.getBytes(), 0, data.length());
		Utils.log("Enviado " + data);
	}
	
	public Wrapper wrapper() {
		return this.wrapper;
	}
}
