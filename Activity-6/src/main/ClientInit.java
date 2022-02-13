package main;

import java.awt.EventQueue;

import core.ClientWorker;
import view.MainFrame;

/**
 * Classe de inicialização da classe de teste ClientWorker.
 * 
 * @author daniel e mara
 */
public class ClientInit {
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 5005;
		new ClientWorker(host, port).run();
	}
}
