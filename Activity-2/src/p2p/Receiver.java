package p2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Classe responsável por receber mensagens dos outros usuários
 * e mostrar para o usuário atual.
 * 
 * @author daniel
 */
public class Receiver implements Runnable {

	private DatagramSocket socket;
	private Packet packet = new Packet();
	private Sender sender;

	public Receiver(DatagramSocket socket, Sender sender) {
		this.socket = socket;
		this.sender = sender;
	}
	
	public void printResponse() {
		System.out.println("  [" + packet.nickname() + "]: " + packet.message());
		System.out.print("> ");
	}
	
	@Override
	public void run() {
		byte[] buffer = new byte[512];
		DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        try {
        	while (true) {
        		socket.receive(response);
        		Utils.log(response.getAddress().toString());
        		this.packet.fromBytes(buffer);
        		this.resolve();
        	}
		} catch (IOException e) {
			Utils.log("Erro no Receiver.");
		}
	}

	private void resolve() {
		Utils.log("Pacote: " + packet);
		String[] parse = packet.message().split("[ ]+");
		String command = parse[0];
		String complement = this.packet.message().replace(command, "").trim();
		switch (command) {
		case "/conn":
			this.sender.maxPort = Integer.parseInt(complement);
			break;
		case "/rename":
			break;
		default:
			this.printResponse();
		}
	}
	
}
