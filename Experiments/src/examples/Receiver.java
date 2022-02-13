package examples;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receiver implements Runnable {

	private DataInputStream in;

	public Receiver(Socket socket) throws IOException {
		this.in = new DataInputStream(socket.getInputStream());
	}

	@Override
	public void run() {
		String buffer = "";
		while (true) {
			try {
				buffer = in.readUTF(); /* aguarda o envio de dados */
			} catch (IOException ioe) {
				ioe.printStackTrace();
				break;
			}
			System.out.println("Recebeu: " + buffer);

			if (buffer.equals("PARAR")) {
				System.exit(0);
				break;
			}
		}
		try {
			this.in.close();
		} catch (IOException e) {
			System.out.println("Erro ao finalizar receiver");
		}
		System.out.println("Finalizando Receiver");
	}
}
