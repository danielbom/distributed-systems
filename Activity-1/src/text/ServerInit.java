package text;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Classe de inicialização do servidor.
 * 
 * @author daniel
 */
public class ServerInit {

	public static void main(String[] args) {
		int port = 8888;
		ServerSocket server;

		try {
			server = new ServerSocket(port);
			new Server(server, "/home/daniel/shared/").run();
		} catch (IOException e) {
			System.out.println("[ERROR] Class ServerInit");
			System.out.println("IOException " + e.getMessage());
		}
	}
}
