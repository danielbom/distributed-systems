package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Classe responsável por mandar mensagens escritas pelo usuário para
 * os outros usuários.
 * 
 * @author daniel
 */
public class Sender implements Runnable {

	private DatagramSocket socket;
	private InetAddress address;
	Packet packet = new Packet();
	private int serverPort;
	public int maxPort; // Compartilhado
	private int port;

	public Sender(DatagramSocket socket, InetAddress address, String nick, int port, int serverPort) {
		this.address = address;
		this.serverPort = serverPort;
		this.port = port;
		this.maxPort = port;

		this.socket = socket;
		this.packet.nickname(nick);
	}

	private void selfPublic() throws IOException {
		Utils.log("Server " + serverPort + " Porta " + port);
		this.packet.message("/conn " + port);
		this.sendAll();
	}
	
	@Override
	public void run() {
        String message;
		try {
			this.selfPublic();
			while (true) {
				System.out.print("> ");
				message = Utils.getInput();
				this.packet.message(message);
				this.resolve();
	        }
		} catch (IOException e) {
		}
	}
	
	public void sendAll() throws IOException {
		for (int i = serverPort; i <= maxPort; i++)
			if (i != port)
				this.send(i);
	}

	public void send(int port) throws IOException {
		byte[] buffer = packet.getBytes();
		DatagramPacket message = new DatagramPacket(
        		buffer, buffer.length, address, port);
		socket.send(message);
	}
	
	public void resolve() throws IOException {
		String[] parse = this.packet.message().split("[ ]+");
		String command = parse[0];
		String complement = this.packet.message().replace(command, "").trim();
		switch(command) {
		case "/rename":
			if (complement.isEmpty()) {
				System.out.println("Comando incorreto.");
				System.out.println("/rename [nickname]");
				return;
			}
			this.packet.nickname(complement);
			break;
		case "/help":
		case "/?":
			this.help();
			break;
		default:
			this.sendAll();
		}
	}

	private void help() {
		System.out.println("\tComandos do chat:");
//		System.out.println("\t/send \"[user]\" [message]: Manda uma mensagem para um usuário apenas.");
		System.out.println("\t/[re]name [nickname]: Troca o seu nickname.");
		System.out.println("\t/[h]elp: Mostra todos os comandos.");
	}
	
}
