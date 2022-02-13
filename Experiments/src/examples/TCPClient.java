package examples;/** * TCPClient: Cliente para conexao TCP * Descricao: Envia uma informacao ao servidor e recebe confirmações ECHO * Ao enviar "PARAR", a conexão é finalizada. */import java.io.IOException;import java.net.InetAddress;import java.net.Socket;import java.util.ArrayList;public class TCPClient {	public static void main(String args[]) {		Socket clientSocket = null; // socket do cliente		try {			/* Endereço e porta do servidor */			int serverPort = 6666;			InetAddress serverAddr = InetAddress.getByName("127.0.0.1");			/* conecta com o servidor */			clientSocket = new Socket(serverAddr, serverPort);			ArrayList<Thread> threads = new ArrayList<Thread>(2);			threads.add(new Thread(new Sender(clientSocket)));			threads.add(new Thread(new Receiver(clientSocket)));			threads.forEach(t -> {				t.setDaemon(true);				t.start();			});						boolean running = true;			while (running) {				Thread.sleep(200);				for (Thread t : threads) {					if (!t.isAlive()) {						running = false;					}				}			}		} catch (IOException ioe) {			System.out.println("IO:" + ioe.getMessage());		} catch (InterruptedException ie) {			System.out.println("IE: " + ie.getMessage());		} finally {			try {				clientSocket.close();			} catch (IOException ioe) {				System.out.println("IO: " + ioe);			}		}	}}