package uploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Tentativa frustante de tentar fazer uma tarefa sem usar muitas classes e
 * abstrações. Bom, resultou em muitas variáveis globais e relativamente
 * pouco código, porém muito denso e de difícil entendimento, assim como foi
 * feito na classe Client.
 * 
 * Classe responsável por armazenar arquivos enviados por alguma cliente.
 * O caminho, ip e porta são definidos de forma hardcoded. As mensagens de
 * log estão ativas.
 * 
 * @author daniel
 */
public class Server {
	
	static String path = "/home/daniel/shared/";
	static String ip = "127.0.0.1";
	static int serverPort = 7001;
	
	static PacketData data = new PacketData();
	static DatagramPacket request;
	
	static DatagramSocket socket;
	static OutputStream fileStream;
	
	static DatagramPacket finish() {
		InetAddress addr = request.getAddress();
		int port = request.getPort();
		return new DatagramPacket(new byte[1], 1, addr, port);
	}
	
	static void receive() throws IOException {
		socket.receive(request);
		byte[] buffer = request.getData();
		data.fromBytes(buffer);
		
		String filename = path + data.name();
		Utils.log("Download " + filename);
		File file = new File(filename);
		
		if (fileStream == null)
			fileStream = new FileOutputStream(file);
		
		Utils.log("ID: " + data.id());
		Utils.log("Piece: " + data.piece());
		Utils.log("Name: " + data.name());
		Utils.log("Data length: " + data.dataLength());
		Utils.log("Total length: " + data.length());
		if (data.check(buffer)) {
			fileStream.write(data.data());
			Utils.log(data.piece() + " OK!");
		} else {
			Utils.log(data.piece() + " FAIL!");
			socket.send(finish());
		}
		System.out.println();
	}
	
	public static void main(String[] args) throws IOException {
		byte[] buffer = new byte[1300];
		request = new DatagramPacket(buffer, buffer.length);
		socket = new DatagramSocket(serverPort);
        
		while (true) {
			do {
				receive();
			} while ((data.piece() != 0));
			socket.send(finish());
			fileStream.close();
			fileStream = null;
		}
		
	}
}
