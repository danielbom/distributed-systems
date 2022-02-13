package text;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe que encapsula a caracteristica de um servidor.
 * 
 * Esta classe é responsável por esperar conexões e invocar 
 * uma thread para manter a comunicação.
 * 
 * @author daniel
 */
public class Server implements Runnable {

	ServerSocket serverSocket;
	String sharedpath;

	public Server(ServerSocket serverSocket, String sharedpath) {
		this.serverSocket = serverSocket;
		this.sharedpath = sharedpath;
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("LOG: Servidor aguardando conexão.");
				Socket client = this.serverSocket.accept();
				new Thread(new ServerWorker(client, this.sharedpath)).start();
			}
		} catch (IOException e) {
			System.out.println("[ERROR] Class Server.");
			System.out.println("LOG: IOException " + e.getMessage() + ".");
		}
	}
}
