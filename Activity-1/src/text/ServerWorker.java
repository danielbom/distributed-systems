package text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Classe responsável por manter a comunicação com um cliente e
 * resolver suas requisições.
 * 
 * @author daniel
 */
public class ServerWorker implements Runnable {
	
	int id;

	Socket socket;
	String sharedpath;

	TextMessenger messengerText;
	boolean running;
	
	public ServerWorker(Socket socket, String sharedpath) throws IOException {
		String host = socket.getInetAddress().getHostAddress();
		int port = socket.getPort();

		System.out.println("LOG: Cliente conectado. [" + host + ":" + port + "]");

		this.socket = socket;
		this.sharedpath = sharedpath;
		this.messengerText = new TextMessenger(socket);
	}
	
	@Override
	public void run() {
		this.running = true;
		String message = "";
		try {
			while (this.running) {
				message = (String) this.messengerText.receiveMessage();
				System.out.println("LOG: Recebido: " + message);
				if (message == null) break;
				this.resolve(message);
			}

			System.out.println("LOG: Cliente (" + id + ") desconectado");
		} catch (IOException e) {
			System.out.println("[ERROR] Class ServerWorker");
			System.out.println("IOException " + e.getMessage());
		} finally {
			try {
				socket.close();
			} catch (IOException e) { }
		}
	}
	
	private String getFiles() throws IOException {
		List<String> allpaths = new ArrayList<String>();
		try (Stream<Path> paths = Files.walk(Paths.get(this.sharedpath))) {
		    paths
		    	.filter(Files::isRegularFile)
		        .forEach(file -> allpaths.add(file.toString()));
		}
		return String.join(";", allpaths);
	}
	
	private String getDate() {
		/*
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		String formatedDate = String.format("%02d/%02d/%d",
				calendar.get(Calendar.DATE),
				calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.YEAR));
		return formatedDate;
		*/
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	private String getHour() {
		/*
		return new Date().toString().split(" ")[3];
		*/
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	private String downloadFile(String filepath) throws IOException {
		String message = "";
		if (filepath.equals("")) return "0";
		System.out.println("[LOG] FILENAME: \"" + filepath + "\"");

		try {
			File file = new File(filepath);
			InputStream fileStream = new FileInputStream(file);
			OutputStream outputStream = socket.getOutputStream();
			message = String.valueOf(file.length());
			this.messengerText.sendMessage(message);

			int bufferSize = 1;
			byte[] buffer = new byte[bufferSize];
			int length;
			for (int i = 0; (length = fileStream.read(buffer)) > 0; i++) {
				outputStream.write(buffer, 0, length);
				System.out.printf("LOG: (%d) Tamanho recebido [%d]\n", i, length);
				this.messengerText.receiveMessage();
			}
			
			fileStream.close();
		} catch (FileNotFoundException ex) {
			message = "0";
			this.messengerText.sendMessage(message);
			
			System.out.println("[ERROR] Class ServerWork");
			System.out.println("FileNotFoundException " + ex.getMessage());
        }
		return message;
	}
	
	private String getHelp() {
		return "- Comandos: ;" +
				"\tTIME: Retorna a hora atual no formato HH:MM:SS.;" +
				"\tDATE: Retorna a data atual no formato DD/MM/AAAA.;" +
				"\tFILES: Lista todos os arquivos compartilhados.;" +
				"\tDOWN [filename]: Faz o download do arquivo com o nome \"filename\".;" +
				"\tHELP: Lista todos os comandos.";
	}
	
	public void resolve(String data) throws IOException {
		data = data.trim();
		String response = "";
		switch (data) {
		case "TIME":
			response = this.getHour();
			this.messengerText.sendMessage(response);
			break;
		case "DATE":
			response = this.getDate();
			this.messengerText.sendMessage(response);
			break;
		case "FILES":
			response = this.getFiles();
			this.messengerText.sendMessage(response);
			break;
		case "HELP":
			response = this.getHelp();
			this.messengerText.sendMessage(response);
			break;
		case "EXIT":
			this.running = false;
			break;
		default:
			if (data.startsWith("DOWN")) {
				response = this.downloadFile(data.replaceFirst("DOWN", "").trim());
			} else {
				response = "WARNING: Entrada não encontrada.";
			}
			break;
		}
		System.out.println("[LOG] Envio:\n" + response);
	}
}
