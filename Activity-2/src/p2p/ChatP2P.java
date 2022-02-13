package p2p;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Classe responsável por inicializar as classes Sender e
 * Receiver, além de criar um socket para transmissão.
 * 
 * @author daniel
 */
public class ChatP2P implements Runnable {

	int serverPort = 7001;
	int port = 7000;
	DatagramSocket socket;
	InetAddress address;
	String nickname;
	
	public ChatP2P setServerAddress(String ip) throws UnknownHostException {
        address = InetAddress.getByName(ip);
		return this;
	}

	public ChatP2P setNickname(String nickname) {
		Utils.log("Nickname: " + nickname);
		this.nickname = nickname;
		return this;
	}
	
	void connect() {
		boolean connected = false;
		while (!connected) {
			try {
				socket = new DatagramSocket(++port);
				connected = true;
			} catch (SocketException e) {
				// Ignore ...
			}
		}
		Utils.log("Socket connectado na porta " + port);
	}
	
	@Override
	public void run() {
		this.connect();
		
		Sender sender = new Sender(socket, address, nickname, port, serverPort);
		Receiver receiver = new Receiver(socket, sender);
		new Thread(sender).start();
		new Thread(receiver).run();
	}

}
