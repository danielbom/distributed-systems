package text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import binary.Utils;

/**
 * Classe responsável por encapsular as operações de comunicação
 * com o servidor pelo cliente.
 * 
 * @author daniel
 */
public class ClientWorker implements Runnable {

	Socket socket;
	boolean running;
	String sharedpath;
	TextMessenger messengerText;
	
	public ClientWorker(Socket client, String sharedpath) throws IOException {
		String host = client.getInetAddress().getHostAddress();
		int port = client.getPort();
		Utils.log("Cliente conectado. [" + host + ":" + port + "]");

		this.socket = client;
		this.sharedpath = sharedpath;
		this.messengerText = new TextMessenger(client);
	}
	
	@Override
	public void run() {
		this.running = true;
        Scanner reader = new Scanner(System.in);
        String message = "";
		try {
			while (this.running) {
				System.out.print("|> ");
				message = reader.nextLine();
				Utils.log("Enviado: " + message);
				if (message == null) break;
				this.resolve(message);
			}
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (IOException e) { }
			reader.close();
		}
	}
	
	private String download(String filepath) throws IOException {
		String response = messengerText.receiveMessage();
		if (response.equals("0"))
			return response;
		long length = Long.parseLong(response);

		Utils.log("FILENAME: \"" + filepath + "\"");
		String[] parse = filepath.split("/");
		File file = new File(this.sharedpath + "/" + parse[parse.length - 1]);
		InputStream inputStream = this.socket.getInputStream();
		OutputStream fileStream = new FileOutputStream(file);
		
		int bufferSize = 1;
		byte[] buffer = new byte[bufferSize];
		for (int i = 0; length > 0; i++) {
			int size = inputStream.read(buffer);
			length -= size;
			fileStream.write(buffer, 0, size);
			Utils.log("(" + i + ") Tamanho recebido [" + size + "/" + length + "]\n");
			this.messengerText.sendMessage("OK");
		}
		
		fileStream.close();
		return response;
	}

	public void resolve(String data) throws IOException {
		this.messengerText.sendMessage(data);
		data = data.trim();
		String response = "";
		switch(data) {
		case "TIME":
		case "DATE":
		case "HELP":
		case "FILES":
			response = messengerText.receiveMessage().replace(';', '\n');
			break;
		case "EXIT":
			this.running = false;
			break;
		default:
			if (data.startsWith("DOWN")) {
				response = this.download(data.replaceFirst("DOWN", "").trim());
			} else {
				response = "WARNING: Entrada não encontrada.";
			}
			break;
		}
		Utils.log("Resposta:\n" + response);
	}
}
