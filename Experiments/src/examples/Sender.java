package examples;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Sender implements Runnable {

	private DataOutputStream out;

	public Sender(Socket socket) throws IOException {
		this.out = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		String buffer = "";
		Scanner reader = new Scanner(System.in);
		while (true) {
			buffer = reader.nextLine();
			if (buffer.equals("PARAR")) ;
			
			try {
				out.writeUTF(buffer); // envia a mensagem para o servidor
			} catch (IOException e) {
				break;
			}
		}
	    reader.close();
		System.out.println("Close Sender");
	}
	
}
