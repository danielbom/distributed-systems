package text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe que encapsula operações de transmissão (IN/OUT) de mensagens
 * do tipo String.
 * 
 * @author daniel
 */
public class TextMessenger {

	private BufferedReader socketReader;
	private PrintWriter socketPrinter;
	
	public TextMessenger(Socket socket) throws IOException {
		// Referencia para mandar mensagens
		OutputStream outputStream = socket.getOutputStream();
		this.socketPrinter = new PrintWriter(outputStream, true);
		
		// Referencia para receber mensagens
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	    this.socketReader = new BufferedReader(inputStreamReader);
	}
	
	public String receiveMessage() throws IOException {
		return this.socketReader.readLine();
	}

	public void sendMessage(String message) {
		if (message.trim() != "") {
			this.socketPrinter.println(message);
			this.socketPrinter.flush();
		}
	}

}
