package uploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Tentativa frustante de tentar fazer uma tarefa sem usar muitas classes e
 * abstrações. Bom, resultou em muitas variáveis globais e relativamente
 * pouco código, porém muito denso e de difícil entendimento, assim como foi
 * feito na classe Server.
 * 
 * Essa classe ficou responsável por representar o cliente. O cliente deve
 * entrar com o endereço de IP do servidor e a porta. As configurações do
 * diretório compartilhado é feito de forma hardcoded. Então, o usuário
 * tem a possibilidade de escrever o nome do arquivo exitente no diretório
 * compartilhado para ser enviado para o servidor.
 * 
 * @author daniel
 */
public class Client {

	static String path = "/home/daniel/Downloads/";
	
	static String input = "";
	
	static PacketData data = new PacketData();

	static DatagramSocket socket;
	static InetAddress address;
	
	static int portServer;
	static byte[] inputBuffer;
	
	static long totalLength;
	static long totalSend;
	
	static int id = 1;
	
	static void progressBar(double val) {
		long round = Math.round(val / 2);
		System.out.print('[');
		for (int i = 0; i < 50; i++) {
			System.out.print(round < i ? '.' : '|');
		}
		System.out.println(']');
	}

	static void send(int i, int length, Receiver receiver) throws IOException {
		totalSend += length;
		data.piece(i).dataLength(length);
		byte[] buffer = data.getBytes();
		socket.send(new DatagramPacket(buffer, buffer.length, address, portServer));
		if (data.piece() != 0) { // End
			if (data.check(buffer)) {
				double percente = (double) totalSend * 100 / totalLength;
				System.out.printf("Enviado %.2f %%%n", percente);
				progressBar(percente);
				Utils.log(data.piece() + " OK!");
			} else {
				System.out.println("Falha ao enviar o arquivo!");
				Utils.log(data.piece() + " FAIL!");
				receiver.running = false;
			}
		}
	}
	
	static void send() throws IOException {
		Utils.showLog = false;
		String filename = path + input;
		Utils.log("Download " + filename);
		File file = new File(filename);
		
		// Verificando se o arquivo existe
		if (!file.exists()) {
			System.out.println("Arquivo não existe!");
			return;
		}
		
		totalLength = file.length();
		totalSend = 0;
		
		Receiver receiver = new Receiver(socket, (self) -> {
			self.running = false;
			return null;
		});
		
		Thread thread = new Thread(receiver);
		thread.start();

		InputStream fileStream = new FileInputStream(file);
		int length;
		byte[] buffer = data.data();
		data.id(id++).name(input);
		Utils.log(data.toString());
		for (int i = 1; (length = fileStream.read(buffer, 0, buffer.length)) > 0 && receiver.running; i++)
			send(i, length, receiver);
		send(0, 1, receiver);
		fileStream.close();
		receiver.running = false;
	}

	public static void main(String[] args) {
		System.out.print("Informe o IP do servidor: ");
		String ipServer = Utils.getInput(); 				// 127.0.0.1
		System.out.print("Informe a porta do servidor: ");
		portServer = Utils.getInt(); 						// 7001
		
		try {
			address = InetAddress.getByName(ipServer);
			socket = new DatagramSocket(7002);

			while (!input.equals("exit")) {
				System.out.println("Digite o nome de algum arquivo do seu diretório compartilhado [" + path + "]");
				System.out.print(">");
				input = Utils.getInput();
				Utils.log("Entrada: " + input);
				
				if (!input.equals("exit"))
					send();
			}
			socket.close();
		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
}
