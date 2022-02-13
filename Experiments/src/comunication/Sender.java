package comunication;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {

	private PrintWriter socketPrinter;
	
	public Sender(Socket socket) throws IOException {
		OutputStream outputStream = socket.getOutputStream();
		this.socketPrinter = new PrintWriter(outputStream, true);
	}

	public void send(String message) {
		this.socketPrinter.println(message);
		this.socketPrinter.flush();
	}
	
	public void close() {
		this.socketPrinter.close();
	}
}
