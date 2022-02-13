package uploader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.function.Function;

/**
 * Classe responsável por resolver requisições simples.
 * 
 * @author daniel
 */
public class Receiver implements Runnable {

	private PacketData data = new PacketData();

	private DatagramPacket request;
	private DatagramSocket socket;

	private Function<Receiver, ?> funct;
	public volatile boolean running = true;

	public Receiver(DatagramSocket socket, Function<Receiver, ?> funct) {
		this.socket = socket;
		this.funct = funct;

		byte[] buffer = new byte[1300];
		request = new DatagramPacket(buffer, buffer.length);
	}

	@Override
	public void run() {
		Utils.log("Inicialização da thread de confirmação.");
		try {
			while (running) {
				socket.receive(request);
				byte[] buffer = request.getData();
				data.fromBytes(buffer);
				
				this.funct.apply(this);
			}
		} catch (IOException e) {
			Utils.log("IOException " + e.getMessage());
		} finally {
			Utils.log("Finalização da thread de confirmação.");
		}
	}

}
