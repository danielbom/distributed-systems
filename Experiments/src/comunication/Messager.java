package comunication;

import java.io.IOException;
import java.net.Socket;

public class Messager {

	private Receiver receiver;
	private Sender sender;

	public Messager(Socket socket) throws IOException {
		this.receiver = new Receiver(socket);
		this.sender = new Sender(socket);
	}

	public void sendMessage(String message) throws IOException {
		if (message.trim() != "") {
			this.sender.send(message);
		}
	}

	public String receiveMessage() throws IOException {
		return this.receiver.receive();
	}

	public void close() throws IOException {
		this.receiver.close();
		this.sender.close();
	}

	public Receiver getReceiver() {
		return this.receiver;
	}

	public Sender getSender() {
		return this.sender;
	}
}
