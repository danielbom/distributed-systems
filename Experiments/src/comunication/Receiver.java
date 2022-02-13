package comunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class Receiver {

	private BufferedReader socketReader;
	private String message;

	public Receiver(Socket socket) throws IOException {
		InputStream inputStream = socket.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		this.socketReader = new BufferedReader(inputStreamReader);
	}

	public String receive() throws IOException {
		this.message = this.socketReader.readLine();
	    return this.message;
	}

	public String getMessage() {
		return this.message;
	}
	
	public void close() throws IOException {
		this.socketReader.close();
	}
}
