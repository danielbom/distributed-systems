package text;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe de inicialização do cliente.
 * 
 * @author daniel
 */
public class ClientInit {
	public static void main(String[] args) throws IOException {
		String sharedDirectory = "/home/daniel/shared-client/";
		String host = "127.0.0.1";
		int port = 8888;

		try {
			Socket client = new Socket(host, port);
			new ClientWorker(client, sharedDirectory).run();
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
	}
}
